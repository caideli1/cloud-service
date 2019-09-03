package com.cloud.order.model;

import lombok.Data;

/**
 * 平台审批统计报表
 */
@Data
public class SystemCountModel {
    /**
     * 日期
     */
    private String createDate;
    /**
     * 今天处理初审订单数
     */
    private long firstSum;
    /**
     * 今日初审通过数
     */
    private long firstPassedSum;
    /**
     * 今日处理终审订单数
     */
    private long finalSum;
    /**
     * 今日终审通过数
     */
    private long finalPassedSum;
    /**
     * 今日处理申请金额
     */
    private double loanAmount;
    /**
     * 今日处理放款金额
     */
    private double finalAmount;

    /**
     * 今日初审通过率
     */
    private String firstRate;

    /**
     * 今日终审通过率
     */
    private String finalRate;


    public  void setFirstRate() {
        try {
            this.firstRate = getFirstPassedSum()*100 / getFirstSum()+"%";
        }catch (Exception e){
            this.firstRate = "0%";
        }finally {
            setFinalRate();
        }
    }

    public void setFinalRate() {
        try {
            this.finalRate = getFinalPassedSum()*100 / getFinalSum()+"%";
        }catch (Exception e){
            this.finalRate="0%";
        }
    }

}
