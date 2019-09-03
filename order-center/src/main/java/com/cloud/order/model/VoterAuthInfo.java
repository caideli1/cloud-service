package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class VoterAuthInfo {

    /**
     * voter背面
     */
    private String voterBackUrl;
    /**
     * voter证明
     */
    private String voterFrontUrl;

    /**
     * 卡号
     */
    private String voterIdNo;

    /**
     * 名称
     */
    private String voterIdName;

    /**
     * 家族姓氏
     */
    private String voterIdFamilyName;
}
