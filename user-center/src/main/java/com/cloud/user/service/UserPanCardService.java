package com.cloud.user.service;

import com.cloud.model.appUser.UserPanCardModel;

/**
 * 用户pancard信息service
 *
 * @author walle
 */
public interface UserPanCardService {

    UserPanCardModel getUserPanCardByUserId(Long userId);
}
