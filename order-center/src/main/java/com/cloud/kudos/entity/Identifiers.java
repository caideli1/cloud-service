package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@Data
public class Identifiers {

    @JsonProperty("Identifier")
    private List<Identifier> identifier;
}