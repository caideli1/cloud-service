package com.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cloud.model.cibil.LoanCIBILEntity;
import com.cloud.model.cibil.OrderTableModel;
import com.cloud.model.user.UserAddress;
/**
 * cibil 
 * ssp
 * */
@Mapper
public interface CibilOrderDao {
	/**
     * 获取订单编号
     * */
    OrderTableModel queryOrderNo(@Param("orderNo") String orderNo);
    /**
     * kudos adstype
     * ssp
     * */
    UserAddress queryUserAds(@Param("userId") String userId,@Param("adsType") String adsType);
    
    /**
     * kudos  cibil api
     * ssp
     * */
    LoanCIBILEntity queryCibilInformation(@Param("orderNo") String orderNo);
}
