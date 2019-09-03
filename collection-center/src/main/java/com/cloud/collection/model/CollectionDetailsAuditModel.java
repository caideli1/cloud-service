package com.cloud.collection.model;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * 催收审核详情查看表
 *
 * @author danquan.miao
 * @date 2019/6/4 0004
 * @since 1.0.0
 */
@Data
@Table(name = "collection_details_audit")
public class CollectionDetailsAuditModel {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 催收记录主键id
     */
    private Long collectionId;
    /**
     * 审核状态 0:待审核 1:拒绝 2:通过
     */
    private Integer auditStatus;
    /**
     * 审核日期
     */
    private Date auditDate;
    /**
     * 申请日期
     */
    private Date applyDate;
    /**
     * 审核人id
     */
    private Integer auditorId;
    /**
     * 查看人(申请人)ID
     */
    private Integer applyCheckUserId;
    /**
     * 是否已删除, 0:未删除 1:已删除
     */
    private Integer isDeleted;
}
