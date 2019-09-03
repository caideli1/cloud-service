package com.cloud.order.model.kudos;

import lombok.Data;

/**
 * kudosApi父类
 *
 * @author danquan.miao
 * @date 2019/5/27 0027
 * @since 1.0.0
 */
@Data
public class KudosBaseApiEntity {
    private String partnerBorrowerId;

    private String kudosBorrowerId;

    private String partnerLoanId;

    private String kudosLoanId;
    /**
     * kudos 提供的密钥
     */
    private String secretKey;
}
