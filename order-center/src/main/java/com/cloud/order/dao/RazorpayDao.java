package com.cloud.order.dao;

import com.cloud.model.razorpay.RazorpayPayout;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface RazorpayDao {

	@Update("update razorpay_payout set status =  #{status}, utr = #{utr}, failure_reason=#{failureReason} where id = #{id}")
	int updateRazorpayPayout(RazorpayPayout razorpayPayout);
	

	List<RazorpayPayout> selectByRefId(@Param("orderNo")String orderNo);
}
