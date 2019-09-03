package com.cloud.order.model.req;

import java.time.LocalDateTime;

import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kd_nc_check")
public class KDNCCheckReq {
	  private Integer id;
	  private String orderNo;
	  private String status;
	  private String resultCode;
	  private String message;
	  private String kudosloanid;
	  private String kudosborrowerid;
	  private String partnerLoanid;
	  private String partnerBorrowerid;
	  private String ncStatus;
	  private String accountStatus;
	  private String remark;
	  private String error;
	  private String info;
	  private String reason;
	  private String onboarded;
	  private String data;
	  private String timestampofRequest;
	  private LocalDateTime createTime;
	  private LocalDateTime updateTime;
}
