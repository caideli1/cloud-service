package com.cloud.common.enums;

/**
 * Created by hasee on 2019/6/4.
 */
public enum OperatorTypeEnum {
    /**
     * 平台
     */
    PLATFORM(1, "platform"),
    /**
     * 用户
     */
    USER(2, "user"),
    /**
     * 线下
     */
    OFFLINE(3, "offline"),
    /**
     * 手动
     */
    MANUAL(4, "manual"),
    ;

    OperatorTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    private Integer code;

    private String value;

    public Integer getCode() {
        return code;
    }
}
