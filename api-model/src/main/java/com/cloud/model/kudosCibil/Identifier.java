package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Identifier {

    @JsonProperty("IdType")
    private String idtype;
    @JsonProperty("IdNumber")
    private String idnumber;
    public void setIdtype(String idtype) {
         this.idtype = idtype;
     }
     public String getIdtype() {
         return idtype;
     }

    public void setIdnumber(String idnumber) {
         this.idnumber = idnumber;
     }
     public String getIdnumber() {
         return idnumber;
     }

}