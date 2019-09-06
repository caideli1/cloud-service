package com.rabbit.example.rabbitdemo.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.service.feign.collection.CollectionClient;
import com.rabbit.example.rabbitdemo.rabbit.Sender;
import com.rabbit.example.rabbitdemo.rabbitStream.MySender;
import com.rabbit.example.rabbitdemo.rabbitStream.UserDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Autowired(required = false)
    private Sender sender;
    @Autowired
    private MySender mySender;
    @Autowired
    private CollectionClient collectionClient;

    @GetMapping("/sendMessage")
    public JsonResult sendMessage(String message) {
        sender.send(message);
        return JsonResult.ok();
    }

    @GetMapping("/sendMyMessage")
    public JsonResult sendMyMessage(String message) {
        UserDemo userDemo = new UserDemo();
        userDemo.setAge(12);
        userDemo.setUserName(message);
        mySender.send(userDemo);
        System.out.println("发送成功！");
        return JsonResult.ok();
    }

    @GetMapping("/getSleuthLog")
    public JsonResult getSleuthLog(@RequestParam String message) {
        return JsonResult.ok(collectionClient.getSleuthLog(message));
    }
}
