package com.cloud.backend.dao;

import com.cloud.model.user.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author yoga
 * @Description: SysUserDeptDao
 * @date 2019-05-0313:38
 */
@Mapper
public interface SysUserDeptDao {

    @Insert("insert into sys_user_dept_relation (sys_user_id, dept_id) values (#{sysUserId}, #{deptId})")
    void insertSysUserToDept(Long sysUserId, Long deptId);

    @Delete("delete from sys_user_dept_relation where sys_user_id = #{sysUserId}")
    void deleteSysUserFromDept(Long sysUserId);

    @Update("update sys_user_dept_relation set dept_id = #{deptId} where sys_user_id = #{sysUserId}")
    void updateSysUserToDept(Long sysUserId, Long deptId);

    @Select("select sys_user_id from sys_user_dept_relation where dept_id = #{deptId}")
    List<Long> findSysUserIdsInDept(Long deptId);

    @Delete("delete from sys_user_dept_relation where dept_id = #{deptId}")
    int deleteDeptAllUser(Long deptId);

    @Select("select count(1) from sys_user_dept_relation where dept_id = #{deptId}")
    int deptUserCount(Long deptId);
}
