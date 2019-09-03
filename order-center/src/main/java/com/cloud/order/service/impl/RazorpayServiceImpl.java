package com.cloud.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.app.service.AppLoanService;
import com.cloud.common.component.utils.DateUtil;
import com.cloud.common.config.CommonConfig;
import com.cloud.common.enums.LoanChannelEnum;
import com.cloud.common.enums.NotificationTemplateTypeEnum;
import com.cloud.common.enums.OperatorTypeEnum;
import com.cloud.common.mq.sender.NotificationSender;
import com.cloud.common.utils.RazorpaySignatureUtils;
import com.cloud.model.common.PayStatus;
import com.cloud.model.notification.NotificationDto;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.model.pay.UniversalAck;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.model.razorpay.RazorpayPayout;
import com.cloud.order.constant.LoanType;
import com.cloud.order.dao.*;
import com.cloud.order.handle.HandlePayment;
import com.cloud.order.model.razorpay.RazorpayAccountValidation;
import com.cloud.order.payment.facility.PaymentFacility;
import com.cloud.order.service.RazorpayService;
import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.cloud.service.feign.pay.PayClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RazorpayServiceImpl implements RazorpayService {

    @Autowired
    private PayClient payClient;

    @Autowired
    private FinanceLoanDao financeLoanDao;

    @Autowired
    private RazorpayDao razorpayDao;

    @Autowired
    private AppLoanService appLoanService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private KudosDao kudosDao;

    @Autowired
    private PaymentFacility paymentFacility;

    @Autowired
    private HandlePayment handlePayment;

    @Autowired
    private RazorpayAccountValidationDao razorpayAccountValidationDao;

    @Autowired
    private NotificationSender notificationSender;

    @Autowired(required = false)
    private RedisUtil redisUtil;

    @Override
    public void handleWebhook(String razorpayEventResponse, String receivedSignature) {
        String payload = null;
        String event = null;
        try {
            log.info("接收到payoutWebHook回调的请求内容:{}", razorpayEventResponse);
            // 根据secret和webhook_body签名
            String signature = RazorpaySignatureUtils.getHash(razorpayEventResponse, "Moneed1234");
            log.info("生成webhook的哈希签名:{}", signature);
            RazorpayPayout razorpayPayout = null;
            if (redisUtil.get(signature) == null) {
                // 将签名存入redis 作为请求是否重复的判断依据
                redisUtil.setKeyExpire(signature, "OK",  60);
                log.info("接收到的webhook的哈希签名:{}", receivedSignature);
                // 校验签名
                if (RazorpaySignatureUtils.verifySignature(razorpayEventResponse, receivedSignature, "Moneed1234")) {
                    JSONObject jSONObject = JSONObject.parseObject(razorpayEventResponse);
                    event = jSONObject.getString("event");
                    payload = jSONObject.getString("payload");

                    log.info("接收到payoutWebHook回调事件:{}", event);
                    log.info("接收到payoutWebHook回调实体:{}", payload);

                    //还款回调只处理失败事件
                    if (event.contains("failed")) {
                        appLoanService.handleFail(razorpayEventResponse);
                        return;
                    }
                    //银行卡校验通知
                    if (event.contains("validation")) {
                        RazorpayAccountValidation razorpayAccountValidation = parseRazorpayAccountValidation(payload);
                        if (razorpayAccountValidation != null) {
                            loanAfterValidation(razorpayAccountValidation);
                        } else {
                            log.info("payload无法解析出来RazorpayAccountValidation对象");
                        }
                        return;
                    }
                    /*if (event.contains("captured")) {
                        appLoanService.handleCapture(razorpayEventResponse);
                        return;
                    }*/
                    //处理了授权 就没必要处理cap1了
                    if (event.contains("authorized")) {
                        appLoanService.handleAuthorized(razorpayEventResponse);
                        return;
                    }

                    if (!StringUtils.isEmpty(event) && !StringUtils.isEmpty(payload)) {
                        //放款回调事件
                        if (event.contains("processed") || (event.contains("reversed"))) {
                            razorpayPayout = parseRazorpayPayout(payload);
                            if (razorpayPayout != null) {
                                if (razorpayDao.updateRazorpayPayout(razorpayPayout) < 1) {
                                    log.info(">>>>>>>>>>>>>>>>>>处理razorpay回调失败，根据解析出RazorpayPayout对象未更新到对应数据库记录的状态");
                                    return;
                                }
                            } else {
                                log.info(">>>>>>>>>>>>>>>>>>处理razorpay回调失败，接收到的请求response无法解析出RazorpayPayout对象");
                                return;
                            }
                            if (StringUtils.isBlank(razorpayPayout.getReferenceId())) {
                                log.info(">>>>>>>>>>>>>>>>>>处理razorpay回调失败，没有reference_id");
                                return;
                            }
							/*razorpayPayout = razorpayDao.selectOne(razorpayPayout);
							if(razorpayPayout == null){
								log.info(">>>>>>>>>>>>>>>>>>处理razorpay回调失败，根据解析出RazorpayPayout对象的id无法匹配一条数据库记录");
								return;
							}*/
                            //根据RazorpayPayout的referenceId查找出一条处理中的放款申请记录
                            //对于一笔申请 处理中的放款交易记录最多只有一条（包括放款申请和放款重提）
                            Map<String, Object> params = new HashMap<String, Object>();
                            //reference_id是放款记录的orderNo
                            params.put("orderNo", razorpayPayout.getReferenceId());
                            params.put("payStatus", PayStatus.UNDERWAY.num);
                            int[] loanTypes = new int[]{LoanType.LOANAPPLY.num, LoanType.RELOAN.num};
                            params.put("loanTypes", loanTypes);
                            params.put("handler", OperatorTypeEnum.PLATFORM.getCode());
                            FinancePayLogModel financePayLogModel = financeLoanDao.getFinancePayLogModelByOrderNo(params);
                            if (financePayLogModel != null) {
                                /**
                                 * 其实这里用放款记录处理更方便 因为一个申请订单
                                 * 放款记录只有一条 无需处理各种类型
                                 * 而交易记录要区分各种类型
                                 *
                                 * 但是如果这里使用放款记录来处理  在更新相应还款数据的时候还是需要
                                 * 使用类型查询放款交易记录  所以这里面无论怎么处理其实不存在哪一种更省事
                                 */
                                UniversalAck universalAck = new UniversalAck();
                                universalAck.setFailureReason(razorpayPayout.getFailureReason());
                                universalAck.setOrderNo(razorpayPayout.getReferenceId());
                                universalAck.setStatus(razorpayPayout.getStatus());
                                universalAck.setChannel(LoanChannelEnum.RAZORPAY.getCode());
                                payClient.afterPaymentHandle(financePayLogModel.getSerialNumber(), universalAck);
                            } else {
                                log.info(">>>>>>>>>>>>>>>>>>处理razorpay回调失败，根据匹配的库记录razorpay_payout的reference_id:{}无法匹配一条处理中的交易记录", razorpayPayout.getReferenceId());
                            }
                        }
                    }
                }
            } else {
                log.info("重复的请求,不作处理!");
            }
        } catch (Exception e) {
            log.info(">>>>>>>>>>>>>>>>>>处理razorpay回调事件：{}失败", event);
            e.printStackTrace();
        }
    }

    @Override
    public void validationFailMsg(FinanceLoanModel financeLoanModel) {
        if (financeLoanModel.getModifyBankcardCount() == 0) {
            financeLoanModel.setNoticeModifyBankcardDate(new Date());
        }
        Date noticeModifyBankcardDate = financeLoanModel.getNoticeModifyBankcardDate();
        Map<String, Object> map = new HashMap<>();
        log.info("订单{}失败短信通知,已经修改了的次数：{}", financeLoanModel.getOrderNo(), financeLoanModel.getModifyBankcardCount());
        if (financeLoanModel.getModifyBankcardCount() < CommonConfig.MAX_MODIFY_BANKCARD_COUNT) {
            //三天内修改好
            Date bankCardClosingModifyDate = DateUtil.getDate(noticeModifyBankcardDate, CommonConfig.MAX_MODIFY_BANKCARD_PERIOD);
            map.put("bankCardClosingModifyDate", bankCardClosingModifyDate);
            NotificationDto notificationDto = NotificationDto.builder()
                    .templateType(NotificationTemplateTypeEnum.BANKCARD_VALIDATION_FAIL.getCode())
                    .userId(financeLoanModel.getCustomerNo())
                    .templateOtherParams(map)
                    .build();
            notificationSender.send(notificationDto);
        } else {
            log.info("订单{}失败短信通知,已经修改了的次数 超时短信", financeLoanModel.getOrderNo());
            //七天后开放重新申请
            Date bankCardClosingModifyDate = DateUtil.getDate(noticeModifyBankcardDate, CommonConfig.REAPPLYPERIOD);
            map.put("openApplyDate", bankCardClosingModifyDate);
            NotificationDto notificationDto = NotificationDto.builder()
                    .templateType(NotificationTemplateTypeEnum.BANKCARD_VALIDATION_FAIL_TIMEOUT.getCode())
                    .userId(financeLoanModel.getCustomerNo())
                    .templateOtherParams(map)
                    .build();
            notificationSender.send(notificationDto);
        }
    }

    //银行卡校验通过再放款
    public void loanAfterValidation(RazorpayAccountValidation razorpayAccountValidation) throws Exception {
        log.info(">>>>>>>>>>>>>>>>开始处理校验银行卡后的回调");
        RazorpayAccountValidation existOne = razorpayAccountValidationDao.getByFavId(razorpayAccountValidation.getFavId());
        if (existOne == null) {
            log.info(">>>>>>>>>>>>>>>>favId:{},银行卡回调没有在razorpay_account_validation表中查到对应的记录", razorpayAccountValidation.getFavId());
            return;
        }
        Map<String, Object> params = new HashMap<String, Object>();
        //reference_id是放款记录的orderNo
        params.put("orderNo", existOne.getOrderNo());
        //银行卡校验中的交易记录
        params.put("payStatus", PayStatus.BANKCARD_VALIDATING.num);
        int[] loanTypes = new int[]{LoanType.LOANAPPLY.num, LoanType.RELOAN.num};
        params.put("loanTypes", loanTypes);
        params.put("handler", OperatorTypeEnum.PLATFORM.getCode());
        FinancePayLogModel financePayLogModel = financeLoanDao.getFinancePayLogModelByOrderNo(params);
        FinanceLoanModel financeLoanModel = financeLoanDao.getFinanceLoanModelByOrderNo(financePayLogModel.getOrderNo(),
                PayStatus.BANKCARD_VALIDATING.num);
        if (financePayLogModel == null || financeLoanModel == null) {
            log.info(">>>>>>>>>>>>>>>>orderNo:{},对应的交易记录或者放款记录异常", existOne.getOrderNo());
            return;
        }

        //如果是kudos_2 则手动批量放款
        boolean loanTimely = paymentFacility.isLoanTimely(financeLoanModel.getLoanChannel());

        PayStatus payStatus = null;
        if ("active".equals(razorpayAccountValidation.getStatus())) {
            if (!loanTimely) {
                payStatus = PayStatus.WAIT_IN_KUDOS;
            } else {
                payStatus = paymentFacility.handle(financeLoanModel);
            }
        } else {
            payStatus = PayStatus.FAILURE;
            financeLoanModel.setFailureReason(CommonConfig.BANKCARD_ERROR_FLAG + " BAD_REQUEST_ERROR:razorpay校验银行卡无效");
        }

        razorpayAccountValidationDao.updateRazorpayAccountValidation(existOne.getOrderNo(), existOne.getFavId(), razorpayAccountValidation.getStatus());
        handlePayment.savePayStatus(payStatus, financeLoanModel, financePayLogModel, params);
    }

    //接收到的payload解析出来RazorpayPayout对象
    public RazorpayPayout parseRazorpayPayout(String payload) {
        RazorpayPayout razorpayPayout = null;
        try {
            JSONObject payloadJSONObject = JSONObject.parseObject(payload);
            if (payloadJSONObject == null) {
                return null;
            }
            String payout = payloadJSONObject.getString("payout");
            JSONObject payoutJSONObject = JSONObject.parseObject(payout);
            if (payoutJSONObject == null) {
                return null;
            }
            String entity = payoutJSONObject.getString("entity");
            JSONObject entityJSONObject = JSONObject.parseObject(entity);
            if (entityJSONObject == null) {
                return null;
            }
            razorpayPayout = JSONObject.toJavaObject(entityJSONObject, RazorpayPayout.class);
            razorpayPayout.setReferenceId(entityJSONObject.getString("reference_id"));
            razorpayPayout.setFailureReason(entityJSONObject.getString("failure_reason"));
        } catch (Exception e) {
            return null;
        }
        return razorpayPayout;
    }

    //接收到的payload解析出来RazorpayAccountValidation对象
    public RazorpayAccountValidation parseRazorpayAccountValidation(String payload) {
        RazorpayAccountValidation razorpayAccountValidation = new RazorpayAccountValidation();
        try {
            JSONObject payloadJSONObject = JSONObject.parseObject(payload);
            String validation = payloadJSONObject.getString("fund_account.validation");
            JSONObject validationJO = JSONObject.parseObject(validation);
            String entity = validationJO.getString("entity");
            JSONObject entityJO = JSONObject.parseObject(entity);
            if (entityJO == null) {
                return null;
            }
            razorpayAccountValidation.setFavId(entityJO.getString("id"));

            String results = entityJO.getString("results");
            JSONObject resultsJO = JSONObject.parseObject(results);
            razorpayAccountValidation.setStatus(resultsJO.getString("account_status"));
        } catch (Exception e) {
            log.info("fund_account.validation 无效的回调信息");
            e.printStackTrace();
            return null;
        }
        return razorpayAccountValidation;
    }

    //当前日期--
    public String toDate() {
        SimpleDateFormat simdf = new SimpleDateFormat("MM-dd-yyyy");
        Calendar cal = Calendar.getInstance();
        return simdf.format(cal.getTime());
    }
}
