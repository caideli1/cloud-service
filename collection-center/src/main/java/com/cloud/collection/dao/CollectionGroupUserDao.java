package com.cloud.collection.dao;

import com.cloud.collection.model.CollectionGroupUser;
import com.cloud.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/16 14:48
 * 描述：
 */
@Mapper
public interface CollectionGroupUserDao extends CommonMapper<CollectionGroupUser> {
    int batchInsert(List<CollectionGroupUser> list);
}
