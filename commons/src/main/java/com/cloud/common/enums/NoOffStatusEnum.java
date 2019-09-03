package com.cloud.common.enums;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 第三方API控制开关
 *
 * @author ssp
 */
public enum NoOffStatusEnum implements EnumKeyValue<NoOffStatusEnum> {
    CRIF(0, "crif"),
    CIBIL(1, "cibil"),
    MESSAGE(2, "message"),
    TRUSTCHECKR(3, "TrustCheckr"),
    MSG91(4, "msg91"),
    AWS(5, "aws"),
    /**
     * razorpay放款
     */
    PAYOUT_RAZORPAY(6, "payout_razorpay"),
    /**
     * kotak放款
     */
    PAYOUT_KOTAK(7, "payout_kotak"),
    /**
     * cashFree银行卡认证
     */
    CASHFREE_VALID_BANKCARD(8, "cashfree_valid_bankcard"),
    ;

    NoOffStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Optional<NoOffStatusEnum> getByCode(Integer code) {
        return Stream.of(NoOffStatusEnum.values())
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
