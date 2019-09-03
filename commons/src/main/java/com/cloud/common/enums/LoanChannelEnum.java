package com.cloud.common.enums;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 放款通道枚举
 *
 * @author danquan.miao
 * @create 2019/4/25 0025
 * @since 1.0.0
 */
public enum LoanChannelEnum {
    /**
     * RAZORPAY
     */
    RAZORPAY(1, "razorpay"),
    /**
     * 科塔克
     */
    KOTAK(2, "kotak"),

    /**
     * cashfree
     */
    CASH_FREE(3, "cashfree"),
    /**
     * ccavenue
     */
    CCAVENUE(4, "ccavenue")
    ;

    LoanChannelEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    private Integer code;

    private String value;

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static String getByCode(Integer code) {
        String name = null;
        Optional<LoanChannelEnum> optional= Stream.of(LoanChannelEnum.values())
                .filter(value -> value.getCode().equals(code))
                .findFirst();
        if (optional.isPresent()) {
            name = optional.get().getValue();
        }

        return name;
    }

    public static Optional<LoanChannelEnum> getByValue(String value) {
        return Stream.of(LoanChannelEnum.values())
                .filter(loanChannelEnum -> loanChannelEnum.getValue().equalsIgnoreCase(value))
                .findFirst();
    }
}
