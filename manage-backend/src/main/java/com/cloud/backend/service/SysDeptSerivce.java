package com.cloud.backend.service;

import com.cloud.model.user.SysDept;
import com.cloud.model.user.SysUser;

import java.util.List;

/**
 * @author yoga
 * @Description: SysDeptSerivce
 * @date 2019-05-0518:06
 */
public interface SysDeptSerivce {
    List<SysDept> allDepts();

    void save(SysDept dept);

    void update(SysDept dept);

    void deleteDept(Long deptId);

    void updateEnable(Long deptId, Integer status);

    int userCount(Long deptId);
}
