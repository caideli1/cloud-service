package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfoParam {
    /**
     * 用户名称
     */
    private  String name;
    /**
     * 订单编号
     */
    private  String orderNo;

    /**
     * 婚姻状态
     */
    private String  marriageStatus;

    /**
     * 发薪日
     */
    private String  payDays;

    /**
     * 是否是学生标识
     */
    private  Integer  studentStatus;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 是否识别  pancard
     *
     */
    private  Integer isIdentifyPancard;

    /**
     * 贷款原因
     */
    private  String loanPurpose;

    /**
     * facebook账号
     */
    private String facebookAccount;

    /**
     * 宗教信仰
     */
    private  String  religion;

    /**
     * 邮编
     */
    private  String  postcode;
    /**
     * skype账号
     */
    private  String skypeAccount;

    /**
     * whatsapp账号
     */
    private  String whatsappAccount;


    /**
     *
     */
    private Integer match;

    /**
     * 第一联系人ID
     */
    private   Integer firstContactId;

    /**
     * 第二联系人Id
     */
    private  Integer secondContactId;

    /**
     * pancard生日
     */
    private  String birthdayPancard;





}
