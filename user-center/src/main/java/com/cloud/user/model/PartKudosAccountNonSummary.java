package com.cloud.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;


/**
 * @author yoga
 * @Description: kudos_creditreport account字段的一部分
 * @date 2019-06-1914:05
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PartKudosAccountNonSummary implements Serializable {
    private static final long serialVersionUID = 7437451843056165332L;

    @JsonProperty("PaymentHistoryEndDate")
    @JsonFormat(pattern = "ddMMyyyy")
    private LocalDate paymentHistoryEndDate;
    @JsonProperty("PaymentHistoryStartDate")
    @JsonFormat(pattern = "ddMMyyyy")
    private LocalDate paymentHistoryStartDate;
    @JsonProperty("PaymentHistory1")
    private String paymentHistory1 = "";
    @JsonProperty("PaymentHistory2")
    private String paymentHistory2 = "";
    @JsonProperty("CurrentBalance")
    private int currentBalance;
    @JsonProperty("DateOfLastPayment")
//    @JsonFormat(pattern = "ddMMyyyy")
    private String dateOfLastPayment;
    @JsonProperty("AccountType")
    private String accountType = "";
    @JsonProperty("DateClosed")
    @JsonFormat(pattern = "ddMMyyyy")
    private String dateClosed;
    @JsonProperty("HighCreditOrSanctionedAmount")
    private int highCreditOrSanctionedAmount;
    @JsonProperty("EmiAmount")
    private int emiAmount;
    @JsonProperty("TypeOfCollateral")
    private String typeOfCollateral;
    @JsonProperty("WrittenOffAmountPrincipal")
    private String writtenOffAmountPrincipal;
    @JsonProperty("WrittenOffAmountTotal")
    private String writtenOffAmountTotal;
    @JsonProperty("AmountOverdue")
    private String amountOverdue = "0";

}