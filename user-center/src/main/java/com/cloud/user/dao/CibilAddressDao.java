package com.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.user.model.Address;
@Mapper
public interface CibilAddressDao extends CommonMapper<Address>{
	int insertAddress(Address address);
}
