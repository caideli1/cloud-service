package com.cloud.collection.model;

import java.time.LocalDateTime;

import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="collection_manage")
public class SmsManageModel {
  	private Integer id;
	private String orderNo;
  	private Integer userId;
  	private String userName;
  	private String sendSum;
  	private String receiveSum;
  	private String phone;
  	private String templateType;
  	private String noticeContext;
  	private String statusCode;
  	private String messageid;
  	private LocalDateTime createTime;
  	private LocalDateTime updateTime;
}
