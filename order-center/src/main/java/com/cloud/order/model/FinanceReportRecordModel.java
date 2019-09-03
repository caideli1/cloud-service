package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

/**
 * @Author:         zhujingtao
 * @CreateDate:     2019/2/26 14:05
 * 对应数据库表 `finance_report_record``
 */
@Data
@Builder
public class FinanceReportRecordModel implements Serializable {
    /**
     * 主键Id
     */
    private Long id;
    /**
     * 报表 日期
     */
    private Date reportDate;
    /**
     * '总应还本金'
     */
    private BigDecimal principalAmount;
    /**
     * '14天应还本金',
     */
    private BigDecimal principalFourteenAmount;
    /**
     * '7天应还本金'
     */
    private BigDecimal principalSevenAmount;
    /**
     *  '总实际还款金额'
     */
    private BigDecimal repaymentAmount;
    /**
     * '14天  实际还款金额'
     */
    private BigDecimal repaymentFourteenAmount;
    /**
     *  '7天实际还款金额'
     */
    private BigDecimal repaymentSevenAmount;
    /**
     * '展期笔数'
     */
    private Integer exporeCount;
    /**
     * '14 实际还款笔数'
     */
    private Integer paymentsFourteenCount;
    /**
     *  '7实际还款笔数'
     */
    private Integer paymentsSevenCount;
    /**
     *  '实际还款笔数'
     */
    private Integer paymentsCount;
    /**
     * '14 应还款笔数'
     */
    private Integer dueFourteenCount;
    /**
     * '7 应还款笔数'
     */
    private Integer dueSevenCount;
    /**
     * '应还款笔数'
     */
    private Integer dueCount;

    /**
     * '7天-3k应还笔数与本金'
     */
    private String due7d3kCountAndPrincipalAmount;
    /**
     * '7天-3k实还笔数与金额'
     */
    private String pay7d3kCountAndAmount;
    /**
     * '7天-5k应还笔数与本金'
     */
    private String due7d5kCountAndPrincipalAmount;
    /**
     * '7天-5k实还笔数与金额'
     */
    private String pay7d5kCountAndAmount;
    /**
     * '7天-8k应还笔数与本金'
     */
    private String due7d8kCountAndPrincipalAmount;
    /**
     * '7天-8k实还笔数与金额'
     */
    private String pay7d8kCountAndAmount;
    /**
     * '7天-10k应还笔数与本金'
     */
    private String due7d10kCountAndPrincipalAmount;
    /**
     * '7天-10k实还笔数与金额'
     */
    private String pay7d10kCountAndAmount;
    /**
     * '7天-12k应还笔数与本金'
     */
    private String due7d12kCountAndPrincipalAmount;
    /**
     * '7天-12k实还笔数与金额'
     */
    private String pay7d12kCountAndAmount;
    /**
     * '7天-13k应还笔数与本金'
     */
    private String due7d13kCountAndPrincipalAmount;
    /**
     * '7天-13k实还笔数与金额'
     */
    private String pay7d13kCountAndAmount;
    /**
     * '14天-3k应还笔数与本金'
     */
    private String due14d3kCountAndPrincipalAmount;
    /**
     * '14天-3k实还笔数与金额'
     */
    private String pay14d3kCountAndAmount;
    /**
     * '14天-5k应还笔数与本金'
     */
    private String due14d5kCountAndPrincipalAmount;
    /**
     * '14天-5k实还笔数与金额'
     */
    private String pay14d5kCountAndAmount;
    /**
     * '14天-8k应还笔数与本金'
     */
    private String due14d8kCountAndPrincipalAmount;
    /**
     * '14天-8k实还笔数与金额'
     */
    private String pay14d8kCountAndAmount;
    /**
     * '14天-10k应还笔数与本金'
     */
    private String due14d10kCountAndPrincipalAmount;
    /**
     * '14天-10k实还笔数与金额'
     */
    private String pay14d10kCountAndAmount;
    /**
     * '14天-12k应还笔数与本金'
     */
    private String due14d12kCountAndPrincipalAmount;
    /**
     * '14天-12k实还笔数与金额'
     */
    private String pay14d12kCountAndAmount;
    /**
     * '14天-13k应还笔数与本金'
     */
    private String due14d13kCountAndPrincipalAmount;
    /**
     * '14天-13k实还笔数与金额'
     */
    private String pay14d13kCountAndAmount;
    /**
     * '展期笔数'
     */
    private Integer financeExtensionCount;
    /**
     * '减免统计'
     */
    /*private BigDecimal interestReductionAmount;*/

    /**
     * 分类列表
     */
    List<FinanceReportRecordTypeParms> financeReportRecordType;


}
