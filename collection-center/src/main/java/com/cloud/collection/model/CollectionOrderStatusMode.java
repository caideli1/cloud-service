package com.cloud.collection.model;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class CollectionOrderStatusMode {



    /**
     * 借款金额
     */
    private BigDecimal browAmount;
    private String orderNo;

    /**
     * 名称
     */
    private String name;
    /**
     * 电话
     */
    private String mobile;

    /**
     *订单状态 字符串返回
     */
    private  String   orderStatus;


    /**
     * 贷款期限
     */
    private Integer loanPeriod;


}
