package com.cloud.order.model;

import lombok.Data;

@Data
public class StatisticsModelAuditor {
    /**
     * 审批员
     */
    private String statisticsAuditor;

    private long todaySum;

    private long todayPassedSum;

    /**
     * 今日订单通过率
     */
    private float todayPassedRate;

    private double todayApplyAmount;

    private double todayPassedAmount;
}
