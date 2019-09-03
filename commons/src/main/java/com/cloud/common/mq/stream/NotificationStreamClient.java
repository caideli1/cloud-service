package com.cloud.common.mq.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * 短信、邮件消息绑定接口
 *
 * @author walle
 */
@Component
public interface NotificationStreamClient {

    String TOPIC_OUTPUT_NAME = "notification-topic-output";

    String TOPIC_INPUT_NAME = "notification-topic-input";

    @Output(TOPIC_OUTPUT_NAME)
    MessageChannel output();

    @Input(TOPIC_INPUT_NAME)
    SubscribableChannel input();
}
