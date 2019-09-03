package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Request {

    @JsonProperty("Request")
    private Request request;
}