package com.cloud.model.razorpay;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RazorpayContact {
    @JsonProperty("id")
	private String id;
    @JsonProperty("entity")
    private String entity;
    @JsonProperty("name")
    private String name;
    @JsonProperty("contact")
    private String contact;
    @JsonProperty("email")
    private String email;
    @JsonProperty("reference_id")
    private String referenceId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("active")
    private Integer active;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("created_at")
    private Date createdAt;
}
