package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StudentInfo {
    /**
     * 学校
     */
    private String school;

    /**
     * 入学年份
     */
    private  String enrollmentYear;

    private String alimony;

    private String specialty;

    private String schoolState;

    private String schoolDistrict;

    private String schoolCounty;

    private String schoolTown;

    private String schoolAddressDetail;
}
