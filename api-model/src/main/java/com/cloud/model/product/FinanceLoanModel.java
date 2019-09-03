package com.cloud.model.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zhujingtao
 * @CreateDate: 2019/2/26 14:05
 * 对应数据库表 finance_loan
 */
@Data
public class FinanceLoanModel implements Serializable {
    /**
     * '交易时间'
     */
    private Date createTime;
    /**
     *  '借款金额'
     */
    private BigDecimal loanAmount;
    /**
     * '放款金额'
     */
    private BigDecimal payAmount;
    /**
     * '客户编号'
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
     * '借款期限'
     */
    private int loanPeriod;
    /**
     * '银行卡账号'
     */
    private String bankNo;
    /**
     *  '放款通道'
     */
    private int loanChannel;
    /**
     * '支付状态1：支付成功0：支付失败'
     */
    private int payStatus;
    /**
     *  '订单状态:1:成功0：失败2：处理中'
     */
    private int orderStatus;
    /**
     * '失败原因'
     */
    private String failureReason;
    /**
     *  '备注'
     */
    private String comment;
    /**
     * '主键id'
     */
    private Long id;
    /**
     * '借据编号'
     */
    private String loanNumber;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 交易方向
     */
    private String txnDirection;

    private BigDecimal originAmount;

    private String ifscCode;
    //是否失效
    private String  isInvalid;

    //该放款记录对应关联的银行卡已经修改的次数
    private int modifyBankcardCount;

    //校验银行卡失败  通知修改银行卡的时间
    private Date noticeModifyBankcardDate;

}
