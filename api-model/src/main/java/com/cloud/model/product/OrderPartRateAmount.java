package com.cloud.model.product;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yoga
 * @Description: 收费类型 费用
 * @date 2019-03-1411:22
 */

@Builder
@Data
public  class OrderPartRateAmount extends OrderPartAmountDetail implements Serializable {

    private static final long serialVersionUID = 3156547275340138982L;

    private String name;

    private BigDecimal rate;

    private BigDecimal amount;

    /**
     *  增值税
     */
    private BigDecimal gstAmount;



    public BigDecimal amountAndGst() {
        return amount.add(gstAmount);
    }

}
