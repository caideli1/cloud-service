package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Creditreportinquiry {

    @JsonProperty("Header")
    private Header header;
    @JsonProperty("Names")
    private Names names;
    @JsonProperty("Identifications")
    private Identifications identifications;
    @JsonProperty("Telephones")
    private Telephones telephones;
    @JsonProperty("Addresses")
    private Addresses addresses;
}