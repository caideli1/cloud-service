package com.cloud.model.appUser;

import lombok.Data;

@Data
public class AppUserRepayModeReq {
    private Long userId;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 银行名称
     */
    private String netBankingName;
    /**
     * 其他方法
     */
    private String otherRepaymentMethod;
    /**
     * 还款方式
     */
    private String repaymentType;
}
