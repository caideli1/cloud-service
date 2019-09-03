package com.cloud.backend.service.impl;

import com.cloud.backend.dao.SysDeptDao;
import com.cloud.backend.dao.SysDeptPermissionDao;
import com.cloud.backend.dao.SysUserDeptDao;
import com.cloud.backend.dao.SysUserPermissionDao;
import com.cloud.backend.service.SysPermissionService;
import com.cloud.backend.service.SysUserService;
import com.cloud.model.user.SysDept;
import com.cloud.backend.service.SysDeptSerivce;
import com.cloud.common.component.utils.DateUtil;
import com.cloud.model.user.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @author yoga
 * @Description: SysDeptServiceImpl
 * @date 2019-05-0518:06
 */

@Service
public class SysDeptServiceImpl implements SysDeptSerivce {
    @Autowired
    private SysDeptDao deptDao;
    @Autowired
    private SysDeptPermissionDao deptPermissionDao;
    @Autowired
    private SysUserDeptDao userDeptDao;
    @Autowired
    private SysUserService sysUserService;


    @Override
    public List<SysDept> allDepts() {
        return deptDao.allDepts();
    }

    @Override
    public void save(SysDept dept) {
        Date now = DateUtil.getDate();
        dept.setCreateTime(now);
        dept.setUpdateTime(now);
        deptDao.save(dept);
    }

    @Override
    public void update(SysDept dept) {
        dept.setUpdateTime(DateUtil.getDate());
        deptDao.update(dept);
    }

    @Transactional
    @Override
    public void deleteDept(Long deptId) {
        // 删除部门所有权限
        deptPermissionDao.deleteDeptAllPermission(deptId);
        // 删除成员+成员权限+成员从部门删除+后续操作
        List<Long> userIds = userDeptDao.findSysUserIdsInDept(deptId);
        userIds.forEach(i -> sysUserService.deleteSysUser(i));
        // 删除部门
        deptDao.delete(deptId);
    }


    @Transactional
    @Override
    public void updateEnable(Long deptId, Integer status) {
        deptDao.upateEnable(status, deptId);
        List<Long> userIds = userDeptDao.findSysUserIdsInDept(deptId);
        userIds.forEach(i -> sysUserService.updateEnable(i, status));
    }

    @Override
    public int userCount(Long deptId) {
        return userDeptDao.deptUserCount(deptId);
    }
}
