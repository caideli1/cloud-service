package com.cloud.collection.constant;

/**
 * 罚息减免记录状态枚举
 *
 * @author danquan.miao
 * @date 2019/6/18 0018
 * @since 1.0.0
 */
public enum ReportCollReductionEnum {
    ENABLE(1),
    UNABLE(2),
    ;
    ReportCollReductionEnum(Integer status) {
        this.status = status;
    }

    private Integer status;

    public Integer getStatus() {
        return status;
    }
}
