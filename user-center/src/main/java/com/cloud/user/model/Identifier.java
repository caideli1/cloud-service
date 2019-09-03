package com.cloud.user.model;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Table(name="kudos_identifier")
public class Identifier extends CibilOrderEntity{
	private static final long serialVersionUID = 2534999486687714494L;
	@JsonProperty("IdType")
    private String idtype;
    @JsonProperty("IdNumber")
    private String idnumber;
    public void setIdtype(String idtype) {
         this.idtype = idtype;
     }
     public String getIdtype() {
         return idtype;
     }

    public void setIdnumber(String idnumber) {
         this.idnumber = idnumber;
     }
     public String getIdnumber() {
         return idnumber;
     }

}