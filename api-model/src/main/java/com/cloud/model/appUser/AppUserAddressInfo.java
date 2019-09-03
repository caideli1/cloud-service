package com.cloud.model.appUser;

import lombok.Data;

import java.util.Date;

/**
* @Description:    user_address实体类
* @Author:         wza
* @CreateDate:     2019/1/22 16:03
* @Version:        1.0
*/
@Data
public class AppUserAddressInfo {
    private Long userId;
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
    //创建时间
    private Date createTime;
    /**
     * 地址类型
     * 0>家庭地址,1>单位地址,2>学校地址
     */
    private Integer addressType;
    private Integer status;
}
