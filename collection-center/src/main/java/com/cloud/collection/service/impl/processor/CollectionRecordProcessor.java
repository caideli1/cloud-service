package com.cloud.collection.service.impl.processor;

import com.cloud.collection.model.CollectionAssginRecordModel;
import com.cloud.model.collection.CollectionRecordVo;

import java.util.Date;
import java.util.List;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/22 13:45
 * 描述：
 */
public interface CollectionRecordProcessor {
    /**
     * 弃用罚息申请
     * 弃用详情查看申请
     * @param collectionIds
     */
    public void unEnableReductionAndDetailAudit(String collectionIds);

    /**
     * 修改催收为最新的指派Id
     * @param collectionAssginRecordModelList
     */
    public void batchUpdateCollectionAssginIds(List<CollectionAssginRecordModel> collectionAssginRecordModelList);

    /**
     * 如果是修改，要判断是否是历史停催，要历史更新进去
     * @param collectionRecordList
     */
    public void batchUpdateCollectionStopHistory(List<CollectionRecordVo> collectionRecordList, Date now);

}
