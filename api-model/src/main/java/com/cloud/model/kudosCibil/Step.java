package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Step {

    @JsonProperty("Name")
    private String name;
    @JsonProperty("Duration")
    private String duration;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setDuration(String duration) {
         this.duration = duration;
     }
     public String getDuration() {
         return duration;
     }

}