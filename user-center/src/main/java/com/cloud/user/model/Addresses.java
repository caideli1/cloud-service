package com.cloud.user.model;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Addresses {

    @JsonProperty("Address")
    private Address address;
    public void setAddress(Address address) {
         this.address = address;
     }
     public Address getAddress() {
         return address;
     }

}