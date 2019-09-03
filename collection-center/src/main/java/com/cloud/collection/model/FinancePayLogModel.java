package com.cloud.collection.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author:         zhujingtao
 * @CreateDate:     2019/2/26 14:05
 * 对应数据库表 `finance_pay_log`
 */
@Data
public class FinancePayLogModel implements Serializable {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * '交易时间'
     */
    private Date createTime;
    /**
     *  '订单号'
     */
    private String  orderNo;
    /**
     * '客户编号'
     */
    private Long customerNo;
    /**
     *  '姓名'
     */
    private String  name;
    /**
     *'手机号'
     */
    private String mobile;
    /**
     *'交易金额'
     */
    private BigDecimal amount;
    /**
     *'银行卡号'
     */
    private String bankNo;
    /**
     * 交易类型 1：放款申请2：放款重提 3：正常还款4：逾期还款5：提前结清6：展期申请(正常展期) 7：复贷放款 9:提前展期 10:逾期展期
     *
     */
    private int loanType;
    /**
     *  '处理对象'1:平台2：个人
     */
    private int  handler;
    /**
     *支付状态1：支付成功0：支付失败', 2支付中 3取消支付
     */
    private int payStatus;
    /**
     *'订单类型：1：成功0：失败2：处理中3：取消
     */
    private int  orderStatus;
    /**
     * '失败原因'
     */
    private String failureReason;
    /**
     *'应还日期',
     */
    private Date repayDate;
    /**
     *'放款通道'
     */
    private int loanChannel;
    /**
     *'货币类型',
     */
    private String moneyType;
    /**
     * 流水号
     */
    private String serialNumber;
    /**
     * 原始金额
     */
    private BigDecimal originAmount;
    /**
     * 展期周期
     */
    private Integer extensionPeroid;
    /**
     * 展期后应还款日
     */
    private Date extensionEnd;
    /**
     * 展期后应还款金额
     */
    private BigDecimal extensionRepayAmount;
    /**
     * 还款通道
     */
    private Integer repayChannel;
    /**
     * 用户邮箱
     */
    private String email;
}
