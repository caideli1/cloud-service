package com.cloud.collection.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cloud.collection.model.SmsManageModel;
import com.cloud.common.mapper.CommonMapper;
import com.cloud.model.user.SysUser;

@Mapper
public interface SmsManageDao extends CommonMapper<SmsManageModel>{
	List<SmsManageModel> querySmsManageInfo(@Param("userId") Integer userId,@Param("sendStartTime") String sendStartTime,@Param("sendEndTime") String sendEndTime);
	SysUser querySysUserInfo(@Param("userId") Integer userId);
}
