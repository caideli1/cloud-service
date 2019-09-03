package com.cloud.order.payment.facility;


import com.cloud.common.enums.LoanChannelEnum;
import com.cloud.model.common.PayStatus;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.order.payment.IThirdParPay;
import com.cloud.order.payment.ThirdParPayFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentFacility {

    @Autowired
    private ThirdParPayFactory thirdParPayFactory;

    public PayStatus handle(FinanceLoanModel financeLoanModel) {
        IThirdParPay thirdParty = thirdParPayFactory.getThirdParPayInstance(financeLoanModel.getLoanChannel());
        if (thirdParty == null) {
            financeLoanModel.setFailureReason("无效的支付渠道");
            return PayStatus.FAILURE;
        }
        return thirdParty.payment(financeLoanModel);
    }

    public boolean isLoanTimely(int loanChannel) {
        if (loanChannel == LoanChannelEnum.RAZORPAY.getCode()) {
            boolean kudos_v_2 = false;
            if (kudos_v_2) {
                return false;
            }
        }
        return true;
    }
}
