package com.cloud.order.model;

/**
 * 展期成功以后 生成展期合同
 * Created by hasee on 2019/4/3.
 */
public class ContractExtensionAgreement {
    private Integer loanId;
    //借款人
    public String borrower;
    //出借人
    public String lender;
    //合同编号
    public String contractNo;

    private String applyNo;
    //借款金额
    public String borrowAmount;
    //合同到期日
    public String loanEndDate;
    //展期到期日
    public String extensionEndDate;
    ////展期之后要还的钱  相当于还款计划  也就是本金
    public String extensionAmount;
    //展期利息
    public String extensionInterest;
    //签署日期
    public String signedDate;

    public String extensionSignature;

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getExtensionSignature() {
        return extensionSignature;
    }

    public void setExtensionSignature(String extensionSignature) {
        this.extensionSignature = extensionSignature;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getBorrowAmount() {
        return borrowAmount;
    }

    public void setBorrowAmount(String borrowAmount) {
        this.borrowAmount = borrowAmount;
    }

    public String getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(String loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public String getExtensionEndDate() {
        return extensionEndDate;
    }

    public void setExtensionEndDate(String extensionEndDate) {
        this.extensionEndDate = extensionEndDate;
    }

    public String getExtensionAmount() {
        return extensionAmount;
    }

    public void setExtensionAmount(String extensionAmount) {
        this.extensionAmount = extensionAmount;
    }

    public String getExtensionInterest() {
        return extensionInterest;
    }

    public void setExtensionInterest(String extensionInterest) {
        this.extensionInterest = extensionInterest;
    }

    public String getSignedDate() {
        return signedDate;
    }

    public void setSignedDate(String signedDate) {
        this.signedDate = signedDate;
    }
}
