package com.cloud.order.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name="order_onther_refuse")
public class OrderOntherRefuse {
    @Id
    private  Integer id;

    private String userId;

    private String orderNo;

    private Date updateTime;

    private Date createTime;
}
