package com.cloud.model.cibil;

public class LoanCIBILEntity {
	private String borrowerFName;//借款人名字
	private String borrowerMName;//借款人中间名
	private String borrowerLName;//借款人姓氏
	private String borrowerDOB;//借款人出生日期格式应为DDMMYYYY
	private String borrowerGender;//借款人性别
	private String borrowerEmail;//借款人电子邮件地址
	private String borrowerCompanyName;//借款人公司名称
	private String idnumber;//借款人身份证号码为Idtype
	private String borrowerAdhaar;//aadhaar卡号
	private String idtype;//借款人ID类型有关不同类型的ID，请参阅附录1
	private String borrowerPhone;//借款人电话号码
	private String borrowerPhoneType;//借款人电话号码类型 - 参见附录2
	private String borrowerAddr1;//借款人地址第1行
	private String borrowerAddr2;//借款人地址第2行
	private String borrowerAddr3;//借款人地址第3行
	private String borrowerAddr4;//借款人地址第4行
	private String borrowerAddr5;//借款人地址第5行
	private String borrowerAddrType;//借款人地址类型参见附录3
	private String borrowerCity;//借款人城市
	private String borrowerPincode;//借款人地址第1行
	private String borrowerResiType;//借款人居住类型
	private String borrowerStateCode;//借款人州代码 - 参见附录4
	private String borrowerRequestAmount;//借款人申请金额
	private String borrowerLoanPurpose;//借款人贷款目的{请使用值05}
	private String borrowerRepaymentPerMnths;//借款人还款期限为几个月
	private String consumerConsentForUIDAIAuthentication;//消费者同意UIDAI认证真或假值    
	private String gSTStateCode;//消费税国家代码 - 参见附录5
	private String requestReferenceNum;//请求参考编号（仅使用数字0-9）
	private String borrowerIncome;//没有范围的借款人收入，以及逗号分隔符或小数（例如：12500）
	private String userId;
	private String secretKet;
	private String postcode;
	
