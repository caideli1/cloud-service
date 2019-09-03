package com.cloud.collection.service;

import com.cloud.collection.model.FinanceDueOrderModel;
import com.cloud.collection.model.FinanceRepayModel;
import com.cloud.collection.model.LaterDetailsModel;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhujingtao
 * @CreateDate: 2019/2/26 14:05
 * 还款管理服务接口
 */
public interface FinanceRepayService {
    /**
     * 获取 逾期订单 详情接口
     *
     * @param dueId 逾期订单ID
     * @return
     */
    List<LaterDetailsModel>  queryLaterDetails(String dueId);

    List<FinanceDueOrderModel> queryDueOrderList(Map<String, Object> params);

    /**
     * 根据条件获取一条还款记录 created by caideli 2019/8/9
     * @param params
     * @return
     */
    FinanceRepayModel getOneFinanceRepayByParams(Map<String,Object> params);
}
