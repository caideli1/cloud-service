package com.cloud.model.product;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yoga
 * @Description: - 金额成分具体项目
 * @date 2019-04-0412:16
 */
@Builder
@Getter
@Setter
public class OrderPartAmount extends OrderPartAmountDetail implements Serializable {
    private static final long serialVersionUID = 1227576112625334545L;
    private boolean isPaid;
    private String name;
    private BigDecimal amount;
}
