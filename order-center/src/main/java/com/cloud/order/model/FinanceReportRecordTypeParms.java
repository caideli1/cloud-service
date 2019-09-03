package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 报表统计  分类项目
 */
@Data
@Builder
public class FinanceReportRecordTypeParms {

    /**
     * 金额 分类  3000 5000 8000 10000 12000 13000
     */
    private Double  amountType;

    /**
     * '14天应还本金',
     */
    private BigDecimal principalFourteenAmount;
    /**
     * '7天应还本金'
     */
    private BigDecimal principalSevenAmount;

    /**
     * '14天  实际还款金额'
     */
    private BigDecimal repaymentFourteenAmount;
    /**
     *  '7天实际还款金额'
     */
    private BigDecimal repaymentSevenAmount;

    /**
     * '14 实际还款笔数'
     */
    private Integer paymentsFourteenCount;
    /**
     *  '7实际还款笔数'
     */
    private Integer paymentsSevenCount;

    /**
     * '14 应还款笔数'
     */
    private Integer dueFourteenCount;
    /**
     * '7 应还款笔数'
     */
    private Integer dueSevenCount;




}
