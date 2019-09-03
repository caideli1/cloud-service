package com.cloud.order.dao;

import com.cloud.model.order.OrderFailureVo;
import com.cloud.model.pay.FinancePayLogModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/26 15:30
 * 描述：
 */
@Mapper
public interface FinancePayLogDao {

    List<OrderFailureVo> getOrderFailureList(Map<String, Object> params);

    List<OrderFailureVo> getMyOrderFailureList(Map<String, Object> params);

    Integer getMyOrderFailureCount(@Param("userId") Long userId);

    Integer getMyOrderCount(@Param("userId") Long userId);

    @Select("select create_time,IFNULL(failure_reason,'') as failure_reason from finance_pay_log where order_no = #{orderNo} and `loan_type` IN (3, 4, 5) AND `pay_status` = 0 AND `order_status` = 0")
    List<FinancePayLogModel> getFailureFinancePayLogListByOrderNo(@Param("orderNo") String orderNo);
}
