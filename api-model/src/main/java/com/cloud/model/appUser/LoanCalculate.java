package com.cloud.model.appUser;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanCalculate {
    private int loanSpan;
    /**
     * 服务费
     */
    private BigDecimal serviceFee;
    /**
     * 税费
     */
    private BigDecimal gstFee;
    /**
     * 利息
     */
    private BigDecimal interestFee;
    /**
     * 总费用
     */
    private BigDecimal totalFee;
    /**
     * 到期日
     */
    private String dueDate;
    /**
     * 逾期利率
     */
    private BigDecimal overDueRate;

    /**
     * 借款金额
     */
    private BigDecimal borrowAmount;

    /**
     * 逾期金额
     */
    private BigDecimal lateChargeAmt;


    public String toAppToastString() {
        String extensionStr = "This option is to delay your loan due date, " +
                "but you need pay the loan extension fees Rs. %s. " +
                "After you paid successfully, your loan new due date will be %s. " +
                "You need repay full repayment Rs. %s on this due date";

        String overdueExtensionStr = "This option is to delay your loan due date, " +
                "but you need pay the loan extension fees Rs. %s (Include overdue fees RS. %s ). " +
                " After you paid successfully, your loan new due date will be %s. " +
                "You need repay full repayment Rs. %s on this due date";

        if (null == lateChargeAmt || lateChargeAmt.compareTo(BigDecimal.ZERO) == 0) {
            return String.format(extensionStr, totalFee.setScale(2, BigDecimal.ROUND_HALF_UP), dueDate, borrowAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
        } else {
            return String.format(overdueExtensionStr, totalFee.setScale(2, BigDecimal.ROUND_HALF_UP), lateChargeAmt.setScale(2, BigDecimal.ROUND_HALF_UP), dueDate, borrowAmount.setScale(2, BigDecimal.ROUND_HALF_UP));
        }

    }
}
