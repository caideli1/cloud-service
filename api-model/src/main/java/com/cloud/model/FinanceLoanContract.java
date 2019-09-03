package com.cloud.model;

import java.util.Date;

import lombok.Data;

@Data
public class FinanceLoanContract {
	private String contractNo;
	
	private Integer loanId;
	
	private String contractUrl;
	
	private String contractType;
	
	private String createUser;
	
	private Date createTime;

	private String applyNo;

}
