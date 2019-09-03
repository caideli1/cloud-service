package com.cloud.backend.dao;

import com.cloud.model.user.SysDept;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author yoga
 * @Description: 部门
 * @date 2019-05-0216:04
 */
@Mapper
public interface SysDeptDao {

    @Select("select * from sys_dept where id = #{deptId}")
    SysDept findById(Long deptId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("INSERT INTO sys_dept (`name`, `desc`, `create_time`, `update_time`)" +
            "VALUES (#{name}, #{desc}, #{createTime}, #{updateTime})")
    int save(SysDept sysDept);

    @Update("UPDATE sys_dept SET `name`=#{name}, `desc`=#{desc}, `update_time`=#{updateTime} where id = #{id}")
    int update(SysDept sysDept);

    @Update("UPDATE sys_dept SET `enabled` = #{enabled} WHERE id = #{deptId}")
    int upateEnable(Integer enabled, Long deptId);

    @Delete("delete from sys_dept where id = #{deptId}")
    int delete(Long deptId);

    @Select("SELECT dept.id,dept.NAME,dept.DESC,dept.enabled ,dept.create_time,IFNULL(re.user_count,0) user_count FROM sys_dept dept LEFT JOIN (\n" +
            "SELECT dept_id,count(*) user_count FROM sys_user_dept_relation GROUP BY (dept_id) ) re ON dept.id=re.dept_id")
    List<SysDept> allDepts();

    List<SysDept> findData(Map<String, Object> params);
}
