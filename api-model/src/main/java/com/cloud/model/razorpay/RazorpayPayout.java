package com.cloud.model.razorpay;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class RazorpayPayout {
    @JsonProperty("id")
    private String id;
    @JsonProperty("entity")
    private String entity;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("fundAccount_id")
    private String fundAccountId;
    @JsonProperty("mode")
    private String mode;
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("reference_id")
    private String referenceId;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("fees")
    private Integer fees;
    @JsonProperty("tax")
    private Integer tax;
    @JsonProperty("status")
    private String status;
    @JsonProperty("utr")
    private String utr;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
    @JsonProperty("failure_reason")
    private  String failureReason;
}
