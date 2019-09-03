package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name="report_due")
@Builder
public class ReportDueModel {

    @Id
    private  Integer id;
    /**
     * 报表日期
     */
    private Date reportDate;

    /**
     * 应还数量
     */
    private BigDecimal loanAmount;

    /**
     * 逾期数量
     */
    private BigDecimal repayLoanAmount;

    /**
     * 0:汇总
     * 1：d1
     * 3：d3
     * 7：d7
     * 15:d15
     * 30:d30
     */
    private Integer dueType;
}