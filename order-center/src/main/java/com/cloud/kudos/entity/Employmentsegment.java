package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Employmentsegment {

    @JsonProperty("Length")
    private String length;
    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("AccountType")
    private String accounttype;
    @JsonProperty("DateReportedCertified")
    private String datereportedcertified;
    @JsonProperty("OccupationCode")
    private String occupationcode;
}