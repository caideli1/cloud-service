package com.cloud.backend.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;

import com.cloud.model.user.SysUser;

@Mapper
public interface SysUserDao {

	@Options(useGeneratedKeys = true, keyProperty = "id")
	@Insert("insert into sys_user(username, password, nickname, avatar_url, email, phone, enabled, comment, create_time, update_time) "
			+ "values(#{username}, #{password}, #{nickname}, #{avatarUrl}, #{email}, #{phone}, #{enabled}, #{comment}, #{createTime}, #{updateTime})")
	int save(SysUser sysUser);


	int update(SysUser sysUser);

	@Update("UPDATE sys_user SET `enabled` = #{enabled} WHERE id = #{sysUserId}")
	int upateEnable(Integer enabled, Long sysUserId);

	@Deprecated
	@Select("select * from sys_user t where t.username = #{username}")
	SysUser findByUsername(String username);

	@Delete("delete from sys_user where id = #{sysUserId}")
	int deleteSysUser(Long sysUserId);

//	@Select("select * from sys_user t where t.id = #{id}")
	SysUser findById(Long id);

	@Select("select * from sys_user t where t.id = #{id}")
	SysUser getOneSysUserById(Integer id);

	int count(Map<String, Object> params);

	List<SysUser> findData(Map<String, Object> params);

	List<SysUser> allSysUsers(@Param("nickname") String nickname, @Param("sysUserId") Long sysUserId,  @Param("deptId") Long deptId);

	@Update("UPDATE sys_user SET `avatar_url` = #{avatarUrl} WHERE id = #{sysUserId}")
	void updateAvatar(Long sysUserId, String avatarUrl);

	/**
	 * 根据已经分组的催收员用户Id查出未分组的催收员用户列表
	 * created by caideli 2019/8/19
	 * @param groupUserIdList
	 * @return
	 */
	List<SysUser> queryNotGroupUsers(@Param("groupUserIdList") List<Integer> groupUserIdList);
}
