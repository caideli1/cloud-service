package com.cloud.user.model;

import lombok.*;

/**
* @Author:         wza
* @CreateDate:     2019/1/18 10:39
* @Version:        1.0
*/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AppUserBasicInfoReq {
    //id
    private Long userId;
    //用户名
    private String firstName;
    //用户姓
    private String lastName;
    //性别
    private String sex;
    //手机号
//    private String cellPhone;
    //邮箱
    private String email;
    //客户状态：0>新建；1>激活 有借款行为
    private int status;
    //生日
    private String birthday;
    //婚姻状况
    private String marriageStatus;
    //是否是学生 0>不是；1>是
    private Integer studentStatus;
    //渠道来源
    private String channel;
    //宗教
    private String religion;
    //邀请人总数
    private int inviteCount;
    //aadhaar账号
    private String aadhaarAccount;
    /**
     * aadhaar卡正面照
     */
    private String aadhaarUrlFront;
    /**
     * aadhaar卡背面照
     */
    private String aadhaarUrlBack;
    //借款用途
    private String loanPurpose;
    //邮编
    private String postcode;
    //是否识别
    private Boolean isIdentified;
    //是否匹配 0:否 1:是
    private Boolean match;
    //匹配分数
    private String matchScore;
    //银行流水url
    private String bankStatementUrl;

    private String bankStatementPdf;

    private String aadhaarPdfFront;

    private String aadhaarPdfBack;
    //头像url
    private String profileUrl;

    private String qrCode;

    private String aadhaarHouse;
    private String aadhaarStreet;
    private String aadhaarLoc;
    private String aadhaarVtc;
    private String aadhaarPo;
    private String aadhaarDist;
    private String aadhaarSubdist;
    private String aadhaarState;

}
