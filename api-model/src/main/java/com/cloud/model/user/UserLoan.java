package com.cloud.model.user;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yoga
 * @Description: 用户借据
 */

@Data
public class UserLoan implements Serializable {
    private static final long serialVersionUID = -7585969759736803070L;
    private Integer id;
    private Long userId;
    private Integer productId;
    private String productName;
    private Integer repaymentType;
    private String loanNumber;
    private String userName;
    private String userPhone;
    private String overdueDay;
    private String loanChannel;
    private String loanPeriod;
    private Date createTime;
    /**
     * 借款金额
     */
    private BigDecimal borrowAmount;
    /**
     * 放款金额
     */
    private BigDecimal loanMoneyAmount;
    /**
     * 应还本金
     */
    private BigDecimal shouldRepayAmount;
    /**
     * 已还本金
     */
    private BigDecimal paidTotalAmount;
    /**
     * 罚息金额
     */
    private BigDecimal lateCharge;
    /**
     * 展期支出
     */
    private BigDecimal deferDisbursement;
    /**
     * 是否展期 :0>否；1>是；
     */
    private Integer overdueStatus;
    /**
     * 贷款状态 :0>正常；1>逾期；2>完成；3>未激活；4>已处置；
     */
    private Integer loanStatus;
    /**
     * 是否提前结清 :0>否；1>是；
     */
    private Integer balanceDateStatus;
    /**
     * 提前结清日期
     */
    private Date balanceDate;
    /**
     * 逾期起始日期
     */
    private Date overdueStartDate;
    /**
     * 展期结束日期
     */
    private Date extensionDueDate;
    /**
     * 实际结清日期
     */
    private Date realClosingDate;
    /**
     * 贷款应还日期
     */
    private Date loanEndDate;
    /**
     * 贷款开始日期
     */
    private Date loanStartDate;
    /**
     * 展期次数
     */
    private int extensionCount;
    /**
     * 借据 订单的申请编号
     */
    private String applyNum;
    /**
     * 凭证Id
     */
    private String voucherId;

    /**
     * 处理对象 1平台 2用户 3：人工 4：手动
     */
    private Integer handler;

    /**
     * 应收总额
     */
    private BigDecimal shouldTotalRepay;

    /**
     * 实际还款
     */
    private BigDecimal repaidAmount;

    /**
     * 逾期结束日期
     */
    private Date overdueEndDate;
    /**
     * 逾期次数
     */
    private Integer dueCount;
    /**
     * 操作类型 1：结清 2：展期
     */
    private Integer operateType;
    /**
     * 转账金额
     */
    private BigDecimal transferAmount;
}
