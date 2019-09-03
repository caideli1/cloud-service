package com.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.user.model.Enquiry;
@Mapper
public interface CibilEnquiryDao extends CommonMapper<Enquiry> {
	int insertEnquiry(Enquiry enquiry);
}
