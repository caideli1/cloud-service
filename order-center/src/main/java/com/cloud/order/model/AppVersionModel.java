package com.cloud.order.model;

import java.util.Date;

import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="app_version")
public class AppVersionModel {
	private Integer id;
    private String onlinestatus;
    private String appversion;
    private Date createTime;
    private Date updateTime;
}
