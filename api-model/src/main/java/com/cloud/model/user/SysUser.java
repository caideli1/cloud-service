package com.cloud.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysUser implements Serializable {

	private static final long serialVersionUID = 611197991672067628L;

	private Long id;
	private String username;
	private String password;
	private String nickname;
	private String avatarUrl;
	private String phone;
	private String email;
	/**
	 * 状态
	 */
	private Boolean enabled;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	private String comment;
	private SysDept dept;
}
