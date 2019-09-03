package com.cloud.order.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zhujingtao
 * @CreateDate: 2019/3/15 14:05
 * 对应数据库表 `finance_account_manager`
 */
@Data
public class FinanceAccountManagerModel {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 账户名称
     */
    private String accountName;
    /**
     * 放款通道
     */
    private String loanPassageway;

    /**
     * 还款通道
     */
    private String payPassageway;

    /**
     * 总额
     */
    private BigDecimal amount;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否删除
     * 0：否
     * 1：是
     */
    private Integer isDelete;

    /**
     * 是否启用
     * 0：否 1：是
     */
    private Integer station;

    /**
     * 支付顺序
     */
    private Integer payOrder;

    /**
     * 备注描述
     */
    private String remark;

    /**
     * 发送时间
     */
    private Date sendTime;
}
