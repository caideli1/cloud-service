package com.cloud.order.model.req;

import java.time.LocalDateTime;

import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kd_loan_demandnote")
public class KDLoanDemandNoteReq {
	  private Integer id;
	  private String orderNo;
	  private String status;
	  private String resultCode;
	  private String message;
	  private String kudosloanid;
	  private String kudosborrowerid;
	  private String accountStatus;
	  private String onboarded;
	  private String timestampofRequest;
	  private String loanDemandNoteIssued;
	  private String loanDemandNoteIssuedDate;
	  private String details;
	  private String remark;
	  private String error;
	  private String info;
	  private String reason;
	  private LocalDateTime createTime;
	  private LocalDateTime updateTime;
}
