package com.cloud.model.kudosCibil;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Telephones {

    @JsonProperty("Telephone")
    private List<Telephone> telephone;
    public void setTelephone(List<Telephone> telephone) {
         this.telephone = telephone;
     }
     public List<Telephone> getTelephone() {
         return telephone;
     }
//    private Telephone telephone;
//    public void setTelephone(Telephone telephone) {
//         this.telephone = telephone;
//     }
//     public Telephone getTelephone() {
//         return telephone;
//     }

}