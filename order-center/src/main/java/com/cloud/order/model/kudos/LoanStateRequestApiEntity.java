package com.cloud.order.model.kudos;

import lombok.Data;

/**
 * 查询贷款状态类
 *
 * @author danquan.miao
 * @date 2019/5/27 0027
 * @since 1.0.0
 */
@Data
public class LoanStateRequestApiEntity extends KudosBaseApiEntity {
    private String loanStmtReqFlg;
}
