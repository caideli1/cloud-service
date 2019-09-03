package com.cloud.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yoga
 * @Description: 部门
 * @date 2019-05-0216:06
 */

@Data
public class SysDept implements Serializable {
    private static final long serialVersionUID = -7939440033684713978L;

    private Long id;
    private String name;
    private String desc;
    private Boolean enabled;
    private Date createTime;
    private Date updateTime;
    private int userCount;
}
