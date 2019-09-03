package com.cloud.model.product;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author yoga
 * @Description: OrderPartRes
 * @date 2019-04-0411:51
 */
@Data
@Builder
public class OrderPartRes implements Serializable {
    private static final long serialVersionUID = -8126932445350279435L;

    /**
     * 收费项目
     */
    private List<OrderPartRateAmount> rateInfoList;

    private List<OrderPartAmountDetail> otherAmountList;
}
