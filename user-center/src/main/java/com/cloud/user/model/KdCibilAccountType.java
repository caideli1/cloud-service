package com.cloud.user.model;

import java.time.LocalDateTime;

import javax.persistence.Table;

import lombok.Data;
@Data
@Table(name="kd_cibil_accounttype")
public class KdCibilAccountType {
	private Integer id;
	private String code;
	private String name;
	private String value;
	private String module;
	private String type;
	private LocalDateTime createTime;
	private String createUser;
	private LocalDateTime updateTime;
	private String updateUser;
}
