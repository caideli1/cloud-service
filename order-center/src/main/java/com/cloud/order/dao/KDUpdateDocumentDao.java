package com.cloud.order.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.order.model.req.KDUpdateDocumentReq;
@Mapper
public interface KDUpdateDocumentDao extends CommonMapper<KDUpdateDocumentReq> {

}
