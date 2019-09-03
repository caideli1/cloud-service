package com.cloud.platform.model;

import lombok.Data;

/**
 * 用户反馈表req
 * @author wza
 * @date 2019/3/29
 */
@Data
public class PlatformUserFeedbackReq {
    private long answerId;
    private long userId;
    private String userName;
    private String userMobile;
    private String userEmail;
    private int feedbackType;
    private String feedbackContext;
}
