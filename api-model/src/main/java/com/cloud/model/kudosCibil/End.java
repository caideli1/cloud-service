package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class End {

    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("TotalLength")
    private String totallength;
    public void setSegmenttag(String segmenttag) {
         this.segmenttag = segmenttag;
     }
     public String getSegmenttag() {
         return segmenttag;
     }

    public void setTotallength(String totallength) {
         this.totallength = totallength;
     }
     public String getTotallength() {
         return totallength;
     }

}