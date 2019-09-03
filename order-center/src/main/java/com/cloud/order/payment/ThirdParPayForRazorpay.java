package com.cloud.order.payment;

import com.cloud.common.config.CommonConfig;
import com.cloud.common.utils.StringUtils;
import com.cloud.model.common.PayStatus;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.service.feign.pay.PayClient;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class ThirdParPayForRazorpay extends IThirdParPay {

    @Autowired
    private PayClient payClient;

    @Override
    public PayStatus payment(FinanceLoanModel financeLoanModel) {
        try {
            log.info("开始调服务处理借据【loanNum:{}】的放款, loanChannel:{}", financeLoanModel.getOrderNo(), financeLoanModel.getLoanChannel());
            Map<String, Object> map = payClient.payment(financeLoanModel);
            financeLoanModel.setFailureReason((String) map.get("failureReason"));
            PayStatus payStatus = parsePayStatus(map);

            //在银行卡校验打开的前提下  这里的失败不认为是银行卡信息错误
            //在银行卡校验关闭的前提下  这里的失败认为可能是银行卡信息错误
            if (payStatus.num == PayStatus.FAILURE.num && StringUtils.isNotBlank(financeLoanModel.getFailureReason())
                    && financeLoanModel.getFailureReason().contains("BAD_REQUEST_ERROR")) {
                if (!CommonConfig.VALIDATE_BANKCARD_OPEN) {
                    financeLoanModel.setFailureReason(CommonConfig.BANKCARD_ERROR_FLAG + " " + financeLoanModel.getFailureReason());
                } else {
                    financeLoanModel.setFailureReason(CommonConfig.ABLE_TO_RELOAN + " " + financeLoanModel.getFailureReason());
                }
            }
            return payStatus;
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("调用razorpay payment服务异常");
            financeLoanModel.setFailureReason(CommonConfig.ABLE_TO_RELOAN + " " + "调用user-razorpay服务放款异常");
            if (ex instanceof RetryableException) {
                log.info("razorpay payment服务超时");
                return PayStatus.UNDERWAY;
            }
            return PayStatus.FAILURE;
        }
    }
}