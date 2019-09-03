package com.cloud.order.model.resp;

import com.cloud.model.product.RateType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author yoga
 * @Description: ServiceRateDetailRes
 * @date 2019-07-0917:53
 */

@Setter
@Getter
public class ServiceRateDetailRes implements Serializable {
    private Long id;
    private String name;
    private BigDecimal gst;
    private BigDecimal totalRate;
    private List<RateType> rateTypeList;
}
