package com.cloud.collection.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zhujingtao
 * @CreateDate: 2019/3/8 14:05
 * 对应数据库表 `collection_record`
 */
@Data
@Table(name="collection_record")
public class CollectionRecord implements Serializable {
    /**
     * 主鍵id
     */
    @Id
    private Long id;

    /**
     * 逾期記錄id
     */
    private Long dueId;

    /**
     * 催收狀態 1.待派单2.催收中（已派单到制定催收员名下）；3.完成（该条借据还清款项）; 4.停催
     */
    private Integer collectionStation;

    /**
     * 借據編號
     */
    private String loanNum;

    /**
     * 處置狀態： 1：未處置 2：已處置
     */
    private Integer handleStatus;

    /**
     * 催收次数
     */
    private Integer accruedCount;

    /**
     * 减免审核状态 1：待审批 2，减免通过 3：减免拒绝
     */
    private Integer applyStatus;

    /**
     * 委案类型 1：s1 2:s2 3:s3
     */
    private Integer appointCaseType;

    /**
     * 跟进状态 存  标签名称
     */
    private String followUpStatus;

    /**
     * 委案结束日期
     */
    private String appointCaseEndDate;

    /**
     * 委案开始日期
     */
    private String appointCaseStartDate;

    /**
     * 0 非标记高亮 1 标记高亮
     */
    private Integer isMark;

    /**
     * 用户名称
     */
    private String name;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 应还金额
     */
    private BigDecimal loanAmount;

    /**
     * 应还金额
     */
    private Integer customerNo;

    /**
     * 逾期金额
     */
    private BigDecimal dueAmount;

    /**
     * 逾期开始日期
     */
    private String dueStart;

    /**
     * 逾期结束日期
     */
    private String dueEnd;

    /**
     * 跟进时间
     */
    private Date followUpTime;

    /**
     * 是否展期 0：未展期 1：已展期
     */
    private Integer isExtension;

    /**
     * 是否展期 0：未展期 1：已展期
     */
    private Integer dueDays;

    /**
     * 应还日
     */
    private String repayDate;

    /**
     * 指派表id
     */
    private Integer assginId;

    /**
     * 实际退案日期
     */
    private String dateWithdrawal;

    /**
     * 支付状态0:未支付 1：已支付 2：已展期
     */
    private Integer payStatus;

    /**
     * 停催次数
     */
    private Integer closeCollectionCount;

}
