package com.cloud.user.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Creditreportinquiry {

    @JsonProperty("Header")
    private Header header;
    @JsonProperty("Names")
    private Names names;
    @JsonProperty("Identifications")
    private Identifications identifications;
    @JsonProperty("Telephones")
    private Telephones telephones;
    @JsonProperty("Addresses")
    private Addresses addresses;
    public void setHeader(Header header) {
         this.header = header;
     }
     public Header getHeader() {
         return header;
     }

    public void setNames(Names names) {
         this.names = names;
     }
     public Names getNames() {
         return names;
     }

    public void setIdentifications(Identifications identifications) {
         this.identifications = identifications;
     }
     public Identifications getIdentifications() {
         return identifications;
     }

    public void setTelephones(Telephones telephones) {
         this.telephones = telephones;
     }
     public Telephones getTelephones() {
         return telephones;
     }

    public void setAddresses(Addresses addresses) {
         this.addresses = addresses;
     }
     public Addresses getAddresses() {
         return addresses;
     }

}