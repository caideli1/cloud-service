package com.cloud.order.constant;

/**
 * kudos接口名枚举
 *
 * @author danquan.miao
 * @date 2019/5/13 0013
 * @since 1.0.0
 */
public enum KudosApiNameEnum {
    LOAN_REQUEST("Loan-Request"),
    BORROWER_INFO("Borrower-Info"),
    LOAN_SCHEDULE("Loan-Schedule"),
    LOAN_EMIPAMNT("Loan-EMIPayment"),
    LOAN_CLOSURE("Loan-Closure"),
    RECONCILIATION("Reconciliation"),
    ;

    KudosApiNameEnum(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }
}
