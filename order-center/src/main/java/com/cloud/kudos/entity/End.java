package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class End {

    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("TotalLength")
    private String totallength;
}