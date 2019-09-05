package com.cloud.collection.controller;

import com.cloud.common.dto.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/9/4 16:33
 * 描述：
 */
@Slf4j
@RefreshScope
@RestController
public class ConfigController {
    @Value("${from}")
    private String from;

    @RequestMapping("/collection/from")
    @ResponseBody
    public JsonResult from() {
        return JsonResult.ok(this.from);
    }
}
