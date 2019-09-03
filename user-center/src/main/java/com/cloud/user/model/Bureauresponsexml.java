package com.cloud.user.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Bureauresponsexml {

    @JsonProperty("CreditReport")
    private Creditreport creditreport;
    public void setCreditreport(Creditreport creditreport) {
         this.creditreport = creditreport;
     }
     public Creditreport getCreditreport() {
         return creditreport;
     }

}