package com.cloud.backend.dao;

import java.util.Set;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cloud.model.user.SysRole;

/**
 * 用户角色关系<br>
 * 用户和角色是多对多关系，sys_role_user是中间表
 *
 * @author nl
 */
@Mapper
public interface UserRoleDao {

	int deleteUserRole(@Param("sysUserId") Long userId, @Param("roleId") Long roleId);

	@Insert("insert into sys_role_user(sysUserId, roleId) values(#{sysUserId}, #{roleId})")
	int saveUserRoles(@Param("sysUserId") Long userId, @Param("roleId") Long roleId);

	/**
	 * 根据用户id获取角色
	 * 
	 * @param userId
	 * @return
	 */
	@Select("select r.* from sys_role_user ru inner join sys_role r on r.id = ru.roleId where ru.sysUserId = #{sysUserId}")
	Set<SysRole> findRolesByUserId(Long userId);

}
