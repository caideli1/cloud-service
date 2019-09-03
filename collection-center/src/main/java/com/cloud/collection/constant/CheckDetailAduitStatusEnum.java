package com.cloud.collection.constant;

/**
 * 查看订单详情审批状态枚举
 *
 * @author danquan.miao
 * @date 2019/6/11 0011
 * @since 1.0.0
 */
public enum CheckDetailAduitStatusEnum {
    /**
     * 未申请
     */
    UN_APPLY(-1),
    /**
     * 待审批
     */
    WAIT_AUDIT(0),
    /**
     * 拒绝
     */
    REJECT(1),
    /**
     * 通过
     */
    PASS(2),
    ;

    CheckDetailAduitStatusEnum(Integer status) {
        this.status = status;
    }

    private Integer status;

    public Integer getStatus() {
        return status;
    }

}
