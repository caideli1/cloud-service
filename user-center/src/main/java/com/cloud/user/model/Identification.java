package com.cloud.user.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Identification {

    @JsonProperty("PanNo")
    private String panno;
    @JsonProperty("PassportNumber")
    private String passportnumber;
    @JsonProperty("DLNo")
    private String dlno;
    @JsonProperty("VoterId")
    private String voterid;
    @JsonProperty("UId")
    private String uid;
    @JsonProperty("RationCardNo")
    private String rationcardno;
    @JsonProperty("AdditionalID1")
    private String additionalid1;
    @JsonProperty("AdditionalID2")
    private String additionalid2;
    public void setPanno(String panno) {
         this.panno = panno;
     }
     public String getPanno() {
         return panno;
     }

    public void setPassportnumber(String passportnumber) {
         this.passportnumber = passportnumber;
     }
     public String getPassportnumber() {
         return passportnumber;
     }

    public void setDlno(String dlno) {
         this.dlno = dlno;
     }
     public String getDlno() {
         return dlno;
     }

    public void setVoterid(String voterid) {
         this.voterid = voterid;
     }
     public String getVoterid() {
         return voterid;
     }

    public void setUid(String uid) {
         this.uid = uid;
     }
     public String getUid() {
         return uid;
     }

    public void setRationcardno(String rationcardno) {
         this.rationcardno = rationcardno;
     }
     public String getRationcardno() {
         return rationcardno;
     }

    public void setAdditionalid1(String additionalid1) {
         this.additionalid1 = additionalid1;
     }
     public String getAdditionalid1() {
         return additionalid1;
     }

    public void setAdditionalid2(String additionalid2) {
         this.additionalid2 = additionalid2;
     }
     public String getAdditionalid2() {
         return additionalid2;
     }

}