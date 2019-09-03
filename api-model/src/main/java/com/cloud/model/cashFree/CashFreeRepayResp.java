package com.cloud.model.cashFree;

import lombok.Data;

/**
 * cashfree还款返回实体类
 *
 * @author danquan.miao
 * @date 2019/6/11 0011
 * @since 1.0.0
 */
@Data
public class CashFreeRepayResp {
    private String appId;
    /**
     * 订单号
     */
    private String orderId;
    /**
     * 订单金额
     */
    private Integer orderAmount;
    /**
     * 手机号
     */
    private String customerPhone;
    /**
     * 用户邮箱
     */
    private String customerEmail;
    /**
     * 用户姓名
     */
    private String customerName;
    /**
     * 通知url
     */
    private String notifyUrl;
    /**
     * 支付token
     */
    private String cfToken;
}
