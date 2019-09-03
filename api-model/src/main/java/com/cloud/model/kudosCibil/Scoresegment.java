package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    public void setLength(String length) {
         this.length = length;
     }
     public String getLength() {
         return length;
     }

    public void setScorename(String scorename) {
         this.scorename = scorename;
     }
     public String getScorename() {
         return scorename;
     }

    public void setScorecardname(String scorecardname) {
         this.scorecardname = scorecardname;
     }
     public String getScorecardname() {
         return scorecardname;
     }

    public void setScorecardversion(String scorecardversion) {
         this.scorecardversion = scorecardversion;
     }
     public String getScorecardversion() {
         return scorecardversion;
     }

    public void setScoredate(String scoredate) {
         this.scoredate = scoredate;
     }
     public String getScoredate() {
         return scoredate;
     }

    public void setScore(String score) {
         this.score = score;
     }
     public String getScore() {
         return score;
     }

}