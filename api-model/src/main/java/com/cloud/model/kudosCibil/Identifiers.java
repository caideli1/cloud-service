package com.cloud.model.kudosCibil;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Identifiers {

    @JsonProperty("Identifier")
    private List<Identifier> identifier;
    public void setIdentifier(List<Identifier> identifier) {
         this.identifier = identifier;
     }
     public List<Identifier> getIdentifier() {
         return identifier;
     }

}