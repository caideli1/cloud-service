package com.cloud.order.constant;

/**
 * 回调返回枚举
 *
 * @author danquan.miao
 * @create 2019/06/13
 * @since 1.0.0
 */
public enum CallBackRespEnum {
    SUCCESS("success"),
    FAIL("fail"),
    ;

    CallBackRespEnum(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }
}
