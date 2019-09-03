package com.cloud.mq.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * 风控请求消息绑定接口
 */
@Component
public interface RiskExecuteStreamClient {

    String TOPIC_INPUT_NAME = "risk-topic-input";

    String TOPIC_OUTPUT_NAME = "risk-topic-output";

    @Input(TOPIC_INPUT_NAME)
    SubscribableChannel input();

    @Output(TOPIC_OUTPUT_NAME)
    MessageChannel output();

}
