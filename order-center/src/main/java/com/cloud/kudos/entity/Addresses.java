package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Addresses {

    @JsonProperty("Address")
    private Address address;
}