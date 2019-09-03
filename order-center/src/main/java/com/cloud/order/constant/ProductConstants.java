package com.cloud.order.constant;

/**
 * @author yoga
 * @Description: 产品常量
 * @date 2019/1/103:50 PM
 */
public interface ProductConstants {
     int STATUS_NEW = 1;
     int STATUS_ACTIVE = 2;
     int STATUS_ABANDON = 3;
     int NOT_RELEVSNCE = 0;
     int PRO_IS_DELETED = 1;

    /**
     *先息后本
     */
     int REPAYMENT_TYPE_BEFORE_INTEREST_AFTER_PRINCIPAL = 1;

    /**
     *等额本息
     */
     int REPAYMENT_TYPE_EQUAL_LOAN = 2;

    /**
     *等额本金
     */
     int REPAYMENT_TYPE_EQUAL_PRINCIPAL = 3;

    /**
     *等本等息
     */
     int REPAYMENT_TYPE_EQUAL_PRINCIPAL_AND_INTEREST = 4;

    /**
     *一次性还本付息
     */
     int REPAYMENT_TYPE_DUE_TIME = 5;

    /**
     *按日计息
     */
     int REPAYMENT_TYPE_DAILY_INTEREST = 6;

    /**
     * 区间额度
     */
     int LOAN_AMOUNT_TYPE_BETWEEN = 1;

    /**
     * 固定额度（此时 最大最小额度应该相等）
     */
     int LOAN_AMOUNT_TYPE_EXACT = 2;

    /**
     *全额本金
     */
     int PENALTY_GROUP_FULL = 1;

    /**
     *逾期本金
     */
     int PENALTY_GROUP_OVERDUE = 2;

    /**
     *剩余本金
     */
     int PENALTY_GROUP_BALANCE = 3;

    /**
     *全部应还
     */
     int PENALTY_GROUP_ALL = 4;


    /**
     * 不允许多笔借款
     */
    int NOT_ALLOW_MULTIPLE = 0;

    /**
     * 允许多笔借款
     */
    int ALLOW_MULTIPLE = 1;

    /**
     * 不允许提前结清
     */
    int NOT_ALLOW_PREPAY = 0;

    /**
     * 允许提前结清
     */
    int ALLOW_PREPAY = 1;

    /**
     * 不收取罚息款
     */
    int NOT_COLLECT_PENALTY = 0;

    /**
     * 收取罚息款
     */
    int COLLECT_PENALTY = 1;

}


