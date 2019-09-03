package com.cloud.user.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Milestone {

    @JsonProperty("Step")
    private List<String> step;
    public void setStep(List<String> step) {
         this.step = step;
     }
     public List<String> getStep() {
         return step;
     }

}