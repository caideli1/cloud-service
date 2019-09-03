package com.cloud.order.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 逾期订单详情 列表
 * @author  zhujingtao
 *
 */
@Data
public class LaterDetailsModel {

    /**
     * 订单号码
     */
    private  String  orderNo;
    /**
     * 逾期结束日期
     */
    private Date dueEnd;

    /**
     * 逾期开始日期
     */
    private  Date dueStart;
    /**
     * 展期 开始时间
     */
    private Date extensionStartDate;

    /**
     * 展期结束日期
     */
    private Date extensionEndDate;
    /**
     * 展期 金额
     */
    private BigDecimal extensionAmount;
    /**
     * 实际还款金额
     */
    private  BigDecimal  actualAmount;

    /**
     * 是否展期
     */
    private Integer  isExtension;
    /**
     * 贷款 状态
     */
    private String  loanStatus;

    /**
     * 放款金额
     */
    private BigDecimal loanAmount;

}
