package com.cloud.order.model;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "product_service_rate")
public class ServiceRateModel implements Serializable {
    private static final long serialVersionUID = 3765618052460797116L;

    @Id
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
