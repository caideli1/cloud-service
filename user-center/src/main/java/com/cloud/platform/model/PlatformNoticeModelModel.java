package com.cloud.platform.model;

import lombok.Data;

/**
 * 平台通知模板表
 * @author bjy
 * @date 2019/3/8 0008 14:38
 */
@Data
public class PlatformNoticeModelModel {
    private long id;
    private int noticeType;
    private String noticeName;
    private String noticeDesc;
    private String noticeContext;
    private int noticeTriggerType;
    private int noticeTriggerAmPm;
    private String noticeTriggerTime;
    private int msgType = 0;
    private String templateType;
}

