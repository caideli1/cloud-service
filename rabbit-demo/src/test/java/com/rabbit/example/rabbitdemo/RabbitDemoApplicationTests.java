package com.rabbit.example.rabbitdemo;

import com.rabbit.example.rabbitdemo.rabbit.Sender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RabbitDemoApplication.class)
public class RabbitDemoApplicationTests {

    @Autowired(required = false)
    private Sender sender;

    @Test
    public void hello() {
        sender.send(null);
    }

}
