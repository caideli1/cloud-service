package com.cloud.model.razorpay;

import lombok.Data;

@Data
public class RazorpayResp {
    /**
     * 金额
     */
    private Integer amount;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 联系人
     */
    private String contact;
    private String key;
    /**
     * razorpay 订单id
     */
    private String razorpayOrderId;
}
