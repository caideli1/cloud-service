package com.cloud.order.model;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * OrderTableModel class
 *
 * @author zhujingtao
 * @date 2019/2/20
 */

@Data
public class OrderTableModel implements Serializable {


    /**
     * 主键Id
     */
    private Long id;

    /**
     * 订单Id
     */

    private Long orderId;

    private Integer isStock;
    /**
     * 申请号
     */
    private String applyNum;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 分配人id
     */
    private Integer managerId;

    /**
     * 分配状态
     */
    private Integer distributionStatus;

    /**
     * 审批人id
     */

    private Integer auditorId;
    /**
     * 审核状态：
     * 1.机审
     * 2.机审拒绝
     * 3待初审
     * 4.人工初审拒绝
     * 5.待终审
     * 6.人工终审拒绝
     * 7.通过
     */
    private Integer status;

    /**
     * 审核状态：
     * 1.机审
     * 2.机审拒绝
     * 3待初审
     * 4.人工初审拒绝
     * 5.待终审
     * 6.人工终审拒绝
     * 7.通过
     * 8.放款池(审批通过，未放款)
     * 9.加入存量
     */
    private Integer checkStatus;

    /**
     * 类型 标识
     */
    private Integer type;
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 贷款金额
     */
    private Double loanAmount;

    /**
     * 用户手机
     */
    private String userPhone;

    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 分配人名称
     */
    private String assessorName;

    /**
     * 提交时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     *最终审批时间，也是放款时间
     */
    private Date finalAuditTime;

    /**
     *借款期限
     */
    private Integer term;

}
