package com.cloud.order.model.resp;

import com.cloud.model.product.RateType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yoga
 * @Description: AppProductRateDescriptionRes
 * @date 2019-07-2414:55
 */

@Getter
@Setter
public class AppProductRateDescriptionRes implements Serializable {
    private static final long serialVersionUID = 8855626324896168368L;

//    private BigDecimal interestRate;
    private BigDecimal gst;
    private List<RateType> rateTypesList;
}
