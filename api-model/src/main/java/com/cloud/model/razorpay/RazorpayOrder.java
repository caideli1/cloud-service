package com.cloud.model.razorpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * razorpay订单类
 *
 * @author danquan.miao
 * @date 2019/7/17 0017
 * @since 1.0.0
 */
@Data
public class RazorpayOrder {
    @JsonProperty("id")
    private String id;
    @JsonProperty("entity")
    private String entity;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("receipt")
    private String receipt;
    @JsonProperty("status")
    private String status;
    @JsonProperty("attempts")
    private Integer attempts;
    @JsonProperty("notes")
    private String notes;
    @JsonProperty("created_at")
    private Date createdAt;
}
