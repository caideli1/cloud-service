package com.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.user.model.Applicant;
@Mapper
public interface CibilApplicantDao extends CommonMapper<Applicant> {

}
