package com.cloud.user.model;

import lombok.Data;

import java.util.Date;

@Data
public class AppUserRepayModeEntity {
    private Integer id;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 银行名称
     */
    private String netbankingName;
    /**
     * 其他方法
     */
    private String otherRepayMode;
    /**
     * 还款方式
     */
    private Integer repayMode;

    private Date createTime;

    private Date updateTime;

    public AppUserRepayModeEntity(String repaymentType){
        switch (repaymentType){
            case "Debit Card":
                this.repayMode = 1;
                break;
            case "NetBanking":
                this.repayMode = 2;
                break;
            case "UPI":
                this.repayMode = 3;
                break;
            case "Others":
                this.repayMode = 4;
                break;
        }
    }
}
