package com.cloud.common.enums;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 现金贷系统-系统统一枚举
 */
public enum CashLoadSystemEnum implements EnumKeyValue<CashLoadSystemEnum>{

    // 400 - Bad Reques
    BAD_REQUEST(400, "提交参数不匹配"),

    // 404 - not found
    NOT_FOUND(404, "请求结果未找到"),

    // 405 - Method Not Allowed
    METHOD_NOT_ALLOWED(405, "请求方法类型不匹配"),

    // 415 - Unsupported Media Type
    UNSUPPORTED_MEDIA_TYPE(415, "不支持当前 Content-Type 类型"),

    ACCESS_DENIED(460, "Access permission denied"),

    ;

    public Integer code;

    public String message;

    CashLoadSystemEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Optional<CashLoadSystemEnum> getByCode(Integer code) {
        return Stream.of(CashLoadSystemEnum.values())
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
