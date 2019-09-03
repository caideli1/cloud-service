package com.cloud.backend.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cloud.model.common.Page;
import com.cloud.model.user.SysUser;
import com.cloud.model.user.LoginSysUser;
import com.cloud.model.user.SysRole;
import org.springframework.lang.Nullable;

public interface SysUserService {

	void saveSysUserAndSetDept(SysUser sysUser);

	void updateSysUser(SysUser sysUser);

	LoginSysUser findByUsername(String username);

	SysUser findById(Long id);

	void setRoleToUser(Long id, Set<Long> roleIds);

	void updatePassword(Long id, String oldPassword, String newPassword);

	Page<SysUser> findUsers(Map<String, Object> params);

	Set<SysRole> findRolesByUserId(Long userId);

	void bindingPhone(Long userId, String phone);

	void setSysUserIntoDept(Long sysUserId, Long deptId);

	void deleteSysUser(Long sysUserId);

	void updateEnable(Long sysUserId, Integer status);

	List<SysUser> allSysUser(@Nullable String nickname, @Nullable Long sysUserId, @Nullable Long deptId);

	void updateAvatar(Long sysUserId, String avatarUrl);

	SysUser getOneSysUserById(Integer id);

	List<SysUser> queryNotGroupUsers(List<Integer> groupUserIdList);
}
