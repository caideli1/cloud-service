package com.cloud.model.risk.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author walle
 */
@Getter
@Setter
public class RiskExecuteResponseDto {

    /**
     * 规则id
     */
    private Integer ruleId;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 规则分类
     */
    private String ruleCategory;

    /**
     * 决策状态(0:拒绝;1:预警;2:通过)
     */
    private Integer approveStatus;

    /**
     * 规则描述
     */
    private String description;

}
