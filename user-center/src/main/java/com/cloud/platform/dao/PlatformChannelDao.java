package com.cloud.platform.dao;

import com.cloud.platform.model.PlatformChannelCount;
import com.cloud.platform.model.PlatformChannelModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 渠道dao层
 * @author bjy
 * @date 2019/3/12 0012 9:42
 */
@Mapper
public interface PlatformChannelDao {

    /**
     * 查询推广渠道列表
     * @param params
     * @return
     */
    List<PlatformChannelModel> getPlatformChannel(Map<String, Object> params);

    /**
     * 新建推广渠道
     * @param model
     * @return
     */
    int createPlatformChannel(PlatformChannelModel model);

    /**
     * 修改推广渠道
     * @param model
     * @return
     */
    int modifyPlatformChannel(PlatformChannelModel model);

    /**
     * 删除推广渠道
     * @param ids
     * @return
     */
    int deletePlatformChannel(@Param("ids") String ids);

    /**
     * 获取平台渠道统计信息
     * @param params
     * @return
     */
    List<PlatformChannelCount> getPlatformCount(Map<String, Object> params);

    /**
     * 上线或者下线渠道
     * @param params
     * @return
     */
    int upOrDownChannel(Map<String, Object> params);
}
