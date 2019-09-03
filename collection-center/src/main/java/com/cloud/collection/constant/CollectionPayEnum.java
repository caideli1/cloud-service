package com.cloud.collection.constant;

/**
 *催收支付状态键值对
 */
public enum CollectionPayEnum {
    //未支付
    UN_PAY(0),
    //支付成功
    PAY_SUCESS(1),
    //展期
    EXTENSION(2);
    CollectionPayEnum(Integer status) {
        this.status = status;
    }

    private Integer status;

    public Integer getStatus() {
        return status;
    }
}
