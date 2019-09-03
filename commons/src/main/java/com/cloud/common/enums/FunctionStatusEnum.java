package com.cloud.common.enums;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 启用状态
 *
 * @author walle
 */
public enum FunctionStatusEnum implements EnumKeyValue<FunctionStatusEnum> {

    DISABLED(0, "禁用"),
    ENABLED(1, "启用"),

    ;

    FunctionStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Optional<FunctionStatusEnum> getByCode(Integer code) {
        return Stream.of(FunctionStatusEnum.values())
                .filter(value -> value.getCode().equals(code))
                .findFirst();
    }

    public Integer code;

    public String message;

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
