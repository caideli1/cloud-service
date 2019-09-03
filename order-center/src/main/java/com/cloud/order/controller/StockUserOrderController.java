package com.cloud.order.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.order.model.StockUserOrderModel;
import com.cloud.order.model.req.StockUserOrderReq;
import com.cloud.order.service.StockUserOrderService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 存量用户订单controller
 *
 * @author danquan.miao
 * @date 2019/8/2 0002
 * @since 1.0.0
 */
@Slf4j
@RestController
public class StockUserOrderController {

    @Autowired(required = false)
    private StockUserOrderService stockUserOrderService;

    /**
     * 查询列表
     *
     * @param stockUserOrderReq
     * @return
     */
    @PostMapping("/orders-anon/queryStockUserOrderList")
    @ResponseBody
    public JsonResult queryStockUserOrderList(StockUserOrderReq stockUserOrderReq) {
        PageInfo<StockUserOrderModel> pageInfo = stockUserOrderService.queryStockUserOrderList(stockUserOrderReq);
        return JsonResult.ok(pageInfo.getList(), (int)pageInfo.getTotal());
    }
}
