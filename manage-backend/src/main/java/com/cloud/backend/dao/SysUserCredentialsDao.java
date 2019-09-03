package com.cloud.backend.dao;

import com.cloud.model.user.SysUser;
import org.apache.ibatis.annotations.*;

import com.cloud.model.user.UserCredential;

@Mapper
public interface SysUserCredentialsDao {

	@Insert("insert into sys_user_credentials (username, type, sys_user_id) values(#{username}, #{type}, #{sysUserId})")
	int save(UserCredential userCredential);

	@Select("select * from sys_user_credentials t where t.username = #{username}")
	UserCredential findByUsername(String username);

	@Select("select u.* from sys_user u inner join sys_user_credentials c on c.sys_user_id = u.id where c.username = #{username}")
    SysUser findUserByUsername(String username);

	@Delete("delete from sys_user_credentials where sys_user_id = #{sysUserId} and type = 'USERNAME' ")
	int deleteBySysUserId(Long sysUserId);

	@Update("UPDATE sys_user_credentials SET username = #{userName} WHERE sys_user_id = #{sysUserId}  ")
	int updateUserName(@Param("sysUserId") Long sysUserId, @Param("userName") String userName);
}
