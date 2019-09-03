package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Cibilbureauresponse {

    @JsonProperty("BureauResponseRaw")
    private String bureauresponseraw;
    @JsonProperty("BureauResponseXml")
    private Bureauresponsexml bureauresponsexml;
    @JsonProperty("SecondaryReportXml")
    private Secondaryreportxml secondaryreportxml;
    @JsonProperty("IsSucess")
    private String issucess;
    public void setBureauresponseraw(String bureauresponseraw) {
         this.bureauresponseraw = bureauresponseraw;
     }
     public String getBureauresponseraw() {
         return bureauresponseraw;
     }

    public void setBureauresponsexml(Bureauresponsexml bureauresponsexml) {
         this.bureauresponsexml = bureauresponsexml;
     }
     public Bureauresponsexml getBureauresponsexml() {
         return bureauresponsexml;
     }

    public void setSecondaryreportxml(Secondaryreportxml secondaryreportxml) {
         this.secondaryreportxml = secondaryreportxml;
     }
     public Secondaryreportxml getSecondaryreportxml() {
         return secondaryreportxml;
     }

    public void setIssucess(String issucess) {
         this.issucess = issucess;
     }
     public String getIssucess() {
         return issucess;
     }

}