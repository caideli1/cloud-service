package com.cloud.model.kudosCibil;

import java.io.Serializable;

public class CibilOrderEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4346604027090676131L;
	private Long userId;
	private Integer orderId;
	private String orderNum;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
}
