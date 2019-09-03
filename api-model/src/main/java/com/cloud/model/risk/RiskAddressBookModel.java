package com.cloud.model.risk;

import lombok.Data;

import java.util.Date;

/**
 * @author zhujingtao
 */
@Data
public class RiskAddressBookModel {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 订单编号
     */
    private  String orderNum;
    /**
     * 姓名
     */
    private String  name;
    /**
     * 电话
     */
    private String  phone;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 跟新时间
     */
    private Date updateTime;

    /**
     * 通话次数
     */
    private Long callTimes;

    /**
     * 最后联系时间
     */
    private Date lastCallTime;

    /**
     * 归属地
     */
    private String belongPlace;

    /**
     * 设置默认值
     */
   public  RiskAddressBookModel()
    {
        callTimes=0L;
    }

}
