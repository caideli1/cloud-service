package com.cloud.model.appUser;

import lombok.Data;

import java.util.Date;

@Data
public class UserBindCardStatus {
    private String aadhaarAccount;
    private String voterIdCard;
    private String panCardAccount;
    private String facebookAccount;
    private String whatsappAccount;
    private String skypeAccount;
    private Date updateTime;
    private Date  userUpdateTime;
}
