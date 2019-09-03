package com.cloud.collection.service.impl.processor;

import com.cloud.collection.constant.CollectionApplyStatus;
import com.cloud.collection.constant.CollectionStationEnum;
import com.cloud.collection.dao.CollectionDao;
import com.cloud.collection.dao.CollectionDetailsAuditDao;
import com.cloud.collection.model.*;
import com.cloud.collection.service.CollectionRecordService;
import com.cloud.collection.service.CollectionStopHistoryService;
import com.cloud.model.collection.CollectionRecordVo;
import com.cloud.model.collection.CollectionStopHistoryVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/22 13:48
 * 描述：
 */
@Service
public class CollectionRecordProcessorImpl implements CollectionRecordProcessor {

    @Resource
    private CollectionDetailsAuditDao collectionDetailsAuditDao;
    @Resource
    private CollectionDao collectionDao;
    @Autowired
    private CollectionRecordService collectionRecordService;
    @Autowired
    private CollectionStopHistoryService collectionStopHistoryService;

    @Override
    public void unEnableReductionAndDetailAudit(String collectionIds) {
        // 弃用罚息申请
        List<CollectionInterestReductionModel> collectionInterestReductionModels = collectionDao.queryInterestReduction(collectionIds, CollectionApplyStatus.APPROVALPENDING.num);
        if (CollectionUtils.isNotEmpty(collectionInterestReductionModels)) {
            Map<String, Object> parameter = new HashMap<>(2);
            parameter.put("ids", collectionIds);
            parameter.put("applyStatus", CollectionApplyStatus.ABANDON.num);
            collectionDao.updateInterestReduction(parameter);
        }

        //弃用详情查看申请
        List<CollectionDetailsAuditModel> detailsAuditModelList = collectionDetailsAuditDao.queryCollDetailAuditByCollIds(collectionIds);
        if (CollectionUtils.isNotEmpty(detailsAuditModelList)) {
            collectionDetailsAuditDao.updateBatch(detailsAuditModelList);
        }
    }

    @Override
    public void batchUpdateCollectionAssginIds(List<CollectionAssginRecordModel> collectionAssginRecordModelList) {
        if (CollectionUtils.isNotEmpty(collectionAssginRecordModelList)){
            Date now = new Date();
            for (CollectionAssginRecordModel collectionAssginRecordModel:collectionAssginRecordModelList){
                CollectionRecordVo collectionRecordVo = CollectionRecordVo.builder()
                        .id(collectionAssginRecordModel.getCollectionId())
                        .assginId(Integer.valueOf(collectionAssginRecordModel.getId().toString()))
                        .assginDate(now).build();
                collectionRecordService.update(collectionRecordVo);
            }
        }
    }

    @Override
    public void batchUpdateCollectionStopHistory(List<CollectionRecordVo> collectionRecordList,Date now) {
        if (CollectionUtils.isNotEmpty(collectionRecordList)){
            List<Long> collectionIds = new ArrayList<>();
            for (CollectionRecordVo collectionRecordVo:collectionRecordList){
                if (collectionRecordVo.getCollectionStation()== CollectionStationEnum.STOP.num){
                    CollectionStopHistoryVo collectionStopHistoryVo = collectionStopHistoryService.getNewOneCollectionStopHistory(collectionRecordVo.getId());
                    collectionIds.add(collectionStopHistoryVo.getId());
                }

            }
            if (CollectionUtils.isNotEmpty(collectionIds)){
                Example example = new Example(CollectionStopHistory.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andIn("id",collectionIds);
                CollectionStopHistoryVo collectionStopHistoryVoForUpdate = new CollectionStopHistoryVo();
                collectionStopHistoryVoForUpdate.setAssginTime(now);
                collectionStopHistoryService.updateByExampleSelective(example,collectionStopHistoryVoForUpdate);
            }
        }
    }
}
