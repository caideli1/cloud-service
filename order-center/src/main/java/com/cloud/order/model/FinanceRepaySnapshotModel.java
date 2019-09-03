package com.cloud.order.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @Author:         zhujingtao
 * @CreateDate:     2019/2/26 14:05
 * 对应数据库表 `finance_repay_snapshot`
 */
@Data
public class FinanceRepaySnapshotModel  implements Serializable {
    /**
     * 主键ID
     */
    private Long id;
    /**
     *  '应还日期'
     */
    private Date repayDate;
    /**
     * 客户编号
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
     * 借款期限'
     */
    private Integer loanPeriod;
    /**
     * '借款金额'
     */
    private BigDecimal loanAmount;
    /**
     * '还款金额'
     */
    private BigDecimal  payAmount;
    /**
     * '订单状态:1:成功0：失败2：处理中'
     */
    private int  orderStatus;
    /**
     * '银行卡号'
     */
    private String bankNo;
    /**
     *  '订单编号'
     */
    private String orderNo;
    /**
     *  '还款快照修改时间',
     */
    private Date createTime;
    /**
     * 借据编号
     */
    private String loanNumber;




}
