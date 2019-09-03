package com.cloud.order.model.req;


import lombok.Data;

@Data
public class KDRoot {
	private String status;
	private String resultCode;
	private String message;
	private KDValues values;
}
