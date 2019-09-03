package com.cloud.platform.service;

import com.cloud.common.dto.JsonResult;
import com.cloud.platform.model.PlatformStartPageModel;

import java.util.Map;

/**
 * 启动页服务接口
 * @author bjy
 * @date 2019/3/8 0008 17:22
 */
public interface PlatformStartPageService {
    /**
     * 创建启动页
     * @param model
     * @return
     */
    JsonResult createPlatformStartPage(PlatformStartPageModel model);

    /**
     * 修改启动页
     * @param model
     * @return
     */
    JsonResult updatePlatformStartPage(PlatformStartPageModel model);

    /**
     * 获取启动页
     * @param params
     * @return
     */
    JsonResult getPlatformStartPage(Map<String, Object> params);

    /**
     * 启动页上线或者下线
     * @param params
     * @return
     */
    JsonResult upOrDownStartPage(Map<String, Object> params);

    /**
     * 删除启动页
     * @param params
     * @return
     */
    JsonResult deleteStartPage(Map<String, Object> params);
    
}
