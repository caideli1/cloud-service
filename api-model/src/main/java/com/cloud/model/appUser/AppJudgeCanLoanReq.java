package com.cloud.model.appUser;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AppJudgeCanLoanReq {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 产品ID
     */
    private Integer productId;
    /**
     * 页面借款金额
     */
    private BigDecimal amount;
    /**
     * 放款通道
     */
    private String loanChannel;
    /**
     * 签名图片URL
     */
    private String signatureUrl;

    private String phone;

    private String code;

    private String sessionId;

    private String applyNum;
}
