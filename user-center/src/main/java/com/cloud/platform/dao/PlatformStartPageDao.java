package com.cloud.platform.dao;

import com.cloud.platform.model.PlatformStartPageModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 启动页dao层
 * @author bjy
 * @date 2019/3/8 0008 17:36
 */
@Mapper
public interface PlatformStartPageDao {
    /**
     * 创建启动页
     * @param model
     */
    void createPlatformStartPage(PlatformStartPageModel model);

    /**
     * 修改启动页
     * @param model
     */
    void updatePlatformStartPage(PlatformStartPageModel model);

    /**
     * 获取启动页
     * @param params
     * @return
     */
    List<PlatformStartPageModel> getPlatformStartPage(Map<String, Object> params);

    /**
     * 启动页上线或者下线
     * @param params
     * @return
     */
    int upOrDownStartPage(Map<String, Object> params);

    /**
     * 删除启动页
     * @param params
     * @return
     */
    int deleteStartPage(Map<String, Object> params);
    
}
