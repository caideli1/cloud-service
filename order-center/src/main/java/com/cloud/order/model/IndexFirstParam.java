package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class IndexFirstParam {
    /**
     * 全部 逾期金額
     */
    private BigDecimal totalDueAmount;

    /**
     * 放款總額
     */
    private BigDecimal  loanAmount;

    /**
     * 今日 新增用戶數
     */
    private Integer  nowUserAdded;

    /**
     * 今日訂單新增數
     */
    private  Integer nowOrderAdded;

    /**
     * 昨日新增用戶數
     */
    private Integer yesterdayUserAdded;
    /**
     * 今日 訂單新增數
     */
    private Integer yesterdayOrderAdded;


}
