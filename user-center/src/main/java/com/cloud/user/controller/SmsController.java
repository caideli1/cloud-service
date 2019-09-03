package com.cloud.user.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.common.utils.PhoneUtil;
import com.cloud.user.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

    @Autowired
    private VerificationCodeService verificationCodeService;


    /**
     * 发送短信验证码
     *
     * @param phone
     * @return
     * @author nieliang
     */
    @PostMapping(value = "/users-anon/app/getMsgCodes", params = {"phone", "msgType"})
    public JsonResult sendSmsVerificationCode(String phone, int msgType) {
        if (!PhoneUtil.checkPhone(phone)) {
            throw new IllegalArgumentException("Phone number format is incorrect");
        }

        verificationCodeService.sendVerificationCode(phone, msgType);
        return JsonResult.build(200, "OTP send success", null);
    }
}
