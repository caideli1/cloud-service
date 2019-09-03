package com.cloud.order.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.order.dao.OrderOfflineRepayVoucherDao;
import com.cloud.order.model.OrderOfflineRepayVoucherModel;
import com.cloud.order.test.OrderCenterApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 线下还款测试
 *
 * @author danquan.miao
 * @date 2019/7/24 0024
 * @since 1.0.0
 */
public class OfflineRepayTest extends OrderCenterApplicationTest {
    @Autowired
    private OrderOfflineRepayVoucherDao orderOfflineRepayVoucherDao;

    @Test
    public void findById() {
        int id = 48;
        OrderOfflineRepayVoucherModel orderOfflineRepayVoucherModel = orderOfflineRepayVoucherDao.findById(id);
        System.out.println(JSON.toJSONString(orderOfflineRepayVoucherModel));
    }
}
