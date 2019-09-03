package com.cloud.collection.service.impl;

import com.cloud.collection.dao.CollectionHistoryStopDao;
import com.cloud.collection.model.CollectionStopHistory;
import com.cloud.collection.service.CollectionStopHistoryService;
import com.cloud.common.base.BaseServiceImpl;
import com.cloud.model.collection.CollectionStopHistoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/22 16:37
 * 描述：
 */
@Service
public class CollectionStopHistoryServiceImpl extends BaseServiceImpl<CollectionStopHistoryVo, CollectionStopHistory> implements CollectionStopHistoryService {
    @Resource
    private CollectionHistoryStopDao collectionHistoryStopDao;

    @PostConstruct
    public void init() {
        super.commonMapper = collectionHistoryStopDao;
    }

    @Override
    public CollectionStopHistoryVo getNewOneCollectionStopHistory(Long collectionId) {
        CollectionStopHistoryVo collectionStopHistoryVo = new CollectionStopHistoryVo();
        CollectionStopHistory collectionStopHistory = collectionHistoryStopDao.getNewOneCollectionStopHistory(collectionId);
        if (collectionStopHistory!=null){
            BeanUtils.copyProperties(collectionStopHistory,collectionStopHistoryVo);
        }
        return collectionStopHistoryVo;
    }
}
