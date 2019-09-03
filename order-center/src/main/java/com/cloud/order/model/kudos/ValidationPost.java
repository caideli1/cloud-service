package com.cloud.order.model.kudos;

import lombok.Data;

@Data
public class ValidationPost {
	private Integer id;
	private String borrowerAdhaarNum;
	private String borrowerPanNum;
	private String borrowerCreditScore;
}
