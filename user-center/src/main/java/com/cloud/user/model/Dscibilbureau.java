package com.cloud.user.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Dscibilbureau {

    @JsonProperty("DsCibilBureauData")
    private Dscibilbureaudata dscibilbureaudata;
    @JsonProperty("DsCibilBureauStatus")
    private Dscibilbureaustatus dscibilbureaustatus;
    @JsonProperty("Response")
    private Response response;
    public void setDscibilbureaudata(Dscibilbureaudata dscibilbureaudata) {
         this.dscibilbureaudata = dscibilbureaudata;
     }
     public Dscibilbureaudata getDscibilbureaudata() {
         return dscibilbureaudata;
     }

    public void setDscibilbureaustatus(Dscibilbureaustatus dscibilbureaustatus) {
         this.dscibilbureaustatus = dscibilbureaustatus;
     }
     public Dscibilbureaustatus getDscibilbureaustatus() {
         return dscibilbureaustatus;
     }

    public void setResponse(Response response) {
         this.response = response;
     }
     public Response getResponse() {
         return response;
     }

}