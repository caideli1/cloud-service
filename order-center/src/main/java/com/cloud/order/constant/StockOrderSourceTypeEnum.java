package com.cloud.order.constant;

/**
 * 库存订单来源类型枚举
 *
 * @author danquan.miao
 * @date 2019/8/5 0005
 * @since 1.0.0
 */
public enum StockOrderSourceTypeEnum {
    /**
     * 放款池
     */
    LENDING_POOL(1),
    /**
     * 审核
     */
    AUDIT(2),
    ;

    StockOrderSourceTypeEnum(Integer sourceType) {
        this.sourceType = sourceType;
    }

    private Integer sourceType;

    public Integer getSourceType() {
        return this.sourceType;
    }
}
