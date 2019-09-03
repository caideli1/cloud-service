package com.cloud.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.component.utils.Util;
import com.cloud.common.constants.MessageSendCount;
import com.cloud.common.constants.SMSRedisKey;
import com.cloud.common.enums.NotificationTemplateTypeEnum;
import com.cloud.common.mq.sender.NotificationSender;
import com.cloud.model.notification.NotificationDto;
import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.cloud.user.enums.MsgCodeVerifyTypeEnum;
import com.cloud.user.service.VerificationCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private NotificationSender notificationSender;

    @Autowired(required = false)
    private RedisUtil redisUtil;

    @Override
    public void sendVerificationCode(String phone, Integer msgType) {
        // 1. 限制条件校验 - 6/day && 3/hour && expire=60s
        this.checkLimitCondition(phone);
        // 2. 生成验证码
        String verificationCode = this.generateCode(phone);
        // 3. 发送验证码短信
        this.sendMessage(phone, verificationCode, msgType);
    }

    private String generateCode(String phone) {
        String code = Util.randomCode(6);
        Map<String, String> map = new HashMap<>(16);
        map.put("code", code);
        map.put("phone", phone);
        // 验证码缓存 redis 10分钟
        redisUtil.set(SMSRedisKey.OTP_PHONE_PREFIX + phone, JSONObject.toJSONString(map), 10 * 60);
        return code;
    }

    private void sendMessage(String phone, String code, Integer msgType) {
        Map<String, Object> params = new HashMap<>(16);
        params.put("code", code);
        // 为了适配APP老版本,将签名的短信类型3转换成消息模板类型的2
        if (MsgCodeVerifyTypeEnum.SINGNATURE.getCode().equals(msgType)) {
            msgType = NotificationTemplateTypeEnum.LOAN_CONTRACT_CONFIRMED.getCode();
        }
        log.info("手机[{}] 发送验证码: [{}]", phone, code);

        NotificationDto notificationDto = NotificationDto.builder()
                .templateType(msgType)
                .mobile(phone)
                .templateOtherParams(params)
                .build();
        notificationSender.send(notificationDto);
    }

    /**
     * OPT发送次数限制：一小时内3次上限；一天内6次上限。次日重新起算。（不区分登录、找回密码、合同签订OTP）
     *
     * @param phone
     */
    private void checkLimitCondition(String phone) {

        // 一小时内3次上限；一天内6次上限。次日重新起算。
        String optLimitDayKey = SMSRedisKey.OTP_LIMIT_DAY_PREFIX + phone;
        String optLimitHourKey = SMSRedisKey.OTP_LIMIT_HOUR_PREFIX + phone;

        // 一天内没有发送过对应短信
        if (!redisUtil.exists(optLimitDayKey)) {
            long restOfDay = Duration.between(LocalDateTime.now(), LocalDateTime.of(LocalDate.now(), LocalTime.MAX)).getSeconds();
            redisUtil.incr(optLimitDayKey);
            redisUtil.expire(optLimitDayKey, restOfDay);
        }

        // 一小时内没有发送过短信
        if (!redisUtil.exists(optLimitHourKey)) {
            redisUtil.incr(optLimitHourKey);
            redisUtil.expire(optLimitHourKey, 60 * 60);
        }

        // 一天内有发送过短信
        String sendMessageCountForDay = redisUtil.get(optLimitDayKey);
        if (Integer.valueOf(sendMessageCountForDay).equals(MessageSendCount.OPT_LIMIT_DAY)) {
            throw new IllegalArgumentException("OTP up to the limit. Pls retry tomorrow");
        }

        // 一小时内有发送过短信
        String sendMessageCountForHour = redisUtil.get(optLimitHourKey);
        if (Integer.valueOf(sendMessageCountForHour).equals(MessageSendCount.OPT_LIMIT_HOUR)) {
            throw new IllegalArgumentException("OTP up to the limit. Pls retry after 1 hour");
        } else {
            // 没有达到一小时最大限制数
            redisUtil.incr(optLimitHourKey);
            // 没有达到一天最大限制数
            redisUtil.incr(optLimitDayKey);
        }
    }

}
