package com.cloud.platform.dao;

import com.cloud.platform.model.PlatformAadhaarDataModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 平台服务数据dao
 * @author bjy
 * @date 2019/3/12 0012 14:23
 */
@Mapper
public interface PlatformDataDao {
    /**
     * 获取Aadhaar内部数据
     * @param params
     * @return
     */
    List<PlatformAadhaarDataModel> getInnerAadhaar(Map<String, Object> params);

    /**
     * 获取Aadhaar外部数据
     * @param params
     * @return
     */
    List<PlatformAadhaarDataModel> getOuterAadhaar(Map<String, Object> params);

    /**
     * 插入Aadhaar外部数据
     * @param list
     * @return
     */
    int insertOutAadhaarByList(List<PlatformAadhaarDataModel> list);

    /**
     * 筛选重复Aadhaar外部数据
     * @param params
     * @return
     */
    List<PlatformAadhaarDataModel> screenOutAadhaar(Map<String, Object> params);

    /**
     * 删除重复Aadhaar外部数据
     * @param params
     * @return
     */
    int deleteRepeatAadhaar(Map<String, Object> params);
}
