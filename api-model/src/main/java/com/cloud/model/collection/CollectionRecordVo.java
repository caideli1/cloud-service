package com.cloud.model.collection;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/14 14:48
 * 描述：
 */
@Data
@Builder
public class CollectionRecordVo implements Serializable {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 逾期表ID
     */
    private Long dueId;
    /**
     * 处置状态 :0：正常 1：未處置 2：已處置
     */
    private int handleStatus;
    /**
     * '订单号'
     */
    private String orderNo;
    /**
     * '客户编号'
     */
    private String customerNo;
    /**
     * 姓名
     */
    private String name;
    /**
     * '手机号'
     */
    private String mobile;
    /**
     * '借款金额'
     */
    private BigDecimal loanAmount;

    /**
     * '逾期天数'
     */
    private Integer dueDays;
    /**
     * '罚息金额'
     */
    private BigDecimal dueAmount;
    /**
     * '逾期开始日期'
     */
    private Date dueStart;
    /**
     * '逾期结束日期'
     */
    private String dueEnd;

    /**
     * 罚息减免申请时间
     */
    private Date reductionApplyTime;
    /**
     * 催收状态
     * 1.待派单；
     * 2.催收中（已派单到制定催收员名下）；
     * 3.完成（该条借据还清款项）。
     * 4.停催(默认归属到催收主管名下)
     */
    private int collectionStation;

    /**
     * 借据编号
     */
    private String loanNum;

    /**
     * 罚息减免
     */
    private BigDecimal reductionAmount;

    /**
     * 罚息减免后应还金额
     */
    private BigDecimal reductionSumAmount;

    /**
     * 应还金额
     */
    private BigDecimal repayAmount;

    /**
     * 催收次数
     */
    private Integer accruedCount;

    /**
     * 减免审核申请状态
     * 1：待审批
     * 2，减免通过
     * 3：减免拒绝
     */
    private Integer applyStatus;
    /**
     * ada卡账号
     */
    private String userAadhaarNo;
    /**
     * 催收人姓名
     */
    private String collectionName;
    /**
     * 指派日期
     */
    private Date assginDate;

    /**
     * 减免金额
     */
    private BigDecimal reductionAmmount;

    /**
     * 减免后金额
     */
    private BigDecimal reductionSumAmmount;

    /**
     * 停催次数
     */
    private Integer closeCollectionCount;

    /**
     * 案件起始日期
     */
    private Date appointCaseStartDate;
    /**
     * 案件结束日期
     */
    private Date appointCaseEndDate;
    /**
     * 案件类型
     */
    private Integer appointCaseType;
    /**
     * 跟进状态
     */
    private String followUpStatus;

    private Date  loanStart;

    private  Date  loanEnd;

    /**
     * 是否續貸
     */
    private Integer   renewalState;

    /**
     *  '是否展期：1:表示展期0：表示未展期'
     */
    private Integer  isExtension;

    private  Integer  payStatus;

    /**
     * 支付 字符处理
     */
    private String   payStatusStr;

    /**
     * 跟進時間
     */
    private Date followUpTime;

    /**
     * 应还款日
     */
    private Date repayDate;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 是否标记高亮
     */
    private Integer isMark;

    /**
     * 指派表Id
     */
    private Integer assginId;

    /**
     * 实际退案日期
     */
    private Date dateWithdrawal;
}
