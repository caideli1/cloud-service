package com.cloud.order.dao;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.order.model.OrderOfflineRepayVoucherModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by hasee on 2019/5/22.
 */
@Mapper
public interface OrderOfflineRepayVoucherDao extends CommonMapper<OrderOfflineRepayVoucherModel> {

    @Select("select * from order_offline_repay_voucher where id= #{id}")
    OrderOfflineRepayVoucherModel findById(int id);

    @Select("select * from order_offline_repay_voucher where order_no = #{orderNo} AND operate_type = #{operateType}")
    List<OrderOfflineRepayVoucherModel> findByOrderNoAndOperateType(String orderNo, Integer operateType);

    @Select("UPDATE order_offline_repay_voucher SET order_no = #{orderNo},transfer_bank_account = #{transferBankAccount},transfer_amount = #{transferAmount}," +
            "closing_date = #{closingDate},account_date = #{accountDate}," +
            "transfer_id = #{transferId},transfer_channel = #{transferChannel},transfer_voucher_url = #{transferVoucherUrl}," +
            "operate_type = #{operateType},extension_start_date = #{extensionStartDate}, reduction_amount = #{reductionAmount}, memo = #{memo} WHERE id = #{id} ")
    OrderOfflineRepayVoucherModel updateById(OrderOfflineRepayVoucherModel orderOfflineRepayVoucherModel);

    /**
     * 根据id与订单号更新确认操作标识
     * @param orderNo
     * @param voucherId
     * @param isConfirm
     * @return
     */
    @Update("UPDATE order_offline_repay_voucher SET is_confirm = #{isConfirm} WHERE id = #{voucherId} AND order_no = #{orderNo}")
    Integer updateByIdAndOrderNo(String orderNo, Integer voucherId, Integer isConfirm);
}
