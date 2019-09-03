package com.cloud.user.model;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name="kudos_step")
public class Step extends CibilOrderEntity{
	private static final long serialVersionUID = -7618291714255244571L;
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