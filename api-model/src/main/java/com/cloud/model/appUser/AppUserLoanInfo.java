package com.cloud.model.appUser;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AppUserLoanInfo {
    private Integer id;
    //用户ID
    private Long userId;
    //产品ID
    private Integer productId;
    //借据编号 MN00******PD
    private String loanNumber;
    //用户名称
    private String userName;
    //用户手机号
    private String userPhone;
    //逾期天数
    private Integer overdueDay;
    //放款通道
    private Integer loanChannel;
    //借款金额
    private BigDecimal borrowAmount;
    //放款金额
    private BigDecimal loanMoneyAmount;
    //应还总额
    private BigDecimal shouldRepayAmount;
    //已还总额
    private BigDecimal paidTotalAmount;
    //罚息金额
    private BigDecimal lateCharge;
    //展期支出
    private BigDecimal deferDisbursement;
    /**
     * 是否展期
     * 0>否；1>是
     */
    private int overdueStatus;
    /**
     * 贷款状态
     * 0>正常；1>逾期；2>完成；3>未激活；4>已处置
     */
    private int loanStatus;
    /**
     * 是否提前结清
     * 0>否；1>是
     */
    private int balanceDateStatus;
    private Date createTime;
    //提前结清日期
    private Date balanceDate;
    //逾期起始日期
    private Date overdueStartDate;
    //展期结束日期
    private Date extensionDueDate;
    //实际结清日期
    private Date realClosingDate;
    //贷款应还日期
    private Date loanEndDate;
    //贷款开始日期
    private Date loanStartDate;
    //产品期限
    private int term;
    private String bankName;
    private String bankAccount;
    /**
     * 签名图片URL
     */
    private String signatureUrl;
    private String sessionId;
    private String applyNum;

    //放款通道
    private Integer repayChannel;
}
