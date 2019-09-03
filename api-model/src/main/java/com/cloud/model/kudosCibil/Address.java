package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

    @JsonProperty("AddressSegmentTag")
    private String addresssegmenttag;
    @JsonProperty("Length")
    private String length;
    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("AddressLine1FieldLength")
    private String addressline1fieldlength;
    @JsonProperty("AddressLine1")
    private String addressline1;
    @JsonProperty("AddressLine2FieldLength")
    private String addressline2fieldlength;
    @JsonProperty("AddressLine2")
    private String addressline2;
    @JsonProperty("AddressLine3FieldLength")
    private String addressline3fieldlength;
    @JsonProperty("AddressLine3")
    private String addressline3;
    @JsonProperty("AddressLine5FieldLength")
    private String addressline5fieldlength;
    @JsonProperty("AddressLine5")
    private String addressline5;
    @JsonProperty("StateCode")
    private String statecode;
    @JsonProperty("PinCodeFieldLength")
    private String pincodefieldlength;
    @JsonProperty("PinCode")
    private String pincode;
    @JsonProperty("AddressCategory")
    private String addresscategory;
    @JsonProperty("ResidenceCode")
    private String residencecode;
    @JsonProperty("DateReported")
    private String datereported;
    @JsonProperty("EnrichedThroughEnquiry")
    private String enrichedthroughenquiry;
    public void setAddresssegmenttag(String addresssegmenttag) {
         this.addresssegmenttag = addresssegmenttag;
     }
     public String getAddresssegmenttag() {
         return addresssegmenttag;
     }

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

    public void setAddressline1fieldlength(String addressline1fieldlength) {
         this.addressline1fieldlength = addressline1fieldlength;
     }
     public String getAddressline1fieldlength() {
         return addressline1fieldlength;
     }

    public void setAddressline1(String addressline1) {
         this.addressline1 = addressline1;
     }
     public String getAddressline1() {
         return addressline1;
     }

    public void setAddressline2fieldlength(String addressline2fieldlength) {
         this.addressline2fieldlength = addressline2fieldlength;
     }
     public String getAddressline2fieldlength() {
         return addressline2fieldlength;
     }

    public void setAddressline2(String addressline2) {
         this.addressline2 = addressline2;
     }
     public String getAddressline2() {
         return addressline2;
     }

    public void setAddressline3fieldlength(String addressline3fieldlength) {
         this.addressline3fieldlength = addressline3fieldlength;
     }
     public String getAddressline3fieldlength() {
         return addressline3fieldlength;
     }

    public void setAddressline3(String addressline3) {
         this.addressline3 = addressline3;
     }
     public String getAddressline3() {
         return addressline3;
     }

    public void setAddressline5fieldlength(String addressline5fieldlength) {
         this.addressline5fieldlength = addressline5fieldlength;
     }
     public String getAddressline5fieldlength() {
         return addressline5fieldlength;
     }

    public void setAddressline5(String addressline5) {
         this.addressline5 = addressline5;
     }
     public String getAddressline5() {
         return addressline5;
     }

    public void setStatecode(String statecode) {
         this.statecode = statecode;
     }
     public String getStatecode() {
         return statecode;
     }

    public void setPincodefieldlength(String pincodefieldlength) {
         this.pincodefieldlength = pincodefieldlength;
     }
     public String getPincodefieldlength() {
         return pincodefieldlength;
     }

    public void setPincode(String pincode) {
         this.pincode = pincode;
     }
     public String getPincode() {
         return pincode;
     }

    public void setAddresscategory(String addresscategory) {
         this.addresscategory = addresscategory;
     }
     public String getAddresscategory() {
         return addresscategory;
     }

    public void setResidencecode(String residencecode) {
         this.residencecode = residencecode;
     }
     public String getResidencecode() {
         return residencecode;
     }

    public void setDatereported(String datereported) {
         this.datereported = datereported;
     }
     public String getDatereported() {
         return datereported;
     }

    public void setEnrichedthroughenquiry(String enrichedthroughenquiry) {
         this.enrichedthroughenquiry = enrichedthroughenquiry;
     }
     public String getEnrichedthroughenquiry() {
         return enrichedthroughenquiry;
     }

}