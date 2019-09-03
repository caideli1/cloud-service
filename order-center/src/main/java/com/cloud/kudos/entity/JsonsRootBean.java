package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class JsonsRootBean {

    @JsonProperty("Status")
    private String status;
    @JsonProperty("Authentication")
    private Authentication authentication;
    @JsonProperty("ResponseInfo")
    private Responseinfo responseinfo;
    @JsonProperty("ContextData")
    private Contextdata contextdata;
}