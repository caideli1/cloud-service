package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Secondaryreportxml {

    @JsonProperty("Root")
    private String root;
    public void setRoot(String root) {
         this.root = root;
     }
     public String getRoot() {
         return root;
     }

}