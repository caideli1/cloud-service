package com.cloud.collection.service;

import com.cloud.collection.model.CollectionRecord;
import com.cloud.common.base.BaseService;
import com.cloud.model.collection.CollectionRecordVo;
import com.cloud.model.order.FinanceDueOrderVo;

import java.util.Date;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/14 15:02
 * 描述：
 */
public interface CollectionRecordService extends BaseService<CollectionRecordVo, CollectionRecord> {
    /**
     * 批量停催
     * @param collectionIds
     * @param managerId
     */
    public void batchStop(String collectionIds,Long managerId);

    /**
     * 批量标量
     * @param ids
     * @param isMark
     * @return
     */
    int batchMarkCollectionRecords(String ids, Boolean isMark);
    /**
     * 根據逾期  修改催收數據
     * @param financeDueOrderVo
     * @param transactionTime
     * @return
     */
    boolean updateCollectionRecordByDue(FinanceDueOrderVo financeDueOrderVo, Date transactionTime);

    /**
     * 批量分配催收订单
     * @param collectionIds
     * @param userIds
     * @param managerId
     * @return
     */
    int batchAssginColletion(Long[] collectionIds, Long[] userIds,Long managerId,Boolean isUpdate);
}
