package com.cloud.order.constant;

/**
 * 凭证确认枚举
 *
 * @author danquan.miao
 * @date 2019/8/23 0023
 * @since 1.0.0
 */
public enum VoucherIsConfirmEnum {
    /**
     * 未确认
     */
    UN_CONFIRM(0),
    /**
     * 已确认
     */
    CONFIRMED(1),
    ;

    VoucherIsConfirmEnum(Integer status) {
        this.status = status;
    }

    private Integer status;

    public Integer getStatus() {
        return status;
    }
}
