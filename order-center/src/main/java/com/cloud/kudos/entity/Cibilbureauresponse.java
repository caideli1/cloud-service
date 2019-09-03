package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Cibilbureauresponse {

    @JsonProperty("BureauResponseRaw")
    private String bureauresponseraw;
    @JsonProperty("BureauResponseXml")
    private Bureauresponsexml bureauresponsexml;
    @JsonProperty("SecondaryReportXml")
    private Secondaryreportxml secondaryreportxml;
    @JsonProperty("IsSucess")
    private String issucess;
}