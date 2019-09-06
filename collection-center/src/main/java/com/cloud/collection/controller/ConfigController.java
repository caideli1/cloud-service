package com.cloud.collection.controller;

import com.cloud.common.dto.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping("/collection/from")
    @ResponseBody
    public JsonResult from() {
        return JsonResult.ok(this.from);
    }

    @GetMapping("/collection/getSleuthLog")
    public String getSleuthLog(@RequestParam String message) {
        return message+"from collection sleuthLog";
    }
}
