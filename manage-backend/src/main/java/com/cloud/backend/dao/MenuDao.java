package com.cloud.backend.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.*;

import com.cloud.backend.model.Menu;

@Mapper
public interface MenuDao {

	@Insert("insert into sys_menu(parent_id, name, en_name, url, css, sort, create_time, update_time) "
			+ "values (#{parentId}, #{name}, #{enName}, #{url}, #{css}, #{sort}, #{createTime}, #{updateTime})")
	int save(Menu menu);

	int update(Menu menu);

	@Select("select * from sys_menu t where t.id = #{id}")
	Menu findById(Long id);

	@Delete("delete from sys_menu where id = #{id}")
	int delete(Long id);

	@Delete("delete from sys_menu where parent_id = #{id}")
	int deleteByParentId(Long id);

	@Select("select * from sys_menu t order by t.sort")
	List<Menu> findAll();
}
