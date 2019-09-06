package com.rabbit.example.rabbitdemo.rabbitStream;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/9/5 17:22
 * 描述：
 */
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding({MyStreamChannel.class})
public class MyReceiver {
    @StreamListener(MyStreamChannel.TOPIC_INPUT_NAME)
    public void receive(UserDemo userDemo) {
        System.out.println("接收成功: " + JSON.toJSONString(userDemo));
    }
}
