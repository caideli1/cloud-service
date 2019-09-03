package com.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.user.model.EmailContactSegment;
@Mapper
public interface CibilEmailContactSegmentDao extends CommonMapper<EmailContactSegment>{
	int insertEmailContactSegment(EmailContactSegment emailContactSegment);
}
