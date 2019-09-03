package com.cloud.order.constant;

/**
 * 交易类型
 * @author bjy
 * @date 2019/2/27 0027 10:08
 */
public enum LoanType {
    /**
     * 1：放款申请
     */
    LOANAPPLY(1),
    /**
     * 2:放款重提
     */
    RELOAN(2),
    /**
     * 3：正常还款
     */
    NORMALREPAY(3),
    /**
     * 4：逾期还款
     */
    DUEREPAY(4),
    /**
     * 5：提前结清
     */
    ADVANCECLEAR(5),
    /**
     * 6：展期申请(正常展期)
     */
    EXTENSION(6),
    /**
     * 7：复贷放款
     */
    REPEATLOAN(7),
    /**
     * 待还
     */
    WAITREPAY(8),
    /**
     * 9：提前展期
     */
    EXTENSIONAHEAD(9),
    /**
     * 10：逾期展期
     */
    EXTENSIONOVERDUE(10);
    public int num =0;
    LoanType(int num){
        this.num=num;
    }
}
