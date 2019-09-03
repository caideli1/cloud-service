package com.cloud.order.model;

import lombok.Data;

/**
 * 借款请求
 * Body
 * */
@Data
public class LoanRequestEntity {
	private String partnerLoanid;//	string	Body	Required[Mandatory]	合作伙伴的贷款ID。	接受字母数字字符，最大长度200
	private String partnerClientid;//	string	Body	Required[Mandatory]	合作伙伴客户ID。	接受字母数字字符，最大长度200
	private String partnerStatus;//	string	Body	Required[Mandatory]	合作伙伴端的贷款状况。	字母表但没有接受特殊字符，最大长度为350
	private String partnerBucket;//	string	Body	Required[Mandatory]	合作伙伴或合作伙伴本身的合作伙伴，如果作为贷方的合作伙伴是子贷款，这是申请。	字母表接受，最大长度200
	private String borrowerFName;//	string	Body	Required[Mandatory]	借款人名字。
	private String borrowerMName;//	string	Body	Required[Mandatory]	借款人中间名。
	private String borrowerLName;//	string	Body	Required[Mandatory]	借款人姓氏。
	private String borrowerEmployerName;//	string	Body	Required	借款人雇主姓名（通常是公司名称）。
	private String borrowerEmailId;//	string	Body	Required[Mandatory]	借款人电子邮件地址。（使用预共享编码方法来共享此参数）
	private String borrowerPhone;//	string	Body	Required[Mandatory]	借款人电话号码。（使用预共享编码方法来共享此参数）
	private String borrowerDob;//	string	Body	Required[Mandatory]	借款人出生日期。
	private String borrowerLoanPurpose;//	string	Body	Required[Mandatory]	借款人贷款目的。
	private String borrowerSex;//	string	Body	Required[Mandatory]	借款人性别（M / F / O）。
	private String loanAmount;//	string	Body	Required[Mandatory]	贷款额度。
	private String processingFees;//	string	Body	Required[Mandatory]	处理费用方便收费。
	private String loanDisbAmount;//	string	Body	Required[Mandatory]	贷款支付金额
	private String loanType;//	string	Body	Required[Mandatory]	贷款申请类型。
	private String numInstallments;//	string	Body	Required[Mandatory]	分期付款期数。
	private String loanTenure;//	string	Body	Required[Mandatory]	自支付日期起的回收期限。
	private String borrowerPanCard;//	string	Body	Required[Mandatory]	借款人潘卡。（使用预共享编码方法来共享此参数）
	private String borrowerAdhaar;//	string	Body	Required[Mandatory]	借款人Adhaar卡。（使用预共享编码方法来共享此参数）
	
	private String partnerComment;
	private String secretKey;
	private String orderNum;
	
	private String loanStartDate;//申请时间
	private String loanEndDate;//应还时间
	private String realClosingDate;//实际还时间
}
