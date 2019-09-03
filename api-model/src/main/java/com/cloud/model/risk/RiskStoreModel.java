package com.cloud.model.risk;

import lombok.Data;

@Data
public class RiskStoreModel    {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 风控ID
     */
    private String riskName;
    /**
     * 订单编号
     */
    private String orderNum;

    /**
     * 风控描述
     */
    private String riskDesc;

    /**
     * 决策状态(0:拒绝;1:预警)
     */
    private int  approveStatus;
    /**
     *规则分类
     */
    private String   category;


}
