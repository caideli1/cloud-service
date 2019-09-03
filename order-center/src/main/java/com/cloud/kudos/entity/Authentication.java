package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Authentication {

    @JsonProperty("Status")
    private String status;
    @JsonProperty("Token")
    private String token;
}