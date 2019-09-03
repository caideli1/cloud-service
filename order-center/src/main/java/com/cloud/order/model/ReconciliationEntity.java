package com.cloud.order.model;

public class ReconciliationEntity {
	private String kudosLoanid;//这包含正在发送的请求的kudos贷款ID
	private String kudosClientid;//这包含正在发送的请求的kudos客户端ID
	private String partnerloanid;//这包含正在发送的请求的合作伙伴贷款ID
	private String partnerclientid;//它包含正在发送的请求的合作伙伴客户端ID
	private String loanReconciliationStatus;//这包含Kudos的对帐状态更新，此更新通常在贷款EMI payemnts启动后定期完成，这是贷款还款的状态更新。在此之前需要调用的相关端点是贷款关闭，贷款还款延期，贷款需求票据检查，贷款预约申请，贷款EMI支付更新。
	private String loanReconciliationDateSent;//其中包含此请求的对帐更新日期。这将是当前时间戳，而不是最后一个对帐请求时间
	private String secretKey;
	
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
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
	public String getLoanReconciliationStatus() {
		return loanReconciliationStatus;
	}
	public void setLoanReconciliationStatus(String loanReconciliationStatus) {
		this.loanReconciliationStatus = loanReconciliationStatus;
	}
	public String getLoanReconciliationDateSent() {
		return loanReconciliationDateSent;
	}
	public void setLoanReconciliationDateSent(String loanReconciliationDateSent) {
		this.loanReconciliationDateSent = loanReconciliationDateSent;
	}
	
}
