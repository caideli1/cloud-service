package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Dscibilbureau {

    @JsonProperty("DsCibilBureauData")
    private Dscibilbureaudata dscibilbureaudata;
    @JsonProperty("DsCibilBureauStatus")
    private Dscibilbureaustatus dscibilbureaustatus;
    @JsonProperty("Response")
    private Response response;
}