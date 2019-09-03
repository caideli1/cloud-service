package com.cloud.order.model;

import lombok.Data;

@Data
public class RepayMentRspVo {
    /**
     * 还款金额
     */
	private String amt;

    /**
     * 还款状态
     * 1-已还款  2-未还款
     */
	private int repayMentStatus;
    /**
     * 还款时间
     */
	private String repayMentTime;
    /**
     * 借款时间
     */
    private String loanStartDate;
}
