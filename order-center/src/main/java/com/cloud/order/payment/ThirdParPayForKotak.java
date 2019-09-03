package com.cloud.order.payment;

import com.cloud.model.common.PayStatus;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.service.feign.pay.PayClient;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by hasee on 2019/4/23.
 */
@Slf4j
@Component
public class ThirdParPayForKotak extends IThirdParPay {

    @Autowired
    private PayClient payClient;

    @Override
    public PayStatus payment(FinanceLoanModel financeLoanModel) {
        try {
            log.info("订单号[{}]开始调用放款-kotak", financeLoanModel.getOrderNo());
            Map<String, Object> map = payClient.payment(financeLoanModel);
            if (map.get("failureReason") != null) {
                financeLoanModel.setFailureReason((String) map.get("failureReason"));
            }
            PayStatus payStatus = parsePayStatus(map);
            log.info("订单号[{}]的放款结果[{}]", financeLoanModel.getOrderNo(), payStatus.num);
            return payStatus;
        } catch (RetryableException rex) {
            log.info("kotak payment服务超时");
            financeLoanModel.setFailureReason("调用kotak服务放款中 超时处理");
            return PayStatus.UNDERWAY;
        } catch (Exception ex) {
            log.error("kotak payment服务异常", ex);
            financeLoanModel.setFailureReason("调用kotak服务放款异常");
            return PayStatus.FAILURE;
        }
    }

}
