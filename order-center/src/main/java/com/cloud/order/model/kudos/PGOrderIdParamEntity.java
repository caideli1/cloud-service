package com.cloud.order.model.kudos;

import lombok.Data;

@Data
public class PGOrderIdParamEntity {
	private Integer id;
	private String serialNumber;//暂代orderId
	private String orderNo;
}
