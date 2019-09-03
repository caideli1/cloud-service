package com.cloud.backend.service.impl;

import com.cloud.backend.dao.*;
import com.cloud.model.common.SysPermissionCategory;
import com.cloud.model.user.SysDept;
import com.cloud.backend.service.SysPermissionService;
import com.cloud.common.utils.PageUtil;
import com.cloud.model.common.Page;
import com.cloud.model.user.SysPermission;
import com.cloud.model.user.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author yoga
 * @Description: SysPermissionServiceImpl
 * @date 2019-04-3017:58
 */
@Slf4j
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionDao sysPermissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private SysUserPermissionDao sysUserPermissionDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysDeptPermissionDao sysDeptPermissionDao;
    @Autowired
    private SysDeptDao sysDeptDao;
    @Autowired
    private SysUserDeptDao userDeptDao;


    @Override
    public Set<SysPermission> findByRoleIds(Set<Long> roleIds) {
        return rolePermissionDao.findPermissionsByRoleIds(roleIds);
    }

    @Transactional
    @Override
    public void save(SysPermission sysPermission) {
        SysPermission permission = sysPermissionDao.findByPermission(sysPermission.getPermission());
        if (permission != null) {
            throw new IllegalArgumentException("权限标识已存在");
        }
        sysPermission.setCreateTime(new Date());
        sysPermission.setUpdateTime(sysPermission.getCreateTime());

        sysPermissionDao.save(sysPermission);
        log.info("保存权限标识：{}", sysPermission);
    }

    @Transactional
    @Override
    public void update(SysPermission sysPermission) {
        sysPermission.setUpdateTime(new Date());
        sysPermissionDao.update(sysPermission);
        log.info("修改权限标识：{}", sysPermission);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        SysPermission permission = sysPermissionDao.findById(id);
        if (permission == null) {
            throw new IllegalArgumentException("权限标识不存在");
        }

        sysPermissionDao.delete(id);
        rolePermissionDao.deleteRolePermission(null, id);
        log.info("删除权限标识：{}", permission);
    }

    @Override
    public Page<SysPermission> findPermissions(Map<String, Object> params) {
        int total = sysPermissionDao.count(params);
        List<SysPermission> list = Collections.emptyList();
        if (total > 0) {
            PageUtil.pageParamConver(params, false);

            list = sysPermissionDao.findData(params);
        }
        return new Page<>(total, list);
    }

    @Override
    public Map<String, List<SysPermission>> findGroupPermissions() {
        List<SysPermission> list = sysPermissionDao.findData(null);
        Map<String, List<SysPermission>> res = list.stream()
                .filter(i -> i.getCategory().intValue() != SysPermissionCategory.ADMIN.getNum())
                .collect(groupingBy(SysPermission::categoryDesc));
        return res;
    }

    @Override
    public Set<SysPermission> getUserPermissionsByUserId(Long userId) {
        return sysUserPermissionDao.findUserPermissionsByUserId(userId);
    }


    @Transactional
    @Override
    public void setPermissionsToSysUser(Long sysUserId, Set<Long> permissionIds) {
        SysUser sysUser = sysUserDao.findById(sysUserId);
        if (sysUser == null) {
            throw new IllegalArgumentException("the user does not exist");
        }

        // 查出对应的old权限
        Set<Long> oldPermissionIds = sysUserPermissionDao.findUserPermissionsByUserId(sysUserId).stream()
                .map(p -> p.getId()).collect(Collectors.toSet());

        // 需要添加的权限
        Collection<Long> addPermissionIds = org.apache.commons.collections4.CollectionUtils.subtract(permissionIds,
                oldPermissionIds);
        if (!CollectionUtils.isEmpty(addPermissionIds)) {
            addPermissionIds.forEach(permissionId -> {
                sysUserPermissionDao.saveUserPermission(sysUserId, permissionId);
            });
        }
        // 需要移除的权限
        Collection<Long> deletePermissionIds = org.apache.commons.collections4.CollectionUtils
                .subtract(oldPermissionIds, permissionIds);
        if (!CollectionUtils.isEmpty(deletePermissionIds)) {
            deletePermissionIds.forEach(permissionId -> {
                sysUserPermissionDao.deleteUserPermission(sysUserId, permissionId);
            });
        }

        log.info("给用户id：{}，分配权限：{}", sysUserId, permissionIds);
    }

    @Override
    public Set<SysPermission> getDeptPermissionsByDeptId(Long deptId) {
        return sysDeptPermissionDao.findDeptPermissionsByDeptId(deptId);
    }

    @Transactional
    @Override
    public void setPermissionsToSysDept(Long sysDeptId, Set<Long> permissionIds) {
        SysDept sysDept = sysDeptDao.findById(sysDeptId);
        if (sysDept == null) {
            throw new IllegalArgumentException("部门不存在");
        }

        // 查出对应的old dept权限
        Set<Long> deptOldPermissionIds = sysDeptPermissionDao.findDeptPermissionsByDeptId(sysDeptId).stream()
                .map(p -> p.getId()).collect(Collectors.toSet());

        // 需要添加的dept权限
        Collection<Long> deptAddPermissionIds = org.apache.commons.collections4.CollectionUtils.subtract(permissionIds,
                deptOldPermissionIds);
        if (!CollectionUtils.isEmpty(deptAddPermissionIds)) {
            deptAddPermissionIds.forEach(permissionId -> {
                sysDeptPermissionDao.saveDeptPermission(sysDeptId, permissionId);
            });
        }
        // 需要移除的dept权限
        Collection<Long> deptDeletePermissionIds = org.apache.commons.collections4.CollectionUtils
                .subtract(deptOldPermissionIds, permissionIds);
        if (!CollectionUtils.isEmpty(deptDeletePermissionIds)) {
            deptDeletePermissionIds.forEach(permissionId -> {
                sysDeptPermissionDao.deleteDeptPermission(sysDeptId, permissionId);
            });
        }

        log.info("部门老权限： {}; 部门要添加的权限为：{}; 要删除的权限为：{}", deptOldPermissionIds, deptAddPermissionIds, deptDeletePermissionIds);

        // 保留user自己的权限，变动共有的权限。
        List<Long> sysUserIds = userDeptDao.findSysUserIdsInDept(sysDeptId);
        for (Long sysUserId : sysUserIds) {
            // 查出user对应的old权限
            Set<Long> userOldPermissionIds = sysUserPermissionDao.findUserPermissionsByUserId(sysUserId).stream()
                    .map(p -> p.getId()).collect(Collectors.toSet());

            // 需要添加的user权限
            Collection<Long> intersectionPermissionIds = org.apache.commons.collections4.CollectionUtils.intersection(deptAddPermissionIds,
                    userOldPermissionIds);
            Collection<Long> userAddPermissionIds = org.apache.commons.collections4.CollectionUtils.subtract(deptAddPermissionIds,
                    intersectionPermissionIds);
            userAddPermissionIds.forEach(i -> sysUserPermissionDao.saveUserPermission(sysUserId, i));

//            for (Long userAddPermissionId : deptAddPermissionIds) {
//                if (!userOldPermissionIds.contains(userAddPermissionId)) {
//                    sysUserPermissionDao.saveUserPermission(sysUserId, userAddPermissionId);
//                }
//            }

            // 需要删除的user权限
            Collection<Long> userDeletePermissionIds = org.apache.commons.collections4.CollectionUtils
                    .intersection(deptDeletePermissionIds, userOldPermissionIds);
            userDeletePermissionIds.forEach(i -> sysUserPermissionDao.deleteUserPermission(sysUserId, i));

//            for (Long userDeletePermissionId : deptDeletePermissionIds) {
//                if (userOldPermissionIds.contains(userDeletePermissionId)) {
//                    sysUserPermissionDao.deleteUserPermission(sysUserId, userDeletePermissionId);
//                }
//            }

            log.info("用户id:{}; 老权限：{}; 要添加的权限为：{}; 要删除的权限为：{}",sysUserId, userOldPermissionIds, userAddPermissionIds, userDeletePermissionIds);
        }
    }
}