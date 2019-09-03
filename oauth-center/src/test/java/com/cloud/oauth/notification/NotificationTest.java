package com.cloud.oauth.notification;

import com.cloud.common.mq.stream.NotificationStreamClient;
import com.cloud.model.notification.NotificationDto;
import com.cloud.oauth.OauthTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(NotificationStreamClient.class)
public class NotificationTest extends OauthTest {

    @Autowired
    private NotificationStreamClient notificationStreamClient;
    
    @Test
    public void test (){
        NotificationDto notificationDto = NotificationDto.builder()
                .templateType(1)
                .mobile("123456")
                .build();
        notificationStreamClient.output().send(MessageBuilder.withPayload(notificationDto).build());
    }
}
