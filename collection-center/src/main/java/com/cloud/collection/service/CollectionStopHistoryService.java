package com.cloud.collection.service;

import com.cloud.collection.model.CollectionStopHistory;
import com.cloud.common.base.BaseService;
import com.cloud.model.collection.CollectionStopHistoryVo;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/22 16:36
 * 描述：
 */
public interface CollectionStopHistoryService extends BaseService<CollectionStopHistoryVo, CollectionStopHistory> {
    CollectionStopHistoryVo getNewOneCollectionStopHistory(Long collectionId);
}
