package com.rabbit.example.rabbitdemo.rabbit;

import com.cloud.common.utils.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/9/5 15:17
 * 描述：
 */
//@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String message){
        String context = "hello "+ new Date();
        if (StringUtils.isNotBlank(message)){
            context = message;
        }
        this.rabbitTemplate.convertAndSend("hello", context);
    }
}
