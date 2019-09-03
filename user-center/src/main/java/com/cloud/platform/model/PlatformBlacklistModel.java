package com.cloud.platform.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 黑名单表
 * @author bjy
 * @date 2019/3/8 0008 14:38
 */
@Data
public class PlatformBlacklistModel {
    private long id;
    private long userId;
    private String userName;
    private String userMobile;
    private byte userSex;
    private int userAge;
    private BigDecimal userIncome;
    private int signCount;
    private String userAddress;
    private String accountNum;
    private String pancard;
    private int dueCount;


}
