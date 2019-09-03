package com.cloud.order.model.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author yoga
 * @Description: AddRateTypeReq
 * @date 2019-07-0314:50
 */

@Getter
@Setter
public class AddRateTypeReq {
    @NotNull
    private String name;

    @NotNull
    private BigDecimal rate;

    /**
     * 收取模式
     */
    @NotNull
    @Max(2)
    @Min(1)
    private Integer mode;

    @NotNull
    @Max(2)
    @Min(1)
    private Integer type;

}
