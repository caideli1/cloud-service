package com.cloud.order.model.kudos;

import lombok.Data;

@Data
public class BorrowerInfoApiEntity {
    private String partnerBorrowerId;

    private String kudosBorrowerId;

    private String borrowerEmail;

    private String BorrowerEmailId;

    private String borrowerMob;

    private String borrowerCurrAddr;

    private String borrowerCurrCity;

    private String borrowerCurrState;

    private String borrowerCurrPincode;

    private String borrowerPermAddress;

    private String borrowerPermCity;

    private String borrowerPermState;

    private String borrowerPermPincode;

    private String borrowerMaritalStatus;

    private String borrowerQualification;

    private String borrowerEmployerNme;

    private String borrowerEmployerId;

    private String borrowerSalary;

    private String borrowerCreditScore;

    private String borrowerFoir;

    private String borrowerAcHolderNme;

    private String borrowerBnkNme;

    private String borrowerAcNum;

    private String borrowerBnkIfsc;

    private String partnerLoanId;

    private String kudosLoanId;

    private String loanTyp;

    private String loanTenure;

    private String loanInstallmentNum;

    private String loanEmiFreq;

    private String loanPrinAmt;

    private String loanProcFee;

    private String loanConvFee;

    private String loanCouponAmt;

    private String loanAmtDisbursed;

    private String loanIntRt;

    private String loanIntAmt;

    private String loanEmiDte1;

    private String loanEmiAmt1;

    private String loanEmiAmt2;

    private String loanEndDte;

    private String loanSanctionLetter;

    private String loanAgreementDoc;

    private String loanDisbursementUpdStatus;

    private String loanDisbursementUpdDte;

    private String loanDisbursementTransDte;

    private String disbursementTransTracNum;

    private String loanEmiRecdNum;

    private String kudosLoanTyp;

    private String secretKey;//kudos 提供的密钥
    //0:men 1:female
    private String sex;
}
