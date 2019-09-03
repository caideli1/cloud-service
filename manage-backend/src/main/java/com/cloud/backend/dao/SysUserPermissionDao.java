package com.cloud.backend.dao;

import com.cloud.model.user.SysPermission;
import org.apache.ibatis.annotations.*;

import java.util.Set;

/**
 * @author yoga
 * @Description: sys_user_permission_relation
 * @date 2019-04-3017:16
 */
@Mapper
public interface SysUserPermissionDao {
    @Select("SELECT permission_id id FROM sys_user_permission_relation r LEFT JOIN sys_permission p ON p.id=r.permission_id WHERE r.sys_user_id = #{sysUserId}")
    Set<Long> findMenuIdsByUserId(Long userId);

    @Insert("insert into sys_user_permission_relation (sys_user_id, permission_id) values(#{sysUserId}, #{permissionId})")
    int saveUserPermission(Long sysUserId, Long permissionId);

    @Delete("DELETE FROM sys_user_permission_relation WHERE sys_user_id = #{sysUserId} AND permission_id = #{permissionId}")
    int deleteUserPermission(Long sysUserId, Long permissionId);

    Set<SysPermission> findUserPermissionsByUserId(Long sysUserId);

    @Delete("DELETE FROM sys_user_permission_relation WHERE sys_user_id = #{sysUserId}")
    int deleteUserAllPermission(Long sysUserId);
}
