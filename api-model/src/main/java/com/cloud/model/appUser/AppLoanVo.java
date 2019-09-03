package com.cloud.model.appUser;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AppLoanVo {
    /**
     * userId	Integer	用户ID
     * productId	Integer	产品ID
     * loanNumber	String	借据编号
     * borrowAmount	Bigdecimal	借款金额
     * loanStatus	int	贷款状态 :0>正常；1>逾期；2>完成；3>未激活；4>已处置；
     * loanStartDate	long	贷款开始日期
     * loanEndDate	long	贷款应还(结束)日期
     * term	int	期限
     */
    private Long userId;
    private Integer productId;
    private String loanNumber;
    private BigDecimal borrowAmount;
    private int loanStatus;
    private String loanStartDate;
    private String loanEndDate;
    private int overdueStatus;
    private int term;
    //当前应还金额
    private BigDecimal currTotalRepayAmount;
}
