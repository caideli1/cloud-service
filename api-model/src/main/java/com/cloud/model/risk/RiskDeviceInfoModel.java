package com.cloud.model.risk;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RiskDeviceInfoModel {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 订单编号
     */
    private  String orderNum;

    /**
     * 电池电力
     */
    private String  battery;
    /**
     * imiID
     */
    private String imiId;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 经度
     */
    private String altitude;

    /**
     * 维度
     */
    private String longitude;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 电话模式
     */
    private int phoneModel;

    /**
     * gps地址
     */
    private String gpsAddress;

    /**
     * 手机IMEI号
     */
    private String imeId;

    /**
     * 手机型号
     */
    private String phoneType;

    /**
     * 是否模拟器 0：否 1：是
     */
    private String isSimulation;

    /**
     * mac地址
     */
    private String macAddress;

    /**
     * 硬件制造商
     */
    private String handManager;

    /**
     * 网络模式
     */
    private String netMode;

    /**
     * 设备指纹
     */
    private String fingerPrint;

    /**
     * IP地址网络供应商
     */
    private String ipManager;

    /**
     * ip所属城市
     */
    private String ipAddress;

    /**
     * 贷款类App数量
     */
    private long financeAppCount;
}
