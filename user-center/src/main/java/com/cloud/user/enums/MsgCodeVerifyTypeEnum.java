package com.cloud.user.enums;

import com.cloud.common.enums.EnumKeyValue;

/**
 * @author yoga
 * @Description: MsgCodeVerifyTypeEnum
 * @date 2019-07-2614:26
 */

public enum MsgCodeVerifyTypeEnum implements EnumKeyValue<MsgCodeVerifyTypeEnum> {

    REGISTER(1, "注册"),
    SINGNATURE(3, "签名"),
    RESETPWD(25, "找回密码"),
    ;


    public Integer code;
    public String message;

    MsgCodeVerifyTypeEnum(Integer i, String msg) {
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