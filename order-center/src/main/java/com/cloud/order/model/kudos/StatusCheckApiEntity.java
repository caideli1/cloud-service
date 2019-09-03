package com.cloud.order.model.kudos;

import lombok.Data;

/**
 * 状态检查api类
 *
 * @author danquan.miao
 * @date 2019/5/27 0027
 * @since 1.0.0
 */
@Data
public class StatusCheckApiEntity extends KudosBaseApiEntity {
    private String loanDisbursementUpdStatus;

    private String loanReconStatus;

    private String partnerLoanStatus;

    private String partnerLoanComments;
}
