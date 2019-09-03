package com.cloud.order.dao;

import com.cloud.model.user.UserAddress;
import com.cloud.model.user.UserLoan;
import com.cloud.order.model.FinanceExtensionModel;
import com.cloud.order.model.OrderTableModel;
import feign.Param;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hasee on 2019/4/6.
 * 一些建议通用简易方法  不想通过服务调用 所以直接在这个dao编写
 */
@Mapper
public interface BasicDao {

    //根据用户id和地址类型把所有的居住地址查找出来
    @Select("SELECT * FROM user_address WHERE user_id = #{userId} and address_type = #{type} and status = #{status}")
    List<UserAddress> getUserLocalAddress(@Param("userId") long userId, @Param("type") int type, @Param("status") int status);

    @Select("select ip from risk_device_info where order_num = #{orderNo} order by create_time desc limit 1")
    String getIpByOrderNo(@Param("orderNo") String orderNo);

    @Select("select * from finance_extension where order_no=#{orderNo} order by create_at desc limit 1")
    FinanceExtensionModel getFinanceExtensionByOrderNo(@Param("orderNo") String orderNo);

    @Select("select create_time, loan_status from user_loan where user_id=#{userId} order by create_time desc limit 1")
    UserLoan getLatestLoanByUserId(@Param("userId") long userId);

    @Select("select final_audit_time finalAuditTime,check_status checkStatus from user_order where user_id=#{userId} order by create_time desc limit 1")
    Map<String,Object> getLatestRejectOrder(@Param("userId")long userId);


}
