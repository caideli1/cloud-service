package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Idsegment {

    @JsonProperty("Length")
    private String length;
    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("IDType")
    private String idtype;
    @JsonProperty("IDNumberFieldLength")
    private String idnumberfieldlength;
    @JsonProperty("IDNumber")
    private String idnumber;
}