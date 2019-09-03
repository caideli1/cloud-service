package com.cloud.oauth.config;

import com.cloud.oauth.common.MyExceptionHandlerExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yoga
 * @Description: TODO
 * @date 2019-07-2913:57
 */
@EnableWebMvc
@Configuration
public class WebMVCConfiguration extends WebMvcConfigurationSupport {
    @Bean
    @Override
    public ExceptionHandlerExceptionResolver handlerExceptionResolver() {
        MyExceptionHandlerExceptionResolver exceptionResolver = new MyExceptionHandlerExceptionResolver();
        exceptionResolver.setOrder(0);
        exceptionResolver.setMessageConverters(messageConverters());
        return exceptionResolver;
    }

    private MappingJackson2HttpMessageConverter jsonHttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    private List<HttpMessageConverter<?>> messageConverters() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(jsonHttpMessageConverter());
        return messageConverters;
    }
}