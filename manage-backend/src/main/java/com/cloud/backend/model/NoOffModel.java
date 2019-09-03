package com.cloud.backend.model;

import java.time.LocalDateTime;

import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="sys_nooff")
public class NoOffModel {
	private Integer id;
	private String orderNo;
	private String code;
	private String name;
	private String value;
	private Integer status;
	private LocalDateTime createTime;
	private LocalDateTime updateTime;
}
