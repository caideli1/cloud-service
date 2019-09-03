package com.cloud.mail;

import com.cloud.backend.ManageBackendApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.internet.MimeMessage;
import java.io.File;


/**
 * 邮件测试
 *
 * @author danquan.miao
 * @create 2019/4/24 0024
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ManageBackendApplication.class)
public class mailTest {
    @Autowired(required = false)
    private JavaMailSender javaMailSender;

    @Test
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("system@moneed.net");
        message.setTo("danquanmiao@163.com");
        message.setSubject("主题：简单邮件");
        message.setText("账户名称：razorpay\n,设定全额：\" + totalAmount + \",账户预警：截止到当前，账户余额已低于10%，请各位做好资金安排工作。");
        javaMailSender.send(message);
    }

    @Test
    public void sendAttachmentMail() throws Exception {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMailMessage, true);
        helper.setFrom("danquanmiao@163.com");
        helper.setTo("danquanmiao@163.com");
        helper.setSubject("主题：有附件");
        helper.setText("有附件的邮件");

        FileSystemResource file = new FileSystemResource(new File(mailTest.class.getClassLoader().getResource("weixin.png").getPath()));
        helper.addAttachment("附件1.jpg", file);
        helper.addAttachment("附件2.jpg", file);

        javaMailSender.send(mimeMailMessage);

    }



}
