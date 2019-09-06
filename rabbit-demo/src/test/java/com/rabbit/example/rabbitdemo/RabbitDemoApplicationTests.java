package com.rabbit.example.rabbitdemo;

import com.rabbit.example.rabbitdemo.rabbit.Sender;
import com.rabbit.example.rabbitdemo.rabbitStream.MySender;
import com.rabbit.example.rabbitdemo.rabbitStream.MyStreamChannel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitDemoApplication.class)
public class RabbitDemoApplicationTests {

   /* @Autowired(required = false)
    private Sender sender;*/
   @Autowired
   private MySender mySender;
    /*@Test
    public void hello() {
        sender.send(null);
    }*/

    @Test
    public void sendStream() {
        mySender.send(null);
        System.out.println("发送成功！");
    }

}
