package com.cloud.model.kudosCibil;
import com.fasterxml.jackson.annotation.JsonProperty;
public class Account  extends CibilOrderEntity {
	private static final long serialVersionUID = 24826082532845114L;
	@JsonProperty("Length")
    private String length;
    @JsonProperty("SegmentTag")
    private String segmenttag;
    @JsonProperty("Account_Summary_Segment_Fields")
    private AccountSummarySegmentFields accountSummarySegmentFields;
    @JsonProperty("Account_NonSummary_Segment_Fields")
    private AccountNonsummarySegmentFields accountNonsummarySegmentFields;
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

    public void setAccountSummarySegmentFields(AccountSummarySegmentFields accountSummarySegmentFields) {
         this.accountSummarySegmentFields = accountSummarySegmentFields;
     }
     public AccountSummarySegmentFields getAccountSummarySegmentFields() {
         return accountSummarySegmentFields;
     }

    public void setAccountNonsummarySegmentFields(AccountNonsummarySegmentFields accountNonsummarySegmentFields) {
         this.accountNonsummarySegmentFields = accountNonsummarySegmentFields;
     }
     public AccountNonsummarySegmentFields getAccountNonsummarySegmentFields() {
         return accountNonsummarySegmentFields;
     }

}