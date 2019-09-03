package com.cloud.order.model.kudos;

import lombok.Data;

/**
 * 对帐实体类
 *
 * @author danquan.miao
 * @date 2019/5/27 0027
 * @since 1.0.0
 */
@Data
public class ReconciliationApiEntity extends KudosBaseApiEntity {
    private String loanReconStatus;

    private String loanReconDte;
}
