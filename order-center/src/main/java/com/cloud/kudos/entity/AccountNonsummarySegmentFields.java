package com.cloud.kudos.entity;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

@Data
public class AccountNonsummarySegmentFields {

    @JsonProperty("ReportingMemberShortNameFieldLength")
    private String reportingmembershortnamefieldlength;
    @JsonProperty("ReportingMemberShortName")
    private String reportingmembershortname;
    @JsonProperty("AccountType")
    private String accounttype;
    @JsonProperty("OwenershipIndicator")
    private String owenershipindicator;
    @JsonProperty("DateOpenedOrDisbursed")
    private String dateopenedordisbursed;
    @JsonProperty("DateOfLastPayment")
    private String dateoflastpayment;
    @JsonProperty("DateClosed")
    private String dateclosed;
    @JsonProperty("DateReportedAndCertified")
    private String datereportedandcertified;
    @JsonProperty("HighCreditOrSanctionedAmountFieldLength")
    private String highcreditorsanctionedamountfieldlength;
    @JsonProperty("HighCreditOrSanctionedAmount")
    private String highcreditorsanctionedamount;
    @JsonProperty("CurrentBalanceFieldLength")
    private String currentbalancefieldlength;
    @JsonProperty("CurrentBalance")
    private String currentbalance;
    @JsonProperty("PaymentHistory1FieldLength")
    private String paymenthistory1fieldlength;
    @JsonProperty("PaymentHistory1")
    private String paymenthistory1;
    @JsonProperty("PaymentHistoryStartDate")
    private String paymenthistorystartdate;
    @JsonProperty("PaymentHistoryEndDate")
    private String paymenthistoryenddate;
    @JsonProperty("PaymentFrequency")
    private String paymentfrequency;
    @JsonProperty("ActualPaymentAmountFieldLength")
    private String actualpaymentamountfieldlength;
    @JsonProperty("ActualPaymentAmount")
    private String actualpaymentamount;
}