package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Telephone {

    @JsonProperty("TelephoneNumber")
    private String telephonenumber;
    @JsonProperty("TelephoneExtension")
    private String telephoneextension;
    @JsonProperty("TelephoneType")
    private String telephonetype;
}