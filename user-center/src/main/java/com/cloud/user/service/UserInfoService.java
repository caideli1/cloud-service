package com.cloud.user.service;


import com.cloud.model.appUser.AppUserAddressInfo;
import com.cloud.model.appUser.AppUserLoanInfo;
import com.cloud.user.model.SysRefusalCycle;
import com.cloud.user.model.UserContactRepairEntity;
import com.cloud.model.product.OrderPartRes;
import com.cloud.model.user.*;
import com.cloud.user.model.UserContactRepairReq;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 * @author yoga
 * @Description: UserInfoService
 * @date 2019/1/183:39 PM
 */

public interface UserInfoService {
    UserInfo getUserInfoById(Long id);

    List<UserInfo> getUserInfoList(String id,
                                   String phone,
                                   String aadhaar,
                                   String startTime,
                                   String endTime,
                                   Integer status);

    UserLoan getUserLoanById(Integer id);

    List<UserLoan> getUserLoanList(String loanNumber,
                                   String userId,
                                   String phone,
                                   Integer handlertype,
                                   Integer loanStatus,
                                   String startTime,
                                   String endTime);

    List<UserAddress> getUserAddressList(long userId, int type);

    List<Long> getUserIdByAddressItem( String state, String district);
    List<UserInfo> getUserAccountByAadhaarAccount(String aadhaarAccount);

    List<UserInfoExpand> getUserAccountByPancardAccount(String pancardAccount);

    int getCompanyPhoneCountWhenSameName(String orderNo);

    int getCompanyNameCountWhenSamePhone(String orderNo);

    int getRelativePhoneAsApplyPhoneCount(long userId);

    int getRelativePhoneAsContactPhoneCount(long userId);

    List<AppUserAddressInfo> getUserAddressListByOrderNum(String orderNum);

    Map<String, Object> getUserCreditExtension(long userId);

    Map<String, Object> getUserDueInfo(long userId);

    List<Map<String, Object>> getUserDueInfoList(long userId, String orderNo);

    List<Map<String, Object>> getUserOrderExtensionInfoList(String orderNo);

    Map<String, Object> userLoanAmountComposition(String orderNo);

    OrderPartRes userLoanRepayComposition(String orderNo);

    List<UserContactRepairEntity> getContactList(long userId, int contactType);

    int repairUserContact(UserContactRepairReq repairReq);

    Map<String, String> getUserIdAndRelation(Map<String, Object> params);

    UserContact getContactById(String contractId);

    AppUserLoanInfo getUserLoanByOrderNo(String orderNo);

    List<SysRefusalCycle> queryAllSysRefusalCycle();

    int updateSysRefusalCycle(SysRefusalCycle sysRefusalCycle);

    String getCompanyNameByOrderNum(String orderNum);

    List<String> getFirstAndSecondContactPhoneByOrderNum(String orderNum);

    String getUserWhatsappAccountByOrderNum(String orderNum);

    List<Long> listGetUserIdsByPhones(List<String> phones);

    String getAadharrAddressByOrderNum(String orderNum);

    List<String> listGetUserRelatedPhonesByPhone(List<String> phoneList, String excludedPhone);
}
