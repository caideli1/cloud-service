package com.cloud.order.model;

public class LoanBorrowerInfoEntity {
	
	private String kudosLoanid;//这包含正在发送的请求的kudos贷款ID    接口一返回的成功数据
	private String kudosClientid;//这包含正在发送的请求的kudos客户端ID   接口一返回的成功数据
	private String partnerLoanid;//这包含正在发送的请求的合作伙伴贷款ID
	private String partnerClientid;//它包含正在发送的请求的合作伙伴客户端ID
	private String borrowerEmailid;//这包含正在发送的请求的借用者电子邮件地址
	private String borrowerPhone;//这包含正在发送的请求的借用者电话号码
	private String borrowerEmployerName;//这包含正在发送的请求的借用者雇主名称
	private String borrowerCurrAddress1;//这包含正在发送的请求的借用者当前地址1
	private String borrowerCurrAddress2;//这包含正在发送的请求的借用者当前地址2
	private String borrowerCurrAddress3;//它包含正在发送的请求的借用者当前地址3
	private String borrowerCurrCity;//这包含正在发送的请求的借用者当前城市
	private String borrowerCurrState;//这包含正在发送的请求的借用者当前状态
	private String borrowerCurrPincode;//它包含正在发送的请求的借用者当前密码
	private String borrowerPermAddress1;//这包含正在发送的请求的借用者永久地址1
	private String borrowerPermAddress2;//这包含正在发送的请求的借用者永久地址2
	private String borrowerPermAddress3;//这包含正在发送的请求的借用者永久地址3
	private String borrowerPermCity;//这包含正在发送的请求的借用者永久城市
	private String borrowerPermState;//这包含正在发送的请求的借用者永久状态
	private String borrowerPermPincode;//这包含正在发送的请求的借用者永久密码
	private String borrowerSalary;//这包含借款人的月薪金额
	private String borrowerMaritalStatus;//这包含正在发送的请求的借款人婚姻状况
	private String borrowerQualification;//这包含借款人对发送请求的最高教育资格
	private String borrowerCreditscore;//这包含正在发送的请求的借款人信用评分
	private String borrowerfoir;//这包含正在发送的请求的借用者foir
	private String partnerLoanType;//这包含分配给正在发送的请求的伙伴的贷款类型
	private String loanTenure;//这包含正在发送的请求的贷款期限
	private String loanNumberOfInstallments;//这包含偿还正在发送的请求的贷款的安装数量
	private String loanEMIFreq;//这包含正在发送的请求的贷款频率（每日，每周，每月，每两周）
	private String loanPrincipalAmount;//这包含为正在发送的请求的贷款请求的本金金额
	private String loanProcessingFees;//其中包含正在发送的请求的贷款收取的处理费
	private String loanConvinienceFees;//这包含正在发送的请求的贷款的便利费用
	private String loanCouponAmount;//这包含正在发送的请求的优惠券代码
	private String loanAmountDisbursed;//这包含为正在发送的请求支付的贷款金额
	private String loanInterestRate;//这包含正在发送的请求的贷款利率
	private String loanInterestAmount;//这包含正在发送的请求的利息金额
	private String loan1stEmiDate;//这包含正在发送的请求的贷款偿还的第一个emi日期
	private String loan1stEmiAmnt;//这包含正在发送的请求的第一笔emi偿还贷款金额
	private String loanSubsqntEmiAmnt;//这包含正在发送的请求的后续emi贷款偿还金额
	private String loanendDate;//这包含正在发送的请求的偿还贷款的最后一个emi日期
	private String borrowerAccountHolderName;//这包含借款人帐户持有人姓名，如发送请求的银行记录中所示
	private String borrowerBankName;//其中包含借款人对其发送请求的帐户所在的银行名称
	private String borrowerAccountNumber;//其中包含正在发送的请求的借款人的银行帐号
	private String borrowerIFSCCode;//这包含银行的IFSC代码，其中借款人拥有其正在发送的请求的帐户
	private String borrowerEmployerId;//这包含借款人的雇主ID号码正在发送的请求
	private String borrowerPanFile;//这包含PAN卡文件的路径。您可以发送由';'分隔的多个文件，URL之间不应有空格，您可以发送HTTP和HTTPS URL。访问URL不应该进行身份验证。文件类型必须严格为PDF。
	private String borrowerAdhaarFile;//这包含Adhaar卡文件的路径。您可以发送由';'分隔的多个文件，URL之间不应有空格，您可以发送HTTP和HTTPS URL。访问URL不应该进行身份验证。文件类型必须严格为PDF。
	private String borrowerBankStatementFile;//它包含Bank Statement文件的路径。您可以发送由';'分隔的多个文件，URL之间不应有空格，您可以发送HTTP和HTTPS URL。访问URL不应该进行身份验证。文件类型必须严格为PDF。
	private String borrowerCIBILFile;//它包含CIBIL文件的路径。您可以发送由';'分隔的多个文件，URL之间不应有空格，您可以发送HTTP和HTTPS URL。访问URL不应该进行身份验证。文件类型必须严格为PDF。
	private String borrowerPhotoFile;//这包含照片文件的路径。您可以发送由';'分隔的多个文件，URL之间不应有空格，您可以发送HTTP和HTTPS URL。访问URL不应该进行身份验证。文件类型必须严格为PDF。
	private String borrowerSanctionLetter;//这包含制裁信函文件的路径。您可以发送由';'分隔的多个文件，URL之间不应有空格，您可以发送HTTP和HTTPS URL。访问URL不应该进行身份验证。文件类型必须严格为PDF。
	private String borrowerAgreementfile;//这包含协议文件的路径。您可以发送由';'分隔的多个文件，URL之间不应有空格，您可以发送HTTP和HTTPS URL。访问URL不应该进行身份验证。文件类型必须严格为PDF。
	private String loanDisbursementUpdateStat;//其中包含与正在发送的请求的贷款支付相关的状态
	private String loanDisbInfoUpdateTime;//这包含正在发送的请求的支付信息更新时间
	private String loanDisbTxnTime;//这包含正在发送的请求的支付转换时间/日期
	private String loanDisbTxnTrackingNum;//它包含正在发送的请求的支付交易事务参考号
	private String loanEMIPaymentCount;//其中包含合作伙伴收到的正在发送的请求的emi付款计数
	private String loanPlanType;// 这包含Kudos Loan Plan键入正在发送的请求
	
