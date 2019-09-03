package com.cloud.order.test.service.impl;

import com.cloud.OrderCenterApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/7/30 14:30
 * 描述：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OrderCenterApplication.class)
@WebAppConfiguration
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
        Map<String, Object> parameter = new HashMap<>();
        /*String[] arry = {"1","2"};
        parameter.put("page",1);
        parameter.put("limit",10);*/
        String a = mvc.perform(MockMvcRequestBuilders.post("/failure/follow").param("loanNumber","591062313470001152").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
        //String a = mvc.perform(MockMvcRequestBuilders.post("/orders-anon/lendingPoolLoan",arry).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
        System.out.println("第一次："+a);
    }
    @Test
    public void test1() throws Exception {
        Map<String, Object> parameter = new HashMap<>();
        /*String[] arry = {"1","2"};
        parameter.put("page",1);
        parameter.put("limit",10);*/
        String b = mvc.perform(MockMvcRequestBuilders.post("/failure/follow").param("loanNumber","591062313470001152").accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
        //String a = mvc.perform(MockMvcRequestBuilders.post("/orders-anon/lendingPoolLoan",arry).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
        System.out.println("第二次："+b);
    }
    }