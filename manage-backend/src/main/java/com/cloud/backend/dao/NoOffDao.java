package com.cloud.backend.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.backend.model.NoOffModel;
import com.cloud.common.mapper.CommonMapper;
@Mapper
public interface NoOffDao extends CommonMapper<NoOffModel>{
	
	int updateNoOffInfo(NoOffModel noOffModel);
}
