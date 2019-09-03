package com.cloud.model.user.constants;

import java.util.Arrays;
import java.util.Optional;

/**
 * 用户账号类型
 *
 * @author nl
 */
public enum CredentialType {

    /**
     * 用户名
     */
    BACKEND_USERNAME,
    /**
     * app短信
     */
    APP_SMS,
    /**
     * app密码登录
     */
    APP_PWD,
    /**
     * 微信openid
     */
    WECHAT_OPENID,

    /**
     * 刷新
     */
    REFRESH;
    public static Optional<CredentialType> fromCredentialTypeString(String name) {
        return Arrays.stream(CredentialType.values())
                .filter(i -> i.name().equals(name))
                .findAny();
    }

}
