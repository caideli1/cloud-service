package com.cloud.service.feign.pay;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.service.fallback.PayFallBackFactory;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.model.pay.UniversalAck;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 支付中心feign调用类
 *
 * @author danquan.miao
 * @date 2019/7/31 0031
 * @since 1.0.0
 */
@FeignClient(name = "pay-center", fallbackFactory = PayFallBackFactory.class)
public interface PayClient {
    /**
     * 放款
     *
     * @param financeLoanModel
     * @return
     */
    @PostMapping("/pay-anon/payout/payment")
    Map<String, Object> payment(@RequestBody FinanceLoanModel financeLoanModel);

    /**
     * 放款后置处理
     *
     * @param txSerialNum
     * @param universalAck
     */
    @PostMapping("/pay-anon/payout/afterPaymentHandle")
    void afterPaymentHandle(@RequestParam String txSerialNum, @RequestBody UniversalAck universalAck);

    /**
     * 还款
     *
     * @param loanNumber
     * @param repayChannel
     * @return
     */
    @PostMapping("/pay-anon/app/repay")
    JsonResult repay(@RequestParam String loanNumber, @RequestParam Integer repayChannel);

    /**
     * 还款回调
     *
     * @param requestBody
     * @return
     */
    @PostMapping("/pay-anon/app/repayCallBackFromOrder")
    JsonResult repayCallBackFromOrder(@RequestParam String requestBody);

    /**
     * 申请展期
     *
     * @param loanNumber
     * @param repayChannel
     * @return
     */
    @PostMapping("/pay-anon/app/applyExtension")
    JsonResult applyExtension(@RequestParam String loanNumber, @RequestParam Integer repayChannel);

    /**
     * 支付成功处理
     * @param financePayLogModel
     * @param transactionTime
     */
    @PostMapping("/pay-anon/app/handPaymentSuccess")
    void handPaymentSuccess(@RequestBody FinancePayLogModel financePayLogModel, @RequestParam(value = "transactionTimeStr") String transactionTimeStr);

    /**
     * 支付失败处理
     * @param financePayLogModel
     * @param failReason
     */
    @PostMapping("/pay-anon/app/handPaymentFail")
    void handPaymentFail(@RequestBody FinancePayLogModel financePayLogModel, @RequestParam String failReason);

    /**
     * 线下展期成功处理
     * @param financePayLogModel
     * @param transactionTime
     * @param extensionStartDate
     */
    @PostMapping("/pay-anon/app/handleOfflineExtensionSuccess")
    void handleOfflineExtensionSuccess(@RequestBody FinancePayLogModel financePayLogModel, @RequestParam Date transactionTime, @RequestParam Date extensionStartDate);


    /**
     * 认证银行卡
     *
     * @param name
     * @param phone
     * @param bankAccount
     * @param ifsc
     * @return
     */
    @GetMapping("/pay-anon/payout/validBankCard")
    Boolean validBankCard(@RequestParam String name, @RequestParam String phone, @RequestParam String bankAccount, @RequestParam String ifsc);

    /**
     * 将借据标记为 已跟进
     * @param loanNumber 借据编号 也是订单编号
     * @return
     */
    @PostMapping("/pay-anon/failure/follow")
    public Integer follow(@RequestParam String loanNumber);
}
