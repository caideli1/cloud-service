package com.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.user.model.Identifier;

@Mapper
public interface CibilIdentifierDao extends CommonMapper<Identifier>{
	
}
