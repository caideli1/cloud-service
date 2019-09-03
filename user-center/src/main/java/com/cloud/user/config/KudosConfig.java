package com.cloud.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kudos")
public class KudosConfig {

    private String sandBox;

    private String production;

    private String NA;

    private String ossUrl;
}
