package com.cloud.order.model;

import lombok.Data;

@Data
public class StatisticsModel {

    /**
     * 日期
     */
    private String statisticsDate;

    private long todaySum;

    private long todayPassedSum;

    /**
     * 今日订单通过率
     */
    private float todayPassedRate;

    private double todayApplyAmount;

    private double todayPassedAmount;

}
