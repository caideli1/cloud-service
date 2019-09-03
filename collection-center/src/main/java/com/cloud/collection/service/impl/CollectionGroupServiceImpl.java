package com.cloud.collection.service.impl;

import com.cloud.collection.dao.CollectionGroupDao;
import com.cloud.collection.model.CollectionGroup;
import com.cloud.collection.model.CollectionGroupUser;
import com.cloud.collection.service.CollectionGroupService;
import com.cloud.collection.service.CollectionGroupUserService;
import com.cloud.collection.service.impl.processor.CollectionGroupProcessor;
import com.cloud.common.base.BaseServiceImpl;
import com.cloud.common.exception.BusinessException;
import com.cloud.model.collection.CollectionGroupVo;
import com.cloud.model.collection.CollectionGroupUserVo;
import com.cloud.model.user.SysUser;
import com.cloud.service.feign.backend.BackendClient;
import com.cloud.service.feign.user.UserClient;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/16 14:51
 * 描述：
 */
@Service
public class CollectionGroupServiceImpl extends BaseServiceImpl<CollectionGroupVo, CollectionGroup> implements CollectionGroupService {
    @Resource
    private CollectionGroupDao collectionGroupDao;

    @Autowired
    private CollectionGroupUserService collectionGroupUserService;

    @Autowired
    private BackendClient backendClient;

    @Autowired
    private CollectionGroupProcessor collectionGroupProcessor;

    @PostConstruct
    public void init() {
        super.commonMapper = collectionGroupDao;
    }

    @Override
    public List<CollectionGroupVo> getAllGroupUsers() {
        List<CollectionGroupVo> collectionGroupVoList = collectionGroupProcessor.getAllGroupUsers();
        //获取未分配的催收员用户列表
        CollectionGroupVo collectionGroupVo = this.getNotGroupUsers();
        collectionGroupVoList.add(collectionGroupVo);
        return collectionGroupVoList;
    }

    @Override
    @Transactional
    public void saveGroupAndUsers(CollectionGroupVo collectionGroupVo, List<Integer> userIdList) {
        //查询过滤大部分重复的情况
        Example example = new Example(CollectionGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", collectionGroupVo.getName());
        CollectionGroupVo collectionGroupVo_ = this.findOne(example);
        if (collectionGroupVo_ !=null){
            throw new BusinessException("组名不能重复",null);
        }
        try {
            this.insert(collectionGroupVo);
        } catch (Exception e) {
            //根据唯一索引异常判断组名不能重复，防并发
            if (e instanceof SQLIntegrityConstraintViolationException
                    ||(e.getCause() instanceof SQLIntegrityConstraintViolationException)
            || (e.getCause()!=null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException)){
                throw new BusinessException("组名不能重复",null);
            }else {
                throw e;
            }
        }
        if (CollectionUtils.isNotEmpty(userIdList)){
            collectionGroupProcessor.addGroupUsers(collectionGroupVo.getId(),userIdList);
        }
    }

    @Override
    @Transactional
    public int updateGroupAndUsers(Integer groupId, List<Integer> userIdList) {
        Example example = new Example(CollectionGroupUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("groupId", groupId);
        collectionGroupUserService.deleteByExample(example);
        return collectionGroupProcessor.addGroupUsers(groupId,userIdList);
    }

    @Override
    public CollectionGroupVo getNotGroupUsers() {
        CollectionGroupVo collectionGroupVo = new CollectionGroupVo();
        List<Integer> groupUserIdList = new ArrayList<>();
        List<CollectionGroupUserVo> collectionGroupUserVos = collectionGroupUserService.getAll();
        if (CollectionUtils.isNotEmpty(collectionGroupUserVos)){
            for (CollectionGroupUserVo collectionGroupUserVo : collectionGroupUserVos){
                groupUserIdList.add(collectionGroupUserVo.getUserId());
            }
        }
        List<SysUser> sysUserList = backendClient.queryNotGroupUsers(groupUserIdList);
        List<CollectionGroupUserVo> collectionGroupUserVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysUserList)){
            for (SysUser sysUser:sysUserList){
                CollectionGroupUserVo collectionGroupUserVo = new CollectionGroupUserVo();
                collectionGroupUserVo.setUserId(Integer.parseInt(sysUser.getId().toString()));
                collectionGroupUserVo.setUserName(sysUser.getUsername());
                collectionGroupUserVo.setGroupId(0);
                collectionGroupUserVoList.add(collectionGroupUserVo);
            }
        }
        collectionGroupVo.setId(0);
        collectionGroupVo.setName("未分组");
        collectionGroupVo.setCollectionGroupUserVos(collectionGroupUserVoList);
        return collectionGroupVo;
    }

    @Override
    public List<CollectionGroupVo> getCollectionGroupList(Integer id) {
        return collectionGroupDao.getCollectionGroupList(id);
    }

    @Override
    public List<CollectionGroupVo> getGroupUsers() {
        return collectionGroupProcessor.getAllGroupUsers();
    }

    @Override
    public List<CollectionGroupVo> getGroupAndUsersDetailList(Integer groupId) {
        List<CollectionGroupVo> collectionGroupVoList = new ArrayList<>();

        CollectionGroupVo nowCollectionGroupVo = new CollectionGroupVo();
        Example example = new Example(CollectionGroupUser.class);
        nowCollectionGroupVo.setCollectionGroupUserVos(collectionGroupProcessor.getGroupUsersByGroupId(groupId,example));
        nowCollectionGroupVo.setId(groupId);
        collectionGroupVoList.add(nowCollectionGroupVo);
        collectionGroupVoList.add(this.getNotGroupUsers());
        return collectionGroupVoList;
    }

    @Override
    @Transactional
    public int batchDeleteGroups(List<Integer> groupIdList) {
        Example example = new Example(CollectionGroupUser.class);
        Example.Criteria groupCriteria = example.createCriteria();
        groupCriteria.andIn("id", groupIdList);
        int num = this.deleteByExample(example);

        example = new Example(CollectionGroupUser.class);
        Example.Criteria groupUserCriteria = example.createCriteria();
        groupUserCriteria.andIn("groupId", groupIdList);
        collectionGroupUserService.deleteByExample(example);
        return num;
    }
}
