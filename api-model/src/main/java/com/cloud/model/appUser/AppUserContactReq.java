package com.cloud.model.appUser;

import lombok.Data;

@Data
public class AppUserContactReq {
    private Long userId;
    /**
     * 亲属联系人名称
     */
    private String name;
    /**
     * 亲属关系
     */
    private String relation;
    /**
     * 亲属手机号
     */
    private String phone;
    /**
     * 其他人姓名
     */
    private String anotherName;
    /**
     * 其他人关系
     */
    private String anotherRelation;
    /**
     * 其他人手机号
     */
    private String anotherPhone;

    private String facebookAccount;
    private String whatsappAccount;
    private String skypeAccount;
}
