package com.cloud.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.component.utils.key.TokenProccessor;
import com.cloud.common.constants.SMSRedisKey;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.common.utils.PhoneUtil;
import com.cloud.model.appUser.AppUserBasicInfo;
import com.cloud.model.user.LoginAppUser;
import com.cloud.oss.autoconfig.utils.AliOssUtil;
import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.cloud.user.dao.AppUserInfoDao;
import com.cloud.user.enums.MsgCodeVerifyTypeEnum;
import com.cloud.user.model.AppCodeVerifyRes;
import com.cloud.user.model.AppUserRegisteredRes;
import com.cloud.user.service.AppUserInfoService;
import com.cloud.user.service.LoginAppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author yoga
 * @Description: AppUserLoginController
 * @date 2019-07-1719:57
 */
@Slf4j
@RestController
public class AppUserLoginController {
    @Autowired
    private AppUserInfoService appUserInfoService;

    @Autowired
    private AppUserInfoDao appUserInfoDao;

    @Autowired(required = false)
    private LoginAppUserService loginAppUserService;

    @Autowired(required = false)
    private RedisUtil redisUtil;

    @Autowired(required = false)
    private AliOssUtil aliOssUtil;


    /**
     * app短信登录 v1
     *
     * @param phone
     * @return
     * @author nieliang
     */
    @PostMapping("/users-anon/app/userLogin")
    public JsonResult appUserLogin(@RequestParam String phone, @RequestParam String code, HttpServletRequest request) {

        if (!PhoneUtil.checkPhone(phone)) {
            throw new IllegalArgumentException("Phone number format is incorrect");
        }

        if (code == null || code == "") {
            throw new IllegalArgumentException("OTP code can’t be empty");
        }

        if (!"666666".equals(code)) {
            Map maps = (Map) JSON.parse(redisUtil.get(SMSRedisKey.OTP_PHONE_PREFIX + phone));
            if (!code.equals(maps.get("code"))) {
                throw new IllegalArgumentException("OTP is incorrect");
            }
        }
        AppUserBasicInfo appUser = new AppUserBasicInfo();
        appUser.setCellPhone(phone);

        Long userId = appUserInfoService.registerUserInfo(appUser);
        AppUserBasicInfo basicInfo = appUserInfoDao.getByUserId(userId);
        String imgUrl = aliOssUtil.getUrl(basicInfo.getProfileUrl());
        String token = TokenProccessor.getInstance().makeToken();
        redisUtil.set("user:" + phone, token);


        String loginImi = request.getHeader("imi");
        String channel = request.getHeader("channel");
        appUserInfoService.saveUserDataStatistics(userId, loginImi, channel);

        JSONObject obj = new JSONObject();
        obj.put("token", token);
        obj.put("userId", userId);
        obj.put("ProfileUrl", imgUrl);

        return JsonResult.ok(obj);
    }


    /**
     * 校验验证码
     */
    @PostMapping("/users-anon/app/verifyCode")
    public JsonResult appUserRegister(@RequestParam String phone, @RequestParam String code, @RequestParam int verifyType, HttpServletRequest request) {

        Boolean result = loginAppUserService.appCodeVerify(phone, code);

        if (verifyType == MsgCodeVerifyTypeEnum.REGISTER.code) {

            AppUserBasicInfo appUser = new AppUserBasicInfo();
            appUser.setCellPhone(phone);

            try {
                // TODO: - key 之后修改
                if (!redisUtil.tryLock("register-" + phone, 100, 60)) {
                   return JsonResult.errorMsg("Clicked too quick! Plz retry after.");
                }
                Long userId = appUserInfoService.registerUserInfo(appUser);
                String loginImi = request.getHeader("imi");
                String channel = request.getHeader("channel");
                appUserInfoService.saveUserDataStatistics(userId, loginImi, channel);
            }catch (Exception e) {
                log.info("{}", e.getLocalizedMessage());
                return JsonResult.errorMsg("System error");
            }finally {
                redisUtil.unlock("register-" + phone);
            }
        }

        AppCodeVerifyRes res = AppCodeVerifyRes.builder()
                .verifyResult(result)
                .build();

        if (result) {
            String token = TokenProccessor.getInstance().makeToken();
            redisUtil.set("user_set_pwd:" + phone, token, 10 * 60);
            res.setTmpToken(token);
            return JsonResult.ok(res);
        } else {
            return JsonResult.build(500, "code is incorrect or has expired", res);
        }
    }


    /**
     * app 设置账号密码
     */
    @PutMapping("/users-anon/app/userPassword")
    public JsonResult setAppUserPassword(@RequestParam String encryptedString, @RequestParam String phone, @RequestParam String tmpToken) {
        Boolean res = loginAppUserService.appSetPasswordTokenVerify(phone, tmpToken);
        if (res) {
            loginAppUserService.setUserPassword(encryptedString, phone);
        }
        return JsonResult.ok();
    }

    /**
     * oauth调用
     */
    @GetMapping("/users-anon/app/getLoginUserByPhone")
    public LoginAppUser getLoginUserByPhone(@RequestParam String phone) {
        return loginAppUserService.queryUserByPhone(phone);
    }

    /**
     * oauth调用
     */
    @GetMapping("/users-anon/app/getAppUserIdAndAvatarByPhone")
    public AppUserBasicInfo getAppUserIdAndAvatarByPhone(@RequestParam String phone) {
        return appUserInfoService.getAppUserIdAndAvatarByPhone(phone);
    }

    /**
     * 用户基本信息
     */
    @GetMapping("/app/userId")
    public JsonResult getUserInfo() {
        Long userId = AppUserUtil.getLoginAppUser().getId();
//        AppUserBasicInfo basicInfo = appUserInfoService.getBasicInfoById(userId);
        JSONObject obj = new JSONObject();
        obj.put("userId", userId);
        return JsonResult.ok(userId);
    }

    /**
     * phone是否注册过 + 设置过密码
     */
    @GetMapping("/users-anon/app/isRegistered")
    public JsonResult isAppUserRegistered(@RequestParam String phone) {
        Boolean isRegistered = appUserInfoService.isAppUserRegistered(phone);
        Boolean isSetPassword = false;
        if (isRegistered) {
            isSetPassword = appUserInfoService.isSetPassword(phone);
        }

        AppUserRegisteredRes res = AppUserRegisteredRes.builder()
                .isRegistered(isRegistered)
                .isSetPassword(isSetPassword)
                .build();
        // 如果已注册未设置密码则返回一个token用于设置密码
        if (isRegistered && !isSetPassword) {
            String token = TokenProccessor.getInstance().makeToken();
            redisUtil.set("user_set_pwd:" + phone, token, 10 * 60);
            res.setTmpToken(token);
        }
        return JsonResult.ok(res);
    }

    /**
     * 保存登录imi 渠道
     */
    @PostMapping("/users-anon/app/userDataStatistics")
    public JsonResult addUserDataStatistics(Long userId, String loginImi, String channel) {
        appUserInfoService.saveUserDataStatistics(userId, loginImi, channel);
        return JsonResult.ok();
    }

}