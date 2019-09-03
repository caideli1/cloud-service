package com.cloud.backend.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Menu implements Serializable {

	private static final long serialVersionUID = 749360940290141180L;

	private Long id;
	private Long parentId;
	private String name;
	private String enName;
	private String css;
	private String url;
	private Integer sort;
	private Date createTime;
	private Date updateTime;

	private List<Menu> child;


	@Override
	public int hashCode() {
		int hashno = 7;
		hashno = 13 * hashno + (name == null ? 0 : name.hashCode());
		return hashno;
	}


	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		final Menu menu = (Menu) obj;
		if (this == menu) {
			return true;
		} else {
			return  this.id == menu.id;
		}

	}
}
