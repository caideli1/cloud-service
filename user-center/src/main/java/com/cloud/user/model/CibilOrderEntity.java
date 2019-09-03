package com.cloud.user.model;

import java.io.Serializable;
import java.util.Date;

public class CibilOrderEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4346604027090676131L;
//	private Integer orderId;
	private String orderNo;
	private Date createTime;
	
//	public Integer getOrderId() {
//		return orderId;
//	}
//	public void setOrderId(Integer orderId) {
//		this.orderId = orderId;
//	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
