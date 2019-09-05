package com.rabbit.example.rabbitdemo.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/9/5 15:22
 * 描述：
 */
@Component
//@RabbitListener(queues = "hello")
public class Receiver {
    //@RabbitHandler
    @RabbitListener(queues = "hello")
    public void process(String hello) {
        System.out.println("Receiver : "+ hello);
    }
}
