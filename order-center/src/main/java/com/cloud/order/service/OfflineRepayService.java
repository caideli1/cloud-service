package com.cloud.order.service;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.user.UserLoan;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * Created by hasee on 2019/5/22.
 */
public interface OfflineRepayService {
    /**
     * 借据结清
     * @param orderNo
     * @param voucherId
     * @return
     */
    JsonResult clearIous(String orderNo, Integer voucherId);

    /**
     * 线下还款订单列表
     * @param parameter
     * @return
     */
    PageInfo<UserLoan> pageOfflineRepayOrder(Map<String, Object> parameter);
}
