package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Header {

    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("Version")
    private String version;
    @JsonProperty("ReferenceNumber")
    private String referencenumber;
    @JsonProperty("MemberCode")
    private String membercode;
    @JsonProperty("SubjectReturnCode")
    private String subjectreturncode;
    @JsonProperty("EnquiryControlNumber")
    private String enquirycontrolnumber;
    @JsonProperty("DateProcessed")
    private String dateprocessed;
    @JsonProperty("TimeProcessed")
    private String timeprocessed;
}