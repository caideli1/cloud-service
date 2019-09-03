package com.cloud.model.appUser;

import lombok.Data;

import java.util.Date;

/**
* @Description:    user_info_expand实体类
* @Author:         wza
* @CreateDate:     2019/1/22 16:03
* @Version:        1.0
*/
@Data
public class AppUserJobInfo {
    private Long userId;
    private String voterIdCard;

    private String voterFrontUrl;
    private String voterBackUrl;
    private String voterFrontPdf;
    private String voterBackPdf;
    private String electorsName;
    private String relationsName;


    private String facebookAccount;
    private String whatsappAccount;
    private String skypeAccount;
    private String pancardAccount;
    //学校名称
    private String school;
    //受教育程度
    private String educationDegree;
    //职业
    private String profession;
    //职位
    private String position;
    //公司名称
    private String companyName;
    //当前公司工作年限
    private String workYear;
    //工作(学生)证明文件
    private String workCard;
    //专业
    private String specialty;
    //入学年份
    private String enrollmentYear;
    //生活费
    private String alimony;
    private Date createTime;
    private Date updateTime;
    private String salary;
    /**
     * 单位号码
     */
    private String companyPhone;
    /**
     * 住址详情
     */
    private String addressDetail;
    /**
     * 州(邦)
     */
    private String state;
    /**
     * 区
     */
    private String district;
    /**
     * 县
     */
    private String county;
    /**
     * 村镇
     */
    private String town;
    /**
     * 地址类型
     * 0>家庭地址,1>单位地址,2>学校地址
     */
    private Integer addressType;

    private String addressDetailOfHome;
    private String stateOfHome;
    private String districtOfHome;
    private String countyOfHome;
    private String townOfHome;
    private String payday;


}
