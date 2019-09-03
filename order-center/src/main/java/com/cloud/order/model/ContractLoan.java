package com.cloud.order.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hasee on 2019/4/12.
 */
public class ContractLoan {

    private int loanId;
    //签约时间
    public Date loanDate;
    //
    public String aadhaar;
    //申请时间
    public Date applyDate;
    //用户名字
    public String borrower;
    //公司地址
    public String companyAddress;
    //家庭地址
    public String homeAddress;
    //貸款結束時間
    public Date loanEndDate;
    //申請本金
    public BigDecimal applyAmount;
    //借款周期
    public int period;
    //利息利率
    public String interestRate;
    //利息金额
    public BigDecimal interestAmount;
    //砍头费
    public BigDecimal headAmount;
    //应还金额
    public BigDecimal repayAmount;
    //逾期利息利率
    public String overdueInterestRate;
    //借据编号
    public String contractNo;
    //借据编号
    public String applyNo;
    //设备名称
    public String deviceName;
    //设备id
    public String deviceId;
    //签名
    public  String borrowerSignature;

    public String kudosSignature;

    public String signaturePlace;

    public String ip;

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    public String getKudosSignature() {
        return kudosSignature;
    }

    public void setKudosSignature(String kudosSignature) {
        this.kudosSignature = kudosSignature;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSignaturePlace() {
        return signaturePlace;
    }

    public void setSignaturePlace(String signaturePlace) {
        this.signaturePlace = signaturePlace;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Date getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(Date loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(BigDecimal interestAmount) {
        this.interestAmount = interestAmount;
    }

    public BigDecimal getHeadAmount() {
        return headAmount;
    }

    public void setHeadAmount(BigDecimal headAmount) {
        this.headAmount = headAmount;
    }

    public BigDecimal getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(BigDecimal repayAmount) {
        this.repayAmount = repayAmount;
    }

    public String getOverdueInterestRate() {
        return overdueInterestRate;
    }

    public void setOverdueInterestRate(String overdueInterestRate) {
        this.overdueInterestRate = overdueInterestRate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        deviceName = deviceName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getBorrowerSignature() {
        return borrowerSignature;
    }

    public void setBorrowerSignature(String borrowerSignature) {
        this.borrowerSignature = borrowerSignature;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }
}
