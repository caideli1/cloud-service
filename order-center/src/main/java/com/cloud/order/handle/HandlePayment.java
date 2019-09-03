package com.cloud.order.handle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.app.config.RazorpayConfig;
import com.cloud.common.config.CommonConfig;
import com.cloud.common.utils.RazorpaySignatureUtils;
import com.cloud.common.utils.StringUtils;
import com.cloud.model.common.OrderStatus;
import com.cloud.model.common.PayStatus;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.model.user.UserBankCard;
import com.cloud.order.dao.FinanceLoanDao;
import com.cloud.order.dao.RazorpayAccountValidationDao;
import com.cloud.order.model.razorpay.RazorpayAccountValidation;
import com.cloud.order.payment.facility.PaymentFacility;
import com.cloud.order.service.RazorpayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hasee on 2019/6/20.
 */
@Component
@Slf4j
public class HandlePayment {

    @Autowired
    private RazorpayAccountValidationDao razorpayAccountValidationDao;

    @Autowired
    private FinanceLoanDao loanDao;

    @Autowired
    private PaymentFacility paymentFacility;

    @Autowired
    private RazorpayService razorpayService;

    @Autowired(required = false)
    private RazorpayConfig razorpayConfig;

    //校验银行卡
    public void checkUserBankCard(FinanceLoanModel loanModel, boolean loanTimely, Map<String, Object> result) throws Exception {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单-校验银行卡开始");
        try {
            UserBankCard userBankCard = (UserBankCard) result.get("userBankCard");
            if(userBankCard == null){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                result.put("judgment", "failure:没有获取到银行卡信息");
                throw new Exception("failure:没有获取到银行卡信息");
            }
            //接口开放
            if (CommonConfig.VALIDATE_BANKCARD_OPEN) {
                log.info("银行卡校验open>>>>>");
                //没有放过款就校验银行卡 0:未启动
                if (userBankCard.getStatus() == 0) {
                    result.put("waiting", PayStatus.BANKCARD_VALIDATING.name());
                }
                if (userBankCard.getStatus() == 1 && !loanTimely) {
                    //等待批量放款
                    result.put("waiting", PayStatus.WAIT_IN_KUDOS.name());
                    return;
                }
                if (userBankCard.getStatus() == 1 && loanTimely) {
                    return;
                }
            } else {
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>银行卡校验未开放");
                return;
            }
            RazorpayAccountValidation razorpayAccountValidation = new RazorpayAccountValidation();
            razorpayAccountValidation.setUserId(loanModel.getCustomerNo());
            razorpayAccountValidation.setUserBankcardId(userBankCard.getId());
            razorpayAccountValidation.setOrderNo(loanModel.getOrderNo());
            JSONObject validationJO = callRazorpayValidation(userBankCard);
            if (StringUtils.isBlank(validationJO.getString("error"))) {
                String fundAccount = validationJO.getString("fund_account");
                JSONObject fundAccountJO = JSONObject.parseObject(fundAccount);
                razorpayAccountValidation.setFavId(fundAccountJO.getString("id"));
                razorpayAccountValidation.setStatus(validationJO.getString("status"));
                razorpayAccountValidationDao.saveRazorpayAccountValidation(razorpayAccountValidation);
            } else {
                JSONObject errorJO = JSONObject.parseObject(validationJO.getString("error"));
                //服务器错误 回滚
                if("SERVER_ERROR".equals(errorJO.getString("code"))){
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>razorpay银行卡校验服务错误");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    result.put("judgment", "failure:razorpay银行卡校验服务错误");
                    throw new Exception("failure:razorpay银行卡校验服务错误");
                }
                //初步校验银行卡错误 不回滚 交易失败 并且短信通知
                if("BAD_REQUEST_ERROR".equals(errorJO.getString("code"))){
                    log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>银行卡信息错误");
                    result.put("hand-validation-error", "failure:银行卡信息错误");
                    //银行卡修改是根据“BAD_REQUEST_ERROR”这个错误原因来判定
                    loanModel.setFailureReason(CommonConfig.BANKCARD_ERROR_FLAG+" BAD_REQUEST_ERROR:"+errorJO.getString("description"));
                }
            }
        } catch (Exception e) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单-校验银行卡异常");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if(StringUtils.isBlank((String)result.get("judgment"))){
                result.put("judgment", "failure:校验银行卡异常");
            }
            e.printStackTrace();
            throw e;
        }
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单-校验银行卡结束");
    }

    public JSONObject callRazorpayValidation(UserBankCard userBankCard) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        String auth = RazorpaySignatureUtils.buildAuth(razorpayConfig.getKeyId(), razorpayConfig.getKeySecret());;
        HttpPost httpPost = new HttpPost("https://api.razorpay.com/v1/fund_accounts/validations");
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(JSON.toJSONString(buildValidationParam(userBankCard))));
        httpPost.setHeader("Authorization", "Basic " + auth);
        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String responseContent = EntityUtils.toString(entity, "UTF-8");
        JSONObject jSONObject = JSONObject.parseObject(responseContent);
        response.close();
        return jSONObject;
    }

    public Map<String, Object> buildValidationParam(UserBankCard userBankCard) {
        Map<String, String> bankAccount = new HashMap<>();
        Map<String, Object> fundAccount = new HashMap<>();
        Map<String, Object> validationParam = new HashMap<>();
        bankAccount.put("name", userBankCard.getAccountName());
        bankAccount.put("ifsc", userBankCard.getIfscCode());
        bankAccount.put("account_number", userBankCard.getBankAccount());
        fundAccount.put("account_type", "bank_account");
        fundAccount.put("bank_account", bankAccount);
        validationParam.put("fund_account", fundAccount);
        validationParam.put("amount", "100");
        validationParam.put("currency", "INR");
        return validationParam;
    }

    public PayStatus payStatus(boolean loanTimely, Map<String, Object> result, FinanceLoanModel loanModel) {

        if(StringUtils.isNotBlank((String)result.get("hand-validation-error"))){
            return PayStatus.FAILURE;
        }
        PayStatus payStatus = null;
        //是否等待
        String waiting = (String) result.get("waiting");
        if (!StringUtils.isBlank(waiting)) {
            //开放银行卡校验下的等待校验回调中
            if (waiting.equals(PayStatus.BANKCARD_VALIDATING.name())) {
                payStatus = PayStatus.BANKCARD_VALIDATING;
            }
            //开放银行卡校验下的kudos等待
            if (waiting.equals(PayStatus.WAIT_IN_KUDOS.name())) {
                payStatus = PayStatus.WAIT_IN_KUDOS;
            }
        } else {
            //1：开放银行银行卡校验并且已经通过无需继续校验   这种情况loanTimely一定为true
            //2：关闭了银行卡校验 loanTimely可能为false
            if (!loanTimely) {
                //当前仅当关闭银行卡校验  loanTimely为false才会执行到这一步
                payStatus = PayStatus.WAIT_IN_KUDOS;
            } else {
                payStatus = paymentFacility.handle(loanModel);
            }
        }
        return payStatus;
    }

    // 根据支付状态 更新放款记录和交易记录
    //这个方法 终审通过之后和放款重提之后都会执行到这里
    public void savePayStatus(PayStatus payStatus, FinanceLoanModel loanModel, FinancePayLogModel payLogModel,
                              Map<String, Object> result) {
        if (payStatus.num == PayStatus.BANKCARD_VALIDATING.num) {
            payLogModel.setPayStatus(PayStatus.BANKCARD_VALIDATING.num);
            payLogModel.setOrderStatus(OrderStatus.BANKCARD_VALIDATING.num);
            loanModel.setPayStatus(PayStatus.BANKCARD_VALIDATING.num);
            loanModel.setOrderStatus(OrderStatus.BANKCARD_VALIDATING.num);
            result.put("pay", "ok:bankcard-validating");
        } else if (payStatus.num == PayStatus.WAIT_IN_KUDOS.num) {
            payLogModel.setPayStatus(PayStatus.WAIT_IN_KUDOS.num);
            payLogModel.setOrderStatus(OrderStatus.WAIT_IN_KUDOS.num);
            loanModel.setPayStatus(PayStatus.WAIT_IN_KUDOS.num);
            loanModel.setOrderStatus(OrderStatus.WAIT_IN_KUDOS.num);
            result.put("pay", "ok:wait-in-kudos");
        } else if (payStatus.num == PayStatus.FAILURE.num) {
            payLogModel.setPayStatus(PayStatus.FAILURE.num);
            payLogModel.setOrderStatus(OrderStatus.FAILURE.num);
            loanModel.setPayStatus(PayStatus.FAILURE.num);
            loanModel.setOrderStatus(OrderStatus.FAILURE.num);
            payLogModel.setFailureReason(loanModel.getFailureReason());

            //银行卡短信
            if(StringUtils.isNotBlank(loanModel.getFailureReason())
                    && loanModel.getFailureReason().contains(CommonConfig.BANKCARD_ERROR_FLAG)){
                razorpayService.validationFailMsg(loanModel);
                //如果失败  需要把备注 设置为空   因为修改银行卡接口根据这个空判断是否开放
                //如果放款失败的话 应该开放 所以需要设置备注为空
                //超过三次不再开放
                if (loanModel.getModifyBankcardCount() < CommonConfig.MAX_MODIFY_BANKCARD_COUNT) {
                    loanModel.setComment(null);
                }
            }
            result.put("pay", "failure:" + loanModel.getFailureReason());
        } else if (payStatus.num == PayStatus.UNDERWAY.num) {
            payLogModel.setPayStatus(PayStatus.UNDERWAY.num);
            payLogModel.setOrderStatus(OrderStatus.INPROCESSING.num);
            loanModel.setPayStatus(PayStatus.UNDERWAY.num);
            loanModel.setOrderStatus(OrderStatus.INPROCESSING.num);
            result.put("pay", "ok:processing");
        } else {
            result.put("pay", "failure:未知的支付状态");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return;
        }

        log.info("step-over>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单-更新放款记录和放款交易记录开始");
        try {
            loanDao.updateFinanceLoanModel(loanModel);
            loanDao.updateFinancePayLogModel(payLogModel);
        } catch (Exception e) {
            log.info("step-over>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单-更新放款记录和放款交易记录失败,放款结果:{}", result.get("pay"));
            //放款失败 才回滚所有操作
            /**
             * 如果是BANKCARD_VALIDATING 此处保留初始化状态的放款记录和放款交易记录
             * 银行卡校验后（失败或者成功后放款）操作无法更新这些记录 该记录留存证据
             *
             * 如果是WAIT_IN_KUDOS 那么在批量放款时无法对该用户放款
             * 影响为用户收到终审通过短信 但是无法对其放款
             *
             * 如果是UNDERWAY 放款结果将无法更细 该记录留存证据
             */
            if (payStatus.num == PayStatus.FAILURE.num) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            e.printStackTrace();
            return;
        }
        log.info("step-over>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单-更新放款记录和放款交易记录完成");
    }
}
