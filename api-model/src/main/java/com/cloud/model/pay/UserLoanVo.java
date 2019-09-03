package com.cloud.model.pay;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/27 16:14
 * 描述：
 */
@Data
public class UserLoanVo implements Serializable {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 客户id
     */
    private Integer userId;

    /**
     * 产品id
     */
    private Integer productId;

    /**
     * 借据编号
     */
    private String loanNumber;

    /**
     * 客户名称
     */
    private String userName;

    /**
     * 手机号
     */
    private String userPhone;

    /**
     * 逾期天数
     */
    private Integer overdueDay;

    /**
     * 放款通道 0: 无 1：通道一 2：通道二 3：通道三
     */
    private Integer loanChannel;

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
    private String lateCharge;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 提前结清日期
     */
    private String balanceDate;

    /**
     * 逾期起始日期
     */
    private String overdueStartDate;

    /**
     * 展期结束日期
     */
    private String extensionDueDate;

    /**
     * 实际结清日期
     */
    private String realClosingDate;

    /**
     * 贷款应还日期
     */
    private String loanEndDate;

    /**
     * 贷款开始日期
     */
    private String loanStartDate;

    /**
     * 0 未根进 1 已跟进
     */
    private Integer isFollow;
}
