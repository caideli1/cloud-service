package com.cloud.collection.constant;

/**
 * 后台角色类型枚举
 *
 * @author danquan.miao
 * @date 2019/6/11 0011
 * @since 1.0.0
 */
public enum BackRoleTypeEnum {
    /**
     * 普通
     */
    ORDINARY(0),
    /**
     * 初审
     */
    PRIMARY(1),
    /**
     * 终审
     */
    FINAL(2),
    /**
     * 主管
     */
    MANAGER(3),
    ;
    BackRoleTypeEnum(Integer type) {
        this.type = type;
    }
    private Integer type;

    public Integer getType() {
        return type;
    }
}
