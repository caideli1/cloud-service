package com.cloud.user.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Dscibilbureaustatus {

    @JsonProperty("Trail")
    private String trail;
    public void setTrail(String trail) {
         this.trail = trail;
     }
     public String getTrail() {
         return trail;
     }

}