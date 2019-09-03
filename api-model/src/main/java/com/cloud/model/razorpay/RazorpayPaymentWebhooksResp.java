package com.cloud.model.razorpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class RazorpayPaymentWebhooksResp implements Serializable {

    private static final long serialVersionUID = 7725108124634593259L;

    private String orderId;
}
