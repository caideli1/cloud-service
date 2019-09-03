package com.cloud.backend.dao;

import com.cloud.model.user.SysPermission;
import org.apache.ibatis.annotations.*;

import java.util.Set;

/**
 * @author yoga
 * @Description: sys_dept_permission_relation
 * @date 2019-04-3017:16
 */
@Mapper
public interface SysDeptPermissionDao {
    @Insert("insert sys_dept_permission_relation (dept_id, permission_id) values (#{deptId}, #{permissionId})")
    int saveDeptPermission(Long deptId, Long permissionId);

    @Delete("DELETE FROM sys_dept_permission_relation WHERE dept_id = #{deptId} AND permission_id = #{permissionId}")
    int deleteDeptPermission(Long deptId, Long permissionId);

    Set<SysPermission> findDeptPermissionsByDeptId(Long deptId);

    @Delete("DELETE FROM sys_dept_permission_relation WHERE dept_id = #{deptId}")
    int deleteDeptAllPermission(Long deptId);
}
