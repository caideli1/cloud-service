package com.cloud.model.risk;


import lombok.Data;

import java.util.Date;

@Data
public class RiskDeviceAppInfoModel {

    /**
     * 主键Id
     */
    private  Long Id;
    /**
     * 设备ID
     */
    private long deviceId;
    /**
     * app名称
     */
    private String appName;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     *修改时间
     */
    private  Date updateTime;
}
