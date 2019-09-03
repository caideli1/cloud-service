package com.cloud.oauth.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.common.constants.SMSRedisKey;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.LoginSysUser;
import com.cloud.model.user.constants.CredentialType;
import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.cloud.service.feign.backend.BackendClient;
import com.cloud.service.feign.user.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * 根据用户名获取用户<br>
 * <p>
 * 密码校验请看下面两个类
 *
 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
 * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
 */
@Slf4j
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private BackendClient backendClient;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserClient userClient;

    @Autowired(required = false)
    private RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 为了支持多类型登录，这里username后面拼装上登录类型,如type|username
        String[] params = username.split("\\|");

        Optional<CredentialType> optionalCredentialType = CredentialType.fromCredentialTypeString(params[0]);


        if (!optionalCredentialType.isPresent()) { //refresh_token
            LoginAppUser loginAppUser = userClient.getLoginUserByPhone(username);
            return loginAppUser;
        }

        CredentialType credentialType = optionalCredentialType.get();

        // 登录类型
        if (CredentialType.BACKEND_USERNAME == credentialType) {// 管理后台密码登录
            String userName = params[1];// 真正的用户名
            LoginSysUser loginSysUser = backendClient.findByUsername(userName);
            if (loginSysUser == null) {
                throw new AuthenticationCredentialsNotFoundException("the user does not exist");
            } else if (!loginSysUser.isEnabled()) {
                throw new DisabledException("用户已作废");
            }
            return loginSysUser;
        }
        else if (CredentialType.APP_SMS == credentialType) {// app短信登陆 // todo: -- sms login 这种处理简单，可以以后改进
            String phone = params[1];// 电话
            String code = params[2];
            LoginAppUser loginAppUser = userClient.getLoginUserByPhone(phone);
            handlerPhoneSmsLogin(loginAppUser, phone, code);
            return loginAppUser;
        }
        else if (CredentialType.APP_PWD == credentialType) {// app密码登陆
            String phone = params[1];// 电话
            LoginAppUser loginAppUser = userClient.getLoginUserByPhone(phone);
            return loginAppUser;
        }

        return null;
    }

    private void handlerWechatLogin(LoginSysUser loginAppUser, String[] params) {
        if (params.length < 3) {
            throw new IllegalArgumentException("非法请求");
        }

        String openid = params[0];
        String tempCode = params[2];

        backendClient.wechatLoginCheck(tempCode, openid);

        // 其实这里是将密码重置，网关层的微信登录接口，密码也用同样规则即可
        loginAppUser.setPassword(passwordEncoder.encode(tempCode));
        log.info("微信登陆，{},{}", loginAppUser, openid);
    }

    // todo: -- sms login 这种处理简单，可以以后改进
    /**
     * 手机号+短信验证码登陆，处理逻辑
     *
     * @param loginAppUser
     * @param - params
     */
    private void handlerPhoneSmsLogin(LoginAppUser loginAppUser, String phone, String code) {
        // 验证code --
        if (code == null || code == "") {
            throw new IllegalArgumentException("OTP code can’t be empty");
        }

        if (!"666666".equals(code)) {
            Map maps = (Map) JSON.parse(redisUtil.get(SMSRedisKey.OTP_PHONE_PREFIX + phone));
            if (!ObjectUtils.equals(code, maps.get("code"))) {
                throw new IllegalArgumentException("OTP is incorrect");
            }
        }

        // 其实这里是将密码重置，网关层的短信登录接口，密码也用同样规则即可
        loginAppUser.setPassword(passwordEncoder.encode(code));
        log.info("手机号+短信验证码登陆，{},{}", loginAppUser.getUsername(), code);
    }

}
