package com.cloud.platform.model;

import lombok.Data;

/**
 * @author bjy
 * @date 2019/3/15 0015 13:48
 */
@Data
public class PlatformChannelCount {

    private String channelName;
    private long channelLoginNo;
    private long loanNo;
    private long passedNo;
    private String passedRate="0%";
    private long repayNo;
    private long dueNo;
    private String dueRate="0%";
    private long firstDueNo;
    private String firstDueRate="0%";

}
