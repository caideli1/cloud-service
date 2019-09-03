package com.cloud.order.model.req;

import java.time.LocalDateTime;

import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kd_update_document")
public class KDUpdateDocumentReq {
	  private Integer id;
	  private String orderNo;
	  private String status;
	  private String resultCode;
	  private String message;
	  private String kudosloanid;
	  private String kudosborrowerid;
	  private String validationstatus;
	  private String validationresponse;
	  private String details;
	  private String accountStatus;
	  private String remark;
	  private String error;
	  private String info;
	  private String reason;
	  private String timestampofRequest;
	  private LocalDateTime createTime;
	  private LocalDateTime updateTime;
}
