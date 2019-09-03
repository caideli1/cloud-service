package com.cloud.user.model;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
@Data
@Table(name="kudos_account_nonsummary_segment_fields")
public class AccountNonsummarySegmentFields extends CibilOrderEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 758296708643010108L;
    private String reportingmembershortnamefieldlength;
    private String reportingmembershortname;
    private String accounttype;
    private String owenershipindicator;
    private String dateopenedordisbursed;
    private String dateoflastpayment;
    private String dateclosed;
    private String datereportedandcertified;
    private String highcreditorsanctionedamountfieldlength;
    private String highcreditorsanctionedamount;
    private String currentbalancefieldlength;
    private String currentbalance;
    private String paymenthistory1fieldlength;
    private String paymenthistory1;
    private String paymenthistorystartdate;
    private String paymenthistoryenddate;
    private String paymentfrequency;
    private String actualpaymentamountfieldlength;
    private String actualpaymentamount;
    
    private String paymenthistory2;
    private String paymenthistory2fieldlength;
    private String repaymenttenurefieldlength;
    private String repaymenttenure;
    private String emiamountfieldlength;
    private String emiamount;
    
    private String typeofcollateralfieldlength;
    private String typeofcollateral;
    
    private String amountoverduefieldlength;
    private String amountoverdue;
    
    @Transient
    private TreeMap<String, String> map;//拆分000+
    
    @Transient
    private Map<String, String> maps;//统计
    
    private String rateofinterest;
    private String rateofinterestfieldlength;
    private String writtenoffandsettled;
    private String writtenoffamounttotalfieldlength;
    private String writtenoffamounttotal;
    private String writtenoffamountprincipalfieldlength;
    private String writtenoffamountprincipal;
//    private String status;//暂未出现此字段
    
    
}