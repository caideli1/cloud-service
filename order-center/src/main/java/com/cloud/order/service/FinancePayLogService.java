package com.cloud.order.service;

import com.cloud.model.order.OrderFailureVo;
import com.cloud.model.pay.FinancePayLogModel;

import java.util.List;
import java.util.Map;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/26 15:43
 * 描述：
 */
public interface FinancePayLogService {
    /**
     * 催收主管的失败订单列表
     * @param params
     * @return
     */
    List<OrderFailureVo> getOrderFailureList(Map<String, Object> params,int page,int pageSize);
    /**
     * 我的催收员的三个订单统计数量
     * @param userId
     * @return
     */
    Map<String,Object> getMyOrderFailureCount(Long userId);
    /**
     * 我的催收员的失败订单列表
     * @param params
     * @return
     */
    List<OrderFailureVo> getMyOrderFailureList(Map<String, Object> params,int page,int pageSize);

    List<FinancePayLogModel> getFailureFinancePayLogListByOrderNo(String orderNo);
}
