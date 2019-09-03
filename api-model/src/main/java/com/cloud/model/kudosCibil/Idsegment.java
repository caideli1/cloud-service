package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Idsegment {

    @JsonProperty("Length")
    private String length;
    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("IDType")
    private String idtype;
    @JsonProperty("IDNumberFieldLength")
    private String idnumberfieldlength;
    @JsonProperty("IDNumber")
    private String idnumber;
    @JsonProperty("EnrichedThroughEnquiry")
    private String enrichedThroughEnquiry;
    public void setLength(String length) {
         this.length = length;
     }
     public String getLength() {
         return length;
     }

    public void setSegmenttag(String segmenttag) {
         this.segmenttag = segmenttag;
     }
     public String getSegmenttag() {
         return segmenttag;
     }
    public void setIdtype(String idtype) {
         this.idtype = idtype;
     }
     public String getIdtype() {
         return idtype;
     }
    public void setIdnumberfieldlength(String idnumberfieldlength) {
         this.idnumberfieldlength = idnumberfieldlength;
     }
     public String getIdnumberfieldlength() {
         return idnumberfieldlength;
     }
    public void setIdnumber(String idnumber) {
         this.idnumber = idnumber;
     }
     public String getIdnumber() {
         return idnumber;
     }
	public String getEnrichedThroughEnquiry() {
		return enrichedThroughEnquiry;
	}
	public void setEnrichedThroughEnquiry(String enrichedThroughEnquiry) {
		this.enrichedThroughEnquiry = enrichedThroughEnquiry;
	}

}