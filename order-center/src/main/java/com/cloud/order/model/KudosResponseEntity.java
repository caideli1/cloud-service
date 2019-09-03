package com.cloud.order.model;

import java.util.Date;

import lombok.Data;

@Data
public class KudosResponseEntity {
    private int kudosid;
    private String status;
    private String resultCode;
    private String message;
    private String kudosloanid;
    private String kudosclientid;
    private String onboarded;
    private String accountStatus;
    private String kudosType;
    private String remark;
    private String info;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
    private String orderId;
    private String orderNo;
}
