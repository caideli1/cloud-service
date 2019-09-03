package com.cloud.platform.model;

import lombok.Data;

/**
 * 平台问题表
 * @author bjy
 * @date 2019/3/8 0008 14:38
 */
@Data
public class PlatformAnswerModelModel {
    private long id;
    private String answerTitle;
    private String answerContext;
    private int answerType;


}
