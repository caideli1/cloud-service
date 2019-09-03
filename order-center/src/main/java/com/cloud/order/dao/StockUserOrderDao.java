package com.cloud.order.dao;

import com.cloud.order.model.StockUserOrderModel;
import com.cloud.order.model.req.StockUserOrderReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 存量用户订单dao
 *
 * @author danquan.miao
 * @date 2019/8/5 0005
 * @since 1.0.0
 */
@Mapper
public interface StockUserOrderDao {
    /**
     * 查询列表
     *
     * @param reqParams
     * @return
     */
    List<StockUserOrderModel> selectByParams(StockUserOrderReq reqParams);

    /**
     * 保存存量用户订单
     *
     * @param stockUserOrderModel
     * @return
     */
    Integer saveStockUserOrder(StockUserOrderModel stockUserOrderModel);
}
