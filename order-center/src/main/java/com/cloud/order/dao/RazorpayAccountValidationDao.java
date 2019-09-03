package com.cloud.order.dao;

import com.cloud.order.model.razorpay.RazorpayAccountValidation;
import org.apache.ibatis.annotations.*;

/**
 * Created by hasee on 2019/6/19.
 */

@Mapper
public interface RazorpayAccountValidationDao {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into razorpay_acount_validation(user_id,user_bankcard_id,order_no,fav_id,status,failure_reason) "
            + "values(#{userId},#{userBankcardId}, #{orderNo}, #{favId}, #{status}, #{failureReason})")
    int saveRazorpayAccountValidation(RazorpayAccountValidation razorpayAccountValidation);

    @Select("select * from razorpay_acount_validation where fav_id = #{favId}")
    RazorpayAccountValidation getByFavId(String favId);

    @Update("update razorpay_acount_validation set status=#{status} where fav_id=#{favId} and order_no=#{orderNo}")
    void updateRazorpayAccountValidation(@Param("orderNo")String orderNo,@Param("favId")String favId, @Param("status")String status);
}
