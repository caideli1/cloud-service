package com.cloud.user.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
/**
 * @Description:    user_info_history 实体类
 * @Author:         wza
 * @CreateDate:     2019/5/17 11:10
 * @Version:        1.0
 */
@Data
@Builder
public class AppUserInfoHistoryEntity {
    @Id
    private Integer id;
    /**
     * 学校地址id
     */
    private Integer schoolAddressId;
    /**
     * 订单id
     */
    private String orderNo;

    private Long userId;
    /**
     * pancard ID
     */
    private Integer panCardId;
    /**
     * 银行卡id
     */
    private Integer bankId;
    /**
     * 第一联系人id
     */
    private Integer firstContactId;
    /**
     * 家庭地址id
     */
    private Integer homeAddressId;
    /**
     * 公司地址id
     */
    private Integer companyAddressId;
    /**
     * aadhaar正面照
     */
    private String aadhaarUrlFront;
    /**
     * aadhaar背面照
     */
    private String aadhaarUrlBack;
    /**
     * 是否匹配 0不匹配 1匹配
     */
    private Integer match;
    /**
     * votercard正面照
     */
    private String voterFrontUrl;
    /**
     * votercard背面照
     */
    private String voterBackUrl;
    /**
     * 性别 0男 1女
     */
    private Integer sex;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 婚姻状态
     */
    private String marriageStatus;
    /**
     * 是否是学生 1是 0否
     */
    private Integer isStudent;
    /**
     * 邮箱
     */
    private String email;
    /**
     * aadhaar账号
     */
    private String aadhaarAccount;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * facebook账号
     */
    private String facebookAccount;
    /**
     * skype账号
     */
    private String skypeAccount;
    /**
     * whatsapp账号
     */
    private String whatsappAccount;
    /**
     * 宗教
     */
    private String religion;
    /**
     * voterIdcard账号
     */
    private String voterIdCard;
    /**
     * 邮编
     */
    private String postCode;
    /**
     * 借款用途
     */
    private String loanPurpose;
    /**
     * 第二联系人id
     */
    private Integer secondContactId;
    /**
     * 职业
     */
    private String profession;
    /**
     * 职位
     */
    private String position;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 受教育程度
     */
    private String educationDegree;
    /**
     * 学校名称
     */
    private String school;
    /**
     * 工作年限
     */
    private String workYear;
    /**
     * 银行流水
     */
    private String bankStatementUrl;
    /**
     * 工作证明
     */
    private String workCard;
    private String electorsName;
    private Integer salaryId;
    private String faceImg;
    private String enrollmentYear;
    private String alimony;
    private String relationsName;
    /**
     *   专业
     */
    private   String specialty;


    private String paydays;


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
     * 是否续贷
     */
    private Integer renewalState;
    /**
     * panCard正面照
     */
    private String cardFrontUrlPan;
    /**
     * panCard背面照
     */
    private String cardBackUrlPan;
    /**
     * panCard正面照pdf
     */
    private String cardFrontPdfPan;
    /**
     * panCard背面照pdf
     */
    private String cardBackPdfPan;
    /**
     * 持有人的全名
     */
    private String holdsFullNamePan;
    /**
     * 父亲名称
     */
    private String fathersNamePan;

    /**
     * 永久账户
     */
    private String permanentNoPan;

    private Integer  isIdentifiedPan;


    private String  birthdayPan;

    private Boolean matchPan;

    private String matchScorePan;



}
