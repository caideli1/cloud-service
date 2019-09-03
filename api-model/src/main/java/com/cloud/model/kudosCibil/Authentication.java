package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Authentication {

    @JsonProperty("Status")
    private String status;
    @JsonProperty("Token")
    private String token;
    public void setStatus(String status) {
         this.status = status;
     }
     public String getStatus() {
         return status;
     }

    public void setToken(String token) {
         this.token = token;
     }
     public String getToken() {
         return token;
     }

}