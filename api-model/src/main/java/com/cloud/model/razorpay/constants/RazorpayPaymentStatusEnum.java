package com.cloud.model.razorpay.constants;

public enum RazorpayPaymentStatusEnum {

    /**
     * 状态字段：status | string | The status of payment. It can be created, authorized, captured, refunded, failed.
     *
     * @link {https://docs.razorpay.com/docs/return-objects}
     */
    CREATED(1, "created", "Razorpay首次收到其请求时的默认支付状态。在此状态下没有进行任何处理"),
    AUTHORIZED(2, "authorized", "付款授权成功后设置。这笔钱已经从客户账户里扣除了，但还没有从他的银行转出来。"),
    CAPTURED(3, "captured", "设置成功后捕获付款。在此之后，您将在捕获后的T+3天内收到钱。"),
    REFUNDED(4, "refunded", "设置后缴获的付款已完全退款。它不设置为部分退款。"),
    FAILED(5, "failed", "设置如果支付失败由于一些错误。检查错误字段中的实际原因。"),
    ;

    public int code;

    public String value;

    public String description;

    RazorpayPaymentStatusEnum(int code, String value, String description) {
        this.code = code;
        this.value = value;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
