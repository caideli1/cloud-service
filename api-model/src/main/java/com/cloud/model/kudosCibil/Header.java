package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Header {

    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("Version")
    private String version;
    @JsonProperty("ReferenceNumber")
    private String referencenumber;
    @JsonProperty("MemberCode")
    private String membercode;
    @JsonProperty("SubjectReturnCode")
    private String subjectreturncode;
    @JsonProperty("EnquiryControlNumber")
    private String enquirycontrolnumber;
    @JsonProperty("DateProcessed")
    private String dateprocessed;
    @JsonProperty("TimeProcessed")
    private String timeprocessed;
    public void setSegmenttag(String segmenttag) {
         this.segmenttag = segmenttag;
     }
     public String getSegmenttag() {
         return segmenttag;
     }

    public void setVersion(String version) {
         this.version = version;
     }
     public String getVersion() {
         return version;
     }

    public void setReferencenumber(String referencenumber) {
         this.referencenumber = referencenumber;
     }
     public String getReferencenumber() {
         return referencenumber;
     }

    public void setMembercode(String membercode) {
         this.membercode = membercode;
     }
     public String getMembercode() {
         return membercode;
     }

    public void setSubjectreturncode(String subjectreturncode) {
         this.subjectreturncode = subjectreturncode;
     }
     public String getSubjectreturncode() {
         return subjectreturncode;
     }

    public void setEnquirycontrolnumber(String enquirycontrolnumber) {
         this.enquirycontrolnumber = enquirycontrolnumber;
     }
     public String getEnquirycontrolnumber() {
         return enquirycontrolnumber;
     }

    public void setDateprocessed(String dateprocessed) {
         this.dateprocessed = dateprocessed;
     }
     public String getDateprocessed() {
         return dateprocessed;
     }

    public void setTimeprocessed(String timeprocessed) {
         this.timeprocessed = timeprocessed;
     }
     public String getTimeprocessed() {
         return timeprocessed;
     }

}