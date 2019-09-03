package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class Scoresegment {

    @JsonProperty("Length")
    private String length;
    @JsonProperty("ScoreName")
    private String scorename;
    @JsonProperty("ScoreCardName")
    private String scorecardname;
    @JsonProperty("ScoreCardVersion")
    private String scorecardversion;
    @JsonProperty("ScoreDate")
    private String scoredate;
    @JsonProperty("Score")
    private String score;
}