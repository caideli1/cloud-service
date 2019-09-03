package com.cloud.user.dao;

import com.cloud.common.mapper.CommonMapper;
import com.cloud.user.model.UserContactRepairEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yoga
 * @Description: UserContactRepairDao
 * @date 2019-06-0617:44
 */
@Mapper
public interface UserContactRepairDao extends CommonMapper<UserContactRepairEntity> {
    @Select("SELECT contact_type,contact_name,contact_mobile,repair_date FROM user_contact_repaired WHERE user_id=#{userId} AND contact_type=#{contactType}")
    List<UserContactRepairEntity> getContactRepairList(@Param("userId")  long userId, @Param("contactType") int contactType);

    List<UserContactRepairEntity> getContact(@Param("userId") long userId, @Param("contactType") int contactType);
}
