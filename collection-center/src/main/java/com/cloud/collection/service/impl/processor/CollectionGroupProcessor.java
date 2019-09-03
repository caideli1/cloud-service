package com.cloud.collection.service.impl.processor;

import com.cloud.model.collection.CollectionGroupUserVo;
import com.cloud.model.collection.CollectionGroupVo;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/19 11:47
 * 描述：催收分组
 */
public interface CollectionGroupProcessor {
    /**
     * 获取所有已经分组和崔收员信息
     * @return
     */
    List<CollectionGroupVo> getAllGroupUsers();

    /**
     * 根据groupId获取催收员信息
     * @return
     */
    List<CollectionGroupUserVo> getGroupUsersByGroupId(Integer groupId, Example example);

    /**
     * 新增催收员分组
     * @return
     */
    Integer addGroupUsers(Integer groupId, List<Integer> userIdList);
}
