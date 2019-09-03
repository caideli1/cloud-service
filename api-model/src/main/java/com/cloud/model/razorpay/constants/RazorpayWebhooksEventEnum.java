package com.cloud.model.razorpay.constants;

/**
 * razorpay 放款事件和状态枚举类
 *
 * @author danquan.miao
 * @date 2019/7/16 0011
 * @since 1.0.0
 */
public enum RazorpayWebhooksEventEnum {
    PAYOUT_PENDING("payout.pending", "pending"),
    PAYOUT_QUEUED("payout.queued", "queued"),
    PAYOUT_INITIATED("payout.initiated", "processing"),
    PAYOUT_PROCESSED("payout.processed", "processed"),
    PAYOUT_REVERSED("payout.reversed", "reversed"),
    PAYOUT_CANCELED("payout.cancelled", "cancelled"),
    PAYOUT_REJECTED("payout.rejected", "rejected"),
    TRANSACTION_CREATED("transaction.created", "processing"),
    ;

    private String event;

    private String status;


    RazorpayWebhooksEventEnum(String event, String status) {
        this.event = event;
        this.status = status;
    }

    public String getEvent() {
        return event;
    }

    public String getStatus() {
        return status;
    }
}
