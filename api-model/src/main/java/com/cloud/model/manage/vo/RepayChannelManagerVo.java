package com.cloud.model.manage.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/7/25 13:42
 * 描述：
 */
@Data
public class RepayChannelManagerVo implements Serializable {
    /**
     * id
     */
    private Integer id;

    /**
     * 通道名称
     */
    private String name;

    /**
     * 是否可用，false 禁用 true 激活
     */
    private Boolean status;

    /**
     * 是否填写线下账户信息，false 不填写 true填写
     */
    private Boolean offlineStatus;

    /**
     * 开户账号
     */
    private String account;

    /**
     * 开户人姓名
     */
    private String accountName;

    /**
     * 开户地址
     */
    private String accountAddress;

    /**
     * ifsc码
     */
    private String ifscCode;

    /**
     * upi
     */
    private String upi;

    /**
     * create_time
     */
    private Date createTime;

    /**
     * update_time
     */
    private Date updateTime=new Date();
}
