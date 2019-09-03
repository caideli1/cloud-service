package com.cloud.app.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.app.dao.AppLoanDao;
import com.cloud.app.dao.PaymentIssueDao;
import com.cloud.app.model.PaymentIssue;
import com.cloud.app.service.AppLoanService;
import com.cloud.common.component.utils.DateUtil;
import com.cloud.common.config.CommonConfig;
import com.cloud.common.dto.JsonResult;
import com.cloud.model.appUser.AppLoanVo;
import com.cloud.model.appUser.AppUserLoanInfo;
import com.cloud.model.appUser.LoanCalculate;
import com.cloud.model.common.OrderStatus;
import com.cloud.model.common.PayStatus;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.model.product.InterestPenaltyModel;
import com.cloud.model.product.LoanProductModel;
import com.cloud.model.product.RateType;
import com.cloud.model.product.constant.ProductConstants;
import com.cloud.model.razorpay.RazorpayEventEntity;
import com.cloud.model.razorpay.RazorpayPaymentEntity;
import com.cloud.model.razorpay.constants.RazorpayPaymentStatusEnum;
import com.cloud.order.dao.FinanceLoanDao;
import com.cloud.order.dao.OrderDao;
import com.cloud.order.dao.ProductDao;
import com.cloud.order.model.FinanceRepayModel;
import com.cloud.order.model.RepayMentRspVo;
import com.cloud.order.model.resp.AppMyCurrentLoanInfoRes;
import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.cloud.service.feign.pay.PayClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AppLoanServiceImpl implements AppLoanService {
    @Autowired
    private AppLoanDao appLoanDao;

    @Autowired
    private FinanceLoanDao financeLoanDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired(required = false)
    private RazorpayClient razorpayClient;

    @Autowired(required = false)
    private PayClient payClient;

    @Autowired
    private PaymentIssueDao paymentIssueDao;

    @Autowired(required = false)
    private RedisUtil redisUtil;


    /**
     * 分页查询结局列表
     *
     * @param params userId 用户ID pageNo pageSize status 借款状态
     * @return
     */
    @Override
    public PageInfo<AppLoanVo> getLoansById(Map<String, Object> params) {
        Integer page = MapUtils.getInteger(params, "pageNo");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        PageHelper.startPage(page, pageSize);

        List<AppUserLoanInfo> appUserLoanInfos = appLoanDao.selectAllLoansById(params);
        List<AppLoanVo> loanVos = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(appUserLoanInfos)) {
            for (AppUserLoanInfo userLoan : appUserLoanInfos) {
                AppLoanVo vo = new AppLoanVo();
                vo.setBorrowAmount(userLoan.getBorrowAmount());
                if (userLoan.getLoanStatus() == 4) {
                    userLoan.setLoanStatus(1);
                }
                vo.setLoanStatus(userLoan.getLoanStatus());

                vo.setLoanNumber(userLoan.getLoanNumber());
                vo.setTerm(userLoan.getTerm());
                vo.setOverdueStatus(userLoan.getOverdueStatus());
                if (userLoan.getOverdueStatus() == 1) {
                    vo.setLoanEndDate(
                            DateUtil.getStringDate(userLoan.getExtensionDueDate(), DateUtil.DateFormat2));
                    if (ProductConstants.FINISH.equals(userLoan.getLoanStatus())) {
                        vo.setOverdueStatus(0);
                    }
                } else {
                    vo.setLoanEndDate(
                            DateUtil.getStringDate(userLoan.getLoanEndDate(), DateUtil.DateFormat2));
                }
                vo.setLoanStartDate(
                        DateUtil.getStringDate(userLoan.getLoanStartDate(), DateUtil.DateFormat2));
                loanVos.add(vo);
            }
        }

        PageInfo<AppLoanVo> pageInfo = new PageInfo<>(loanVos);
        return pageInfo;
    }

    /**
     * 查询借据详情
     *
     * @param loanId 借据编号
     * @return
     */
    @Override
    public AppLoanVo getLoanInfoById(String loanId) {
        AppUserLoanInfo userLoanInfo = appLoanDao.selectLoanInfoById(loanId);
        AppLoanVo vo = new AppLoanVo();
        if (null != userLoanInfo) {
            vo.setUserId(userLoanInfo.getUserId());
            vo.setProductId(userLoanInfo.getProductId());
            vo.setBorrowAmount(userLoanInfo.getBorrowAmount());
            if (userLoanInfo.getLoanStatus() == 4) {
                userLoanInfo.setLoanStatus(1);
            }
            vo.setLoanStatus(userLoanInfo.getLoanStatus());
            vo.setLoanNumber(userLoanInfo.getLoanNumber());
            vo.setTerm(userLoanInfo.getTerm());
            vo.setOverdueStatus(userLoanInfo.getOverdueStatus());
            vo.setCurrTotalRepayAmount(userLoanInfo.getBorrowAmount().add(userLoanInfo.getLateCharge()));
            if (userLoanInfo.getOverdueStatus() == 1) {
                vo.setLoanEndDate(
                        DateUtil.getStringDate(userLoanInfo.getExtensionDueDate(), DateUtil.DateFormat2));
                if (ProductConstants.FINISH.equals(userLoanInfo.getLoanStatus())) {
                    vo.setOverdueStatus(0);
                }
            } else {
                vo.setLoanEndDate(
                        DateUtil.getStringDate(userLoanInfo.getLoanEndDate(), DateUtil.DateFormat2));
            }
            vo.setLoanStartDate(
                    DateUtil.getStringDate(userLoanInfo.getLoanStartDate(), DateUtil.DateFormat2));
        }

        return vo;
    }

    private JsonResult verificationLoan(AppUserLoanInfo userLoanInfo) {
        if (null == userLoanInfo) {
            return JsonResult.errorMsg("借据不存在");
        }
        if (ProductConstants.NOT_ACTIVATED.equals(userLoanInfo.getLoanStatus())) {
            return JsonResult.errorMsg("借据未激活!");
        }
        if (ProductConstants.FINISH.equals(userLoanInfo.getLoanStatus())) {
            return JsonResult.errorMsg("账单已结清");
        }
        return JsonResult.ok();
    }

    @Override
    @Transactional
    public void handleFail(String razorpayEventResponse) {
        RazorpayEventEntity razorpayEventEntity = JSONObject.parseObject(razorpayEventResponse,
                RazorpayEventEntity.class);
        RazorpayPaymentEntity razorpayPaymentEntity = razorpayEventEntity.getPayload().getPayment().getEntity();
        RazorpayPaymentEntity.NotesBean notes = razorpayPaymentEntity.getNotes();
        // notes 中 orderid 属性是写在jsp页面中传递的参数，是不为空的。   为流水号 唯一标识
        String orderId = notes.getOrderid();
        FinancePayLogModel financePayLogModel = financeLoanDao.getFinancePayLogModelBySerialNumber(orderId);
        if (financePayLogModel == null) {
            log.info("不存在的交易记录流水号: {}", orderId);
            return;
        }
        if (financePayLogModel.getPayStatus() != PayStatus.WAIT.num) {
            return;
        }
        payClient.handPaymentFail(financePayLogModel, razorpayPaymentEntity.getError_description());
    }

    //处理capture回调
    @Override
    public void handleAuthorized(String razorpayEventResponse) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>开始处理authorized");
        RazorpayEventEntity razorpayEventEntity = JSONObject.parseObject(razorpayEventResponse,
                RazorpayEventEntity.class);
        RazorpayPaymentEntity razorpayPaymentEntity = razorpayEventEntity.getPayload().getPayment().getEntity();
        RazorpayPaymentEntity.NotesBean notes = razorpayPaymentEntity.getNotes();
        // notes 中 orderid 属性是写在jsp页面中传递的参数，是不为空的。   为流水号 唯一标识
        String orderId = notes.getOrderid();

        //6.24 服务器时间修改为孟买时间    但是因为razorpay返回的是北京时间
        //razorpay放还款的时间都是北京时间
        //这里应该用服务器时间
        //如果继续使用北京时间  可能会导致还款类型错误 例如正常还款变成了逾期还款 但是没有罚息的情况
        Date transactionTime = new Date();
        log.info(">>>>>>>>>>>>>>>>>>>>>>准备进入capture");
        capture(transactionTime, orderId, new BigDecimal(razorpayPaymentEntity.getAmount()), razorpayPaymentEntity.getId());
    }

    @Override
    public JsonResult capture(Date transactionTime, String serialNumber, BigDecimal amount, String razorpay_payment_id) {
        if (redisUtil.isLock("capture:" + serialNumber)) {
            return JsonResult.ok();
        }
        FinancePayLogModel financePayLogModel = financeLoanDao.getFinancePayLogModelBySerialNumber(serialNumber);
        if (financePayLogModel == null) {
            log.info("不存在的交易记录流水号: {}", serialNumber);
            return JsonResult.errorMsg("Order not found.");
        }
        if (financePayLogModel.getPayStatus() == PayStatus.SUCCESS.num ||
                (financePayLogModel.getPayStatus() == PayStatus.FAILURE.num && financePayLogModel.getFailureReason().contains("moneed:"))) {
            log.info("交易记录已经被处理 流水号: {}", serialNumber);
            return JsonResult.errorMsg("System error");
        }
        String orderNo = financePayLogModel.getOrderNo();
        try {
            org.json.JSONObject options = new org.json.JSONObject();
            options.put("amount", amount);
            log.info("【orderNo:{}】开始调用capture");
            Payment payment = razorpayClient.Payments.capture(razorpay_payment_id, options);
            log.info("【orderNo:{}】完成调用capture");
            org.json.JSONObject paymentJson = payment.toJson();
            String status = paymentJson.getString("status");
            if (StringUtils.equalsIgnoreCase(RazorpayPaymentStatusEnum.CAPTURED.value, status)) {
                log.info("支付授权成功，capture成功，开始【orderNo:{}】还款成功后的处理", orderNo);
                String transactionTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(transactionTime);
                payClient.handPaymentSuccess(financePayLogModel, transactionTimeStr);
                log.info("支付授权成功，capture成功，【orderNo:{}】还款成功后的处理完成", orderNo);
                return JsonResult.ok();
            } else {
                log.warn("moneed:支付授权成功，capture失败, orderNo:{}", orderNo);
                //capture处理失败暂不做处理，交给定时任务处理
                //appLoanService.handPaymentFail(financePayLogModel, "moneed:支付授权成功，capture失败！");
                return JsonResult.ok("Your payment is being processed!");
            }
        } catch (RazorpayException e) {
            log.info("【orderNo:{}】capture失败");
            // 还款异常处理
            log.info("支付授权成功，【orderNo:{}】还款失败, e:{}", orderNo, e);
            payClient.handPaymentFail(financePayLogModel, "moneed:razorpay capture failure");
            return JsonResult.errorMsg("Razorpay capture failure");
        } catch (Exception e) {
            log.info("支付授权成功，capture成功，【orderNo:{}】还款成功后的处理异常, e:{}", orderNo, e);
            payClient.handPaymentFail(financePayLogModel, "moneed:System error！");
            return JsonResult.errorMsg("System error");
        }
    }

    @Override
    public PageInfo<RepayMentRspVo> queryRepayById(Map<String, Object> params) {
        Integer page = MapUtils.getInteger(params, "pageNo");
        Integer pageSize = MapUtils.getInteger(params, "pageSize");
        params.put("loanType", 6);
        PageHelper.startPage(page, pageSize);

        List<FinanceRepayModel> repayModels = appLoanDao.queryRepayById(params);
        List<RepayMentRspVo> repayMentRspVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(repayModels)) {
            for (FinanceRepayModel repay : repayModels) {
                AppUserLoanInfo loanInfo = appLoanDao.selectLoanInfoById(repay.getLoanNumber());
                RepayMentRspVo rspVo = new RepayMentRspVo();

                if (PayStatus.SUCCESS.num == repay.getPayStatus().intValue()
                        && OrderStatus.SUCCESS.num == repay.getOrderStatus().intValue()) {
                    // 已还款
                    rspVo.setRepayMentStatus(1);
                    repayMentRspVos.add(rspVo);
                } else if (PayStatus.FAILURE.num == repay.getPayStatus().intValue()
                        && OrderStatus.FAILURE.num == repay.getOrderStatus().intValue()) {
                    // 还款失败
                    rspVo.setRepayMentStatus(2);
                    repayMentRspVos.add(rspVo);
                }

                if (rspVo.getRepayMentStatus() != 0) {

                    if (null != loanInfo && null != rspVo) {
                        rspVo.setLoanStartDate(
                                DateUtil.getStringDate(loanInfo.getLoanStartDate(), DateUtil.DateFormat2));
                    }
                    if (null != repay.getPayDate() && null != rspVo) {
                        rspVo.setRepayMentTime(DateUtil.getStringDate(repay.getPayDate(), DateUtil.DateFormat2));
                    }
                    rspVo.setAmt(repay.getActualAmount().toString());
                }

            }
        }
        PageInfo<RepayMentRspVo> pageInfo = new PageInfo<>(repayMentRspVos);
        return pageInfo;
    }

    @Override
    public JsonResult deferredCalc(String loanNum) {
        AppUserLoanInfo userLoanInfo = appLoanDao.selectLoanInfoById(loanNum);
        JsonResult js = this.verificationLoan(userLoanInfo);
        if (js.getCode() == 500) {
            throw new IllegalArgumentException("订单已结清");
        }
        LoanProductModel model = productDao.getLoanProductById(userLoanInfo.getProductId());
        //罚息表Info
        InterestPenaltyModel penaltyModel = productDao.findById(model.getPenaltyInterestId());

        LoanCalculate calculate = new LoanCalculate();
        BigDecimal repayAmount = userLoanInfo.getShouldRepayAmount();
        // 展期利率
        BigDecimal deferRate = productDao.getInterestByType(CommonConfig.EXTENSION_INTEREST);

        // 增值税
        BigDecimal addedRate = productDao.getGstByProductId(userLoanInfo.getProductId());

        // 逾期金额
        BigDecimal lateChargeAmt = userLoanInfo.getLateCharge() == null ? BigDecimal.ZERO : userLoanInfo.getLateCharge();

        calculate.setLateChargeAmt(lateChargeAmt);
        // 总服务利率
        BigDecimal totalRate = BigDecimal.ZERO;
//        List<RateType> typeList = productDao.rateTypeListDistinctName();
        for (RateType rate : model.getRateTypeList()) {
            totalRate = totalRate.add(rate.getRate());
        }

        BigDecimal interestFee = repayAmount.multiply(deferRate.multiply(new BigDecimal(model.getTerm())));
        BigDecimal serviceFee = repayAmount.multiply(totalRate);

        BigDecimal totalFree = interestFee.add(serviceFee)
                .add(serviceFee.multiply(addedRate)).add(lateChargeAmt);

        calculate.setBorrowAmount(userLoanInfo.getBorrowAmount());
        //罚息利率
        calculate.setOverDueRate(penaltyModel.getRate());
        //借款周期
        calculate.setLoanSpan(model.getTerm());
        //到期时间
        if (lateChargeAmt.compareTo(BigDecimal.ZERO) > 0 && ProductConstants.OVERDUE.equals(userLoanInfo.getLoanStatus())) {
            calculate.setDueDate(DateUtil.getStringDate(DateUtil.getDate(DateUtil.getDate(), model.getTerm()), DateUtil.DateFormat2));
            //展期中再申请展期
        } else if (userLoanInfo.getOverdueStatus() == 1) {
            calculate.setDueDate(DateUtil.getStringDate(DateUtil.getDate(userLoanInfo.getExtensionDueDate(), model.getTerm()), DateUtil.DateFormat2));
        } else {
            calculate.setDueDate(DateUtil.getStringDate(DateUtil.getDate(userLoanInfo.getLoanEndDate(), model.getTerm()), DateUtil.DateFormat2));
        }
        //利息费
        calculate.setInterestFee(interestFee);
        //税费
        calculate.setGstFee(serviceFee.multiply(addedRate));
        //服务费
        calculate.setServiceFee(serviceFee);
        //总费用
        calculate.setTotalFee(totalFree);


        return JsonResult.ok(calculate);
    }

    @Override
    public AppMyCurrentLoanInfoRes appMyCurrentOrderInfo(Long userId) {
        return orderDao.appQueryCurrentOrderInfo(userId);
    }

    @Override
    public LoanCalculate appExtensionApplyInfo(String loanNumber) {
        LoanCalculate loanCalculate = (LoanCalculate) deferredCalc(loanNumber).getData();
        return loanCalculate;
    }

    @Override
    public void appAddPaymentIssue(PaymentIssue req) {
        if (StringUtils.isBlank(req.getLoanNumber()) || (StringUtils.isBlank(req.getIssue()) && StringUtils.isBlank(req.getDetailMsg())) ||
                StringUtils.isBlank(req.getPhone()) || StringUtils.isBlank(req.getContactPhone())) {
            throw new IllegalArgumentException("缺少参数");
        }
        paymentIssueDao.insert(req);
    }
}
