package com.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.user.model.Idsegment;
@Mapper
public interface CibilIdsegmentDao extends CommonMapper<Idsegment>{
	int insertIdsegment(Idsegment idsegment);
}
