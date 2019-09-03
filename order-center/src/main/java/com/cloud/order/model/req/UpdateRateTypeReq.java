package com.cloud.order.model.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author yoga
 * @Description: UpdateRateTypeReq
 * @date 2019-07-1211:45
 */
@Getter
@Setter
public class UpdateRateTypeReq {
    @NotNull
    private Long id;
    private String name;
    private BigDecimal rate;
    /**
     * 收取模式
     */
    @Max(2)
    @Min(1)
    private Integer mode;

    @Max(2)
    @Min(1)
    private Integer type;
}
