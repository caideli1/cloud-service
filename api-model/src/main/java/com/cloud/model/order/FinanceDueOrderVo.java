package com.cloud.model.order;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
public class FinanceDueOrderVo {


    /**
     * 0：正常結清
     * 1：逾期還款
     * 2：展期
     */
    private Integer sendType;
    /**
     * 主键ID
     */
    private Integer id;
    /**
     * '订单号'
     */
    private String orderNo;
    /**
     * '客户编号'
     */
    private String customerNo;
    /**
     * 姓名
     */
    private String name;
    /**
     * '手机号'
     */
    private String mobile;
    /**
     * '借款金额'
     */
    private BigDecimal loanAmount;

    /**
     * '逾期天数'
     */
    private Integer dueDays;
    /**
     * '罚息金额'
     */
    private BigDecimal dueAmount;
    /**
     *  '逾期开始日期'，这个值的前一天是应该还款日期
     */
    private Date dueStart;
    /**
     * '逾期结束日期'，后台逾期订单列表的实际还款日期
     */
    private Date dueEnd;
    /**
     *  '是否展期：1:表示展期0：表示未展期'
     */
    private Integer  isExtension;

    /**
     * 借据状态
     */
    private String  loanStatus;

    /**
     * 逾期結束方式 1:綫上 2：綫下
     */
    private Integer  finishedType;

    /**
     * 贷款期限
     */

    private Integer loanPeriod;

}
