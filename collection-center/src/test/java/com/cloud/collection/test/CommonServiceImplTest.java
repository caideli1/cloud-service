package com.cloud.collection.test;

import com.cloud.collection.CollectionCenterApplication;
import com.cloud.service.feign.user.UserClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/7/30 14:30
 * 描述：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CollectionCenterApplication.class)
//@Import({FeignAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class})
//@EnableFeignClients(clients = UserClient.class)
public class CommonServiceImplTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() throws Exception {
        mvc =  MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    @Test
    public void test() throws Exception {
        //对象入参和数组入参
//        param("name","第二组").
//                param("userIdList","1","72","20").
        String a = mvc.perform(MockMvcRequestBuilders.post("/collection-group/getGroupAndUsersDetail").param("groupId","4").
                accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
        System.out.println(a);
    }

    @Test
    public void test1() throws Exception {

    }
    }