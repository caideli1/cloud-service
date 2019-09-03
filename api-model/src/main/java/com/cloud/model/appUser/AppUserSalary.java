package com.cloud.model.appUser;

import lombok.Data;

import java.util.Date;

@Data
public class AppUserSalary {
    private Long userId;
    //薪资(准确值)
    private String salary;
    private Date createTime;
}
