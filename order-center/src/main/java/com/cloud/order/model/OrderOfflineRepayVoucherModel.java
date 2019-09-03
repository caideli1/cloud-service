package com.cloud.order.model;

import lombok.Data;

import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 线下还款
 * Created by hasee on 2019/5/22.
 */
@Data
@Table(name = "order_offline_repay_voucher")
public class OrderOfflineRepayVoucherModel {
    private Integer id;

    private String orderNo;
    //转账银行卡
    private String transferBankAccount;
    //转账金额
    private BigDecimal transferAmount;
    //结清时间
    private Date closingDate;
    //到账时间
    private Date accountDate;
    //转账id
    private String transferId;
    //转账通道
    private String transferChannel;
    //转账凭证
    private String transferVoucherUrl;
    //是否有效
    private Boolean isValid;
    //错误信息
    private String errorInfo;

    private Date createTime;

    private Date updateTime;

    /**
     * 操作类型 1：结清 2：展期
     */
    private Integer operateType;
    /**
     * 展期开始日期 yyyy-MM-dd
     */
    private Date extensionStartDate;
    /**
     * 减免金额
     */
    private BigDecimal reductionAmount;
    /**
     * 备注
     */
    private String memo;
    /**
     * 是否已确认操作, false:否 true:是
     */
    private Integer isConfirm;
}
