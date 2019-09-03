package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Identifier {

    @JsonProperty("IdType")
    private String idtype;
    @JsonProperty("IdNumber")
    private String idnumber;
}