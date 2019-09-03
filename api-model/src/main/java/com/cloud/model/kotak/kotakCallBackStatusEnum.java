package com.cloud.model.kotak;

/**
 * kotak还款状态枚举
 *
 * @author danquan.miao
 * @date 2019/8/16 0016
 * @since 1.0.0
 */
public enum kotakCallBackStatusEnum {
    /**
     * 成功
     */
    SUCCESS("success"),
    /**
     * 失败
     */
    FAILURE("failure"),
    /**
     * 取消
     */
    ABORTED("aborted"),
    /**
     * 未知
     */
    UNKNOWN("Status Not Known"),
    ;

    kotakCallBackStatusEnum(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return status;
    }
}
