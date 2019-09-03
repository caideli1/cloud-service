package com.cloud.service.fallback;

import com.cloud.service.feign.oauth.Oauth2Client;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 认证中心服务降级类
 *
 * @author danquan.miao
 * @date 2019/7/24 0024
 * @since 1.0.0
 */
@Component
public class OauthFallBackFactory implements FallbackFactory<Oauth2Client> {
    @Override
    public Oauth2Client create(Throwable throwable) {
        return new Oauth2Client() {
            @Override
            public Map<String, Object> postAccessToken(Map<String, String> parameters) {
                return null;
            }

            @Override
            public boolean removeToken(String access_token) {
                return false;
            }
        };
    }
}
