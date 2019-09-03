package com.cloud.service.fallback;

import com.cloud.service.feign.notification.SmsClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 通知中心服务降级类
 *
 * @author danquan.miao
 * @date 2019/7/23 0023
 * @since 1.0.0
 */
@Component
public class NotificationFallBackFactory implements FallbackFactory<SmsClient> {
    @Override
    public SmsClient create(Throwable throwable) {
        return new SmsClient() {

			@Override
			public String matcheCodeAndGetPhone(String key, String code, Boolean delete, Integer second) {
				// TODO 自动生成的方法存根
				return null;
			}

			@Override
			public void smsManageInfo(String content, String to, Integer templateType, Integer userId) {
				// TODO 自动生成的方法存根
			}
        };
    }
}
