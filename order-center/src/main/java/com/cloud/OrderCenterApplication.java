package com.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 用户中心
 * 
 * @author nl
 *
 */
@EnableScheduling
@EnableFeignClients(basePackages = {"com.cloud.service.feign"})
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.cloud"})
@EnableConfigurationProperties
public class OrderCenterApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(OrderCenterApplication.class, args);
	}

}
