package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Secondaryreportxml {

    @JsonProperty("Root")
    private String root;
}