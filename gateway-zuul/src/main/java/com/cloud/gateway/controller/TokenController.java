package com.cloud.gateway.controller;

import com.cloud.common.component.utils.HttpEncryptUtil;
import com.cloud.common.dto.JsonResult;
import com.cloud.model.appUser.AppUserBasicInfo;
import com.cloud.model.oauth.SystemClientInfo;
import com.cloud.model.user.constants.CredentialType;
import com.cloud.service.feign.oauth.Oauth2Client;
import com.cloud.service.feign.user.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆、刷新token、退出
 *
 * @author nl
 */
@Slf4j
@RestController
public class TokenController {

    @Autowired
    private Oauth2Client oauth2Client;
    @Autowired
    private UserClient userClient;

    /**
     * 系统登陆<br>
     * 根据用户名登录<br>
     * 采用oauth2密码模式获取access_token和refresh_token
     *
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/sys/login")
    public Map<String, Object> login(String username, String password) {
        Map<String, String> parameters = new HashMap<>(16);
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
//		parameters.put("username", username);
        // 为了支持多类型登录，这里在username后拼装上登录类型
        parameters.put("username", CredentialType.BACKEND_USERNAME.name() + "|" + username );
        parameters.put("password", password);

        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);
        saveLoginLog(username, "管理后台用户名密码登陆");

        return tokenInfo;
    }

    /**
     * app短信登录
     */
    @PostMapping("/app/login-sms")
    public JsonResult appSmsLogin(@RequestParam String phone, @RequestParam String code, HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>(16);
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
        // 为了支持多类型登录，这里在username后拼装上登录类型，同时为了校验短信验证码，我们也拼上code等
        parameters.put("username", CredentialType.APP_SMS.name() + "|" + phone + "|" + code);
        // 短信登录无需密码，但security底层有密码校验，我们这里将手机号作为密码，认证中心采用同样规则即可
        // todo: -- sms login 这种处理方式简单，可以以后改进
        parameters.put("password", code);
        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);
        saveLoginLog(phone, "app手机号短信登陆");

        return JsonResult.ok(tokenInfo);
    }


    /**
     * app手机+密码登录
     */
    @PostMapping("/app/login")
    public JsonResult appLogin(String phone, String encryptedString, HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>(16);
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
        parameters.put("username", CredentialType.APP_PWD.name() + "|" +  phone);

        try {
            //解密
            String decryptString = HttpEncryptUtil.serverDecrypt(encryptedString);
            com.alibaba.fastjson.JSONObject parseObject = com.alibaba.fastjson.JSONObject.parseObject(decryptString);
            String password = (String) parseObject.get("ct");
            parameters.put("password", password);
        } catch (Exception e) {
            log.warn("HttpEncryptUtil 解密 error -- encryptedString : {}", encryptedString, e.getLocalizedMessage());
        }

        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);

        if (tokenInfo.containsKey("access_token")) {
            AppUserBasicInfo basicInfo = userClient.getAppUserIdAndAvatarByPhone(phone);
            tokenInfo.put("user_id", basicInfo.getId());
            tokenInfo.put("avatar", basicInfo.getProfileUrl());
            saveLoginLog(phone, "app手机账号密码登陆");

            String loginImi = request.getHeader("imi");
            String channel = request.getHeader("channel");
            userClient.addUserDataStatistics(basicInfo.getId(), loginImi, channel);

            return JsonResult.ok(tokenInfo);
        } else if (tokenInfo.containsKey("code")){
            return JsonResult.build((Integer) tokenInfo.getOrDefault("code", 400),
                    (String) tokenInfo.getOrDefault("msg", ""),
                    null);
        }

        return JsonResult.errorMsg("login error");
    }



    /**
     * 微信登录
     *
     * @return

    @PostMapping("/sys/login-wechat")
    public Map<String, Object> smsLogin(String openid, String tempCode) {
        Map<String, String> parameters = new HashMap<>(16);
        parameters.put(OAuth2Utils.GRANT_TYPE, "password");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
        // 为了支持多类型登录，这里在username后拼装上登录类型，同时为了服务端校验，我们也拼上tempCode
        parameters.put("username", openid + "|" + CredentialType.WECHAT_OPENID.name() + "|" + tempCode);
        // 微信登录无需密码，但security底层有密码校验，我们这里将手机号作为密码，认证中心采用同样规则即可
        parameters.put("password", tempCode);

        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);
        saveLoginLog(openid, "微信登陆");

        return tokenInfo;
    }
     */

    /**
     * 登陆日志
     *
     * @param username
     */
    private void saveLoginLog(String username, String remark) {
        log.info("{}登陆", username);
    }

    /**
     * 系统刷新refresh_token
     *
     * @param refreshToken
     * @return
     */
    @PostMapping("/sys/refreshToken")
    public JsonResult refresh_token(String phone, String refreshToken) {
        Map<String, String> parameters = new HashMap<>(16);
        parameters.put(OAuth2Utils.GRANT_TYPE, "refresh_token");
        parameters.put(OAuth2Utils.CLIENT_ID, SystemClientInfo.CLIENT_ID);
        parameters.put("client_secret", SystemClientInfo.CLIENT_SECRET);
        parameters.put(OAuth2Utils.SCOPE, SystemClientInfo.CLIENT_SCOPE);
        parameters.put("refresh_token", refreshToken);

        Map<String, Object> tokenInfo = oauth2Client.postAccessToken(parameters);

        if (tokenInfo.containsKey("access_token")) {
            AppUserBasicInfo basicInfo = userClient.getAppUserIdAndAvatarByPhone(phone);
            tokenInfo.put("user_id", basicInfo.getId());
            tokenInfo.put("avatar", basicInfo.getProfileUrl());
            saveLoginLog(phone, "app refreshToken");
            return JsonResult.ok(tokenInfo);
        } else if (tokenInfo.containsKey("code")){
            return JsonResult.build((Integer) tokenInfo.getOrDefault("code", 400),
                    (String) tokenInfo.getOrDefault("message", ""),
                    null);
        }
        return JsonResult.errorMsg("refreshToken error");
    }

    /**
     * 退出
     *
     * @param access_token
     */
    @GetMapping("/sys/logout")
    public JsonResult logout(String access_token, @RequestHeader(required = false, value = "Authorization") String token) {
        if (StringUtils.isBlank(access_token)) {
            if (StringUtils.isNoneBlank(token)) {
                access_token = token.substring(OAuth2AccessToken.BEARER_TYPE.length() + 1);
            }
        }
        boolean flag = oauth2Client.removeToken(access_token);
        if (flag) {
            return JsonResult.ok("退出登录");
        } else {
            return JsonResult.errorMsg("退出失败");
        }
    }


}
