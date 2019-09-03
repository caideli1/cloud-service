package com.cloud.platform.model;

import lombok.Data;

import java.sql.Date;

/**
 * 用户反馈表
 * @author bjy
 * @date 2019/3/8 0008 14:38
 */
@Data
public class PlatformUserFeedbackModel {
    private long id;
    private long answerId;
    private long userId;
    private String userName;
    private String userMobile;
    private String userEmail;
    private int feedbackType;
    private String feedbackContext;
    private Date feedbackDate;
    private int processStatus;
    private Date processDate;


}
