package com.cloud.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * 管理后台
 * 
 * @author nl
 *
 */
@EnableFeignClients(basePackages = {"com.cloud.service.feign"})
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.cloud"})
@EnableConfigurationProperties
public class ManageBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageBackendApplication.class, args);
	}

}