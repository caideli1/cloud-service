package com.cloud.order.model.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yoga
 * @Description: AppProductRes
 * @date 2019-07-2413:57
 */
@Getter
@Setter
public class AppProductRes implements Serializable {
    private static final long serialVersionUID = -2180878507423236029L;

    private Integer productId;
    private String name;

    /**
     * 期限
     */
    private Integer term;

    /**
     * maxAmount = minAmount
     */
    private BigDecimal amount;

    private BigDecimal totalFee;
}
