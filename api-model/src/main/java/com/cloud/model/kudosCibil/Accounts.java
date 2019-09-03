package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Accounts {

    @JsonProperty("Account")
    private Account account;
    public void setAccount(Account account) {
         this.account = account;
     }
     public Account getAccount() {
         return account;
     }

}