package com.cloud.order.model;

import lombok.Data;

/**
 * 放款记录查询参数
 * @author bjy
 * @date 2019/2/26 0026  16:52
 */
@Data
public class FinanceLoanParameter {
    /**
     * 开始日期
     */
    private String beginDate;
    /**
     * 结束日期
     */
    private String endDate;
    /**
     * 客户编号
     */
    private String customerNo;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 订单状态
     */
    private int orderStatus;
    /**
     * 放款通道
     */
    private int loanChannel;
    /**
     * 页数
     */
    private int page=1;
    /**
     * 每页多少条
     */
    private int limit=10;
}
