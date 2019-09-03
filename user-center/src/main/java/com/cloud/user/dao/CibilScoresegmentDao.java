package com.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.user.model.Scoresegment;
@Mapper
public interface CibilScoresegmentDao extends CommonMapper<Scoresegment>{
	int inesrtScoresegment(Scoresegment scoresegment);
}
