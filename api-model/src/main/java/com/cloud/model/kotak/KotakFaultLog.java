package com.cloud.model.kotak;

import lombok.Data;

import java.util.Date;

/**
 * Created by hasee on 2019/4/24.
 */
@Data
public class KotakFaultLog {
    public int id;

    public String messageId;

    public String faultCode;

    public String faultReason;

    public Date createTime;

    public Date updateTime;
}
