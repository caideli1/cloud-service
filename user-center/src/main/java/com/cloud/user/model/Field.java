package com.cloud.user.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Field {
 	
//    @JsonProperty("Applicant")
//    private Applicant applicant;

    @JsonProperty("Applicants")
    private Applicants applicants;
    
    @JsonProperty("Applicationdata")
    private Applicationdata applicationdata;
    @JsonProperty("key")
    private String key;
    @JsonProperty("Field")
    private String field;
   

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Applicationdata getApplicationdata() {
		return applicationdata;
	}

	public void setApplicationdata(Applicationdata applicationdata) {
		this.applicationdata = applicationdata;
	}
    
	public Applicants getApplicants() {
		return applicants;
	}

	public void setApplicants(Applicants applicants) {
		this.applicants = applicants;
	}

	//    public void setApplicant(Applicant applicant) {
//         this.applicant = applicant;
//     }
//     public Applicant getApplicant() {
//         return applicant;
//     }
    public void setKey(String key) {
         this.key = key;
     }
//     public Applicants getApplicants() {
//		return applicants;
//	}
//	public void setApplicants(Applicants applicants) {
//		this.applicants = applicants;
//	}
	public String getKey() {
         return key;
     }

}