package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Applicant {

    @JsonProperty("Accounts")
    private Accounts accounts;
    @JsonProperty("Addresses")
    private Addresses addresses;
    @JsonProperty("Telephones")
    private Telephones telephones;
    @JsonProperty("Identifiers")
    private Identifiers identifiers;
    @JsonProperty("CompanyName")
    private String companyname;
    @JsonProperty("EmailAddress")
    private String emailaddress;
    @JsonProperty("Gender")
    private String gender;
    @JsonProperty("DateOfBirth")
    private String dateofbirth;
    @JsonProperty("ApplicantLastName")
    private String applicantlastname;
    @JsonProperty("ApplicantMiddleName")
    private String applicantmiddlename;
    @JsonProperty("ApplicantFirstName")
    private String applicantfirstname;
    @JsonProperty("ApplicantType")
    private String applicanttype;
    @JsonProperty("DsCibilBureau")
    private Dscibilbureau dscibilbureau;
}