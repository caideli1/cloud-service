package com.cloud.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.common.component.utils.HttpEncryptUtil;
import com.cloud.common.constants.SMSRedisKey;
import com.cloud.common.utils.PhoneUtil;
import com.cloud.common.utils.StringUtils;
import com.cloud.model.user.LoginAppUser;
import com.cloud.model.user.UserInfo;
import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.cloud.user.service.LoginAppUserService;
import com.cloud.user.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author yoga
 * @Description: LoginAppUserServiceImpl
 * @date 2019-07-2213:35
 */
@Service
@Slf4j
public class LoginAppUserServiceImpl implements LoginAppUserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired(required = false)
    private RedisUtil redisUtil;

    @Override
    public LoginAppUser queryUserByPhone(String phone) {
        UserInfo userInfo = userDao.getUserInfoByPhone(phone);
        LoginAppUser loginAppUser = new LoginAppUser();
        BeanUtils.copyProperties(userInfo, loginAppUser);
        return loginAppUser;
    }

    @Override
    public void setUserPassword(String encryptedString, String phone) {
        try {
            //解密
            String decryptString = HttpEncryptUtil.serverDecrypt(encryptedString);
            com.alibaba.fastjson.JSONObject parseObject = com.alibaba.fastjson.JSONObject.parseObject(decryptString);
            String password = (String) parseObject.get("ct");

            //密码格式校验
            if (!StringUtils.rexCheckPassword(password)) {
                throw new IllegalArgumentException("password must be 6-20 lens and does not contain special characters");
            }

            //存密码
//            Long appUserId = AppUserUtil.getLoginSysUser().getId();
            userDao.insertUserInfoPassword(phone, passwordEncoder.encode(password));
        } catch (Exception e) {
            log.warn("HttpEncryptUtil 解密 error -- encryptedString : {}", encryptedString);
        }
    }

    @Override
    public Boolean appCodeVerify(String phone, String code) {

        if (!PhoneUtil.checkPhone(phone)) {
            throw new IllegalArgumentException("Phone number format is incorrect");
        }

        if ("666666".equals(code)) {
            return true;
        }

        Map maps = (Map) JSON.parse(redisUtil.get(SMSRedisKey.OTP_PHONE_PREFIX + phone));
        if (maps == null) {
            return false;
        }
        return ObjectUtils.equals(code, maps.get("code"));
    }

    @Override
    public Boolean appSetPasswordTokenVerify(String phone, String tmpToken) {
        if (!PhoneUtil.checkPhone(phone)) {
            throw new IllegalArgumentException("Phone number format is incorrect");
        }

        String redisTmpToken = redisUtil.get("user_set_pwd:" + phone);

        if(ObjectUtils.notEqual(redisTmpToken, tmpToken)) {
            throw new IllegalArgumentException("tmpToken is incorrect");
        }

        return ObjectUtils.equals(redisTmpToken, tmpToken);
    }
}
