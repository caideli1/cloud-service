package com.cloud.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.component.utils.SHAUtils;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.utils.HttpUtils;
import com.cloud.user.config.AadhaarBridgeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
@RestController
public class AadhaarBridgeController {

    @Autowired
    private AadhaarBridgeConfig aadhaarBridgeConfig;

    /**
     * 获取Kl 验签信息
     * @author nieliang
     * @param
     * @param
     * @return
     */

    @GetMapping("/users-anon/app/getKLSignInfo")
    public JsonResult getKLSignInfo(){
        String request_id = UUID.randomUUID().toString();

        String hash = SHAUtils.getSHA256StrJava(jointSignParam(request_id));
        JSONObject signInfo = new JSONObject();
        signInfo.put("client_code", aadhaarBridgeConfig.getClientCode());
        signInfo.put("api_key", aadhaarBridgeConfig.getApiKey());
        signInfo.put("request_id", request_id);
        signInfo.put("hash", hash);
        return new JsonResult(signInfo);

    }

    /**
     * @desc eKYC Fetch
     * @author nieliang
     * @return
     */
    @PostMapping("/users-anon/app/fetchKYCInfo")
    public JsonResult  fetchKYCInfo(HttpServletRequest request){
        String userId = request.getHeader("mid");
        if (userId == null || userId == "") {
            throw new IllegalArgumentException("userId is empty");
        }
        String hash = SHAUtils.getSHA256StrJava(jointSignParam(userId));

        JSONObject obj = new JSONObject();
        JSONObject cc = new JSONObject();
        obj.put("api_key", aadhaarBridgeConfig.getApiKey());

        obj.put("request_id", "rid2334567891");

        obj.put("user_id", userId);
        obj.put("hash", hash);
        cc.put("request", obj);

        JSONObject header = new JSONObject();
        header.put("client_code", aadhaarBridgeConfig.getClientCode());
        header.put("sub_client_code", "M5a01vUa");
        header.put("actor_type", "NA");
        header.put("channel_code", "ANDROID_SDK");

        header.put("stan", UUID.randomUUID().toString());

        header.put("user_handle_type", "<>");
        header.put("user_handle_value", "<>");
        header.put("location", "");
        header.put("transmission_datetime", "1533123525716");
        header.put("run_mode", "REAL");
        header.put("client_ip", "192.168.0.1");
        header.put("operation_mode", "SELF");
        header.put("channel_version", "0.0.1");
        header.put("function_code", "DEFAULT");
        header.put("function_sub_code", "DEFAULT");

        cc.put("headers", header);

        String result = HttpUtils.doPost(aadhaarBridgeConfig.getUrl(), cc);
        return new JsonResult(null);

    }

    /**
     * @desc 拼接验签参数
     * @author nieliang
     * @return
     */
    private String jointSignParam(String param) {

        StringBuilder builder = new StringBuilder(aadhaarBridgeConfig.getClientCode());
        builder.append("|");
        builder.append(param);
        builder.append("|");
        builder.append(aadhaarBridgeConfig.getApiKey());
        builder.append("|");
        builder.append(aadhaarBridgeConfig.getSalt());

        return builder.toString();
    }
}
