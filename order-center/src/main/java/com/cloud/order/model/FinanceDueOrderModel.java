package com.cloud.order.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author:         zhujingtao
 * @CreateDate:     2019/2/26 14:05
 * 对应数据库表 `finance_due_order`
 */
@Data
@Table(name="finance_due_order")
public class FinanceDueOrderModel  implements Serializable {

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
     * 續貸標識
     */
    private Integer renewalState;
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
