
package com.cloud.model.kudosCibil;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailContactSegment {
	@JsonProperty("Length")
    private String length;
	@JsonProperty("SegmentTag")
    private String segmentTag;
	@JsonProperty("EmailIDFieldLength")
    private String emailIDFieldLength;
	@JsonProperty("EmailID")
    private String emailID;
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getSegmentTag() {
		return segmentTag;
	}
	public void setSegmentTag(String segmentTag) {
		this.segmentTag = segmentTag;
	}
	public String getEmailIDFieldLength() {
		return emailIDFieldLength;
	}
	public void setEmailIDFieldLength(String emailIDFieldLength) {
		this.emailIDFieldLength = emailIDFieldLength;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
    

}