package com.cloud.model.kotak;

import lombok.Data;

/**
 * kotak还款请求返回类
 *
 * @author danquan.miao
 * @date 2019/8/7 0007
 * @since 1.0.0
 */
@Data
public class KotakRepayResp {
    /**
     * 订单号(流水号)
     */
    private String orderId;
    /**
     * 加密后请求体
     */
    private String encRequest;

    /**
     * 访问码
     */
    private String accessCode;
}
