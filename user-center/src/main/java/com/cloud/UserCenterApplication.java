package com.cloud;

import com.cloud.common.component.utils.key.SnowflakeIdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * 用户中心
 * 
 * @author nl
 *
 */
@EnableFeignClients(basePackages = {"com.cloud.service.feign"})
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.cloud"})
@EnableConfigurationProperties
public class UserCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCenterApplication.class, args);
	}

	@Bean
	public SnowflakeIdWorker snowflakeIdWorker(){
		return new SnowflakeIdWorker(0, 0);
	}

}