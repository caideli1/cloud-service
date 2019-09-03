package com.cloud.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yoga
 * @Description: UserAddress
 */
@Data
public class UserAddress implements Serializable {
    private static final long serialVersionUID = -6751697529606860022L;
    private Integer id;
    private Long userId;
    private String companyPhone;
    private String addressDetail;
    private String state;
    private String district;
    private String county;
    private String town;
    private Date createTime;
    /**
     * 地址类型；0>:家庭地址,1>单位地址；
     */
    private String addressType;

    //1:啓用   0：未啓用
    private String status;
}
