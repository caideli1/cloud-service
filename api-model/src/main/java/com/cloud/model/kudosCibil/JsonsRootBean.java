package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonsRootBean {

    @JsonProperty("Status")
    private String status;
    @JsonProperty("Authentication")
    private Authentication authentication;
    @JsonProperty("ResponseInfo")
    private Responseinfo responseinfo;
    @JsonProperty("ContextData")
    private ContextData contextdata;
    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setAuthentication(Authentication authentication) {
         this.authentication = authentication;
     }
     public Authentication getAuthentication() {
         return authentication;
     }

    public void setResponseinfo(Responseinfo responseinfo) {
         this.responseinfo = responseinfo;
     }
     public Responseinfo getResponseinfo() {
         return responseinfo;
     }

    public void setContextdata(ContextData contextdata) {
         this.contextdata = contextdata;
     }
     public ContextData getContextdata() {
         return contextdata;
     }

}