package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@Data
public class Creditreport {

    @JsonProperty("Header")
    private Header header;
    @JsonProperty("NameSegment")
    private Namesegment namesegment;
    @JsonProperty("IDSegment")
    private List<Idsegment> idsegment;
    @JsonProperty("TelephoneSegment")
    private List<Telephonesegment> telephonesegment;
    @JsonProperty("EmploymentSegment")
    private Employmentsegment employmentsegment;
    @JsonProperty("ScoreSegment")
    private Scoresegment scoresegment;
    @JsonProperty("Address")
    private List<Address> address;
    @JsonProperty("Account")
    private List<Account> account;
    @JsonProperty("Enquiry")
    private List<Enquiry> enquiry;
    @JsonProperty("End")
    private End end;
}