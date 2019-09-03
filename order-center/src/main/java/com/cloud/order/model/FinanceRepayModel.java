package com.cloud.order.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author:         zhujingtao
 * @CreateDate:     2019/2/26 14:05
 * 对应数据库表 `finance_repay`
 */
@Data
public class FinanceRepayModel implements Serializable {
    /**
     * 主键Id
     */
    private Long id;
    /**
     *  '应还日期'
     */
    private Date repayDate;
    /**
     * 交易時間
     */
    private Date payDate;
    /**
     * 客户编号
     */
    private Long customerNo;
    /**
     *  '姓名'
     */
    private String name;
    /**
     * '手机号'
     */
    private String mobile;
    /**
     *  '借款期限'
     */
    private int loanPeriod;
    /**
     *  '借款金额'
     */
    private BigDecimal loanAmount;
    /**
     *  '放款金额'
     */
    private BigDecimal payAmount;
    /**
     * '银行卡号'
     */
    private String bankNo;
    /**
     * 订单状态:1:成功0：失败2：处理中3：待还
     */
    private Integer  orderStatus;
    /**
     *  '订单编号'
     */
    private String orderNo;
    /**
     *  '借据编号'
     */
    private String loanNumber;
    /**
     * 实际交易金额
     */
    private BigDecimal actualAmount;

    /**
     * 支付状态1：支付成功0：支付失败', 2支付中3待支付
     */
    private Integer payStatus;
    /**
     *  交易类型
     *  1：放款申请
     *  2：放款重提
     *  3：正常还款
     *  4：逾期还款
     *  5：提前结清
     *  6：展期申请
     *  7：复贷放款
     */
    private Integer loanType;

    /**
     * '放款通道' 1通道一 2通道二 3 通道三
     */
    private Integer loanChannel;
    /**
     *   处理对象 ：1平台 2用户 3 线下
     */
    private Integer  handler;

    /**
     *  逾期数
     */
    private Integer dueDays;

    /**
     * 展期后应还日
     */
    private Date extensionEnd;



    /**
     * 失败原因
     */
    private String failureReason;
    /**
     * 减免罚息金额
     */
    private BigDecimal reductionAmount;

    /**
     * 創建時間
     */
    private Date  createTime;

    /**
     * 交易时间
     */
    private Date payTime;


}
