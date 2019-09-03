package com.cloud.user.service.impl;

import com.cloud.model.appUser.UserPanCardModel;
import com.cloud.user.dao.UserPanCardDao;
import com.cloud.user.service.UserPanCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户pancard信息service impl
 *
 * @author walle
 */
@Service
public class UserPanCardServiceImpl implements UserPanCardService {

    @Autowired
    private UserPanCardDao userPanCardDao;

    @Override
    public UserPanCardModel getUserPanCardByUserId(Long userId){
        return userPanCardDao.getPanCardInfo(userId);
    }
}
