package com.cloud.model.cashFree;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 还款回调实体
 *
 * @author danquan.miao
 * @date 2019/6/13 0013
 * @since 1.0.0
 */
@Data
@Builder
public class RepayCallBackModel {
    /**
     * 订单流水号
     */
    private String orderId;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 第三方生成唯一交易id
     */
    private String referenceId;
    /**
     * 交易状态
     */
    private String txStatus;
    /**
     * 支付模式
     */
    private String paymentMode;
    /**
     * 交易信息
     */
    private String txMsg;
    /**
     * 交易时间
     */
    private String txTime;
    /**
     * 签名
     */
    private String signature;
}
