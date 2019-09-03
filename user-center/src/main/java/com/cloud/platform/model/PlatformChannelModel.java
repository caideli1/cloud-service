package com.cloud.platform.model;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 渠道表
 * @author bjy
 * @date 2019/3/8 0008 14:38
 */
@Data
public class PlatformChannelModel {
    private long id;
    private String channelName;
    private String channelUrl;
    private BigDecimal channelPassRate;
    private BigDecimal channelDueRate;
    private int channelStatus;
    private int channelClickCount;
    private int isDelete;
    private Timestamp createTime;


}
