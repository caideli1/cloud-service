package com.cloud.model.appProduct;
/**
* @Description:    platform_start_page表实体类
* @Author:         wza
* @CreateDate:     2019/3/12 14:28
* @Version:        1.0
*/
import lombok.Data;

@Data
public class PlatformStartPageModel {
    private long id;
    /**
     * 启动页名称
     */
    private String pageName;
    /**
     * 启动页状态
     */
    private int onlineStatus;
    /**
     * 启动页url
     */
    private String pageImgSrc;
    /**
     * 版本号
     */
    private String appVersion;

}
