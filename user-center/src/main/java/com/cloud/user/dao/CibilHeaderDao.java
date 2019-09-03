package com.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.user.model.Header;
@Mapper
public interface CibilHeaderDao extends CommonMapper<Header> {
	int insertHeader(Header header);
}
