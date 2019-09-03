package com.cloud.model.kudosCibil;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Creditreport {

    @JsonProperty("Header")
    private Header header;
    @JsonProperty("NameSegment")
    private Namesegment namesegment;
    @JsonProperty("IDSegment")
    private List<Idsegment> idsegment;
    @JsonProperty("TelephoneSegment")
//    private List<Telephonesegment> telephonesegment;
    private Telephonesegment telephonesegment;
    @JsonProperty("EmploymentSegment")
    private Employmentsegment employmentsegment;
    @JsonProperty("ScoreSegment")
    private Scoresegment scoresegment;
    @JsonProperty("Address")
    private List<Address> address;
    @JsonProperty("Account")
//    private List<Account> account;
    private Account account;
    @JsonProperty("Enquiry")
    private List<Enquiry> enquiry;
    @JsonProperty("End")
    private End end;
    @JsonProperty("EmailContactSegment")
    private EmailContactSegment emailContactSegment;
    
    public EmailContactSegment getEmailContactSegment() {
		return emailContactSegment;
	}
	public void setEmailContactSegment(EmailContactSegment emailContactSegment) {
		this.emailContactSegment = emailContactSegment;
	}
	public void setHeader(Header header) {
         this.header = header;
     }
     public Header getHeader() {
         return header;
     }

    public void setNamesegment(Namesegment namesegment) {
         this.namesegment = namesegment;
     }
     public Namesegment getNamesegment() {
         return namesegment;
     }

    public void setIdsegment(List<Idsegment> idsegment) {
         this.idsegment = idsegment;
     }
     public List<Idsegment> getIdsegment() {
         return idsegment;
     }

    public void setTelephonesegment(Telephonesegment telephonesegment) {
         this.telephonesegment = telephonesegment;
     }
     public Telephonesegment getTelephonesegment() {
         return telephonesegment;
     }

    public void setEmploymentsegment(Employmentsegment employmentsegment) {
         this.employmentsegment = employmentsegment;
     }
     public Employmentsegment getEmploymentsegment() {
         return employmentsegment;
     }

    public void setScoresegment(Scoresegment scoresegment) {
         this.scoresegment = scoresegment;
     }
     public Scoresegment getScoresegment() {
         return scoresegment;
     }

    public void setAddress(List<Address> address) {
         this.address = address;
     }
     public List<Address> getAddress() {
         return address;
     }

    public void setAccount(Account account) {
         this.account = account;
     }
     public Account getAccount() {
         return account;
     }

    public void setEnquiry(List<Enquiry> enquiry) {
         this.enquiry = enquiry;
     }
     public List<Enquiry> getEnquiry() {
         return enquiry;
     }

    public void setEnd(End end) {
         this.end = end;
     }
     public End getEnd() {
         return end;
     }

}