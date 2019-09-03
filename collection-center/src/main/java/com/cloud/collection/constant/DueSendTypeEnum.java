package com.cloud.collection.constant;

/**
 *催收外部調用 枚舉
 */
public enum DueSendTypeEnum {
    //正常支付
    PAY(0),
    //逾期支付
    DUE_PAY(1),
    //展期支付
    EXTENSION_PAY(2);
    DueSendTypeEnum(Integer status) {
        this.status = status;
    }

    private Integer status;

    public Integer getStatus() {
        return status;
    }
}