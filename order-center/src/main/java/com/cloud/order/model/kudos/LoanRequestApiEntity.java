package com.cloud.order.model.kudos;

import lombok.Data;

/**
 * 借款api请求
 * Body
 */
@Data
public class LoanRequestApiEntity {
    private String partnerBorrowId;
    /**
     * 借款人名字
     */
    private String borrowerFName;
    /**
     * 借款人中间名
     */
    private String borrowerMName;
    /**
     * 借款人姓氏
     */
    private String borrowerLName;

    private String borrowerEmployerNme;

    private String borrowerEmail;
    /**
     * Borrowers Phone Number.
     */
    private String borrowerMob;

    private String borrowerDob;

    private String borrowerSex;

    private String borrowerPanNum;

    private String borrowerAdhaarNum;

    private String partnerLoanId;

    private String partnerLoanStatus;

    private String partnerLoanBucket;

    private String loanPurpose;

    private String loanAmt;

    private String loanProcFee;

    private String loanConvFee;
    /**
     * Loan disbursement amount
     */
    private String loanDisbursementAmt;

    private String loanTyp;

    private String loanInstallmentNum;

    private String loanTenure;

    private String loanStartDate;

    private String loanEndDate;

    private String realClosingDate;

    private String secretKey;
}
