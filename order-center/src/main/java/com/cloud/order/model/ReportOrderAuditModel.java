package com.cloud.order.model;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class ReportOrderAuditModel {

    /**
     * 报表时间
     */
    private Date reportDate;
    /**
     * 审批人名称
     */
    private String auditorName;

    private  Integer    auditorId;


    /**
     * 终审数量
     */
    private Integer  finalAuditCount;

    private Integer   firstAuditCount;

    /**
     * 初审通过率
     */
    private Double firstPassRate;
    /**
     * 通过金额
     */
    private  BigDecimal  passAmount;
    /**
     * 申请金额
     */
    private   BigDecimal applyAmount;

    /**
     * 终审通过率
     */
    private  Double  finalPassRate;
    /**
     * 初审通过率 字符串
     */
    private String firstPassRateStr;

    /**
     * 终审通过率
     */
    private String finalPassRateStr;



}
