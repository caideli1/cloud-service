package com.cloud.user.model;

import lombok.Data;

@Data
public class UserAddress extends CibilOrderEntity {
	private String companyphone;
	private String addressdetail;
	private String state;
	private String district;
	private String county;
	private String town;
	private String createtime;
	private String addresstype;
	private String status;
}
