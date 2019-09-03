package com.cloud.user.model;

import javax.persistence.Table;

import lombok.Data;

@Data
@Table(name="kudos_account")
public class Account  extends CibilOrderEntity {
	private static final long serialVersionUID = 24826082532845114L;
    private String length;
    private String segmenttag;
    private AccountSummarySegmentFields accountSummarySegmentFields;
//    private AccountNonsummarySegmentFields accountNonsummarySegmentFields;
    private String accountnonsummarysegmentfields;
}