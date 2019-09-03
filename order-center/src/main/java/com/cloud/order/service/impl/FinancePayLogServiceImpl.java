package com.cloud.order.service.impl;

import com.cloud.model.collection.CollectionInterestReductionModelVo;
import com.cloud.model.order.OrderFailureVo;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.order.dao.FinancePayLogDao;
import com.cloud.order.service.FinancePayLogService;
import com.cloud.service.feign.collection.CollectionClient;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/26 15:44
 * 描述：
 */
@Service
public class FinancePayLogServiceImpl implements FinancePayLogService {
    @Resource
    private FinancePayLogDao financePayLogDao;
    @Autowired
    private CollectionClient collectionClient;
    @Override
    public List<OrderFailureVo> getOrderFailureList(Map<String, Object> params,int page,int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<OrderFailureVo> orderFailureVoList = financePayLogDao.getOrderFailureList(params);
        orderFailureVoList.stream().forEach(orderFailureVo -> {
            if (orderFailureVo.getDueDays()>0){
                CollectionInterestReductionModelVo collectionInterestReductionModelVo = collectionClient.getOneEnableCollectionInterestReduction(orderFailureVo.getOrderNo());
                if (collectionInterestReductionModelVo!=null){
                    orderFailureVo.setShouldRepayAmount(collectionInterestReductionModelVo.getReductionSumAmmount());
                }
            }
        });
        return orderFailureVoList;
    }

    @Override
    public Map<String, Object> getMyOrderFailureCount(Long userId) {
        Map<String, Object> result = new HashMap<>(3);
        Integer orderFailureCount = financePayLogDao.getMyOrderFailureCount(userId);
        Integer orderCount = financePayLogDao.getMyOrderCount(userId);
        Integer orderNotFailureCount = orderCount - orderFailureCount;
        result.put("orderFailureCount",orderFailureCount);
        result.put("orderCount",orderCount);
        result.put("orderNotFailureCount",orderNotFailureCount);
        return result;
    }

    @Override
    public List<OrderFailureVo> getMyOrderFailureList(Map<String, Object> params,int page,int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<OrderFailureVo> orderFailureVoList = financePayLogDao.getMyOrderFailureList(params);
        orderFailureVoList.stream().forEach(orderFailureVo -> {
            if (orderFailureVo.getDueDays()>0){
                CollectionInterestReductionModelVo collectionInterestReductionModelVo = collectionClient.getOneEnableCollectionInterestReduction(orderFailureVo.getOrderNo());
                if (collectionInterestReductionModelVo!=null){
                    orderFailureVo.setShouldRepayAmount(collectionInterestReductionModelVo.getReductionSumAmmount());
                }
            }
        });
        return orderFailureVoList;
    }

    @Override
    public List<FinancePayLogModel> getFailureFinancePayLogListByOrderNo(String orderNo) {
        return financePayLogDao.getFailureFinancePayLogListByOrderNo(orderNo);
    }
}
