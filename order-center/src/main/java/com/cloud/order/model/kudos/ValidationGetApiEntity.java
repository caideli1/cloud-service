package com.cloud.order.model.kudos;

import lombok.Data;

@Data
public class ValidationGetApiEntity {
	private String partnerBorrowerId;

	private String kudosBorrowerId;

	private String partnerLoanId;

	private String kudosLoanId;

	private String secretKey;//kudos 提供的密钥
}
