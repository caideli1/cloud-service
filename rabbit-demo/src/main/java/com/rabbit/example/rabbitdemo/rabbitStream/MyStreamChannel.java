package com.rabbit.example.rabbitdemo.rabbitStream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/9/5 18:47
 * 描述：
 */
@Component
public interface MyStreamChannel {
    String TOPIC_INPUT_NAME = "my-topic-input";

    String TOPIC_OUTPUT_NAME = "my-topic-output";

    @Input(TOPIC_INPUT_NAME)
    SubscribableChannel input();

    @Output(TOPIC_OUTPUT_NAME)
    MessageChannel output();
}
