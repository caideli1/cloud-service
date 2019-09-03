package com.cloud.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yoga
 * @Description: UserInfoExpand
 * @date 2019/1/185:21 PM
 */
@Data
public class UserInfoExpand implements Serializable {
    private static final long serialVersionUID = -3904191988180948647L;
    private Integer id;
    private Long userId;
    private String voterIdCard;
    private String facebookAccount;
    private String whatsappAccount;
    private String school;
    private String educationDegree;
    private String profession;
    private String position;
    private String companyName;
    private String workYear;
    private String workCard;
    private String skypeAccount;
    private String pancardAccount;
    private String specialty;
    private String enrollmentYear;
    private String alimony;
    private Date creteTime;
    private Date updateTime;
}
