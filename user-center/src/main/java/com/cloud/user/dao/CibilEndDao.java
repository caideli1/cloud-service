package com.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.user.model.End;
@Mapper
public interface CibilEndDao extends CommonMapper<End>{
	int insertEnd(End end);
}
