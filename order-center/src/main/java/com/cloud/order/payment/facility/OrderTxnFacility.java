package com.cloud.order.payment.facility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cloud.model.common.OrderStatus;
import com.cloud.model.common.PayStatus;
import com.cloud.model.product.FinanceLoanModel;

@Component
public class OrderTxnFacility {
	
	private Logger logger = LoggerFactory.getLogger(OrderTxnFacility.class);
	
	private PaymentFacility paymentFacility;
	
	public void handle(FinanceLoanModel financeLoanModel)
    {
        logger.debug(">>>>>>>> 订单处理-开始");
        logger.debug("订单编号{},外部订单编号{}", financeLoanModel.getOrderNo());
        
        PayStatus paymentStatus = paymentFacility.handle(financeLoanModel);
        if (paymentStatus == PayStatus.FAILURE){
        	financeLoanModel.setOrderStatus(OrderStatus.FAILURE.num);
        }else {
        	financeLoanModel.setOrderStatus(OrderStatus.INPROCESSING.num);
        }
        financeLoanModel.setPayStatus(paymentStatus.num);
    }
	
}
