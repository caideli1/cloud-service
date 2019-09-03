package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Identification {

    @JsonProperty("PanNo")
    private String panno;
    @JsonProperty("PassportNumber")
    private String passportnumber;
    @JsonProperty("DLNo")
    private String dlno;
    @JsonProperty("VoterId")
    private String voterid;
    @JsonProperty("UId")
    private String uid;
    @JsonProperty("RationCardNo")
    private String rationcardno;
    @JsonProperty("AdditionalID1")
    private String additionalid1;
    @JsonProperty("AdditionalID2")
    private String additionalid2;
}