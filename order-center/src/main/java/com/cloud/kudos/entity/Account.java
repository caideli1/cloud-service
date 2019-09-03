package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Account {

    @JsonProperty("Length")
    private String length;
    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("Account_Summary_Segment_Fields")
    private AccountSummarySegmentFields accountSummarySegmentFields;
    @JsonProperty("Account_NonSummary_Segment_Fields")
    private AccountNonsummarySegmentFields accountNonsummarySegmentFields;
}