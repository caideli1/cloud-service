package com.cloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;

/**
 * feign格式注册器
 *
 * @author danquan.miao
 * @date 2019/8/22 0022
 * @since 1.0.0
 */
@Configuration
public class FeignFormatterRegister implements FeignFormatterRegistrar {
    @Autowired
    private DateFormatter dateFormatter;

    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(dateFormatter);
    }
}
