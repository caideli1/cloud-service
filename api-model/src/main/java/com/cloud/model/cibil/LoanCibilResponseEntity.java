package com.cloud.model.cibil;

import lombok.Data;

import java.util.Date;

/**
 * cibil 接口返回部分数据存储
 * */
@Data
public class LoanCibilResponseEntity {
	private String referenceNumber;//   手机号

	private String password;//   密码

	private String user;// 用户

	private String memberCode;

	private String businessUnitId;

	private String applicationId;

	private String solutionSetId;

	private String step;

	private String start;

	private String code;

	private String description;

	private String panNumber;

	private String score;

	private String scoreType;

	private int orderId;

	private Date createTime;

	private String info;
}
