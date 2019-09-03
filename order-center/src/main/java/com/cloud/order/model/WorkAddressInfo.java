package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WorkAddressInfo {
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
     * 教育程度
     */
    private String educationDegree;

    /**
     * 学校
     */
    private String school;

    /**
     * 工作年限
     */
    private String workYear;

    /**
     * 薪资
     */
    private String salary;

    private String homeState;


    private String homeDistrict;

    private String homeCounty;

    private String homeTown;


    private String homeAddressDetail;

    private String companyState;

    private String companyDistrict;

    private String companyCounty;

    private String companyTown;

    private String companyAddressDetail;

    private String companyMobile;

}
