package com.cloud.collection.service.impl;

import com.cloud.collection.dao.CollectionGroupUserDao;
import com.cloud.collection.model.CollectionGroupUser;
import com.cloud.collection.service.CollectionGroupUserService;
import com.cloud.common.base.BaseServiceImpl;
import com.cloud.model.collection.CollectionGroupUserVo;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/16 14:58
 * 描述：
 */
@Service
public class CollectionGroupUserServiceImpl extends BaseServiceImpl<CollectionGroupUserVo, CollectionGroupUser> implements CollectionGroupUserService {
    @Resource
    private CollectionGroupUserDao collectionGroupUserDao;

    @PostConstruct
    public void init() {
        super.commonMapper = collectionGroupUserDao;
    }
}
