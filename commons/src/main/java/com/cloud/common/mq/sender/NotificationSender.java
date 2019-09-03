package com.cloud.common.mq.sender;

import com.cloud.common.mq.stream.NotificationStreamClient;
import com.cloud.model.notification.NotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 短信、邮件消息生产者发送消息
 *
 * @author walle
 */
@Slf4j
@Component
@EnableBinding(value = {NotificationStreamClient.class})
public class NotificationSender {

    @Autowired
    private NotificationStreamClient notificationStreamClient;

    public void send(NotificationDto notificationDto) {
        try {
            if (Objects.isNull(notificationDto)) {
                throw new IllegalArgumentException("notificationDto is null");
            }

            if (Objects.isNull(notificationDto.getTemplateType())) {
                throw new IllegalArgumentException("templateType is null");
            }

            if (Objects.isNull(notificationDto.getEmail())
                    && Objects.isNull(notificationDto.getMobile())
                    && Objects.isNull(notificationDto.getUserId())) {
                throw new IllegalArgumentException("email、phone and userId cannot be empty at the same time");
            }

            notificationStreamClient.output().send(MessageBuilder.withPayload(notificationDto).build());
        } catch (Exception e) {
            log.error("send sms error: {}", e.getMessage());
        }
    }
}
