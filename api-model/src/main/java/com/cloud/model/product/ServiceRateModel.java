package com.cloud.model.product;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yoga
 * @Description: ServiceRateModel
 * @date 2019-07-09 17:16
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceRateModel implements Serializable {
    private static final long serialVersionUID = 3765618052460797116L;

    private Long id;
    private String name;
    private BigDecimal gst;
    private BigDecimal totalRate;
    private String createUser;
    private String updateUser;
    private Date createTime;
    private Date updateTime;
    private Long isDeleted;
}
