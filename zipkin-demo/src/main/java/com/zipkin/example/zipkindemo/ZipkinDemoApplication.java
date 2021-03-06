package com.zipkin.example.zipkindemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin2.server.internal.EnableZipkinServer;

@EnableZipkinServer
//@EnableZipkinStreamServer
@EnableDiscoveryClient
@SpringBootApplication
@EnableConfigurationProperties
public class ZipkinDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinDemoApplication.class, args);
    }

}
