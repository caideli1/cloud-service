package com.cloud.platform.model;

import lombok.Data;

/**
 * 起始页面表
 * @author bjy
 * @date 2019/3/8 0008 14:38
 */
@Data
public class PlatformStartPageModel {
    private long id;
    private String pageName;
    private int onlineStatus;
    private String pageImgSrc;
    private String createTime;


}
