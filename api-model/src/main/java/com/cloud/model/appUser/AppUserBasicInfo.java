package com.cloud.model.appUser;

import lombok.*;

import java.util.Date;

/**
* @Description:    实体类
* @Author:         wza
* @CreateDate:     2019/1/18 14:07
* @Version:        1.0
*/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserBasicInfo {
    //id
    private Long id;
    //用户姓名
    private String firstName;
    //用户姓
    private String lastName;
    //性别
    private Integer sex;
    //手机号
    private String cellPhone;
    //邮箱
    private String email;
    //客户状态：0>新建；1>激活 有借款行为
    private Integer status;
    //生日
    private Date birthday;
    //婚姻状况：0>未婚；1>已婚
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
    private Integer isIdentified;
    //是否匹配 0:否 1:是
    private Integer match;
    //匹配分数
    private String matchScore;
    //银行流水url
    private String bankStatementUrl;

    private String bankStatementPdf;

    private String aadhaarPdfFront;

    private String aadhaarPdfBack;
    //头像url
    private String profileUrl;
    //注册时间
    private Date registerTime;
    //更新时间
    private Date updateTime;

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

    /**
     * 是否續貸用戶
     */
    private Integer renewalState;
}
