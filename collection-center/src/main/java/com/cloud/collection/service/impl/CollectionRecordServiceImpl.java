package com.cloud.collection.service.impl;

import com.cloud.collection.constant.CheckDetailIsDeletedEnum;
import com.cloud.collection.constant.CollectionPayEnum;
import com.cloud.collection.constant.CollectionStationEnum;
import com.cloud.collection.constant.DueSendTypeEnum;
import com.cloud.collection.dao.CollectionDao;
import com.cloud.collection.dao.CollectionDetailsAuditDao;
import com.cloud.collection.dao.CollectionRecordDao;
import com.cloud.collection.model.CollectionAccruedRecordModel;
import com.cloud.collection.model.CollectionAssginRecordModel;
import com.cloud.collection.model.CollectionDetailsAuditModel;
import com.cloud.collection.model.CollectionRecord;
import com.cloud.collection.service.CollectionRecordService;
import com.cloud.collection.service.CollectionStopHistoryService;
import com.cloud.collection.service.impl.processor.CollectionRecordProcessor;
import com.cloud.common.base.BaseServiceImpl;
import com.cloud.common.enums.FunctionStatusEnum;
import com.cloud.common.exception.BusinessException;
import com.cloud.model.collection.CollectionRecordVo;
import com.cloud.model.collection.CollectionStopHistoryVo;
import com.cloud.model.order.FinanceDueOrderVo;
import com.cloud.service.feign.backend.BackendClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/14 15:03
 * 描述：
 */
@Slf4j
@Service
public class CollectionRecordServiceImpl extends BaseServiceImpl<CollectionRecordVo, CollectionRecord> implements CollectionRecordService {
    @Resource
    private CollectionRecordDao collectionRecordDao;
    @Resource
    private CollectionRecordProcessor collectionRecordProcessor;
    @Autowired
    private CollectionStopHistoryService collectionStopHistoryService;
    @Autowired
    private BackendClient backendClient;

    @Resource
    private CollectionDao collectionDao;

    @PostConstruct
    public void init() {
        super.commonMapper = collectionRecordDao;
    }

    @Override
    @Transactional
    public void batchStop(String collectionIds, Long managerId) {
        Example example = new Example(CollectionRecord.class);
        example.excludeProperties("dueEnd");
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",Arrays.asList(collectionIds));
        List<CollectionRecordVo> collectionRecordList = this.getList(example);
        if (CollectionUtils.isNotEmpty(collectionRecordList)){
            collectionRecordList.stream().forEach(collectionRecordVo -> {
                if (collectionRecordVo.getCollectionStation()==CollectionStationEnum.STOP.num){
                    throw new BusinessException("已停催订单不可二次操作，请重新选择订单",null);
                }
            });
        }
        //此催收记录为停催 停催时间更新进去 次数+1
        collectionRecordDao.batchStopCollection(collectionIds);
        //弃用罚息申请 弃用详情查看申请
        collectionRecordProcessor.unEnableReductionAndDetailAudit(collectionIds);
        //批量插入停催历史
        List<CollectionStopHistoryVo> collectionStopHistoryVoList = getBatchInsertCollectionStopHistoryParams(collectionIds,managerId);
        collectionStopHistoryService.insertBatch(collectionStopHistoryVoList);
    }

    @Override
    public boolean updateCollectionRecordByDue(FinanceDueOrderVo financeDueOrderVo, Date transactionTime) {
        CollectionRecord collectionRecord = new CollectionRecord();
        int result =0;
        //展期
        if (financeDueOrderVo.getSendType().equals(DueSendTypeEnum.EXTENSION_PAY.getStatus())){
            collectionRecord.setIsExtension(FunctionStatusEnum.ENABLED.getCode());
            collectionRecord.setLoanNum(financeDueOrderVo.getOrderNo());
            collectionRecord.setPayStatus(CollectionPayEnum.EXTENSION.getStatus());
            collectionRecordDao.updateCollectionToExtension(collectionRecord);
        }//逾期結束
        else  if (financeDueOrderVo.getSendType().equals(DueSendTypeEnum.DUE_PAY.getStatus())){
            collectionRecord.setDueId(financeDueOrderVo.getId().longValue());
            collectionRecord.setIsExtension(FunctionStatusEnum.DISABLED.getCode());
            collectionRecord.setPayStatus(CollectionPayEnum.PAY_SUCESS.getStatus());
            result = collectionRecordDao.updateCollectionToDueEnd(collectionRecord);
        }else if (financeDueOrderVo.getSendType().equals(DueSendTypeEnum.PAY.getStatus())){
            collectionRecord.setLoanNum(financeDueOrderVo.getOrderNo());
            collectionRecord.setPayStatus(CollectionPayEnum.PAY_SUCESS.getStatus());
            result= collectionRecordDao.updateCollectionPayStatus(collectionRecord);
        }

        return result>0;
    }

