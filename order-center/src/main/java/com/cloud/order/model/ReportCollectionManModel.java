package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @Author 催收员 报表 信息
 */
@Builder
@Data
public class ReportCollectionManModel {
    /**
     * id
     */
    @Id
    private Integer id;
    /**
     * 报表日期
     */
    private Date reportDate;

    /**
     * 催收员ID
     */
    private   Integer collectorId;

    /**
     * 催收员 名称
     */
    private String collectorName;

    /**
     * 催回率
     */
    private Double collectionPre;

    /**
     * 应还总金额
     */
    private BigDecimal  repayAmount;

    /**
     * 催回总金额
     */
    private  BigDecimal payAmount;

    /**
     * 跟进数量
     */
    private Integer followUpNum;

    /**
     * 还款 数量
     */
    private Integer payNum;
}
