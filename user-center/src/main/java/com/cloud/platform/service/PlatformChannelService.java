package com.cloud.platform.service;

import com.cloud.common.dto.JsonResult;
import com.cloud.platform.model.PlatformChannelModel;

import java.util.Map;

/**
 * 渠道服务
 * @author bjy
 * @date 2019/3/12 0012 9:41
 */
public interface PlatformChannelService {
    /**
     * 查询推广渠道列表
     * @param params
     * @return
     */
    JsonResult getPlatformChannel(Map<String, Object> params);

    /**
     * 新建推广渠道
     * @param model
     * @return
     */
    JsonResult createPlatformChannel(PlatformChannelModel model);

    /**
     * 修改推广渠道
     * @param model
     * @return
     */
    JsonResult modifyPlatformChannel(PlatformChannelModel model);

    /**
     * 删除推广渠道
     * @param ids
     * @return
     */
    JsonResult deletePlatformChannel(String ids);

    /**
     * 获取平台渠道统计信息
     * @param params
     * @return
     */
    JsonResult getPlatformCount(Map<String, Object> params);

    /**
     * 上线或者下线渠道
     * @param params
     * @return
     */
    JsonResult upOrDownChannel(Map<String, Object> params);
}
