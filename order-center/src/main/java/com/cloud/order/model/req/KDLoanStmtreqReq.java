package com.cloud.order.model.req;

import java.time.LocalDateTime;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
@Table(name="kd_offline_transaction_notify")
public class KDLoanStmtreqReq {
	  private Integer id;
	  private String orderNo;
	  private String status;
	  private String resultCode;
	  private String message;
	  private String kudosloanid;
	  private String kudosborrowerid;
	  private String accountStatus;
	  
	  private String borrowerFname;
	  private String borrowerMname;
	  private String borrowerLname;
	  private String borrowerLoanAmnt;
	  private String tenure;
	  private String noOfInstallments;
	  private String noOfInstRecieved;
//	  @JsonProperty("1stemidate")
	  private String _1stemidate;
//	  @JsonProperty("1stemiamnt")
	  private String _1stemiamnt;

	  private String subsqntemiamnt;
	  private String enddate;
	  private String compliancestatus;
	  private String reconciliationstatus;
	  private String partnerstatus;
	  private String partnercomment;
//	  private String emipaymentscount;
	  private String disbursementdate;
	  private String lastupdatefrompartner;
	  private String nextemipaymentdate;
	  private String partner_loantype;
//	  private String borrower_loanpurpose;

	  private String remark;
	  private String error;
	  private String info;
	  private String reason;
	  private String timestampofRequest;
	  private LocalDateTime createTime;
	  private LocalDateTime updateTime;
}
