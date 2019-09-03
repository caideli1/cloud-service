package com.cloud.user.model;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
@Table(name="kudos_cibilbureau_response")
public class Cibilbureauresponse extends CibilOrderEntity{
	private static final long serialVersionUID = -278821403324682314L;
	@JsonProperty("BureauResponseRaw")
    private String bureauresponseraw;
    @JsonProperty("BureauResponseXml")
    private Bureauresponsexml bureauresponsexml;
//    @JsonProperty("SecondaryReportXml")
//    private Secondaryreportxml secondaryreportxml;
    @JsonProperty("IsSucess")
    private String issucess;
    
    @JsonProperty("SecondaryReportXml")
    private String secondaryreportxml;
    
    @JsonProperty("ErrorCode")
    private String errorcode;
    @JsonProperty("ErrorMessage")
    private String errormessage;
    
    
    
    public String getSecondaryreportxml() {
		return secondaryreportxml;
	}
	public void setSecondaryreportxml(String secondaryreportxml) {
		this.secondaryreportxml = secondaryreportxml;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public String getErrormessage() {
		return errormessage;
	}
	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}
	public void setBureauresponseraw(String bureauresponseraw) {
         this.bureauresponseraw = bureauresponseraw;
     }
     public String getBureauresponseraw() {
         return bureauresponseraw;
     }

    public void setBureauresponsexml(Bureauresponsexml bureauresponsexml) {
         this.bureauresponsexml = bureauresponsexml;
     }
     public Bureauresponsexml getBureauresponsexml() {
         return bureauresponsexml;
     }

//    public void setSecondaryreportxml(Secondaryreportxml secondaryreportxml) {
//         this.secondaryreportxml = secondaryreportxml;
//     }
//     public Secondaryreportxml getSecondaryreportxml() {
//         return secondaryreportxml;
//     }

    public void setIssucess(String issucess) {
         this.issucess = issucess;
     }
     public String getIssucess() {
         return issucess;
     }

}