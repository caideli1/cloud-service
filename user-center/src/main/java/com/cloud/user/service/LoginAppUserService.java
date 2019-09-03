package com.cloud.user.service;

import com.cloud.model.user.LoginAppUser;

/**
 * @author yoga
 * @Description: LoginAppUserService
 * @date 2019-07-2213:35
 */
public interface LoginAppUserService {
    LoginAppUser queryUserByPhone(String phone);

    void setUserPassword(String encryptedString, String phone);

    Boolean appCodeVerify(String phone, String code);

    Boolean appSetPasswordTokenVerify(String phone, String tmpToken);
}
