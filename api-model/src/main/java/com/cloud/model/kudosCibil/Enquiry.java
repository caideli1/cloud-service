package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    public void setLength(String length) {
         this.length = length;
     }
     public String getLength() {
         return length;
     }

    public void setSegmenttag(String segmenttag) {
         this.segmenttag = segmenttag;
     }
     public String getSegmenttag() {
         return segmenttag;
     }

    public void setDateofenquiryfields(String dateofenquiryfields) {
         this.dateofenquiryfields = dateofenquiryfields;
     }
     public String getDateofenquiryfields() {
         return dateofenquiryfields;
     }

    public void setEnquiringmembershortnamefieldlength(String enquiringmembershortnamefieldlength) {
         this.enquiringmembershortnamefieldlength = enquiringmembershortnamefieldlength;
     }
     public String getEnquiringmembershortnamefieldlength() {
         return enquiringmembershortnamefieldlength;
     }

    public void setEnquiringmembershortname(String enquiringmembershortname) {
         this.enquiringmembershortname = enquiringmembershortname;
     }
     public String getEnquiringmembershortname() {
         return enquiringmembershortname;
     }

    public void setEnquirypurpose(String enquirypurpose) {
         this.enquirypurpose = enquirypurpose;
     }
     public String getEnquirypurpose() {
         return enquirypurpose;
     }

    public void setEnquiryamountfieldlength(String enquiryamountfieldlength) {
         this.enquiryamountfieldlength = enquiryamountfieldlength;
     }
     public String getEnquiryamountfieldlength() {
         return enquiryamountfieldlength;
     }

    public void setEnquiryamount(String enquiryamount) {
         this.enquiryamount = enquiryamount;
     }
     public String getEnquiryamount() {
         return enquiryamount;
     }

}