package com.cloud.order.service.impl;

import com.cloud.order.dao.StockUserOrderDao;
import com.cloud.order.model.StockUserOrderModel;
import com.cloud.order.model.req.StockUserOrderReq;
import com.cloud.order.service.StockUserOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 存量用户订单服务实现
 *
 * @author danquan.miao
 * @date 2019/8/5 0005
 * @since 1.0.0
 */
@Slf4j
@Service
public class StockerUserOrderServiceImpl implements StockUserOrderService {

    @Autowired(required = false)
    private StockUserOrderDao stockUserOrderDao;

    @Override
    public PageInfo<StockUserOrderModel> queryStockUserOrderList(StockUserOrderReq stockUserOrderReq) {
        PageHelper.startPage(stockUserOrderReq.getPage(), stockUserOrderReq.getLimit());
        List<StockUserOrderModel> stockUserOrderModelList = stockUserOrderDao.selectByParams(stockUserOrderReq);
        if (CollectionUtils.isNotEmpty(stockUserOrderModelList)) {
            stockUserOrderModelList.stream().forEach(stockUserOrderModel -> stockUserOrderModel.setOrderNum("MN" + stockUserOrderModel.getOrderNum() + "PD"));
        }
        return new PageInfo<>(stockUserOrderModelList);
    }
}
