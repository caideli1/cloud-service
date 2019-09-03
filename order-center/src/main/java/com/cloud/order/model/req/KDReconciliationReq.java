package com.cloud.order.model.req;

import java.time.LocalDateTime;

import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kd_reconciliation")
public class KDReconciliationReq {
	  private Integer id;
	  private String orderNo;
	  private String status;
	  private String resultCode;
	  private String message;
	  private String kudosloanid;
	  private String kudosborrowerid;
	  private String partnerid;
	  private String accountStatus;
	  private String remark;
	  private String error;
	  private String info;
	  private String reason;
	  private String timestampofRequest;
	  private LocalDateTime createTime;
	  private LocalDateTime updateTime;
}
