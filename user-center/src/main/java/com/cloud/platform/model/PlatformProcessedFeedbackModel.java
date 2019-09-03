package com.cloud.platform.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 处理反馈表
 * @author bjy
 * @date 2019/3/8 0008 14:38
 */
@Data
public class PlatformProcessedFeedbackModel {
    private long id;
    private long feedbackId;
    private String processUser;
    private int processType;
    private String processContext;
    private Timestamp createTime;


}
