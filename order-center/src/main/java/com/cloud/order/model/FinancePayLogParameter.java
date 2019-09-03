package com.cloud.order.model;

import lombok.Data;

/**
 * 交易记录查询的参数
 * @author bjy
 * @date 2019/2/27 0027 16:29
 */
@Data
public class FinancePayLogParameter {
    /**
     *   开始日期
     */
    private String beginDate;
    /**
     * 结束日期
     */
    private String endDate;
    /**
     *     客户编号
     */
    private String customerNo;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 应还日期
     */
    private String repayDate;
    /**
     * 交易类型
     */
    private int loanType;
    /**
     * 处理对象
     */
    private int handler;
    /**
     * 订单状态
     */
    private int orderStatus;
    /**
     * 页数
     */
    private int page=1;
    /**
     * 每页多少条
     */
    private int limit=10;
}
