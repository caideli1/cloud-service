package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    public void setAccounts(Accounts accounts) {
         this.accounts = accounts;
     }
     public Accounts getAccounts() {
         return accounts;
     }

    public void setAddresses(Addresses addresses) {
         this.addresses = addresses;
     }
     public Addresses getAddresses() {
         return addresses;
     }

    public void setTelephones(Telephones telephones) {
         this.telephones = telephones;
     }
     public Telephones getTelephones() {
         return telephones;
     }

    public void setIdentifiers(Identifiers identifiers) {
         this.identifiers = identifiers;
     }
     public Identifiers getIdentifiers() {
         return identifiers;
     }

    public void setCompanyname(String companyname) {
         this.companyname = companyname;
     }
     public String getCompanyname() {
         return companyname;
     }

    public void setEmailaddress(String emailaddress) {
         this.emailaddress = emailaddress;
     }
     public String getEmailaddress() {
         return emailaddress;
     }

    public void setGender(String gender) {
         this.gender = gender;
     }
     public String getGender() {
         return gender;
     }

    public void setDateofbirth(String dateofbirth) {
         this.dateofbirth = dateofbirth;
     }
     public String getDateofbirth() {
         return dateofbirth;
     }

    public void setApplicantlastname(String applicantlastname) {
         this.applicantlastname = applicantlastname;
     }
     public String getApplicantlastname() {
         return applicantlastname;
     }

    public void setApplicantmiddlename(String applicantmiddlename) {
         this.applicantmiddlename = applicantmiddlename;
     }
     public String getApplicantmiddlename() {
         return applicantmiddlename;
     }

    public void setApplicantfirstname(String applicantfirstname) {
         this.applicantfirstname = applicantfirstname;
     }
     public String getApplicantfirstname() {
         return applicantfirstname;
     }

    public void setApplicanttype(String applicanttype) {
         this.applicanttype = applicanttype;
     }
     public String getApplicanttype() {
         return applicanttype;
     }

    public void setDscibilbureau(Dscibilbureau dscibilbureau) {
         this.dscibilbureau = dscibilbureau;
     }
     public Dscibilbureau getDscibilbureau() {
         return dscibilbureau;
     }

}