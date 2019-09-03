package com.cloud.model.cashFree;

/**
 * cashfree交易状态枚举
 *
 * @author danquan.miao
 * @date 2019/6/14 0014
 * @since 1.0.0
 */
public enum CashFreeTxStatusEnum {
    /**
     * 成功
     */
    SUCCESS,
    /**
     * 已标识
     */
    FLAGGED,
    /**
     * 挂起
     */
    PENDING,
    /**
     * 失败
     */
    FAILED,
    /**
     * 取消
     */
    CANCELLED,
    ;
}
