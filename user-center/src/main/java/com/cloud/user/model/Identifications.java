package com.cloud.user.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Identifications {

    @JsonProperty("Identification")
    private Identification identification;
    public void setIdentification(Identification identification) {
         this.identification = identification;
     }
     public Identification getIdentification() {
         return identification;
     }

}