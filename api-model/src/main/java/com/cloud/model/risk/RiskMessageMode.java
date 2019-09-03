package com.cloud.model.risk;

import lombok.Data;

import java.util.Date;

@Data
public class RiskMessageMode {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 内容
     */
    private String body;

    /**
     * type:短信类型1是接收到的;2是已发出
     */
    private int type;

    /**
     * 已读未读 ：0:未读1：已读
     */
    private int read;

    /**
     * 地址
     */
    private String address;

    /**
     * 发出时间
     */
    private Date sendTime;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 订单编号
     */
    private String orderNum;

    /**
     * 电话号码
     */
    private String  phone;


}
