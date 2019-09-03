package com.cloud.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/7/25 11:43
 * 描述：还款通道管理
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="repay_channel_manager")
public class RepayChannelManager implements Serializable {
    /**
     * id
     */
    @Id
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
     * 账号
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
    private Date updateTime;
}