    @Override
    @Transactional
    public int batchAssginColletion(Long[] collectionIds, Long[] userIds,Long managerId,Boolean isUpdate) {
        //批量插入新的催收指派
        Date now = new Date();
        Example example = new Example(CollectionRecord.class);
        example.excludeProperties("dueEnd");
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id",Arrays.asList(collectionIds));
        List<CollectionRecordVo> collectionRecordList = this.getList(example);
        //批量分配组装
        List<CollectionAssginRecordModel> collectionAssginRecordModelList = getBatchInsertCollectionAssginRecordParams(collectionRecordList,collectionIds,userIds,managerId,now);
        //批量分配
        int num = collectionDao.insertAssginCollection(collectionAssginRecordModelList);
        //修改催收为最新的指派Id
        collectionRecordProcessor.batchUpdateCollectionAssginIds(collectionAssginRecordModelList);
        //弃用罚息申请 弃用详情查看申请
        collectionRecordProcessor.unEnableReductionAndDetailAudit(StringUtils.join(collectionIds,","));
        //如果是修改，要判断是否是历史停催，要历史更新进去
        if (true==isUpdate){
            collectionRecordProcessor.batchUpdateCollectionStopHistory(collectionRecordList,now);
        }
        //全部改为催收中
        collectionDao.updateCollectionStation(StringUtils.join(collectionIds,","), CollectionStationEnum.ASSGINING.num);
        return num;
    }

    @Override
    public int batchMarkCollectionRecords(String ids, Boolean isMark) {
        return collectionRecordDao.batchMarkCollectionRecords(ids,isMark);
    }

    /**
     * 获取失效的订单查看审核记录
     *
     * @param assginRecordModels
     * @param detailsAuditModelList
     * @return
     */
    private List<CollectionDetailsAuditModel> getUnValidAuditModelList(List<CollectionAssginRecordModel> assginRecordModels, List<CollectionDetailsAuditModel> detailsAuditModelList) {
        List<CollectionDetailsAuditModel> resultAuditModelList = new ArrayList<>();
        if (CollectionUtils.isEmpty(assginRecordModels) || CollectionUtils.isEmpty(detailsAuditModelList)) {
            log.info("no collDetailAudit record update");
            return resultAuditModelList;
        }
        Map<Long, CollectionAssginRecordModel> assginRecordModelMap = assginRecordModels.stream().collect(Collectors.toMap(model -> model.getCollectionId(), model -> model));
        resultAuditModelList = detailsAuditModelList.stream().filter(model -> isChangedCollUser(model, assginRecordModelMap)).collect(Collectors.toList());
        resultAuditModelList.stream().forEach(model -> model.setIsDeleted(CheckDetailIsDeletedEnum.DELETED.getCode()));
        return resultAuditModelList;
    }

    private boolean isChangedCollUser(CollectionDetailsAuditModel model, Map<Long, CollectionAssginRecordModel> assginRecordModelMap) {
        if (null == assginRecordModelMap || assginRecordModelMap.isEmpty()) {
            return false;
        }

        Long collectionId = model.getCollectionId();
        CollectionAssginRecordModel assginRecordModel = assginRecordModelMap.get(collectionId);
        if (null == assginRecordModel) {
            return false;
        }

        if (Objects.equals(model.getApplyCheckUserId(), assginRecordModel.getCollectionUserId())) {
            return false;
        }

        return true;
    }

    public List<CollectionStopHistoryVo> getBatchInsertCollectionStopHistoryParams(String collectionIds, Long managerId){
        List<CollectionStopHistoryVo> collectionStopHistoryVoList = new ArrayList<>();
        Date now = new Date();
        for (String collectionId:collectionIds.split(",")){
            CollectionStopHistoryVo collectionStopHistoryVo = new CollectionStopHistoryVo();
            collectionStopHistoryVo.setCollectionId(Long.valueOf(collectionId));
            collectionStopHistoryVo.setStopCollectionTime(now);
            collectionStopHistoryVo.setManagerId(managerId);
            collectionStopHistoryVoList.add(collectionStopHistoryVo);
        }
        return  collectionStopHistoryVoList;
    }

    public List<CollectionAssginRecordModel>  getBatchInsertCollectionAssginRecordParams(List<CollectionRecordVo> collectionRecordList,Long[] collectionIds,
                                                                                         Long[] userIds,Long managerId,Date now){
        List<CollectionAssginRecordModel> collectionAssginRecordModelList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(collectionRecordList)){
            for (int i=0;i<collectionRecordList.size();i++){
                CollectionAssginRecordModel collectionAssginRecordModel = CollectionAssginRecordModel.builder()
                        .collectionId(collectionRecordList.get(i).getId())
                        .managerId(managerId)
                        .collectionName(backendClient.getOneSysUserById(Integer.valueOf(userIds[i%userIds.length].toString())).getUsername())
                        .appointCaseType(collectionRecordList.get(i).getAppointCaseType())
                        .assginDate(now)
                        .build();
                collectionAssginRecordModel.setCollectionUserId(userIds[i%userIds.length]);
                collectionAssginRecordModelList.add(collectionAssginRecordModel);
            }
        }
        return collectionAssginRecordModelList;
    }

}
