package com.cloud.model.appUser;

import lombok.Data;

import java.util.Date;

/**
* @Description:    user_contact实体类 不包含主键
* @Author:         wza
* @CreateDate:     2019/1/23 10:33
* @Version:        1.0
*/
@Data
public class AppUserContact {

    private  Integer  id;
    private Long userId;
    /**
     * 联系人姓名
     */
    private String name;
    /**
     * 与本人关系
     */
    private String relation;
    /**
     * 联系人号码
     */
    private String phone;
    private Date CreateTime;

    /**
     * 是否启用
     */
    private  Integer  enabledState;


}
