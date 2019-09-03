package com.cloud.user.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountSummarySegmentFields {

    @JsonProperty("ReportingMemberShortNameFieldLength")
    private String reportingmembershortnamefieldlength;
    public void setReportingmembershortnamefieldlength(String reportingmembershortnamefieldlength) {
         this.reportingmembershortnamefieldlength = reportingmembershortnamefieldlength;
     }
     public String getReportingmembershortnamefieldlength() {
         return reportingmembershortnamefieldlength;
     }

}