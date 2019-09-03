package com.cloud.model.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Data
public class InterestModel implements Serializable {
    private static final long serialVersionUID = 5611272112375170807L;
    private Integer id;
    private String name;
    private Integer type;
    private BigDecimal rate;
    private boolean isDeleted;
    private String createUser;
    private Date createTime;
    private String modifyUser;
    private Date modifyTime;
}
