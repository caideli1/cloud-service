package com.cloud.user.model;

import javax.persistence.Table;

@Table(name="kudos_cibilinfo")
public class CibilInfo extends CibilOrderEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8607423682883155498L;
	
	private String info;
	private String score;
	private String errorcode;
	private String errormessage;
	
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getErrormessage() {
		return errormessage;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
}
