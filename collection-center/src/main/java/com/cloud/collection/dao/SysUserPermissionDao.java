package com.cloud.collection.dao;

import com.cloud.model.user.SysPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * @author yoga
 * @Description: sys_user_permission_relation
 * @date 2019-04-3017:16
 */
@Mapper
public interface SysUserPermissionDao {
    Set<SysPermission> findUserPermissionsByUserId(Long sysUserId);
}
