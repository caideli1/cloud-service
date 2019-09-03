package com.cloud.order.model.kudos;

import lombok.Data;

/**
 * 离线还款实体类
 *
 * @author danquan.miao
 * @date 2019/5/27 0027
 * @since 1.0.0
 */
@Data
public class KudosOfflineRepayEntity extends KudosBaseApiEntity {
    private String loanTrancheId;

    private String paidAmnt;

    private String pmntTimestmp;
}
