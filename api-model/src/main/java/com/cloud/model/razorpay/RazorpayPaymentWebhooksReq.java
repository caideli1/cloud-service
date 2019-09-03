package com.cloud.model.razorpay;

import lombok.Data;

import java.io.Serializable;
@Data
public class RazorpayPaymentWebhooksReq implements Serializable {

    private static final long serialVersionUID = 7725108124634593259L;

    private String razorpayEventResponse;

}
