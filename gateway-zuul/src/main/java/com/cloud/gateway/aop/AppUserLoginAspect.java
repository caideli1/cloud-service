package com.cloud.gateway.aop;

import com.cloud.common.dto.JsonResult;
import com.cloud.common.exception.BusinessException;
import com.cloud.gateway.enums.GatewayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author yoga
 * @Description: AppUserLoginAspect
 * @date 2019-07-2615:25
 */

@Slf4j
@Component
@Aspect
public class AppUserLoginAspect {
    @Value("${login.maxCountPerDay}")
    private long maxLoginCount;
//    @Autowired(required = false)
//    private RedisUtil redisUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static String APP_LOGIN_CNT_PREFIX = "APP_LOGIN_WRONG_CNT_PREFIX_";

    @PostConstruct
    public void configRedisTemple() {
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setValueSerializer(new StringRedisSerializer());
    }

    @Pointcut("execution(public * com.cloud.gateway.controller.TokenController.appLogin(String, String)) && args(phone, eStr)")
    public void appUserLogin(String phone, String eStr) { }

    @Around("appUserLogin(phone, eStr)")
    public Object aroundLogin(ProceedingJoinPoint joinPoint, String phone, String eStr) {

        Long loginCnt = getWrongLoginCount(phone);
        if (null != loginCnt && loginCnt >= maxLoginCount) {
            log.info("{} -- 登录错误次数超过{}次", phone, maxLoginCount);
            throw new BusinessException(GatewayStatusEnum.PASSWORD_RETRY_OVER_LIMIT);
        }

        try {
            JsonResult result = (JsonResult) joinPoint.proceed();

            if (result.getCode() != 200 && result.getCode() != 505) { // 505为FeignFallback, hardcode，别处返回505有可能会出问题
                increaseWrongLoginCount(phone);
            }
            return result;
        }catch (Throwable throwable) {
            log.info(throwable.getLocalizedMessage());
        }
        return null;
    }

    private String getAppUserWrongLoginCountKey(String phone) {
        return APP_LOGIN_CNT_PREFIX + phone;
    }

    private Long getWrongLoginCount(String phone) {
        Long loginCnt = null;
        String cntValue = stringRedisTemplate.opsForValue().get(getAppUserWrongLoginCountKey(phone));
        if (null != cntValue) {
            loginCnt = Long.valueOf(cntValue);
        }
        return loginCnt;
    }

    private Long increaseWrongLoginCount(String phone) {
        Long cnt = getWrongLoginCount(phone);
        if (cnt == null) {
            stringRedisTemplate.opsForValue().set(getAppUserWrongLoginCountKey(phone), "1", 1, TimeUnit.DAYS);
            return 1L;
        }
        return stringRedisTemplate.boundValueOps(getAppUserWrongLoginCountKey(phone)).increment(1);
    }
}
