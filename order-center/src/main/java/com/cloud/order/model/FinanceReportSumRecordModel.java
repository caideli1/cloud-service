package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @Author:         zhujingtao
 * @CreateDate:     2019/2/26 14:05
 * 对应数据库表 `finance_report_sum_record`
 */
@Data
@Builder
public class FinanceReportSumRecordModel  implements Serializable {
    /**
     * '待收总金额',
     */
    private BigDecimal dueInAmount;
    /**
     * '代收总条目数'
     */
    private Integer dueInCount;
    /**
     *  '已还金额',
     */
    private BigDecimal payableAmount;
    /**
     *  '已还条目数'
     */
    private Integer payableCount;
    /**
     *  '放款总金额',
     */
    private BigDecimal loanAmount;
    /**
     *  '7 天 3000待收笔数'
     */
    private Integer dueSeven3000Count;
    /**
     *  '7 5000天待收笔数'
     */
    private Integer dueSeven5000Count;
    /**
     *  '7 天 8000待收笔数'
     */
    private Integer dueSeven8000Count;
    /**
     *  '7天  10000待收笔数',
     */
    private Integer dueSeven10000Count;
    /**
     *  '7天  12000待收笔数',
     */
    private Integer dueSeven12000Count;
    /**
     *  '7天  13000待收笔数',
     */
    private Integer dueSeven13000Count;
    /**
     *  '14天 3000待收笔数',
     */
    private Integer dueFourteen3000Count;
    /**
     *  '14天 5000待收笔数',
     */
    private Integer dueFourteen5000Count;
    /**
     *  '14天 8000待收笔数',
     */
    private Integer dueFourteen8000Count;
    /**
     *  '14天  10000待收笔数',
     */
    private Integer dueFourteen10000Count;
    /**
     *  '14天  12000待收笔数',
     */
    private Integer dueFourteen12000Count;
    /**
     *  '14天  13000待收笔数',
     */
    private Integer dueFourteen13000Count;
    /**
     * '7 天 3000实收笔数',
     */
    private Integer payableSeven3000Count;

    /**
     * '7 天 5000实收笔数',
     */
    private Integer payableSeven5000Count;
    /**
     * '7 天 8000实收笔数',
     */
    private Integer payableSeven8000Count;
    /**
     *  '7 10000天实收笔数',
     */
    private Integer payableSeven10000Count;
    /**
     *  '7 12000天实收笔数',
     */
    private Integer payableSeven12000Count;
    /**
     *  '7 13000天实收笔数',
     */
    private Integer payableSeven13000Count;
    /**
     * '14 3000天实收笔数',
     */
    private Integer payableFourteen3000Count;
    /**
     * '14 5000天实收笔数',
     */
    private Integer payableFourteen5000Count;
    /**
     * '14 8000天实收笔数',
     */
    private Integer payableFourteen8000Count;
    /**
     *  '14 10000实收笔数',
     */
    private Integer payableFourteen10000Count;
    /**
     *  '14 12000实收笔数',
     */
    private Integer payableFourteen12000Count;
    /**
     *  '14 13000实收笔数',
     */
    private Integer payableFourteen13000Count;
    /**
     * '放款 7 3000 笔数',
     */
    private Integer loanSeven3000Count;
    /**
     * '放款 7 5000 笔数',
     */
    private Integer loanSeven5000Count;
    /**
     * '放款 7 8000 笔数',
     */
    private Integer loanSeven8000Count;
    /**
     *  '放款 7 10000 笔数',
     */
    private Integer loanSeven10000Count;
    /**
     *  '放款 7 12000 笔数',
     */
    private Integer loanSeven12000Count;
    /**
     *  '放款 7 13000 笔数',
     */
    private Integer loanSeven13000Count;
    /**
     *  '放款  14  3000 笔数',
     */
    private Integer loanFourteen3000Count;
    /**
     *  '放款  14  5000 笔数',
     */
    private Integer loanFourteen5000Count;
    /**
     *  '放款  14  8000 笔数',
     */
    private Integer loanFourteen8000Count;
    /**
     *  '放款  14  10000 笔数',
     */
    private Integer loanFourteen10000Count;
    /**
     *  '放款  14  12000 笔数',
     */
    private Integer loanFourteen12000Count;
    /**
     *  '放款  14  13000 笔数',
     */
    private Integer loanFourteen13000Count;
    /**
     * '放款总比数',
     */
    private Integer loanCount;


}
