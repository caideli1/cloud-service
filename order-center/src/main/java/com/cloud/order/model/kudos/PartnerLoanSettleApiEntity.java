package com.cloud.order.model.kudos;

import lombok.Data;

/**
 * 贷款结算类
 *
 * @author danquan.miao
 * @date 2019/5/27 0027
 * @since 1.0.0
 */
@Data
public class PartnerLoanSettleApiEntity extends KudosBaseApiEntity {
    private String loanTrancheId;

    private String loanRepayDte;

    private String loanRepayAmt;

    private String loanOutstAmt;

    private String loanOutstDays;

    private String loanProcFee;

    private String kudosLoanProcFee;

    private String partnerLoanProcFee;

    private String loanProcFeeDueFlg;

    private String loanProcFeeDueDte;

    private String loanProcFeeDueAmt;

    private String partnerLoanIntAmt;
}
