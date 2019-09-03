package com.cloud.model.appUser;

import lombok.Data;

@Data
public class AppUserJobInfoReq {
    private Long userId;
    private String voterIdCard;

    private String voterFrontUrl;
    private String voterBackUrl;
    private String voterFrontPdf;
    private String voterBackPdf;
    private String electorsName;
    private String relationsName;

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
    //公司电话
    private String companyPhone;
    //详情地址
    private String addressDetail;
    //州
    private String state;
    //区
    private String district;
    //县
    private String county;
    //村镇
    private String town;
    //住址类型 0家庭 1工作 2学校
    private Integer addressType;
    //薪资
    private String salary;
    //家庭住址地段
    private String addressDetailOfHome;
    private String stateOfHome;
    private String districtOfHome;
    private String countyOfHome;
    private String townOfHome;
    private String payday;


}
