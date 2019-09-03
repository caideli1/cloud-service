package com.cloud.collection.service.impl.processor;

import com.cloud.collection.model.CollectionGroupUser;
import com.cloud.collection.service.CollectionGroupService;
import com.cloud.collection.service.CollectionGroupUserService;
import com.cloud.model.collection.CollectionGroupVo;
import com.cloud.model.collection.CollectionGroupUserVo;
import com.cloud.model.user.SysUser;
import com.cloud.model.user.UserInfo;
import com.cloud.service.feign.backend.BackendClient;
import com.cloud.service.feign.user.UserClient;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/19 11:49
 * 描述：
 */
@Service
public class CollectionGroupProcessorImpl implements CollectionGroupProcessor{
    @Resource
    private CollectionGroupService collectionGroupService;

    @Autowired
    private CollectionGroupUserService collectionGroupUserService;

    @Autowired
    private BackendClient backendClient;

    @Override
    public List<CollectionGroupVo> getAllGroupUsers() {
        List<CollectionGroupVo> collectionGroupVoList = collectionGroupService.getAll();
        if (CollectionUtils.isNotEmpty(collectionGroupVoList)){
            Example example = new Example(CollectionGroupUser.class);
            for (CollectionGroupVo collectionGroupVo : collectionGroupVoList){
                List<CollectionGroupUserVo> collectionGroupUserVos = this.getGroupUsersByGroupId(collectionGroupVo.getId(),example);
                collectionGroupVo.setCollectionGroupUserVos(collectionGroupUserVos);
            }
        }else {
            collectionGroupVoList = new ArrayList<>();
        }
        return collectionGroupVoList;
    }

    @Override
    public List<CollectionGroupUserVo> getGroupUsersByGroupId(Integer groupId,Example example) {
        //获取催收员列表
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("groupId", groupId);
        List<CollectionGroupUserVo> collectionGroupUserVos = collectionGroupUserService.getList(example);
        example.clear();
        if (CollectionUtils.isNotEmpty(collectionGroupUserVos)){
            for (CollectionGroupUserVo collectionGroupUserVo : collectionGroupUserVos){
                //获取用户的名称
                SysUser sysUser = backendClient.getOneSysUserById(collectionGroupUserVo.getUserId());
                collectionGroupUserVo.setUserName(sysUser==null?"":sysUser.getUsername());
            }
        }else {
            collectionGroupUserVos = new ArrayList<>();
        }
        return collectionGroupUserVos;
    }

    @Override
    public Integer addGroupUsers(Integer groupId, List<Integer> userIdList) {
        int result = 0;
        Example example = new Example(CollectionGroupUser.class);
        for (Integer userId:userIdList){
            // 查询过滤大部分重复的情况
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("userId", userId);
            CollectionGroupUserVo collectionGroupUserVo = collectionGroupUserService.findOne(example);
            example.clear();
            if (collectionGroupUserVo ==null){
                collectionGroupUserVo = new CollectionGroupUserVo();
                collectionGroupUserVo.setGroupId(groupId);
                collectionGroupUserVo.setUserId(userId);
                try {
                    result = result + collectionGroupUserService.insert(collectionGroupUserVo);
                } catch (Exception e) {
                    //根据唯一索引异常判断已经分组了的组员不准予处理，防并发
                    if (!(e instanceof SQLIntegrityConstraintViolationException
                            ||(e.getCause() instanceof SQLIntegrityConstraintViolationException)
                            || (e.getCause()!=null && e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException))){
                        throw e;
                    }
                }
            }
        }
        return result;
    }
}
