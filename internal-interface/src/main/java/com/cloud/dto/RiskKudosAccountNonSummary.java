package com.cloud.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author yoga
 * @date 2019-06-2011:29
 */
@Slf4j
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RiskKudosAccountNonSummary implements Serializable {
    private static final long serialVersionUID = 6726495077949281326L;
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
//    @JsonFormat(pattern = "ddMMyyyy") 有时是number有时是string 不能直接按date解析
    private String dateOfLastPayment;
    @JsonProperty("AccountType")
    private String accountType = "";
    @JsonProperty("DateClosed")
    @JsonFormat(pattern = "ddMMyyyy")
    private LocalDate dateClosed;
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


    public YearMonth endYearMonth() {
        return YearMonth.from(paymentHistoryEndDate);
    }

    public YearMonth startYearMonth() {
        return YearMonth.from(paymentHistoryStartDate);
    }

    public String paymentHistory() {
        return paymentHistory2 + paymentHistory1;
    }


    /**
     * 月还款额=(HighCreditOrSanctionedAmount-CurrentBalance)/已还月份数;另外如EmiAmount有数据则取该数据作为月还款额
     */
    public long monthRepayment() {
        if (emiAmount > 0) {
            return emiAmount;
        }

        YearMonth startDate = startYearMonth();
        YearMonth endDate = endYearMonth();

        int years = startDate.getYear() - endDate.getYear();
        int months = startDate.getMonthValue() - endDate.getMonthValue();
        // 总共多少个月
        int totalMonth = years*12 + months + 1;

        return (highCreditOrSanctionedAmount - currentBalance) / totalMonth;
    }

    /**
     * 正在逾期的月份数与最近一次还款的时间间隔小于3个月
     */
    public boolean lastPaymentDateAndDueDateIsBetween3Months() {
        if (null == dateOfLastPayment) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        LocalDate dateOfLastPaymentDate = LocalDate.parse(dateOfLastPayment, formatter);
        YearMonth lastPaymentYearMonth = YearMonth.from(dateOfLastPaymentDate);
        YearMonth targetYearMonth = lastPaymentYearMonth.plusMonths(4);

        List<YearMonthPaymentDue> a = yearMonthPaymentList().stream()
                .filter(i -> i.yearMonth.isBefore(targetYearMonth))
                .filter(i -> i.yearMonth.isAfter(lastPaymentYearMonth))
                .filter(i -> i.getDueDays() > 0).collect(Collectors.toList());

        return yearMonthPaymentList().stream()
                .filter(i -> i.yearMonth.isBefore(targetYearMonth))
                .filter(i -> i.yearMonth.isAfter(lastPaymentYearMonth))
                .filter(i -> i.getDueDays() > 0)
                .count() > 0;

    }


    /**
     * 每月及逾期天数model
     */
    @Setter
    @Getter
    public class YearMonthPaymentDue implements Comparable<YearMonthPaymentDue>{
        // 区分机构
        private int tag;
        private YearMonth yearMonth;
        private Integer dueDays;
        private String dueDaysStr;

        @Override
        public int compareTo(YearMonthPaymentDue obj) {
            return this.yearMonth.compareTo(obj.getYearMonth());
        }

    }


    /**
     * 每月逾期天数列表
     */
    public List<YearMonthPaymentDue> yearMonthPaymentList() {
        YearMonth startDate = startYearMonth();
        YearMonth endDate = endYearMonth();
        String paymentHistory = paymentHistory();

        int years = startDate.getYear() - endDate.getYear();
        int months = startDate.getMonthValue() - endDate.getMonthValue();
        // 总共多少个月
        int totalMonth = years*12 + months + 1;

        if (paymentHistory.length()/3 - totalMonth != 0) {
            log.info("还款月份数（endDate - startDate）: {} 与paymentHistory记录:{}无法对应，该条记录不做计算", totalMonth, paymentHistory);
            return new ArrayList<>();
        }

        List<YearMonthPaymentDue> monthPaymentList = new ArrayList<>();

        // 计算每个月及其对于逾期天数并且按机构作区分
        IntStream.range(0, totalMonth).forEach(j -> {
            // 逾期天数
            String dueDaysStr = paymentHistory.substring(j * 3, 3 + j * 3);
            Integer dueDays;
            try {
                dueDays = Integer.valueOf(dueDaysStr);
            }catch (Exception e) {
                dueDays = 0;
//                e.printStackTrace();
            }
            // 对应年月
            YearMonth yearMonth = startDate.minusMonths(j);

            YearMonthPaymentDue due = new YearMonthPaymentDue();
            due.setYearMonth(yearMonth);
            due.setDueDays(dueDays);
            due.setTag(this.hashCode());
            due.setDueDaysStr(dueDaysStr);

            monthPaymentList.add(due);
        });
        return monthPaymentList;
    }

    public static class Helper {
        /**
         *  从所有机构还款记录中取最近xx个月不同机构的记录
         */
        public static List<YearMonthPaymentDue> recentPaymentHistory(int recentMonths,
                                                                     List<RiskKudosAccountNonSummary> accountNonSummaryList) {
            List<YearMonthPaymentDue> resList = new ArrayList<>();

            if (CollectionUtils.isEmpty(accountNonSummaryList)) {
                return resList;
            }

            List<YearMonthPaymentDue> totalList = new LinkedList<>();
            accountNonSummaryList.forEach( i -> totalList.addAll(i.yearMonthPaymentList()) );

//            totalList.forEach( i -> System.out.println(i.getYearMonth()));

            // 所有机构记录放在一起找出最近还款月份
            Optional<YearMonthPaymentDue> maxMonthPayment = totalList.stream()
                    .max(Comparator.comparing(YearMonthPaymentDue :: getYearMonth));

            if (maxMonthPayment.isPresent()) {
                YearMonth maxYearMonth = maxMonthPayment.get().getYearMonth();
                //目标区间 targetMinYearMonth -- maxYearMonth
                YearMonth targetMinYearMonth = maxYearMonth.minusMonths(recentMonths);

                //按区间过滤
                resList = totalList.stream()
                        .filter(i -> i.getYearMonth().isAfter(targetMinYearMonth))
                        .collect(Collectors.toList());
            }
            return resList;
        }

        /**
         *  每个机构中出现逾期大于dueDays的个数 的list
         */
        public static List<Long> getCountList(int dueDays, List<YearMonthPaymentDue> recentPaymentHistory) {
            Map<Integer, List<YearMonthPaymentDue>> groupMap = recentPaymentHistory
                    .stream()
                    .collect(Collectors.groupingBy(YearMonthPaymentDue :: getTag));

            return groupMap.values()
                    .stream()
                    .map(
                            i -> i.stream().filter(e -> e.getDueDays() > dueDays).count()
                    ).collect(Collectors.toList());
        }

    }






/*
    public static void main(String[] args) {
        //        String jsonStr1 = "{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T001\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":13022019,\"AmountOverdueFieldLength\":\"02\",\"EmiAmount\":2243,\"CurrentBalance\":10,\"PaymentHistoryEndDate\":\"01032019\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"05\",\"HighCreditOrSanctionedAmountFieldLength\":\"04\",\"PaymentHistory1\":\"002\",\"DateReportedAndCertified\":31032019,\"CurrentBalanceFieldLength\":\"02\",\"EmiAmountFieldLength\":\"04\",\"OwenershipIndicator\":1,\"AmountOverdue\":10,\"PaymentHistory1FieldLength\":\"03\",\"PaymentHistoryStartDate\":\"01032019\",\"HighCreditOrSanctionedAmount\":2200}}";
//        String jsonStr1 = "";
//        String jsonStr1 = "[{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T001\",\"Account_NonSummary_Segment_Fields\":{\"PaymentHistory2FieldLength\":\"06\",\"DateOpenedOrDisbursed\":21082017,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01082017\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"PaymentHistory2\":\"000000\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"06\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"001002010030300000000000000000000000000000000000000000\",\"DateReportedAndCertified\":31032019,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":30072018,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":54,\"PaymentHistoryStartDate\":\"01032019\",\"HighCreditOrSanctionedAmount\":19990}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T002\",\"Account_NonSummary_Segment_Fields\":{\"PaymentHistory2FieldLength\":54,\"DateOpenedOrDisbursed\":28052011,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01062016\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"PaymentHistory2\":\"025000000000000000000000000000000000000000000000000000\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":13,\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000000000000000000000000000000000000000000000000000000\",\"DateReportedAndCertified\":31052014,\"CurrentBalanceFieldLength\":\"01\",\"WrittenOffAndSettled\":\"03\",\"DateOfLastPayment\":29042014,\"DateClosed\":23052014,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":54,\"PaymentHistoryStartDate\":\"01052019\",\"HighCreditOrSanctionedAmount\":45120}}]";
//        String jsonStr1 = "{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T001\",\"Account_NonSummary_Segment_Fields\":{\"PaymentHistory2FieldLength\":33,\"DateOpenedOrDisbursed\":\"07112016\",\"EmiAmount\":7946,\"CurrentBalance\":113596,\"PaymentHistoryEndDate\":\"01112016\",\"RepaymentTenureFieldLength\":\"02\",\"RepaymentTenure\":45,\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"PaymentHistory2\":\"STDSTDXXXSTDSTD051021018STDSTDSTD\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"01\",\"HighCreditOrSanctionedAmountFieldLength\":\"06\",\"PaymentHistory1\":\"000000000000000000000000000000000020021018052021020000\",\"DateReportedAndCertified\":31032019,\"CurrentBalanceFieldLength\":\"06\",\"DateOfLastPayment\":12032019,\"EmiAmountFieldLength\":\"04\",\"OwenershipIndicator\":4,\"PaymentHistory1FieldLength\":54,\"PaymentHistoryStartDate\":\"01032019\",\"HighCreditOrSanctionedAmount\":270000}}";
//        String jsonStr1 = "[{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T001\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":14112015,\"EmiAmount\":2484,\"CurrentBalance\":0,\"RepaymentTenureFieldLength\":\"02\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"DateReportedAndCertified\":\"04032019\",\"DateOfLastPayment\":18012019,\"DateClosed\":23022019,\"PaymentHistoryStartDate\":\"01022019\",\"HighCreditOrSanctionedAmount\":48190,\"PaymentHistory2FieldLength\":54,\"RateOfInterestFieldLength\":\"05\",\"ActualPaymentAmountFieldLength\":\"05\",\"ActualPaymentAmount\":70460,\"PaymentHistoryEndDate\":\"01032016\",\"RepaymentTenure\":24,\"PaymentFrequency\":\"03\",\"PaymentHistory2\":\"056087088115085054056086055024024023024022023022023021\",\"AccountType\":13,\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"485452420390365330299268236206177158117087086056056057\",\"CurrentBalanceFieldLength\":\"01\",\"RateOfInterest\":21.67,\"EmiAmountFieldLength\":\"04\",\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":54}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T002\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":17052018,\"CurrentBalance\":18390,\"PaymentHistoryEndDate\":\"01052018\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000000000000000000000000000000000\",\"DateReportedAndCertified\":31032019,\"CurrentBalanceFieldLength\":\"05\",\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":33,\"PaymentHistoryStartDate\":\"01032019\",\"HighCreditOrSanctionedAmount\":18390}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T003\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":20122016,\"ActualPaymentAmountFieldLength\":\"05\",\"ActualPaymentAmount\":25628,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01122016\",\"PaymentFrequency\":\"03\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000072041010000000000000000000\",\"DateReportedAndCertified\":30092017,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":\"07092017\",\"DateClosed\":\"07092017\",\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":30,\"PaymentHistoryStartDate\":\"01092017\",\"HighCreditOrSanctionedAmount\":24900}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T004\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":14032016,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01032016\",\"PaymentFrequency\":\"03\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000077047016000000000000000000\",\"DateReportedAndCertified\":31122016,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":20122016,\"DateClosed\":20122016,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":30,\"PaymentHistoryStartDate\":\"01122016\",\"HighCreditOrSanctionedAmount\":24900}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T005\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":23062015,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01062015\",\"PaymentFrequency\":\"03\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000068039008000000000000000000\",\"DateReportedAndCertified\":31032016,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":14032016,\"DateClosed\":14032016,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":30,\"PaymentHistoryStartDate\":\"01032016\",\"HighCreditOrSanctionedAmount\":24900}}]";
        String jsonStr1 = "[{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T001\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":\"08052019\",\"CurrentBalance\":4000,\"PaymentHistoryEndDate\":\"01052019\",\"RepaymentTenureFieldLength\":\"01\",\"RepaymentTenure\":6,\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"05\",\"HighCreditOrSanctionedAmountFieldLength\":\"04\",\"PaymentHistory1\":\"000\",\"DateReportedAndCertified\":31052019,\"CurrentBalanceFieldLength\":\"04\",\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":\"03\",\"PaymentHistoryStartDate\":\"01052019\",\"HighCreditOrSanctionedAmount\":4000}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T002\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":10032017,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01052017\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000054000000\",\"DateReportedAndCertified\":31082017,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":18082017,\"DateClosed\":18082017,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":12,\"PaymentHistoryStartDate\":\"01082017\",\"HighCreditOrSanctionedAmount\":11500}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T003\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":17122016,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01032017\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"04\",\"PaymentHistory1\":\"000\",\"DateReportedAndCertified\":31032017,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":10032017,\"DateClosed\":10032017,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":\"03\",\"PaymentHistoryStartDate\":\"01032017\",\"HighCreditOrSanctionedAmount\":6000}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T004\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":20082016,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01032017\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"04\",\"PaymentHistory1\":\"000\",\"DateReportedAndCertified\":31032017,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":11032017,\"DateClosed\":11032017,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":\"03\",\"PaymentHistoryStartDate\":\"01032017\",\"HighCreditOrSanctionedAmount\":6500}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T005\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":20072016,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01082016\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"04\",\"PaymentHistory1\":\"000\",\"DateReportedAndCertified\":31082016,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":\"01082016\",\"DateClosed\":\"01082016\",\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":\"03\",\"PaymentHistoryStartDate\":\"01082016\",\"HighCreditOrSanctionedAmount\":6500}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T006\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":10062016,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01062016\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000146115084054000000000000\",\"DateReportedAndCertified\":28022017,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":18022017,\"DateClosed\":18022017,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":27,\"PaymentHistoryStartDate\":\"01022017\",\"HighCreditOrSanctionedAmount\":45200}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T007\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":24052016,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01022017\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000\",\"DateReportedAndCertified\":28022017,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":15022017,\"DateClosed\":15022017,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":\"03\",\"PaymentHistoryStartDate\":\"01022017\",\"HighCreditOrSanctionedAmount\":79000}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T008\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":\"03052016\",\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01022017\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000\",\"DateReportedAndCertified\":28022017,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":15022017,\"DateClosed\":15022017,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":\"03\",\"PaymentHistoryStartDate\":\"01022017\",\"HighCreditOrSanctionedAmount\":12000}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T009\",\"Account_NonSummary_Segment_Fields\":{\"WrittenOffAmountPrincipal\":18520,\"DateOpenedOrDisbursed\":\"05122015\",\"EmiAmount\":724,\"CurrentBalance\":0,\"RepaymentTenureFieldLength\":\"02\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"DateReportedAndCertified\":30092018,\"DateOfLastPayment\":28092018,\"DateClosed\":28092018,\"WrittenOffAmountTotal\":18520,\"PaymentHistoryStartDate\":\"01092018\",\"HighCreditOrSanctionedAmount\":25000,\"WrittenOffAmountPrincipalFieldLength\":\"05\",\"PaymentHistory2FieldLength\":48,\"RateOfInterestFieldLength\":\"04\",\"PaymentHistoryEndDate\":\"01122015\",\"RepaymentTenure\":35,\"PaymentHistory2\":\"SUBSTDSTDSTDSTDSTDSTDSTDSTDSTDSTDSTDSTDSTDSTDSTD\",\"AccountType\":\"00\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000695664633603572542511DBTSUBSUBSUBSUBSUBSUBSUBSUBSUB\",\"CurrentBalanceFieldLength\":\"01\",\"WrittenOffAndSettled\":\"02\",\"RateOfInterest\":9.9,\"EmiAmountFieldLength\":\"03\",\"WrittenOffAmountTotalFieldLength\":\"05\",\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":54}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T010\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":\"01122015\",\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01122015\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000000000063032002000000000\",\"DateReportedAndCertified\":15082016,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":10062016,\"DateClosed\":10062016,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":27,\"PaymentHistoryStartDate\":\"01082016\",\"HighCreditOrSanctionedAmount\":40000}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T011\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":\"01122015\",\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01052016\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000\",\"DateReportedAndCertified\":31052016,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":24052016,\"DateClosed\":24052016,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":\"03\",\"PaymentHistoryStartDate\":\"01052016\",\"HighCreditOrSanctionedAmount\":23000}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T012\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":\"01122015\",\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01052016\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000\",\"DateReportedAndCertified\":31052016,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":24052016,\"DateClosed\":24052016,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":\"03\",\"PaymentHistoryStartDate\":\"01052016\",\"HighCreditOrSanctionedAmount\":12000}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T013\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":\"04112015\",\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01052016\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000\",\"DateReportedAndCertified\":31052016,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":24052016,\"DateClosed\":24052016,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":\"03\",\"PaymentHistoryStartDate\":\"01052016\",\"HighCreditOrSanctionedAmount\":35400}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T014\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":\"09062015\",\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01122015\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000\",\"DateReportedAndCertified\":31122015,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":\"01122015\",\"DateClosed\":\"01122015\",\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":\"03\",\"PaymentHistoryStartDate\":\"01122015\",\"HighCreditOrSanctionedAmount\":23800}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T015\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":\"07052015\",\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01122015\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000\",\"DateReportedAndCertified\":31122015,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":\"01122015\",\"DateClosed\":\"01122015\",\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":\"03\",\"PaymentHistoryStartDate\":\"01122015\",\"HighCreditOrSanctionedAmount\":12500}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T016\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":29042015,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01112015\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000\",\"DateReportedAndCertified\":30112015,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":\"04112015\",\"DateClosed\":\"04112015\",\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":\"03\",\"PaymentHistoryStartDate\":\"01112015\",\"HighCreditOrSanctionedAmount\":33600}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T017\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":16042015,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01052015\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000\",\"DateReportedAndCertified\":31052015,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":\"06052015\",\"DateClosed\":\"06052015\",\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":\"03\",\"PaymentHistoryStartDate\":\"01052015\",\"HighCreditOrSanctionedAmount\":20000}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T018\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":27032015,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01082015\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000000XXX159129098068\",\"DateReportedAndCertified\":29022016,\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":\"01122015\",\"DateClosed\":\"01122015\",\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":21,\"PaymentHistoryStartDate\":\"01022016\",\"HighCreditOrSanctionedAmount\":40000}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T019\",\"Account_NonSummary_Segment_Fields\":{\"PaymentHistory2FieldLength\":54,\"DateOpenedOrDisbursed\":12092012,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01022013\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"PaymentHistory2\":\"000000000000000000000000000000XXX000XXX000000000000000\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":13,\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"000XXXXXX000000000000000XXX000000000000XXX000XXX000XXX\",\"DateReportedAndCertified\":\"06012016\",\"CurrentBalanceFieldLength\":\"01\",\"DateOfLastPayment\":\"08102015\",\"DateClosed\":12102015,\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":54,\"PaymentHistoryStartDate\":\"01012016\",\"HighCreditOrSanctionedAmount\":41000}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T020\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":11092012,\"EmiAmount\":9100,\"CurrentBalance\":8956,\"RepaymentTenureFieldLength\":\"02\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"TypeOfCollateralFieldLength\":\"02\",\"ReportingMemberShortNameFieldLength\":13,\"DateReportedAndCertified\":30092016,\"DateOfLastPayment\":31082016,\"DateClosed\":13092016,\"PaymentHistoryStartDate\":\"01092016\",\"HighCreditOrSanctionedAmount\":300000,\"PaymentHistory2FieldLength\":54,\"RateOfInterestFieldLength\":\"05\",\"ActualPaymentAmountFieldLength\":\"06\",\"ActualPaymentAmount\":427700,\"PaymentHistoryEndDate\":\"01102013\",\"RepaymentTenure\":47,\"PaymentFrequency\":\"03\",\"PaymentHistory2\":\"048048051050050050019000020019000000000000000000XXX000\",\"AccountType\":17,\"HighCreditOrSanctionedAmountFieldLength\":\"06\",\"PaymentHistory1\":\"000000142111141110080049142142111081081XXX081050020019\",\"CurrentBalanceFieldLength\":\"04\",\"RateOfInterest\":19.03,\"TypeOfCollateral\":\"01\",\"EmiAmountFieldLength\":\"04\",\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":54}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T021\",\"Account_NonSummary_Segment_Fields\":{\"RateOfInterestFieldLength\":\"05\",\"WrittenOffAmountPrincipal\":177,\"DateOpenedOrDisbursed\":10112011,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01102017\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":12,\"HighCreditOrSanctionedAmountFieldLength\":\"03\",\"PaymentHistory1\":\"000359328297267236206175DBTSUBSTDSTD\",\"DateReportedAndCertified\":30092018,\"CurrentBalanceFieldLength\":\"01\",\"WrittenOffAndSettled\":\"02\",\"DateOfLastPayment\":20092016,\"RateOfInterest\":15.6,\"DateClosed\":28092018,\"WrittenOffAmountTotalFieldLength\":\"03\",\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":36,\"WrittenOffAmountTotal\":177,\"PaymentHistoryStartDate\":\"01092018\",\"HighCreditOrSanctionedAmount\":177,\"WrittenOffAmountPrincipalFieldLength\":\"03\"}},{\"Account_Summary_Segment_Fields\":{\"ReportingMemberShortNameFieldLength\":13},\"Length\":\"04\",\"SegmentTag\":\"T022\",\"Account_NonSummary_Segment_Fields\":{\"DateOpenedOrDisbursed\":14052014,\"CurrentBalance\":0,\"PaymentHistoryEndDate\":\"01112011\",\"ReportingMemberShortName\":\"NOT DISCLOSED\",\"ReportingMemberShortNameFieldLength\":13,\"AccountType\":\"07\",\"HighCreditOrSanctionedAmountFieldLength\":\"05\",\"PaymentHistory1\":\"XXXXXXXXXXXXXXX000\",\"DateReportedAndCertified\":\"09022015\",\"CurrentBalanceFieldLength\":\"01\",\"DateClosed\":\"01102014\",\"OwenershipIndicator\":1,\"PaymentHistory1FieldLength\":18,\"PaymentHistoryStartDate\":\"01102014\",\"HighCreditOrSanctionedAmount\":56700}}]";
        try {
            KudosModelUtils.getInstance().setFieldKey("Account_NonSummary_Segment_Fields");
            List<RiskKudosAccountNonSummary> summaryList = KudosModelUtils.getInstance().getModelList(jsonStr1, RiskKudosAccountNonSummary.class);
            long res = summaryList.stream().filter(i -> i.lastPaymentDateAndDueDateIsBetween3Months()).count();
        System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/




}
