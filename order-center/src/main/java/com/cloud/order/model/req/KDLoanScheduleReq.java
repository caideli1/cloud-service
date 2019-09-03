package com.cloud.order.model.req;

import java.time.LocalDateTime;

import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kd_loan_schedule")
public class KDLoanScheduleReq {
	  private Integer id;
	  private String orderNo;
	  private String status;
	  private String resultCode;
	  private String message;
	  private String kudosloanid;
	  private String kudosborrowerid;
	  private String onboarded;
	  private String accountStatus;
	  private String remark;
	  private String info;
	  private String reason;
	  private LocalDateTime createTime;
	  private LocalDateTime updateTime;
}
