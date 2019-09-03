package com.cloud.collection.service;

import com.cloud.collection.model.CollectionGroup;
import com.cloud.common.base.BaseService;
import com.cloud.model.collection.CollectionGroupUserVo;
import com.cloud.model.collection.CollectionGroupVo;

import java.util.Arrays;
import java.util.List;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/16 14:50
 * 描述：
 */
public interface CollectionGroupService extends BaseService<CollectionGroupVo, CollectionGroup> {

    List<CollectionGroupVo> getAllGroupUsers();

    void saveGroupAndUsers(CollectionGroupVo collectionGroupVo, List<Integer> userIdList);

    int updateGroupAndUsers(Integer groupId,List<Integer> userIdList);

    /**
     * 获取未分组和催收员信息
     * @return
     */
    CollectionGroupVo getNotGroupUsers();

    /**
     * 分组获取催收分组列表
     * @return
     */
    List<CollectionGroupVo> getCollectionGroupList(Integer id);

    /**
     * 获取已经分组和催收员信息
     * @return
     */
    List<CollectionGroupVo> getGroupUsers();

    /**
     * 获取催收员信息
     * @return
     */
    List<CollectionGroupVo> getGroupAndUsersDetailList(Integer groupId);

    /**
     * 删除分组
     * @param groupIdList
     * @return
     */
    int batchDeleteGroups(List<Integer> groupIdList);

}
