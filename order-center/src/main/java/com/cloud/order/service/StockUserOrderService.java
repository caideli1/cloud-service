package com.cloud.order.service;

import com.cloud.order.model.StockUserOrderModel;
import com.cloud.order.model.req.StockUserOrderReq;
import com.github.pagehelper.PageInfo;

/**
 * 存量用户订单服务
 *
 * @author danquan.miao
 * @date 2019/8/5 0005
 * @since 1.0.0
 */
public interface StockUserOrderService {
    /**
     * 查询列表
     *
     * @param stockUserOrderReq
     * @return
     */
    PageInfo<StockUserOrderModel> queryStockUserOrderList(StockUserOrderReq stockUserOrderReq);
}
