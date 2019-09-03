package com.cloud.model.risk;

import lombok.Data;

import java.util.Date;

/**
 * @author zhujingtao
 */
@Data
public class RiskCallRecordModel {
    /**
     * 订单编号
     */
    private String orderNum;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 呼叫时间
     */
    private Date callTime;
    /**
     * 通话时长
     */
    private Long duration;
    /**
     *通话类型
     */
    private Long type;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
