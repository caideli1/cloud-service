package com.cloud.user.dao;


import com.cloud.common.mapper.CommonMapper;
import com.cloud.model.appUser.UserPanCardModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserPanCardDao extends CommonMapper<UserPanCardModel> {

    int savePanCardInfo(UserPanCardModel model);

    int updatePanCardInfo(UserPanCardModel model);

    UserPanCardModel getPanCardInfo(Long userId);
}
