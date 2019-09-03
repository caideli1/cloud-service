package com.cloud.order.model.kudos;

import lombok.Data;

/**
 * 网关支付交易通知类 *
 * @author danquan.miao
 * @date 2019/5/27 0027
 * @since 1.0.0
 */
@Data
public class PGTxnNotifyApiEntity extends KudosBaseApiEntity {
    private String orderId;

    private String loanTrancheId;

    private String paidAmnt;

    private String pmntTimestmp;
}
