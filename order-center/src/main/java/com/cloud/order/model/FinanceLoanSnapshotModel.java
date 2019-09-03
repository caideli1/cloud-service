package com.cloud.order.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author:         zhujingtao
 * @CreateDate:     2019/2/26 14:05
 * 对应数据库表 `finance_loan_snapshot`
 */
@Data
public class FinanceLoanSnapshotModel implements Serializable {
    /**
     * 主键ID
     */
    private int id;
    /**
     * '放款快照修改时间'
     */
    private Date createTime;
    /**
     * '订单号'
     */
    private String orderNo;
    /**
     *  '客户编号'
     */
    private Integer customerNo;
    /**
     * '姓名'
     */
    private String name;
    /**
     * '手机号'
     */
    private String mobile;
    /**
     * '借款期限'
     */
    private Integer loanPeriod;
    /**
     *  '借款金额'
     */
    private BigDecimal loanAmount;
    /**
     *  '放款金额'
     */
    private BigDecimal payAmount;
    /**
     * '银行卡账号'
     */
    private String bankNo;
    /**
     * '放款通道'
     */
    private  int loanChannel;
    /**
     * 支付状态1：支付成功0：支付失败',
     */
    private int  payStatus;
    /**
     *  '订单状态:1:成功0：失败2：处理中'
     */
    private int orderStatus;
    /**
     *  '失败原因'
     */
    private String failureReason;
    /**
     *  '备注'
     */
    private String comment;
    /**
     * 借据编号
     */
    private String loanNumber;



}
