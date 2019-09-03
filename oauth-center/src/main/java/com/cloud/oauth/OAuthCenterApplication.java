package com.cloud.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 认证中心
 * 
 * @author nl
 *
 */
@EnableFeignClients(basePackages = {"com.cloud.service.feign"})
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.cloud"})
@EnableConfigurationProperties
public class OAuthCenterApplication {
	public static void main(String[] args) {
		SpringApplication.run(OAuthCenterApplication.class, args);
	}
}