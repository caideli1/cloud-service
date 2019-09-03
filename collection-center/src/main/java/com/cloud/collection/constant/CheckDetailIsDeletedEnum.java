package com.cloud.collection.constant;

/**
 * 审核详情查看是否删除枚举
 *
 * @author danquan.miao
 * @date 2019/6/18 0018
 * @since 1.0.0
 */
public enum CheckDetailIsDeletedEnum {
    UN_DELETED(0),
    DELETED(1),
    ;

    CheckDetailIsDeletedEnum(Integer code) {
        this.code = code;
    }

    private Integer code;

    public Integer getCode() {
        return this.code;
    }
}
