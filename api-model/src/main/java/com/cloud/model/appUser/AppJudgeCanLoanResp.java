package com.cloud.model.appUser;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AppJudgeCanLoanResp {
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 借款金额
     */
    private BigDecimal loanAmount;
    /**
     * 借款周期
     */
    private String loanTerm;
    /**
     * 总利息(包含服务费)
     */
    private BigDecimal totalFee;
    /**
     * 最后还款日期
     */
    private String repaymentDate;
    /**
     * 罚息利率
     */
    private BigDecimal penaltyInterest;

    private String applyNum;

    private String sessionId;
}
