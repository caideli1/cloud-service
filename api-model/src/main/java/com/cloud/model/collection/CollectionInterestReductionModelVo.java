package com.cloud.model.collection;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/20 15:52
 * 描述：
 */
@Data
public class CollectionInterestReductionModelVo implements Serializable {
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
    private BigDecimal reductionSumAmmount;

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
