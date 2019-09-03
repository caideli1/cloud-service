package com.cloud.order.model.req;

import java.time.LocalDateTime;

import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kd_borrower_info")
public class KDBorrowerInfoReq {
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
	private String info;//api文档上的
	private String data;
	private String reason;//接口实际返回的
	private String timestampofRequest;
	private String error;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
}
