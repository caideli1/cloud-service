package com.cloud.gateway.enums;

import com.cloud.common.enums.EnumKeyValue;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * gateway 系统状态枚举
 */
public enum GatewayStatusEnum implements EnumKeyValue<GatewayStatusEnum> {

    PASSWORD_RETRY_OVER_LIMIT(465, "Password Errors Over Limits. Please Retry After 24 Hours"),

    ;

    public Integer code;

    public String message;

    GatewayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Optional<GatewayStatusEnum> getByCode(Integer code) {
        return Stream.of(GatewayStatusEnum.values())
                .filter(value -> value.getCode().equals(code))
                .findFirst();
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
