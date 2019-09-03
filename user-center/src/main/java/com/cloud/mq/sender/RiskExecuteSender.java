package com.cloud.mq.sender;

import com.cloud.model.risk.RiskExecuteRequestDto;
import com.cloud.mq.stream.RiskExecuteStreamClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * 请求执行风控消息的发送方
 *
 * @author walle
 */
@Slf4j
@Component
@EnableBinding(RiskExecuteStreamClient.class)
public class RiskExecuteSender {

    @Autowired
    private RiskExecuteStreamClient riskExecuteStreamClient;

    public void send(RiskExecuteRequestDto riskExecuteRequestDto) {
        riskExecuteStreamClient.output().send(MessageBuilder.withPayload(riskExecuteRequestDto).build());
    }
}
