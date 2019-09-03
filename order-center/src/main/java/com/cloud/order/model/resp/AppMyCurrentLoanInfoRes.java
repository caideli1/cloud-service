package com.cloud.order.model.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yoga
 * @Description: AppMyCurrentLoanInfoRes
 * @date 2019-07-2617:18
 */
@Getter
@Setter
public class AppMyCurrentLoanInfoRes implements Serializable {
    private static final long serialVersionUID = -4092866255815678969L;
    private String loanNumber;
    private Integer loanStatus;
    private BigDecimal borrowAmount;
    /**
     * 应还日期
     */
    private Date repayDate;
}
