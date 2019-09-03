package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Namesegment {

    @JsonProperty("Length")
    private String length;
    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("ConsumerName1FieldLength")
    private String consumername1fieldlength;
    @JsonProperty("ConsumerName1")
    private String consumername1;
    @JsonProperty("DateOfBirthFieldLength")
    private String dateofbirthfieldlength;
    @JsonProperty("DateOfBirth")
    private String dateofbirth;
    @JsonProperty("GenderFieldLength")
    private String genderfieldlength;
    @JsonProperty("Gender")
    private String gender;
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

    public void setConsumername1fieldlength(String consumername1fieldlength) {
         this.consumername1fieldlength = consumername1fieldlength;
     }
     public String getConsumername1fieldlength() {
         return consumername1fieldlength;
     }

    public void setConsumername1(String consumername1) {
         this.consumername1 = consumername1;
     }
     public String getConsumername1() {
         return consumername1;
     }

    public void setDateofbirthfieldlength(String dateofbirthfieldlength) {
         this.dateofbirthfieldlength = dateofbirthfieldlength;
     }
     public String getDateofbirthfieldlength() {
         return dateofbirthfieldlength;
     }

    public void setDateofbirth(String dateofbirth) {
         this.dateofbirth = dateofbirth;
     }
     public String getDateofbirth() {
         return dateofbirth;
     }

    public void setGenderfieldlength(String genderfieldlength) {
         this.genderfieldlength = genderfieldlength;
     }
     public String getGenderfieldlength() {
         return genderfieldlength;
     }

    public void setGender(String gender) {
         this.gender = gender;
     }
     public String getGender() {
         return gender;
     }

}