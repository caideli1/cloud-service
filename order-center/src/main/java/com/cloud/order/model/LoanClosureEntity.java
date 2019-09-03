package com.cloud.order.model;

/**
 * Request 5 - Loan Closure
 * */
public class LoanClosureEntity {
	private String kudosLoanid;
	private String kudosClientid;
	private String partnerloanid;
	private String partnerclientid;
	private String loanEnddate;
	private String partnerUpdate;
	private String loanReconciliationDateSent;
	private String secretKet;
	
	public String getSecretKet() {
		return secretKet;
	}
	public void setSecretKet(String secretKet) {
		this.secretKet = secretKet;
	}
	public String getKudosLoanid() {
		return kudosLoanid;
	}
	public void setKudosLoanid(String kudosLoanid) {
		this.kudosLoanid = kudosLoanid;
	}
	public String getKudosClientid() {
		return kudosClientid;
	}
	public void setKudosClientid(String kudosClientid) {
		this.kudosClientid = kudosClientid;
	}
	public String getPartnerloanid() {
		return partnerloanid;
	}
	public void setPartnerloanid(String partnerloanid) {
		this.partnerloanid = partnerloanid;
	}
	public String getPartnerclientid() {
		return partnerclientid;
	}
	public void setPartnerclientid(String partnerclientid) {
		this.partnerclientid = partnerclientid;
	}
	public String getLoanEnddate() {
		return loanEnddate;
	}
	public void setLoanEnddate(String loanEnddate) {
		this.loanEnddate = loanEnddate;
	}
	public String getPartnerUpdate() {
		return partnerUpdate;
	}
	public void setPartnerUpdate(String partnerUpdate) {
		this.partnerUpdate = partnerUpdate;
	}
	public String getLoanReconciliationDateSent() {
		return loanReconciliationDateSent;
	}
	public void setLoanReconciliationDateSent(String loanReconciliationDateSent) {
		this.loanReconciliationDateSent = loanReconciliationDateSent;
	}
	
}
