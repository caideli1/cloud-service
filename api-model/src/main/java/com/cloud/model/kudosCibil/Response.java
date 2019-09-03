package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response extends CibilOrderEntity {

    @JsonProperty("CibilBureauResponse")
    private Cibilbureauresponse cibilbureauresponse;
    public void setCibilbureauresponse(Cibilbureauresponse cibilbureauresponse) {
         this.cibilbureauresponse = cibilbureauresponse;
     }
     public Cibilbureauresponse getCibilbureauresponse() {
         return cibilbureauresponse;
     }

}