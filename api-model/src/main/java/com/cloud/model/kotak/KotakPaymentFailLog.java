package com.cloud.model.kotak;

import lombok.Data;

import java.util.Date;

/**
 * Created by hasee on 2019/4/24.
 */
@Data
public class KotakPaymentFailLog {
    public int id;

    public String messageId;

    public String instRefNo;

    public String instStatusCd;

    public String instStatusRem;

    public String error;

    public Date createTime;

    public Date updateTime;
}
