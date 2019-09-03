package com.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.user.model.Telephonesegment;
@Mapper
public interface CibilTelephonesegmentDao extends CommonMapper<Telephonesegment>{
	int insertTelephonesegment(Telephonesegment telephonesegment);
}
