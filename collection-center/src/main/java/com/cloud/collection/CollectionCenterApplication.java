package com.cloud.collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 催收中心
 *
 * @author danquan.miao
 * @date 2019/8/8 0008
 * @since 1.0.0
 */
@EnableFeignClients(basePackages = {"com.cloud.service.feign"})
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.cloud"})
@EnableConfigurationProperties
public class CollectionCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(CollectionCenterApplication.class, args);
    }

}
