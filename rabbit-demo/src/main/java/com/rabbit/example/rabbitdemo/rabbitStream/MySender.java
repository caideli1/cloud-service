package com.rabbit.example.rabbitdemo.rabbitStream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/9/5 19:30
 * 描述：
 */@Slf4j
@Component
@EnableBinding(value = {MyStreamChannel.class})
public class MySender {

    @Autowired
    private MyStreamChannel myStreamChannel;

    public void send(UserDemo userDemo) {
        myStreamChannel.output().send(MessageBuilder.withPayload(userDemo).build());
    }
}
