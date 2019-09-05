package com.rabbit.example.rabbitdemo.controller;

import com.cloud.common.dto.JsonResult;
import com.rabbit.example.rabbitdemo.rabbit.Sender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/9/5 16:08
 * 描述：
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    private Sender sender;

    @GetMapping("/sendMessage")
    public JsonResult queryCollectionList(String message) {
        sender.send(message);
        return JsonResult.ok();
    }
}
