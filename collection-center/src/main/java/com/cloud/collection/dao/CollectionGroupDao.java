package com.cloud.collection.dao;

import com.cloud.collection.model.CollectionGroup;
import com.cloud.common.mapper.CommonMapper;
import com.cloud.model.collection.CollectionGroupVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/16 14:46
 * 描述：
 */
@Mapper
public interface CollectionGroupDao extends CommonMapper<CollectionGroup> {
    /**
     * 分组获取催收分组列表
     * @param id
     * @return
     */
    List<CollectionGroupVo> getCollectionGroupList(@Param("id") Integer id);
}
