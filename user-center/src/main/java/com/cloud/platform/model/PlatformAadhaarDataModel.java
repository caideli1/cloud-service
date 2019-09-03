package com.cloud.platform.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Aadhaar数据表
 * @author bjy
 * @date 2019/3/8 0008 14:38
 */
@Data
public class PlatformAadhaarDataModel {
    private long id;
    private int dataType;
    private long userId;
    private String userName;
    private String userAddress;
    private String userMobile;
    private Timestamp createTime;
    private String userAadhaarNo;


}
