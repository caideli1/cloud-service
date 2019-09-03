package com.cloud.service.feign.notification;

import com.cloud.service.fallback.NotificationFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "notification-center", fallbackFactory = NotificationFallBackFactory.class)
public interface SmsClient {
	@GetMapping(value = "/notification-anon/internal/phone", params = { "key", "code" })
	String matcheCodeAndGetPhone(@RequestParam("key") String key, @RequestParam("code") String code,
                                 @RequestParam(value = "delete", required = false) Boolean delete,
                                 @RequestParam(value = "second", required = false) Integer second);
	
	@GetMapping("/notification-anon/notice/smsManageInfo")
	void smsManageInfo(@RequestParam String content,@RequestParam String to,@RequestParam Integer templateType,@RequestParam Integer userId);
}
