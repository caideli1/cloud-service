package com.cloud.order.payment;

import com.cloud.model.common.PayStatus;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.order.payment.facility.PaymentFacility;

import java.util.Map;

public abstract class IThirdParPay {
    public abstract PayStatus payment(FinanceLoanModel financeLoanModel);

    public PayStatus parsePayStatus(Map<String, Object> map) {
        Object payStatus = map.get("payStatus");
        if (null == payStatus) {
            return PayStatus.FAILURE;
        }
        int ps = (Integer) payStatus;
        if (ps == PayStatus.FAILURE.num) {
            return PayStatus.FAILURE;
        } else if (ps == PayStatus.INITIALIZE.num) {
            return PayStatus.INITIALIZE;
        } else if (ps == PayStatus.SUCCESS.num) {
            return PayStatus.SUCCESS;
        } else if (ps == PayStatus.UNDERWAY.num) {
            return PayStatus.UNDERWAY;
        } else if (ps == PayStatus.WAIT.num) {
            return PayStatus.WAIT;
        }
        return PayStatus.FAILURE;
    }
}
