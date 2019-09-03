package com.cloud.order.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.order.model.OrderTableModel;
import com.cloud.order.service.OrderService;
import com.cloud.order.test.OrderCenterApplicationTest;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单测试类
 *
 * @author danquan.miao
 * @date 2019/7/8 0008
 * @since 1.0.0
 */
public class OrderServiceImplTest extends OrderCenterApplicationTest {
    @Autowired(required = false)
    private OrderService orderService;

    @Test
    public void queryUnclaimedOrder() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", 4);
        params.put("limit", 10);

        PageInfo<OrderTableModel> pageInfo = orderService.queryUnclaimedOrder(params);
        System.out.println(JSON.toJSONString(pageInfo.getList()));
    }
}
