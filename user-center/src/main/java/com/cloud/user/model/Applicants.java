package com.cloud.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Applicants {
	@JsonProperty("Applicant")
    private Applicant applicant;

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	
}
