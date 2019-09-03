package com.cloud.model.kudosCibil;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Dttrail {

    @JsonProperty("Step")
    private List<Step> step;
    public void setStep(List<Step> step) {
         this.step = step;
     }
     public List<Step> getStep() {
         return step;
     }

}