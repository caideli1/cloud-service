package com.cloud.order.constant;

import com.cloud.common.enums.EnumKeyValue;

/**
 * 订单中心返回码枚举
 *
 * @author danquan.miao
 * @create 2019/4/24 0024
 * @since 1.0.0
 */
public enum OrderCenterRespEnum implements EnumKeyValue<OrderCenterRespEnum> {

    PDF_URL_BLANK(819, "URL为空"),
    ;

    public Integer code;

    public String message;

    OrderCenterRespEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
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
