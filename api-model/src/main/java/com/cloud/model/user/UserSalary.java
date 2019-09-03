package com.cloud.model.user;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yoga
 * @Description: 用户薪资
 */
@Data
@Builder
public class UserSalary implements Serializable {
    private static final long serialVersionUID = -7323017727717813326L;
    private Integer id;
    private Long userId;
    private String salary;
    private Date createTime;
}
