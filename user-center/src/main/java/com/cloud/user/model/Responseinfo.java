package com.cloud.user.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Responseinfo {

    @JsonProperty("ApplicationId")
    private String applicationid;
    @JsonProperty("SolutionSetInstanceId")
    private String solutionsetinstanceid;
    @JsonProperty("CurrentQueue")
    private String currentqueue;
    public void setApplicationid(String applicationid) {
         this.applicationid = applicationid;
     }
     public String getApplicationid() {
         return applicationid;
     }

    public void setSolutionsetinstanceid(String solutionsetinstanceid) {
         this.solutionsetinstanceid = solutionsetinstanceid;
     }
     public String getSolutionsetinstanceid() {
         return solutionsetinstanceid;
     }

    public void setCurrentqueue(String currentqueue) {
         this.currentqueue = currentqueue;
     }
     public String getCurrentqueue() {
         return currentqueue;
     }

}