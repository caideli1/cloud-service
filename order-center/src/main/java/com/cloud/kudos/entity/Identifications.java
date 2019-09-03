package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Identifications {

    @JsonProperty("Identification")
    private Identification identification;
}