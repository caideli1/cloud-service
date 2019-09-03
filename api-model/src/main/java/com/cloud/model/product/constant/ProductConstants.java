package com.cloud.model.product.constant;

/**
 * @author yoga
 * @Description: 产品常量
 * @date 2019/1/103:50 PM
 */
public interface ProductConstants {
    Integer STATUS_NEW = 1;
    Integer STATUS_ACTIVE = 2;
    Integer STATUS_ABANDON = 3;
    Integer NOT_RELEVSNCE = 0;
    Integer PRO_IS_DELETED = 1;


    //借据状态
    //未激活
    Integer NOT_ACTIVATED = 3;
    //还款中
    Integer PAYMENT = 0;
    //逾期--借据状态
    Integer OVERDUE = 1;
    //完成--借据状态
    Integer FINISH = 2;
    //已处置--借据状态
    Integer IS_DISPOSA = 4;
    //已失效
    Integer IS_INVALID = 5;


    //区间额度
    Integer INTERVAL_LINES = 1;
    //固定额度
    Integer FIXED_QUOTA = 2;
    //收取罚息
    Integer CHARGE_PENALTY = 1;
    //不提前结清
    Integer NOT_BALANCE_DATE = 0;
    //不申请展期
    Integer NOT_OVERDUE = 0;
    //工作人员
    Integer WORKER_USER_TYPE = 1;
    //学校地址类型
    Integer SCHOOL_ADDRESS_TYPE = 2;
    //工作地址类型
    Integer WORK_ADDRESS_TYPE = 1;
    //家庭地址类型
    Integer HOME_ADDRESS_TYPE = 0;
    //无人审核
    Long UNMANNED = -1L;
    //审核中状态
    Integer IN_REVIEW = 1;
    /**
     * 先息后本
     */
    Integer REPAYMENT_TYPE_BEFORE_INTEREST_AFTER_PRINCIPAL = 1;

    /**
     * 等额本息
     */
    Integer REPAYMENT_TYPE_EQUAL_LOAN = 2;

    /**
     * 等额本金
     */
    Integer REPAYMENT_TYPE_EQUAL_PRINCIPAL = 3;

    /**
     * 等本等息
     */
    Integer REPAYMENT_TYPE_EQUAL_PRINCIPAL_AND_INTEREST = 4;

    /**
     * 一次性还本付息
     */
    Integer REPAYMENT_TYPE_DUE_TIME = 5;

    /**
     * 按日计息
     */
    Integer REPAYMENT_TYPE_DAILY_INTEREST = 6;

    /**
     * 区间额度
     */
    Integer LOAN_AMOUNT_TYPE_BETWEEN = 1;

    /**
     * 固定额度（此时 最大最小额度应该相等）
     */
    Integer LOAN_AMOUNT_TYPE_EXACT = 2;

    /**
     * 全额本金
     */
    Integer PENALTY_GROUP_FULL = 1;

    /**
     * 逾期本金
     */
    Integer PENALTY_GROUP_OVERDUE = 2;

    /**
     * 剩余本金
     */
    Integer PENALTY_GROUP_BALANCE = 3;

    /**
     * 全部应还
     */
    Integer PENALTY_GROUP_ALL = 4;

    /**
     * 不允许多笔借款
     */
    Integer NOT_ALLOW_MULTIPLE = 0;

    /**
     * 允许多笔借款
     */
    Integer ALLOW_MULTIPLE = 1;

    /**
     * 不允许提前结清
     */
    Integer NOT_ALLOW_PREPAY = 0;

    /**
     * 允许提前结清
     */
    Integer ALLOW_PREPAY = 1;

    /**
     * 不收取罚息款
     */
    Integer NOT_COLLECT_PENALTY = 0;

    /**
     * 收取罚息款
     */
    Integer COLLECT_PENALTY = 1;
}


