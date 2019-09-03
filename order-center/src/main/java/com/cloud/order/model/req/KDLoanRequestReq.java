package com.cloud.order.model.req;

import java.time.LocalDateTime;

import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kd_loan_response")
public class KDLoanRequestReq {
	private Integer id;
	private String orderNo;
	private String status;
	private String resultCode;
	private String message;
	private String kudosloanid;
	private String kudosborrowerid;
	private String onboarded;
	private String accountStatus;
	private String vaAcc;
	private String ifsc;
	private String bankname;
	private String info;//api文档上的
	private String reason;//接口实际返回的
	private String timestampofRequest;
	private String error;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
}
