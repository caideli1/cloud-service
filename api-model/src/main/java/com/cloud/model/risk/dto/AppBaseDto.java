package com.cloud.model.risk.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author walle
 */
@Setter
@Getter
public class AppBaseDto {

    /**
     * app 渠道来源
     */
    private String appSourceChannel;

    /**
     * 订单编号
     */
    private String orderNum;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户手机号
     */
    private String phone;
}
