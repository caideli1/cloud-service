package com.cloud.common.constants;

/**
 * 发送短信计数常量类
 *
 * @author walle
 */
public class MessageSendCount {

    /**
     * 每天发送OPT限制数 6次 （redis increment 1 = 7）
     */
    public static final Integer OPT_LIMIT_DAY = 7;

    /**
     * 每小时发送OPT限制数 3次 （redis increment 1 = 4）
     */
    public static final Integer OPT_LIMIT_HOUR = 4;
}
