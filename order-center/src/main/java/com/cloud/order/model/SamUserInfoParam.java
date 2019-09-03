package com.cloud.order.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 同用户信息 传出实体集
 */
@Data
public class SamUserInfoParam {
    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 审批时间
     */
    private Date auditTime;

    /**
     * 机审结果
     * 0：拒绝
     * 1：通过
     */
    private Integer machineAuditStatus;

    /**
     * 審核拒絕原因
     */
    private String  machineRefuseReason;
    /**
     * 初审结果
     * 0：拒绝
     * 1：通过
     */
    private Integer firstAuditStatus;

    /**
     * 初审标签 列表
     */
    private List<String> firstAuditTags;

    /**
     * 初审理由
     */
    private String  firstAuditReason;

    /**
     * 终审结果
     * 0：拒绝
     * 1：通过
     */
    private Integer finalAuditStatus;

    /**
     * 终审标签 列表
     */
    private List<String> finalAuditTags;

    /**
     * 终审理由
     */
    private String  finalAuditReason;

    /**
     * 借据状态
     * 0 失效
     * 1完成
     * 2 已处置
     */
    private Integer loanStatus;

    /**
     * 放款日
     */
    private Date loanTime;

    /**
     * 应还款日
     */
    private Date repayDate;

    /**
     * 实际还款日
     */
    private Date actualPayDate;

    /**
     * 逾期次数
     */
    private Integer dueCount;

    /**
     * 逾期天数
     */
    private Integer dueDayCount;

    /**
     * 逾期列表 实体
     */
    private List<FinanceDueOrderModel> financeDueOrderModels;

    /**
     * 催收标签 列表
     */
    private  List<CollectionTag> collectionTags;

    /**
     * 催收原因
     */
    private String collectionReason;






}
