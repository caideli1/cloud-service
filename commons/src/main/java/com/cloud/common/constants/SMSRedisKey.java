package com.cloud.common.constants;

/**
 * 消息模块redis key
 *
 * @author walle
 */
public class SMSRedisKey {

    /**
     * 每日发送OPT限制key 前缀
     */
    public static final String OTP_LIMIT_DAY_PREFIX = "SMS:OTP:LIMIT:DAY:";

    /**
     * 每小时发送OPT限制key 前缀
     */
    public static final String OTP_LIMIT_HOUR_PREFIX = "SMS:OTP:LIMIT:HOUR:";

    /**
     * 手机验证码缓存key 前缀
     */
    public static final String OTP_PHONE_PREFIX = "SMS:OTP:";

}
