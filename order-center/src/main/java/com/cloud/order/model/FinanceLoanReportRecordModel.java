package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 放款记录统计查询信息
 * @author bjy
 * @date 2019/2/28 0028 17:25
 */
@Data
@Builder
public class FinanceLoanReportRecordModel {
    /**
     * 主键Id
     */
    private Long id;
    /**
     * 报表 日期
     */
    private String reportDate;
    /**
     *  '放款总金额'
     */
    private BigDecimal loanAmount;
    /**
     * '放款总条目数'
     */
    private Integer loanCount;

    /**
     * 3000
     */
    private Integer loanSeven3000Count;
    /**
     * 5000
     */
    private Integer loanSeven5000Count;

    /**
     * 8000
     */
    private Integer loanSeven8000Count;
    /**
     * 10000
     */
    private Integer loanSeven10000Count;
    /**
     * 12000
     */
    private Integer loanSeven12000Count;

    /**
     * 13000
     */
    private Integer loanSeven13000Count;

    /**
     * 5000
     */
    private Integer  loanFourteen3000Count;
    /**
     * 5000
     */
    private Integer  loanFourteen5000Count;
    /**
     * 8000
     */
    private Integer  loanFourteen8000Count;

    /**
     * 10000
     */
    private Integer  loanFourteen10000Count;

    /**
     * 12000
     */
    private Integer  loanFourteen12000Count;
    /**
     * 13000
     */
    private Integer  loanFourteen13000Count;

    /**
     * 7天 放款笔数集合
     */
    private FinanceLoanReportRecordSeven financeLoanReportRecordSeven;

    private FinanceLoanReportRecordFourteen financeLoanReportRecordFourteen;

}
