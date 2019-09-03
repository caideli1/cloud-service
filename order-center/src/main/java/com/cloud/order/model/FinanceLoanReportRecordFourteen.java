package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

/**
 * 14天 放款笔数 集合
 */
@Data
@Builder
public class FinanceLoanReportRecordFourteen {

    //3000 5000 8000 10000 12000 13000
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
}
