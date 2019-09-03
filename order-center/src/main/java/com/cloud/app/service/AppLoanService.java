package com.cloud.app.service;

import com.cloud.app.model.PaymentIssue;
import com.cloud.common.dto.JsonResult;
import com.cloud.model.appUser.AppLoanVo;
import com.cloud.model.appUser.LoanCalculate;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.order.model.RepayMentRspVo;
import com.cloud.order.model.resp.AppMyCurrentLoanInfoRes;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

public interface AppLoanService {
    PageInfo<AppLoanVo> getLoansById(Map<String, Object> params);

    AppLoanVo getLoanInfoById(String loanId);

    void handleFail(String razorpayEventResponse);

    void handleAuthorized(String razorpayEventResponse);

    /**
     * capture回调处理
     *
     * @param transactionTime
     * @param serialNumber
     * @param amount
     * @param razorpay_payment_id
     * @return
     */
    JsonResult capture(Date transactionTime, String serialNumber, BigDecimal amount, String razorpay_payment_id);

    PageInfo<RepayMentRspVo> queryRepayById(Map<String, Object> params);

    JsonResult deferredCalc(String loanNum);

    AppMyCurrentLoanInfoRes appMyCurrentOrderInfo(Long userId);

    LoanCalculate appExtensionApplyInfo(String loanNumber);

    void appAddPaymentIssue(PaymentIssue req);
}
