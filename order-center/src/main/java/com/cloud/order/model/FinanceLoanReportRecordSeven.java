package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

/**
 * 7天放款 笔数集合
 */
@Data
@Builder
public class FinanceLoanReportRecordSeven {
    //3000 5000 8000 10000 12000 13000

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
}
