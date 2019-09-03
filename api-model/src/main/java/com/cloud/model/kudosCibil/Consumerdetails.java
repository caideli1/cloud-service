package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Consumerdetails {

    @JsonProperty("CreditReportInquiry")
    private Creditreportinquiry creditreportinquiry;
    public void setCreditreportinquiry(Creditreportinquiry creditreportinquiry) {
         this.creditreportinquiry = creditreportinquiry;
     }
     public Creditreportinquiry getCreditreportinquiry() {
         return creditreportinquiry;
     }

}