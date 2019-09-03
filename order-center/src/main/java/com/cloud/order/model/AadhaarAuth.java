package com.cloud.order.model;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AadhaarAuth {
    /**
     * Adahaar账号
     */
    private  String  aadhaarAccount;

    /**
     * Adahaar姓名
     */
    private String aadhaarName;

    /**
     * aadhaar生日
     *
     */
    private String aadhaarBirthday;

    /**
     * aadhaar性别
     */
    private Integer  aadhaarSex;

    /**
     * aadhaar正面照片
     */

    private String   aadhaarFront;


    /**
     * aadhaar背面照片
     */
    private  String aadhaarBack;



    /**
     * 是否识别
     */
    private Integer isIdentify;


    /**
     * 房屋地址
     */
    private String aadhaarHouse;
    /**
     * 街道
     */
    private String aadhaarStreet;
    /**
     * 地區
     */
    private String aadhaarLoc;
    private String aadhaarVtc;
    /**
     * 郵編
     */
    private String aadhaarPo;

    /**
     * addharr 地址详情
     */
    private String  aadhaarAddressDetails;
    /**
     * 地區
     */
    private String aadhaarDist;
    /**
     * 子地區
     */
    private String aadhaarSubdist;
    /**
     * 州
     */
    private String aadhaarState;


}
