package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Namesegment {

    @JsonProperty("Length")
    private String length;
    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("ConsumerName1FieldLength")
    private String consumername1fieldlength;
    @JsonProperty("ConsumerName1")
    private String consumername1;
    @JsonProperty("DateOfBirthFieldLength")
    private String dateofbirthfieldlength;
    @JsonProperty("DateOfBirth")
    private String dateofbirth;
    @JsonProperty("GenderFieldLength")
    private String genderfieldlength;
    @JsonProperty("Gender")
    private String gender;
}