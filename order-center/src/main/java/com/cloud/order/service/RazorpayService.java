package com.cloud.order.service;

import com.cloud.model.product.FinanceLoanModel;

public interface RazorpayService {
    void handleWebhook(String webHookResponse, String receivedSignature);

	void validationFailMsg(FinanceLoanModel financeLoanModel);
}
