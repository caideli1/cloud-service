package com.cloud.order.model;

public class LoanRoot {
	private String status;

	private String result_code;

	private String message;

	private Values values;

	public void setStatus(String status){
	this.status = status;
	}
	public String getStatus(){
	return this.status;
	}
	public void setResult_code(String result_code){
	this.result_code = result_code;
	}
	public String getResult_code(){
	return this.result_code;
	}
	public void setMessage(String message){
	this.message = message;
	}
	public String getMessage(){
	return this.message;
	}
	public void setValues(Values values){
	this.values = values;
	}
	public Values getValues(){
	return this.values;
	}
}
