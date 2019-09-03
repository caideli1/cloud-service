package com.cloud.order.model.kudos;

import lombok.Data;

/**
 * Patner Periodic Statement Update
 *
 * @author danquan.miao
 * @date 2019/5/27 0027
 * @since 1.0.0
 */
@Data
public class PeriodicUpdApiEntity {
    private String compBnkStmtDoc;

    private String compGstStmtDoc;

    private String compBsDoc;

    private String compPnlDoc;

    private String compCfDoc;

    private String compItrDoc;

    private String compNetWorthCertDoc;

    private String compPromoterSharesDoc;
    /**
     * kudos 提供的密钥
     */
    private String secretKey;
}
