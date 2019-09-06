package com.zipkin.example.zipkindemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import zipkin2.server.internal.EnableZipkinServer;

@EnableZipkinServer
@SpringBootApplication
@EnableConfigurationProperties
public class ZipkinDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinDemoApplication.class, args);
    }

}
