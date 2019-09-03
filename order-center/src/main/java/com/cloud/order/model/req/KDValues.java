package com.cloud.order.model.req;

import lombok.Data;

@Data
public class KDValues {
	private String kudosloanid;
	private String kudosborrowerid;
	private String accountStatus;
	private String info;
	private String reason;
	private String remark;
	private String onboarded;
}
