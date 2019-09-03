package com.cloud.user.dao;

import com.cloud.model.appUser.AppUserAddressInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author  zhujingtao
 */
@Mapper
public interface UserAddressDao {

 List<AppUserAddressInfo> queryUserAddressByUserId(@Param("userId") String userId  );
}
