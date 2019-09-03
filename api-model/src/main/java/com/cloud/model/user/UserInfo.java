package com.cloud.model.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author yoga
 * @Description: UserInfo
 */
@Data
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 5183544869191415512L;
    private Long id;
    private String firstName;
    private String lastName;
    /**
     * 性别 0: 男 1：女
     */
    private Integer sex;
    private String cellPhone;
    private String email;
    /**
     * 客户状态 0>新建；1>激活 有借款行为
     */
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
    /**
     * 婚姻状况：0>未婚；1>已婚；
     */
    private String marriageStatus;
    /**
     * 是否是学生  0>不是；1>是；
     */
    private Integer studentStatus;
    private String channel;
    private String religion;
    private Integer inviteCount;
    private String aadhaarAccount;
    /**
     * 身份证 正面照片url
     */
    private String aadhaarUrlFront;
    /**
     * 身份证 反面照片url
     */
    private String aadhaarUrlback;
    private String loanPurpose;
    private String postCode;
    private Date updateTime;
    private Date registerTime;
    /**
     * 是否识别 0:未识别 1:识别
     */
    private int isIdentified;

    /**
     * 授信次数
     */
    private int creditCount;

    private UserInfoExpand userInfoExpand;
    private List<UserBankCard> userBankCardList;
    private List<UserSalary> userSalaryList;
}
