package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Enquiry {

    @JsonProperty("Length")
    private String length;
    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("DateOfEnquiryFields")
    private String dateofenquiryfields;
    @JsonProperty("EnquiringMemberShortNameFieldLength")
    private String enquiringmembershortnamefieldlength;
    @JsonProperty("EnquiringMemberShortName")
    private String enquiringmembershortname;
    @JsonProperty("EnquiryPurpose")
    private String enquirypurpose;
    @JsonProperty("EnquiryAmountFieldLength")
    private String enquiryamountfieldlength;
    @JsonProperty("EnquiryAmount")
    private String enquiryamount;

}