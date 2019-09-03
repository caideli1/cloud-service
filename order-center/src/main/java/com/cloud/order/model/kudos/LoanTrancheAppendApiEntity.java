package com.cloud.order.model.kudos;

import lombok.Data;

/**
 * 认证api类
 *
 * @author danquan.miao
 * @date 2019/5/27 0027
 * @since 1.0.0
 */
@Data
public class LoanTrancheAppendApiEntity extends KudosBaseApiEntity {
    /**
     * 交易id
     */
    private String loanTrancheId;
    /**
     * 放款日期
     */
    private String loanDisbursementDte;
    /**
     * 交易笔数
     */
    private String loanTrancheNum;
    /**
     * 交易额度
     */
    private String loanTrancheAmt;
}
