package com.cloud.user.model;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
@Table(name="kudos_end")
public class End extends CibilOrderEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3083704723403239074L;
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