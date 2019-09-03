package com.cloud.order.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author:         zhujingtao
 * @CreateDate:     2019/2/26 14:05
 * 对应数据库表 `finance_extension`
 */
@Data
public class FinanceExtensionModel implements Serializable {
    /**
     * 主键Id
     */
    private int id;
    /**
     *'展期附件编号'
     */
    private String extensionNo;
    /**
     * '展期开始日期'
     */
    private Date extensionStart;
    /**
     * '展期结束日期'
     */
    private Date extensionEnd;
    /**
     * '展期状态1：正常0：结束'
     */
    private int extensionStatus;
    /**
     *  '订单编号'
     */
    private String orderNo;
    /**
     *  '借据编号'
     */
    private String loanNumber;

    private Date createAt;
}
