package com.cloud.order.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.order.model.req.KDTrancheAppendReq;
@Mapper
public interface KDTrancheAppendDao extends CommonMapper<KDTrancheAppendReq> {

}
