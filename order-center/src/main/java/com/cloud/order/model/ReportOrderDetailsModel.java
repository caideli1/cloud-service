package com.cloud.order.model;



import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author zhujingtao
 * 报表数据存储
 */
@Data
public class ReportOrderDetailsModel {

    /**
     * id主键
     */
    private  Integer id;
    /**
     * 借款金额
     */
    private BigDecimal loanAmount;
    /**
     * 借款数量
     */
    private Integer loanCount;

    /**
     * 日期
     */
    private Date reportDate;

    /**
     * 应还金额
     */
    private   BigDecimal  pendingAmount;

    /**
     * 应还笔数
     */
    private  Integer  pendingCount;


    /**
     * 已还金额
     */
    private  BigDecimal repaymentAmount;

    /**
     * 已还本金
     */
    private Integer repaymentCount;

    /**
     * 期数
     */
    private   Integer  period;

    /**
     * 产品金额类型
     */
    private  Double amountType;

    /**
     * 设置初始值
     */
    public ReportOrderDetailsModel(){
        loanAmount=new BigDecimal(0);
        loanCount=0;
        pendingCount=0;
        pendingAmount=new BigDecimal(0);
        repaymentAmount=new BigDecimal(0);
        repaymentCount=0;



    }


}
