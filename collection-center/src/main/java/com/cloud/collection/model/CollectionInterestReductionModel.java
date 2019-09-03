package com.cloud.collection.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 指派人员实体集
 * @Author:         zhujingtao
 * @CreateDate:     2019/3/8 14:05
 * 对应数据库表 `collection_interest_reduction``
 */
@Data
@Builder
public class CollectionInterestReductionModel {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * '申請狀態
     * 1：待審批
     * 2：減免通過
     * 3：減免拒絕',
     */
    private int applyStatus;

    /**
     *減息金額
     */
    private BigDecimal reductionAmmount;

    /**
     * 催收表ID
     */
    private Long collectionId;

    /**
     * 申請理由
     */
    private String remarks;
    /**
     *  '减免后應還總金額'
     */
    private  BigDecimal reductionSumAmmount;

    /**
     * 申请时间
     */
    private Date applyDate;

    /**
     * 添加order_no字段，并且配合数据迁移脚本，补全数据
     * by caideli 2019/8/6
     */
    private String orderNo;

    /**
     * 免息时间
     */
    private Date auditTime;

    /**
     * 减免失效时间
     */
    private Date expireTime;
}
