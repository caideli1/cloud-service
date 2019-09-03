package com.cloud.order.model.razorpay;

import lombok.Data;

/**
 * Created by hasee on 2019/6/18.
 */
@Data
public class RazorpayAccountValidation {

    private int id;

    private long userId;

    private int userBankcardId;

    private String orderNo;

    private String favId;

    private String status;

    private String failureReason;
}
