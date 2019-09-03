package com.cloud.common.enums;

/**
 * 消息模板枚举类
 *
 * @author walle
 */
public enum NotificationTemplateTypeEnum implements EnumKeyValue<NotificationTemplateTypeEnum> {
    SEND_OTP(1, "注册/登录"),
    LOAN_CONTRACT_CONFIRMED(2, "合同签约确认短信"),
    INVITES_MESSAGE(3, "分享短信"),
    AUDIT_SUCCESS(4, "审核通过"),
    AUDIT_REJECT(5, "审核拒绝"),
    LEND_SUCCESS(6, "放款成功"),
    LEND_FAIL(7, "放款失败"),
    REPAY_SUCCESS(8, "还款成功"),
    REPAY_FAIL(9, "还款失败"),
    EXTENSION_SUCCESS(10, "展期支付申请成功"),
    EXTENSION_FAIL(11, "展期申请支付失败"),
    DUE_LESS_7_DAYS(12, "逾期短信-逾期≤7天"),
    DUE_MORE_7_DAYS(13, "逾期短信-逾期>7天"),
    DUE_IN_3_DAYS(14, "到期前3天提醒还款"),
    DUE_TOMORROW(15, "到期前1天提醒还款"),
    DUE_TODAY(16, "到期当天提醒还款"),
    DUE_3_DAYS(17, "逾期多日-3天"),
    DUE_7_DAYS(18, "逾期多日-7天"),
    DUE_15_DAYS(19, "逾期多日-15天"),
    DUE_28_DAYS(20, "逾期多日-28天"),
    INTEREST_REDUCTION(21, "罚息减免短信"),
    FEED_BACK(22, "用户反馈"),
    BANKCARD_VALIDATION_FAIL(23, "银行卡校验失败"),
    BANKCARD_VALIDATION_FAIL_TIMEOUT(24, "银行卡校验失败超时"),
    RESETPWD(25, "修改登录密码"),
    INVALID_LOAN_REPEAT_SUBMIT(26, "放款失败订单超过有效重提时间，借据状态更新为“失效”，APP前段开放申请发送"),
    ;


    NotificationTemplateTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}