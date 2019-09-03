package com.cloud.model.kotak;

/**
 * kotak订单状态枚举
 *
 * @author danquan.miao
 * @date 2019/8/8 0008
 * @since 1.0.0
 */
public enum KotakOrderStatusEnum {
    /**
     * 取消
     */
    ABORTED("Aborted"),
    /**
     * 自动取消
     */
    AUTO_CANCELLED("Auto-Cancelled"),
    /**
     * 自动反转
     */
    AUTO_REVERSED("Auto-Reversed"),
    /**
     * 等待
     */
    Awaited("Awaited"),
    /**
     * transaction is cancelled by merchant
     */
    CANCELLED("Cancelled"),
    /**
     * 退款
     */
    CHARGE_BACK("Chargeback"),
    /**
     * 无效
     */
    INVALID("Invalid"),
    /**
     * 作弊
     */
    FRAUD("Fraud"),
    /**
     * 初始化
     */
    INITIATED("Initiated"),
    /**
     * 已退款
     */
    REFUNDED("Refunded"),
    /**
     * transaction is confirmed
     */
    SHIPPED("Shipped"),
    /**
     * 系统退款 Refunded by CCAvenue
     */
    SYSTEM_REFUND("System refund"),
    /**
     * 成功
     */
    SUCCESSFUL("Successful"),
    /**
     * 未成功
     */
    UNSUCCESSFUL("Unsuccessful"),
    ;

    KotakOrderStatusEnum(String status) {
        this.status = status;
    }

    private String status;

    public String getStatus() {
        return this.status;
    }

}
