package com.cloud.model.appProduct;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductInfo {
    //产品ID
    private Integer productId;
    //产品名称
    private String name;
    //产品期限
    private int term;
    //最大借款金额
    private BigDecimal maxAmount;
    //最小借款金额
    private BigDecimal minAmount;
    private BigDecimal maxTotalFee;
    private BigDecimal minTotalFee;
}
