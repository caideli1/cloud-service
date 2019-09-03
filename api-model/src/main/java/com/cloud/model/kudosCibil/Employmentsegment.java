package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Employmentsegment {

    @JsonProperty("Length")
    private String length;
    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("AccountType")
    private String accounttype;
    @JsonProperty("DateReportedCertified")
    private String datereportedcertified;
    @JsonProperty("OccupationCode")
    private String occupationcode;
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

    public void setAccounttype(String accounttype) {
         this.accounttype = accounttype;
     }
     public String getAccounttype() {
         return accounttype;
     }

    public void setDatereportedcertified(String datereportedcertified) {
         this.datereportedcertified = datereportedcertified;
     }
     public String getDatereportedcertified() {
         return datereportedcertified;
     }

    public void setOccupationcode(String occupationcode) {
         this.occupationcode = occupationcode;
     }
     public String getOccupationcode() {
         return occupationcode;
     }

}