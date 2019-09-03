package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Telephonesegment {

    @JsonProperty("Length")
    private String length;
    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("TelephoneNumberFieldLength")
    private String telephonenumberfieldlength;
    @JsonProperty("TelephoneNumber")
    private String telephonenumber;
    @JsonProperty("TelephoneType")
    private String telephonetype;
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

    public void setTelephonenumberfieldlength(String telephonenumberfieldlength) {
         this.telephonenumberfieldlength = telephonenumberfieldlength;
     }
     public String getTelephonenumberfieldlength() {
         return telephonenumberfieldlength;
     }

    public void setTelephonenumber(String telephonenumber) {
         this.telephonenumber = telephonenumber;
     }
     public String getTelephonenumber() {
         return telephonenumber;
     }

    public void setTelephonetype(String telephonetype) {
         this.telephonetype = telephonetype;
     }
     public String getTelephonetype() {
         return telephonetype;
     }

}