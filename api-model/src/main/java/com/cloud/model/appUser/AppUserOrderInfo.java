package com.cloud.model.appUser;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class AppUserOrderInfo {
    /**
     * 订单编号
     */
    private String orderNum;
    /**
     * 申请编号
     */
    private String applyNum;
    /**
     * 分配人id -1代表无
     */
    private Long managerId;
    private Integer isStock;
    /**
     * 审核员ID -1代表无
     */
    private Long auditorId;
    /**
     * 审核状态： 1.审批中 2.机审拒绝 3待初审 4.人工初审拒绝 5.待终审 6.人工终审拒绝 7.通过
     */
    private int checkStatus;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 产品ID
     */
    private Integer productId;
    /**
     * 借款金额
     */
    private BigDecimal loanAmount;
    /**
     * 用户手机号
     */
    private String userPhone;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 签名图片URL
     */
    private String signatureUrl;
    private Date createTime;
    private Date updateTime;
    private String sessionId;
    private Date finalAuditTime;
}
