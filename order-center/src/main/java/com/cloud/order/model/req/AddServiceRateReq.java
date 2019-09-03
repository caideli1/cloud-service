package com.cloud.order.model.req;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yoga
 * @Description: AddServiceRateReq
 * @date 2019-07-1016:49
 */

@Getter
@Setter
public class AddServiceRateReq {
    private Long id;
    private String name;
    private BigDecimal gst;
    private BigDecimal totalRate;
    private List<Long> rateTypeIdList;
}
