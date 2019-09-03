package com.cloud.common.utils;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Deprecated
public class MsgUtils {

    private static final Integer MESSAGE_SUCCESS_FLAG = 24;

    private MsgUtils() {
    }

    public static void sendMsg(String phone, String msgContent) {

        try {
            //	String msgContent = codeValue +" is your MoNeed login OTP.For security,pls DO NOT share it with anyone";
            String param = "country=91&sender=MONEED&route=4&mobiles=" + phone + "&authkey=243497ALR1yegRH5bc98558&message=" + msgContent;
            String msgUrl = "http://api.msg91.com/api/sendhttp.php";
            String result = HttpUtils.sendGet(msgUrl, param);
            // 如果短信发送失败
            if (result.length() != MESSAGE_SUCCESS_FLAG) {

                throw new IllegalArgumentException("短信发送失败,请联系短信提供商！");
            }
            log.info("短信發送成功=========>>>>>phone:" + phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
