package com.cloud.order.model.kudos;

import lombok.Data;

/**
 * Inc-FLDG
 *
 * @author danquan.miao
 * @date 2019/5/27 0027
 * @since 1.0.0
 */
@Data
public class IncreaseFldgApiEntity {
    private String partnerFldgPerc;

    private String partnerDisbursementTot;

    private String partnerLimitExt;

    private String partnerStrtDte;

    private String secretKey;
}
