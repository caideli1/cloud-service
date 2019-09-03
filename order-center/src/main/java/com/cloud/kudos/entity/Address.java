package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Address {

    @JsonProperty("AddressSegmentTag")
    private String addresssegmenttag;
    @JsonProperty("Length")
    private String length;
    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("AddressLine1FieldLength")
    private String addressline1fieldlength;
    @JsonProperty("AddressLine1")
    private String addressline1;
    @JsonProperty("AddressLine2FieldLength")
    private String addressline2fieldlength;
    @JsonProperty("AddressLine2")
    private String addressline2;
    @JsonProperty("AddressLine3FieldLength")
    private String addressline3fieldlength;
    @JsonProperty("AddressLine3")
    private String addressline3;
    @JsonProperty("AddressLine5FieldLength")
    private String addressline5fieldlength;
    @JsonProperty("AddressLine5")
    private String addressline5;
    @JsonProperty("StateCode")
    private String statecode;
    @JsonProperty("PinCodeFieldLength")
    private String pincodefieldlength;
    @JsonProperty("PinCode")
    private String pincode;
    @JsonProperty("AddressCategory")
    private String addresscategory;
    @JsonProperty("ResidenceCode")
    private String residencecode;
    @JsonProperty("DateReported")
    private String datereported;
    @JsonProperty("EnrichedThroughEnquiry")
    private String enrichedthroughenquiry;
}