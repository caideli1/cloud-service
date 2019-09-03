package com.cloud.order.payment;

import com.cloud.common.enums.LoanChannelEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ThirdParPayFactory {
    @Autowired
    private IThirdParPay thirdParPayForRazorpay;

    @Autowired
    private IThirdParPay thirdParPayForKotak;

    /**
     * 获取指定渠道支付实例
     *
     * @param loanChannel
     * @return
     * @see [类、类#方法、类#成员]
     */
    public IThirdParPay getThirdParPayInstance(Integer loanChannel) {
        if (loanChannel == LoanChannelEnum.KOTAK.getCode()) {
            return thirdParPayForKotak;
        } else if (loanChannel == LoanChannelEnum.RAZORPAY.getCode()) {
            return thirdParPayForRazorpay;
        } else {
            return null;
        }
    }

}
