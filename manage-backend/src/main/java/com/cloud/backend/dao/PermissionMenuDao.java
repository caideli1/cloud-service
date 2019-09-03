package com.cloud.backend.dao;

import com.cloud.backend.model.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yoga
 * @Description: PermissionMenuDao
 * @date 2019-05-0218:50
 */
@Mapper
public interface PermissionMenuDao {
    List<Menu> getMenusByUserId(Long userId);
}
