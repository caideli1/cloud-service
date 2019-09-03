package com.cloud.service.fallback;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.model.pay.UniversalAck;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.service.feign.pay.PayClient;
import feign.hystrix.FallbackFactory;

import java.util.Date;
import java.util.Map;

/**
 * 支付中心服务降级类
 *
 * @author danquan.miao
 * @date 2019/7/31 0031
 * @since 1.0.0
 */
public class PayFallBackFactory implements FallbackFactory<PayClient> {
    @Override
    public PayClient create(Throwable throwable) {
        return new PayClient() {
            @Override
            public Map<String, Object> payment(FinanceLoanModel financeLoanModel) {
                return null;
            }

            @Override
            public void afterPaymentHandle(String txSerialNum, UniversalAck universalAck) {

            }

            @Override
            public JsonResult repay(String loanNumber, Integer repayChannel) {
                return null;
            }

            @Override
            public JsonResult repayCallBackFromOrder(String requestBody) {
                return null;
            }

            @Override
            public JsonResult applyExtension(String loanNumber, Integer repayChannel) {
                return null;
            }

            @Override
            public void handPaymentSuccess(FinancePayLogModel financePayLogModel, String transactionTime) {

            }

            @Override
            public void handPaymentFail(FinancePayLogModel financePayLogModel, String failReason) {

            }

            @Override
            public void handleOfflineExtensionSuccess(FinancePayLogModel financePayLogModel, Date transactionTime, Date extensionStartDate) {

            }

            @Override
            public Boolean validBankCard(String name, String phone, String bankAccount, String ifsc) {
                return null;
            }

            @Override
            public Integer follow(String loanNumber) {
                return null;
            }
        };
    }
}
