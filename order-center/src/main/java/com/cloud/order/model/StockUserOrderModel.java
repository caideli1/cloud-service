package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 存量用户实体类
 *
 * @author danquan.miao
 * @date 2019/8/2 0002
 * @since 1.0.0
 */
@Builder
@Data
public class StockUserOrderModel {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 订单号
     */
    private String orderNum;
    /**
     * 申请编号
     */
    private String applyNum;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 审批时间 yyyy-MM-dd HH:mm:ss
     */
    private Date auditTime;
    /**
     * 来源类型 1: 放款池 2: 审批
     */
    private Integer sourceType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 订单id主键
     */
    private String orderId;
    /**
     * 审核状态
     */
    private Integer checkStatus;
    /**
     * 用户id
     */
    private Long userId;
}
