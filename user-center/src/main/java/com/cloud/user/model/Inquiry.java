package com.cloud.user.model;

import lombok.Data;

@Data
public class Inquiry extends CibilOrderEntity {
	private String firstname;
	private String birthday;
	private String sex;
	private String email;
	private String cellphone;
	private String fathersname;
	private String permanentno;
}
