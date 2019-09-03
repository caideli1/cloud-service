package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Telephone {

    @JsonProperty("TelephoneNumber")
    private String telephonenumber;
    @JsonProperty("TelephoneExtension")
    private String telephoneextension;
    @JsonProperty("TelephoneType")
    private String telephonetype;
    public void setTelephonenumber(String telephonenumber) {
         this.telephonenumber = telephonenumber;
     }
     public String getTelephonenumber() {
         return telephonenumber;
     }

    public void setTelephoneextension(String telephoneextension) {
         this.telephoneextension = telephoneextension;
     }
     public String getTelephoneextension() {
         return telephoneextension;
     }

    public void setTelephonetype(String telephonetype) {
         this.telephonetype = telephonetype;
     }
     public String getTelephonetype() {
         return telephonetype;
     }

}