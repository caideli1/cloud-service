package com.cloud.order.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.order.model.req.KDLoanSettlementReq;
@Mapper
public interface KDLoanSettlementDao extends CommonMapper<KDLoanSettlementReq> {

}
