package com.cloud.model.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yoga
 * @Description: 订单 借据 应还金额组成
 * @date 2019-03-13 16:45
 */

@Data
public class OrderRepayPart implements Serializable {
    private static final long serialVersionUID = -9169971858442832627L;
    /**
     * 借款金额
     */
    private BigDecimal loanAmount;
    /**
     * 未还逾期
     */
    private BigDecimal dueAmount;
    /**
     * 已还逾期
     */
    private BigDecimal paidDueAmount;
    /**
     * 展期费用
     */
    private BigDecimal extensionAmount;
    /**
     * 减免费用
     */
    private BigDecimal reductionAmount;
    /**
     * 未还费用
     */
    private BigDecimal noRepayAmount;
    /**
     * 收费项目
     */
    private List<OrderPartRateAmount> rateInfoList;
    /**
     * 总增值税
     */
    private BigDecimal totalGstAmount;

    private Long extensionCount;

    private Long productTerm;

    private BigDecimal rate;

    public OrderPartRes toOrderPartRes() {
        OrderPartRes orderPartRes = OrderPartRes.builder().build();
        orderPartRes.setRateInfoList(this.rateInfoList);

        List<OrderPartAmountDetail> orderPartAmountList = new ArrayList<>();
        orderPartAmountList.add(OrderPartAmount.builder().name("GST").amount(totalGstAmount).isPaid(true).build());
        if (null != noRepayAmount && noRepayAmount.compareTo(BigDecimal.ZERO) > 0) {
            orderPartAmountList.add(OrderPartAmount.builder().name("未还总金额").amount(noRepayAmount).isPaid(false).build());
        }
//        if (null != reductionAmount && reductionAmount.compareTo(new BigDecimal("0")) != 0) {
//            orderPartAmountList.add(OrderPartRateAmount.builder().name("减免费用").amount(reductionAmount).build());
//        }
        if (null != extensionAmount && extensionAmount.compareTo(BigDecimal.ZERO) != 0) {
            orderPartAmountList.add(OrderPartAmount.builder().name("展期费用").amount(extensionAmount).isPaid(true).build());
        }
        if (null != dueAmount && dueAmount.compareTo(BigDecimal.ZERO) != 0) {
            orderPartAmountList.add(OrderPartAmount.builder().name("逾期费用").amount(dueAmount).isPaid(false).build());
        }
        if (null != paidDueAmount && paidDueAmount.compareTo(BigDecimal.ZERO) != 0) {
            orderPartAmountList.add(OrderPartAmount.builder().name("逾期费用").amount(paidDueAmount).isPaid(true).build());
        }
        orderPartAmountList.add(OrderPartAmount.builder().name("本金").amount(loanAmount).isPaid(noRepayAmount.compareTo(BigDecimal.ZERO) < 1).build());
        orderPartRes.setOtherAmountList(orderPartAmountList);
        return orderPartRes;
    }

}
