package com.cloud.order.model;

import lombok.Data;

/**
 * 个人审批统计报表
 */
@Data
public class PersonCountModel {



    private  int userId;
    /**
     * 审批人员名
     */
    private String auditorName;
    /**
     * 今日处理初审审批订单数
     */
    private long firstSum;
    /**
     * 今日初审通过订单数
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
