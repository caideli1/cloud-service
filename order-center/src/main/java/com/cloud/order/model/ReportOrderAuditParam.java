package com.cloud.order.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ReportOrderAuditParam {


    /**
     * 报表时间
     */
    private Date reportDate;
    /**
     * 审批人名称
     */
    private String auditorName;
    /**
     * 节点名称
     */
    private String  node;

    /**
     * 处理结果
     */
    private String  result;
    /**
     * 汇总金额
     */
    private BigDecimal amount;
    /**
     * 个数
     */
    private  Integer  num;






}
