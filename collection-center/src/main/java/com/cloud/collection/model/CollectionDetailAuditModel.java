package com.cloud.collection.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 审核详情查看响应类
 *
 * @author danquan.miao
 * @date 2019/6/4 0004
 * @since 1.0.0
 */
@Data
public class CollectionDetailAuditModel {
    /**
     * 详情审核主键id
     */
    private Integer detailAuditId;
    /**
     * 催收记录表主键id
     */
    private Long collectionId;
    /**
     * 客户编号
     */
    private String customerNo;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 借款金额
     */
    private BigDecimal loanAmount;

    /**
     * 罚息金额
     */
    private BigDecimal dueAmount;
    /**
     * 分配日期
     */
    private Date assginDate;
    /**
     * 催收人姓名
     */
    private String collectionName;

    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 跟进状态
     */
    private String followUpStatus;
    /**
     * 审批查看状态  0:待审核 1:拒绝 2:通过
     */
    private Integer auditCheckStatus;
}