	private String pancardAccount;//pancard
	
	private String secretKey;//kudos 提供的密钥
	
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getPancardAccount() {
		return pancardAccount;
	}
	public void setPancardAccount(String pancardAccount) {
		this.pancardAccount = pancardAccount;
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
	public String getPartnerLoanid() {
		return partnerLoanid;
	}
	public void setPartnerLoanid(String partnerLoanid) {
		this.partnerLoanid = partnerLoanid;
	}
	public String getPartnerClientid() {
		return partnerClientid;
	}
	public void setPartnerClientid(String partnerClientid) {
		this.partnerClientid = partnerClientid;
	}
	public String getBorrowerEmailid() {
		return borrowerEmailid;
	}
	public void setBorrowerEmailid(String borrowerEmailid) {
		this.borrowerEmailid = borrowerEmailid;
	}
	public String getBorrowerPhone() {
		return borrowerPhone;
	}
	public void setBorrowerPhone(String borrowerPhone) {
		this.borrowerPhone = borrowerPhone;
	}
	public String getBorrowerEmployerName() {
		return borrowerEmployerName;
	}
	public void setBorrowerEmployerName(String borrowerEmployerName) {
		this.borrowerEmployerName = borrowerEmployerName;
	}
	public String getBorrowerCurrAddress1() {
		return borrowerCurrAddress1;
	}
	public void setBorrowerCurrAddress1(String borrowerCurrAddress1) {
		this.borrowerCurrAddress1 = borrowerCurrAddress1;
	}
	public String getBorrowerCurrAddress2() {
		return borrowerCurrAddress2;
	}
	public void setBorrowerCurrAddress2(String borrowerCurrAddress2) {
		this.borrowerCurrAddress2 = borrowerCurrAddress2;
	}
	public String getBorrowerCurrAddress3() {
		return borrowerCurrAddress3;
	}
	public void setBorrowerCurrAddress3(String borrowerCurrAddress3) {
		this.borrowerCurrAddress3 = borrowerCurrAddress3;
	}
	public String getBorrowerCurrCity() {
		return borrowerCurrCity;
	}
	public void setBorrowerCurrCity(String borrowerCurrCity) {
		this.borrowerCurrCity = borrowerCurrCity;
	}
	public String getBorrowerCurrState() {
		return borrowerCurrState;
	}
	public void setBorrowerCurrState(String borrowerCurrState) {
		this.borrowerCurrState = borrowerCurrState;
	}
	public String getBorrowerCurrPincode() {
		return borrowerCurrPincode;
	}
	public void setBorrowerCurrPincode(String borrowerCurrPincode) {
		this.borrowerCurrPincode = borrowerCurrPincode;
	}
	public String getBorrowerPermAddress1() {
		return borrowerPermAddress1;
	}
	public void setBorrowerPermAddress1(String borrowerPermAddress1) {
		this.borrowerPermAddress1 = borrowerPermAddress1;
	}
	public String getBorrowerPermAddress2() {
		return borrowerPermAddress2;
	}
	public void setBorrowerPermAddress2(String borrowerPermAddress2) {
		this.borrowerPermAddress2 = borrowerPermAddress2;
	}
	public String getBorrowerPermAddress3() {
		return borrowerPermAddress3;
	}
	public void setBorrowerPermAddress3(String borrowerPermAddress3) {
		this.borrowerPermAddress3 = borrowerPermAddress3;
	}
	public String getBorrowerPermCity() {
		return borrowerPermCity;
	}
	public void setBorrowerPermCity(String borrowerPermCity) {
		this.borrowerPermCity = borrowerPermCity;
	}
	public String getBorrowerPermState() {
		return borrowerPermState;
	}
	public void setBorrowerPermState(String borrowerPermState) {
		this.borrowerPermState = borrowerPermState;
	}
	public String getBorrowerPermPincode() {
		return borrowerPermPincode;
	}
	public void setBorrowerPermPincode(String borrowerPermPincode) {
		this.borrowerPermPincode = borrowerPermPincode;
	}
	public String getBorrowerSalary() {
		return borrowerSalary;
	}
	public void setBorrowerSalary(String borrowerSalary) {
		this.borrowerSalary = borrowerSalary;
	}
	public String getBorrowerMaritalStatus() {
		return borrowerMaritalStatus;
	}
	public void setBorrowerMaritalStatus(String borrowerMaritalStatus) {
		this.borrowerMaritalStatus = borrowerMaritalStatus;
	}
	public String getBorrowerQualification() {
		return borrowerQualification;
	}
	public void setBorrowerQualification(String borrowerQualification) {
		this.borrowerQualification = borrowerQualification;
	}
	public String getBorrowerCreditscore() {
		return borrowerCreditscore;
	}
	public void setBorrowerCreditscore(String borrowerCreditscore) {
		this.borrowerCreditscore = borrowerCreditscore;
	}
	public String getBorrowerfoir() {
		return borrowerfoir;
	}
	public void setBorrowerfoir(String borrowerfoir) {
		this.borrowerfoir = borrowerfoir;
	}
	public String getPartnerLoanType() {
		return partnerLoanType;
	}
	public void setPartnerLoanType(String partnerLoanType) {
		this.partnerLoanType = partnerLoanType;
	}
	public String getLoanTenure() {
		return loanTenure;
	}
	public void setLoanTenure(String loanTenure) {
		this.loanTenure = loanTenure;
	}
	public String getLoanNumberOfInstallments() {
		return loanNumberOfInstallments;
	}
	public void setLoanNumberOfInstallments(String loanNumberOfInstallments) {
		this.loanNumberOfInstallments = loanNumberOfInstallments;
	}
	public String getLoanEMIFreq() {
		return loanEMIFreq;
	}
	public void setLoanEMIFreq(String loanEMIFreq) {
		this.loanEMIFreq = loanEMIFreq;
	}
	public String getLoanPrincipalAmount() {
		return loanPrincipalAmount;
	}
	public void setLoanPrincipalAmount(String loanPrincipalAmount) {
		this.loanPrincipalAmount = loanPrincipalAmount;
	}
	public String getLoanProcessingFees() {
		return loanProcessingFees;
	}
	public void setLoanProcessingFees(String loanProcessingFees) {
		this.loanProcessingFees = loanProcessingFees;
	}
	public String getLoanConvinienceFees() {
		return loanConvinienceFees;
	}
	public void setLoanConvinienceFees(String loanConvinienceFees) {
		this.loanConvinienceFees = loanConvinienceFees;
	}
	public String getLoanCouponAmount() {
		return loanCouponAmount;
	}
	public void setLoanCouponAmount(String loanCouponAmount) {
		this.loanCouponAmount = loanCouponAmount;
	}
	public String getLoanAmountDisbursed() {
		return loanAmountDisbursed;
	}
	public void setLoanAmountDisbursed(String loanAmountDisbursed) {
		this.loanAmountDisbursed = loanAmountDisbursed;
	}
	public String getLoanInterestRate() {
		return loanInterestRate;
	}
	public void setLoanInterestRate(String loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}
	public String getLoanInterestAmount() {
		return loanInterestAmount;
	}
	public void setLoanInterestAmount(String loanInterestAmount) {
		this.loanInterestAmount = loanInterestAmount;
	}
	public String getLoan1stEmiDate() {
		return loan1stEmiDate;
	}
	public void setLoan1stEmiDate(String loan1stEmiDate) {
		this.loan1stEmiDate = loan1stEmiDate;
	}
	public String getLoan1stEmiAmnt() {
		return loan1stEmiAmnt;
	}
	public void setLoan1stEmiAmnt(String loan1stEmiAmnt) {
		this.loan1stEmiAmnt = loan1stEmiAmnt;
	}
	public String getLoanSubsqntEmiAmnt() {
		return loanSubsqntEmiAmnt;
	}
	public void setLoanSubsqntEmiAmnt(String loanSubsqntEmiAmnt) {
		this.loanSubsqntEmiAmnt = loanSubsqntEmiAmnt;
	}
	public String getLoanendDate() {
		return loanendDate;
	}
	public void setLoanendDate(String loanendDate) {
		this.loanendDate = loanendDate;
	}
	public String getBorrowerAccountHolderName() {
		return borrowerAccountHolderName;
	}
	public void setBorrowerAccountHolderName(String borrowerAccountHolderName) {
		this.borrowerAccountHolderName = borrowerAccountHolderName;
	}
	public String getBorrowerBankName() {
		return borrowerBankName;
	}
	public void setBorrowerBankName(String borrowerBankName) {
		this.borrowerBankName = borrowerBankName;
	}
	public String getBorrowerAccountNumber() {
		return borrowerAccountNumber;
	}
	public void setBorrowerAccountNumber(String borrowerAccountNumber) {
		this.borrowerAccountNumber = borrowerAccountNumber;
	}
	public String getBorrowerIFSCCode() {
		return borrowerIFSCCode;
	}
	public void setBorrowerIFSCCode(String borrowerIFSCCode) {
		this.borrowerIFSCCode = borrowerIFSCCode;
	}
	public String getBorrowerEmployerId() {
		return borrowerEmployerId;
	}
	public void setBorrowerEmployerId(String borrowerEmployerId) {
		this.borrowerEmployerId = borrowerEmployerId;
	}
	public String getBorrowerPanFile() {
		return borrowerPanFile;
	}
	public void setBorrowerPanFile(String borrowerPanFile) {
		this.borrowerPanFile = borrowerPanFile;
	}
	public String getBorrowerAdhaarFile() {
		return borrowerAdhaarFile;
	}
	public void setBorrowerAdhaarFile(String borrowerAdhaarFile) {
		this.borrowerAdhaarFile = borrowerAdhaarFile;
	}
	public String getBorrowerBankStatementFile() {
		return borrowerBankStatementFile;
	}
	public void setBorrowerBankStatementFile(String borrowerBankStatementFile) {
		this.borrowerBankStatementFile = borrowerBankStatementFile;
	}
	public String getBorrowerCIBILFile() {
		return borrowerCIBILFile;
	}
	public void setBorrowerCIBILFile(String borrowerCIBILFile) {
		this.borrowerCIBILFile = borrowerCIBILFile;
	}
	public String getBorrowerPhotoFile() {
		return borrowerPhotoFile;
	}
	public void setBorrowerPhotoFile(String borrowerPhotoFile) {
		this.borrowerPhotoFile = borrowerPhotoFile;
	}
	public String getBorrowerSanctionLetter() {
		return borrowerSanctionLetter;
	}
	public void setBorrowerSanctionLetter(String borrowerSanctionLetter) {
		this.borrowerSanctionLetter = borrowerSanctionLetter;
	}
	public String getBorrowerAgreementfile() {
		return borrowerAgreementfile;
	}
	public void setBorrowerAgreementfile(String borrowerAgreementfile) {
		this.borrowerAgreementfile = borrowerAgreementfile;
	}
	public String getLoanDisbursementUpdateStat() {
		return loanDisbursementUpdateStat;
	}
	public void setLoanDisbursementUpdateStat(String loanDisbursementUpdateStat) {
		this.loanDisbursementUpdateStat = loanDisbursementUpdateStat;
	}
	public String getLoanDisbInfoUpdateTime() {
		return loanDisbInfoUpdateTime;
	}
	public void setLoanDisbInfoUpdateTime(String loanDisbInfoUpdateTime) {
		this.loanDisbInfoUpdateTime = loanDisbInfoUpdateTime;
	}
	public String getLoanDisbTxnTime() {
		return loanDisbTxnTime;
	}
	public void setLoanDisbTxnTime(String loanDisbTxnTime) {
		this.loanDisbTxnTime = loanDisbTxnTime;
	}
	public String getLoanDisbTxnTrackingNum() {
		return loanDisbTxnTrackingNum;
	}
	public void setLoanDisbTxnTrackingNum(String loanDisbTxnTrackingNum) {
		this.loanDisbTxnTrackingNum = loanDisbTxnTrackingNum;
	}
	public String getLoanEMIPaymentCount() {
		return loanEMIPaymentCount;
	}
	public void setLoanEMIPaymentCount(String loanEMIPaymentCount) {
		this.loanEMIPaymentCount = loanEMIPaymentCount;
	}
	public String getLoanPlanType() {
		return loanPlanType;
	}
	public void setLoanPlanType(String loanPlanType) {
		this.loanPlanType = loanPlanType;
	}
	
//	params.put("Borrower_Pan_File", "http://moneed.oss-ap-south-1.aliyuncs.com/forge/container/docs/loaninfo/e581fb74-8548-49b8-91b6-b05d1573bb1a/loanAgreement.pdf?Expires=1541742089&OSSAccessKeyId=LTAIERXWP0OmDSf9&Signature=FhdJfz7TkBIsTrF5DguJ5s4QYlU%3D");
//	params.put("Borrower_Adhaar_File", "http://moneed.oss-ap-south-1.aliyuncs.com/forge/container/docs/loaninfo/e581fb74-8548-49b8-91b6-b05d1573bb1a/loanAgreement.pdf?Expires=1541742089&OSSAccessKeyId=LTAIERXWP0OmDSf9&Signature=FhdJfz7TkBIsTrF5DguJ5s4QYlU%3D");
//	params.put("Borrower_Bank_Statement_File", "http://moneed.oss-ap-south-1.aliyuncs.com/forge/container/docs/loaninfo/e581fb74-8548-49b8-91b6-b05d1573bb1a/loanAgreement.pdf?Expires=1541742089&OSSAccessKeyId=LTAIERXWP0OmDSf9&Signature=FhdJfz7TkBIsTrF5DguJ5s4QYlU%3D");
//	params.put("Borrower_CIBIL_File", "http://moneed.oss-ap-south-1.aliyuncs.com/forge/container/docs/loaninfo/e581fb74-8548-49b8-91b6-b05d1573bb1a/loanAgreement.pdf?Expires=1541742089&OSSAccessKeyId=LTAIERXWP0OmDSf9&Signature=FhdJfz7TkBIsTrF5DguJ5s4QYlU%3D");
//	params.put("Borrower_Photo_File", "http://moneed.oss-ap-south-1.aliyuncs.com/forge/container/docs/loaninfo/e581fb74-8548-49b8-91b6-b05d1573bb1a/loanAgreement.pdf?Expires=1541742089&OSSAccessKeyId=LTAIERXWP0OmDSf9&Signature=FhdJfz7TkBIsTrF5DguJ5s4QYlU%3D");
//	params.put("Borrower_Sanction_Letter", "http://moneed.oss-ap-south-1.aliyuncs.com/forge/container/docs/loaninfo/e581fb74-8548-49b8-91b6-b05d1573bb1a/loanAgreement.pdf?Expires=1541742089&OSSAccessKeyId=LTAIERXWP0OmDSf9&Signature=FhdJfz7TkBIsTrF5DguJ5s4QYlU%3D");
//	params.put("Borrower_Agreement_file", "http://moneed.oss-ap-south-1.aliyuncs.com/forge/container/docs/loaninfo/e581fb74-8548-49b8-91b6-b05d1573bb1a/loanAgreement.pdf?Expires=1541742089&OSSAccessKeyId=LTAIERXWP0OmDSf9&Signature=FhdJfz7TkBIsTrF5DguJ5s4QYlU%3D");

}
