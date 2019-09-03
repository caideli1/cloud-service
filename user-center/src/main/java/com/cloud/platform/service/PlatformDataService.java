package com.cloud.platform.service;


import com.cloud.common.dto.JsonResult;

import java.io.IOException;
import java.util.Map;

/**
 * 平台数据管理服务接口
 * @author bjy
 * @date 2019/3/12 0012 14:21
 */
public interface PlatformDataService {
    /**
     * 获取内部Aadhaar数据
     * @param params
     * @return
     */
    JsonResult getInnerAadhaar(Map<String, Object> params);

    /**
     * 获取外部Aadhaar数据
     * @param params
     * @return
     */
    JsonResult getOuterAadhaar(Map<String, Object> params);

    /**
     * 上传文档到外部Aadhaar数据
     * @param params
     * @return
     */
    JsonResult uploadOutAadhaar(Map<String, Object> params) throws IOException;

    /**
     * 筛选重复Aadhaar数据
     * @param params
     * @return
     */
    JsonResult screenOutAadhaar(Map<String, Object> params);

    /**
     * 删除重复的Aadhaar数据
     * @param params
     * @return
     */
    JsonResult deleteRepeatAadhaar(Map<String, Object> params);
}
