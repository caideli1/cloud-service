package com.cloud.common.enums;

/**
 * @author yoga
 * @Description: CommonEnum
 * @date 2019-08-1419:29
 */
public enum CommonAppTipEnum implements EnumKeyValue<CommonAppTipEnum> {
    NEED_UPDATE_APP(601, "Pls upgrade into latest App."),
    ;

    public Integer code;
    public String message;

    CommonAppTipEnum(Integer i, String msg) {
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
