package com.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.user.model.Namesegment;
@Mapper
public interface CibilNamesegmentDao extends CommonMapper<Namesegment>  {
	int insertNameSegment(Namesegment namesegment);
}
