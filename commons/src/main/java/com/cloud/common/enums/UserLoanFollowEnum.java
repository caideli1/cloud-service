package com.cloud.common.enums;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/28 11:23
 * 描述：
 */
public enum UserLoanFollowEnum {
    /**
     * 跟进
     */
    FOLLOW(1, "跟进"),
    /**
     * 不跟进
     */
    UN_FOLLOW(0, "不跟进")
    ;

    UserLoanFollowEnum(Integer code, String value) {
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
        Optional<UserLoanFollowEnum> optional= Stream.of(UserLoanFollowEnum.values())
                .filter(value -> value.getCode().equals(code))
                .findFirst();
        if (optional.isPresent()) {
            name = optional.get().getValue();
        }

        return name;
    }

    public static Optional<UserLoanFollowEnum> getByValue(String value) {
        return Stream.of(UserLoanFollowEnum.values())
                .filter(loanChannelEnum -> loanChannelEnum.getValue().equalsIgnoreCase(value))
                .findFirst();
    }
}
