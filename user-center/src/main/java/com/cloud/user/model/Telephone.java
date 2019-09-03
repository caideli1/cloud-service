package com.cloud.user.model;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
@Table(name="kudos_telephone")
public class Telephone extends CibilOrderEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4480868184547340152L;
	@JsonProperty("TelephoneNumber")
    private String telephonenumber;
    @JsonProperty("TelephoneExtension")
    private String telephoneextension;
    @JsonProperty("TelephoneType")
    private String telephonetype;
    public void setTelephonenumber(String telephonenumber) {
         this.telephonenumber = telephonenumber;
     }
     public String getTelephonenumber() {
         return telephonenumber;
     }

    public void setTelephoneextension(String telephoneextension) {
         this.telephoneextension = telephoneextension;
     }
     public String getTelephoneextension() {
         return telephoneextension;
     }

    public void setTelephonetype(String telephonetype) {
         this.telephonetype = telephonetype;
     }
     public String getTelephonetype() {
         return telephonetype;
     }

}