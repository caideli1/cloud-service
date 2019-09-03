package com.cloud.app.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "razorpay")
public class RazorpayConfig {

    private String keyId;

    private String keySecret;

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKeySecret() {
        return keySecret;
    }

    public void setKeySecret(String keySecret) {
        this.keySecret = keySecret;
    }


    @Bean
    public RazorpayClient getRazorpayClient() throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);
        return razorpayClient;
    }
}
