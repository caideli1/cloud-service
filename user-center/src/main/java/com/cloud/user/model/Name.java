package com.cloud.user.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Name {

    @JsonProperty("ConsumerName1")
    private String consumername1;
    @JsonProperty("ConsumerName2")
    private String consumername2;
    @JsonProperty("ConsumerName3")
    private String consumername3;
    @JsonProperty("ConsumerName4")
    private String consumername4;
    @JsonProperty("ConsumerName5")
    private String consumername5;
    @JsonProperty("DateOfBirth")
    private String dateofbirth;
    @JsonProperty("Gender")
    private String gender;
    public void setConsumername1(String consumername1) {
         this.consumername1 = consumername1;
     }
     public String getConsumername1() {
         return consumername1;
     }

    public void setConsumername2(String consumername2) {
         this.consumername2 = consumername2;
     }
     public String getConsumername2() {
         return consumername2;
     }

    public void setConsumername3(String consumername3) {
         this.consumername3 = consumername3;
     }
     public String getConsumername3() {
         return consumername3;
     }

    public void setConsumername4(String consumername4) {
         this.consumername4 = consumername4;
     }
     public String getConsumername4() {
         return consumername4;
     }

    public void setConsumername5(String consumername5) {
         this.consumername5 = consumername5;
     }
     public String getConsumername5() {
         return consumername5;
     }

    public void setDateofbirth(String dateofbirth) {
         this.dateofbirth = dateofbirth;
     }
     public String getDateofbirth() {
         return dateofbirth;
     }

    public void setGender(String gender) {
         this.gender = gender;
     }
     public String getGender() {
         return gender;
     }

}