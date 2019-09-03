package com.cloud.order.model;

import lombok.Data;

import java.util.Date;

/**
 *  OrderProcessModel class
 *
 * @author zhujingtao
 * @date 2019/2/20
 */
@Data
public class OrderProcessModel {

    private long id;

    private String orderNum;

    private String applyNum;

    private long productId;

    private String productName;

    private double loanAmount;

    private int checkStatus;

    private long userId;

    private Date createTime;

    private Boolean isRenewLoans;


    private Integer  renewalState;
}
