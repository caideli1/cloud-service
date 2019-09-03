package com.cloud.user.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Names {

    @JsonProperty("Name")
    private Name name;
    public void setName(Name name) {
         this.name = name;
     }
     public Name getName() {
         return name;
     }

}