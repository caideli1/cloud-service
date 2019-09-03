package com.cloud.model.product;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yoga
 * @Description: OrderPartAmountDetail
 * @date 2019-04-0413:46
 */

@Data
public abstract class OrderPartAmountDetail {
    public BigDecimal amount;
    public String name;
}
