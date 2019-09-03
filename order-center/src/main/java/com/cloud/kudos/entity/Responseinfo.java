package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Responseinfo {

    @JsonProperty("ApplicationId")
    private String applicationid;
    @JsonProperty("SolutionSetInstanceId")
    private String solutionsetinstanceid;
    @JsonProperty("CurrentQueue")
    private String currentqueue;
}