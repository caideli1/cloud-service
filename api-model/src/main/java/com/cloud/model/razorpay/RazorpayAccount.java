package com.cloud.model.razorpay;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RazorpayAccount {
	@JsonProperty("id")
	private String id;
	@JsonProperty("entity")
	private String entity;
	@JsonProperty("contact_id")
	private String contactId;
	@JsonProperty("account_type")
	private String accountType;
	@JsonProperty("active")
	private Integer active;
	@JsonProperty("details")
	private String details;
	@JsonProperty("created_at")
	private Date createdAt;
}
