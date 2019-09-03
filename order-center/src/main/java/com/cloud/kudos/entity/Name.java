package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Name {

    @JsonProperty("ConsumerName1")
    private String consumername1;
    @JsonProperty("ConsumerName2")
    private String consumername2;
    @JsonProperty("ConsumerName3")
    private String consumername3;
    @JsonProperty("ConsumerName4")
    private String consumername4;
    @JsonProperty("ConsumerName5")
    private String consumername5;
    @JsonProperty("DateOfBirth")
    private String dateofbirth;
    @JsonProperty("Gender")
    private String gender;
}