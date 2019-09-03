package com.cloud.order.model.kudos;

import lombok.Data;

@Data
public class ValidationPostApiEntity {
	private String partnerBorrowerId;

	private String kudosBorrowerId;

	private String borrowerPanNum;

	private String borrowerAdhaarNum;
	
	private String borrowerCreditScore;
	
	private String partnerLoanId;
	private String kudosLoanId;

	private String secretKey;//kudos 提供的密钥
}
