package com.cloud.model.product;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yoga
 * @Description: 产品工厂_收费类型
 * @Date 2019/1/99:59 AM
 */

@Builder
@Data
public class RateType implements Serializable {
    private static final long serialVersionUID = -7223374336765462556L;

    /**
     * 收取模式 - 首期收取
     */
    public static final int MODE_FIRST = 1;

    /**
     * 收取模式 - 尾期收取
     */
    public static final int MODE_TAIL = 2;

    private Long id;

    private String name;

    private BigDecimal rate;

    /**
     * 收取模式
     */
    private Integer mode;

    private Integer isDeleted;

    private String createUser;

    private Date createTime;

    private String modifyUser;

    private Date modifyTime;

    // 7天 14天
    @Deprecated
    private Integer type;

    private Long serviceRateId;
}