	public String getBorrowerAdhaar() {
		return borrowerAdhaar;
	}
	public void setBorrowerAdhaar(String borrowerAdhaar) {
		this.borrowerAdhaar = borrowerAdhaar;
	}
	public String getBorrowerFName() {
		return borrowerFName;
	}
	public void setBorrowerFName(String borrowerFName) {
		this.borrowerFName = borrowerFName;
	}
	public String getBorrowerMName() {
		return borrowerMName;
	}
	public void setBorrowerMName(String borrowerMName) {
		this.borrowerMName = borrowerMName;
	}
	public String getBorrowerLName() {
		return borrowerLName;
	}
	public void setBorrowerLName(String borrowerLName) {
		this.borrowerLName = borrowerLName;
	}
	public String getBorrowerDOB() {
		return borrowerDOB;
	}
	public void setBorrowerDOB(String borrowerDOB) {
		this.borrowerDOB = borrowerDOB;
	}
	public String getBorrowerGender() {
		return borrowerGender;
	}
	public void setBorrowerGender(String borrowerGender) {
		this.borrowerGender = borrowerGender;
	}
	public String getBorrowerEmail() {
		return borrowerEmail;
	}
	public void setBorrowerEmail(String borrowerEmail) {
		this.borrowerEmail = borrowerEmail;
	}
	public String getBorrowerCompanyName() {
		return borrowerCompanyName;
	}
	public void setBorrowerCompanyName(String borrowerCompanyName) {
		this.borrowerCompanyName = borrowerCompanyName;
	}
	public String getIdnumber() {
		return idnumber;
	}
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	public String getIdtype() {
		return idtype;
	}
	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}
	public String getBorrowerPhone() {
		return borrowerPhone;
	}
	public void setBorrowerPhone(String borrowerPhone) {
		this.borrowerPhone = borrowerPhone;
	}
	public String getBorrowerPhoneType() {
		return borrowerPhoneType;
	}
	public void setBorrowerPhoneType(String borrowerPhoneType) {
		this.borrowerPhoneType = borrowerPhoneType;
	}
	public String getBorrowerAddr1() {
		return borrowerAddr1;
	}
	public void setBorrowerAddr1(String borrowerAddr1) {
		this.borrowerAddr1 = borrowerAddr1;
	}
	public String getBorrowerAddr2() {
		return borrowerAddr2;
	}
	public void setBorrowerAddr2(String borrowerAddr2) {
		this.borrowerAddr2 = borrowerAddr2;
	}
	public String getBorrowerAddr3() {
		return borrowerAddr3;
	}
	public void setBorrowerAddr3(String borrowerAddr3) {
		this.borrowerAddr3 = borrowerAddr3;
	}
	public String getBorrowerAddr4() {
		return borrowerAddr4;
	}
	public void setBorrowerAddr4(String borrowerAddr4) {
		this.borrowerAddr4 = borrowerAddr4;
	}
	public String getBorrowerAddr5() {
		return borrowerAddr5;
	}
	public void setBorrowerAddr5(String borrowerAddr5) {
		this.borrowerAddr5 = borrowerAddr5;
	}
	public String getBorrowerAddrType() {
		return borrowerAddrType;
	}
	public void setBorrowerAddrType(String borrowerAddrType) {
		this.borrowerAddrType = borrowerAddrType;
	}
	public String getBorrowerCity() {
		return borrowerCity;
	}
	public void setBorrowerCity(String borrowerCity) {
		this.borrowerCity = borrowerCity;
	}
	public String getBorrowerPincode() {
		return borrowerPincode;
	}
	public void setBorrowerPincode(String borrowerPincode) {
		this.borrowerPincode = borrowerPincode;
	}
	public String getBorrowerResiType() {
		return borrowerResiType;
	}
	public void setBorrowerResiType(String borrowerResiType) {
		this.borrowerResiType = borrowerResiType;
	}
	public String getBorrowerStateCode() {
		return borrowerStateCode;
	}
	public void setBorrowerStateCode(String borrowerStateCode) {
		this.borrowerStateCode = borrowerStateCode;
	}
	public String getBorrowerRequestAmount() {
		return borrowerRequestAmount;
	}
	public void setBorrowerRequestAmount(String borrowerRequestAmount) {
		this.borrowerRequestAmount = borrowerRequestAmount;
	}
	public String getBorrowerLoanPurpose() {
		return borrowerLoanPurpose;
	}
	public void setBorrowerLoanPurpose(String borrowerLoanPurpose) {
		this.borrowerLoanPurpose = borrowerLoanPurpose;
	}
	public String getBorrowerRepaymentPerMnths() {
		return borrowerRepaymentPerMnths;
	}
	public void setBorrowerRepaymentPerMnths(String borrowerRepaymentPerMnths) {
		this.borrowerRepaymentPerMnths = borrowerRepaymentPerMnths;
	}
	public String getConsumerConsentForUIDAIAuthentication() {
		return consumerConsentForUIDAIAuthentication;
	}
	public void setConsumerConsentForUIDAIAuthentication(String consumerConsentForUIDAIAuthentication) {
		this.consumerConsentForUIDAIAuthentication = consumerConsentForUIDAIAuthentication;
	}
	public String getgSTStateCode() {
		return gSTStateCode;
	}
	public void setgSTStateCode(String gSTStateCode) {
		this.gSTStateCode = gSTStateCode;
	}
	public String getRequestReferenceNum() {
		return requestReferenceNum;
	}
	public void setRequestReferenceNum(String requestReferenceNum) {
		this.requestReferenceNum = requestReferenceNum;
	}
	public String getBorrowerIncome() {
		return borrowerIncome;
	}
	public void setBorrowerIncome(String borrowerIncome) {
		this.borrowerIncome = borrowerIncome;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSecretKet() {
		return secretKet;
	}
	public void setSecretKet(String secretKet) {
		this.secretKet = secretKet;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
}
