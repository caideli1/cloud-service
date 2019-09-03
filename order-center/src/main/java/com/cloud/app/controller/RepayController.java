package com.cloud.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.common.utils.IOUtils;
import com.cloud.common.dto.JsonResult;
import com.cloud.service.feign.pay.PayClient;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Objects;


/**
 * 还款controller
 *
 * @author danquan.miao
 * @date 2019/6/11 0011
 * @since 1.0.0
 */
@Slf4j
@RestController
public class RepayController {
    @Autowired(required = false)
    private PayClient payClient;

    @ApiOperation(value = "还款")
    @PostMapping("/orders-anon/app/repay")
    public JsonResult payment(@RequestParam String loanNumber, @RequestParam Integer repayChannel) {
        return payClient.repay(loanNumber, repayChannel);
    }

    @ApiOperation(value = "还款回调")
    @PostMapping("/orders-anon/app/repayCallBack")
    public JsonResult repayCallBack(HttpServletRequest httpServletRequest) throws Exception {
        String requestBody = getRequestBody(httpServletRequest);
        return JsonResult.ok(payClient.repayCallBackFromOrder(requestBody));
    }

    @ApiOperation(value = "申请展期")
    @PostMapping("/orders-anon/app/applyExtension")
    public JsonResult applyExtension(@RequestParam String loanNumber, @RequestParam Integer repayChannel) {
        return payClient.applyExtension(loanNumber, repayChannel);
    }


    private String getRequestBody(HttpServletRequest httpServletRequest) throws IOException {
        String requestBody;
        String contentType = httpServletRequest.getContentType();
        if (Objects.equals(ContentType.APPLICATION_FORM_URLENCODED.getMimeType(), contentType)) {
            Enumeration<String> parameterNames = httpServletRequest.getParameterNames();
            JSONObject jsonObject = new JSONObject();
            while (parameterNames.hasMoreElements()) {
                String key = parameterNames.nextElement();
                jsonObject.put(key, httpServletRequest.getParameter(key));
            }
            requestBody = jsonObject.toJSONString();
        } else {
            requestBody = IOUtils.readStreamAsString(httpServletRequest.getInputStream(), "UTF-8");
        }

        return requestBody;
    }

}
