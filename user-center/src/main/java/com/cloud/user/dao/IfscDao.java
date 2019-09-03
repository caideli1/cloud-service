package com.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by hasee on 2019/6/10.
 */
@Mapper
public interface IfscDao {
    @Select("select count(1) from user_bank_ifsc where ifsc = #{ifsc}")
    int isValidIfsc(String ifsc);
}
