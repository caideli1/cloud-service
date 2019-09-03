package com.cloud.oauth.enums;

import com.cloud.common.enums.EnumKeyValue;

public enum AppLoginEnum implements EnumKeyValue<AppLoginEnum> {
    NO_USER(1, "user not exists"),
    PASSWORD_INCORRECT(2, "password is incorrect"),
    MAX_WRONG_LOG(3, "最大错误登录次数限制"),
    ;


    public Integer code;
    public String message;

    AppLoginEnum(Integer i, String msg) {
        this.code = i;
        this.message = msg;
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
