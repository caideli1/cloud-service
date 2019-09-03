package com.cloud.order.constant;

/**
 * 线下还款操作类型枚举
 *
 * @author danquan.miao
 * @date 2019/7/22 0022
 * @since 1.0.0
 */
public enum OfflineRepayOperateTypeEnum {
    /**
     * 结清
     */
    SETTLE(1),
    /**
     * 展期
     */
    EXTENSION(2),
    ;

    OfflineRepayOperateTypeEnum(Integer type) {
        this.type = type;
    }

    private Integer type;

    public Integer getType() {
        return type;
    }
}
