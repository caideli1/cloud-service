package com.cloud.user.dao;

import com.cloud.model.appUser.*;

import com.cloud.model.user.UserAddress;

import com.cloud.model.user.UserDataStatisModel;
import com.cloud.model.user.UserSalary;
import com.cloud.platform.model.PlatformUserFeedbackModel;
import com.cloud.user.model.AppUserInfoHistoryEntity;
import com.cloud.user.model.AppUserRepayModeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface AppUserInfoDao {

    int saveLiveData(AppUserImgInfo appUserImgInfo);

    @Select("select password from user_info where cell_phone = #{phone}")
    String getPwByPhone(String phone);

    @Select("select  count(1)  from  user_info where DATE_FORMAT(register_time,'%Y-%m-%d')=#{date} ")
    Integer getUserCountByDate(@Param("date") String date);

    AppUserBasicInfo getByMobile(String cellPhone);

    AppUserBasicInfo getByUserId(Long id);

    int updateById(AppUserBasicInfo basicInfo);

    void updateWorkCardById(Long id);

    int saveUserInfo(AppUserJobInfo appUserJobInfo);

    int saveAddress(AppUserAddressInfo addressInfo);

    Integer getUserStatus(Long id);

    int saveUserSalary(AppUserSalary userSalary);

    int saveUserContact(AppUserContact appUserContact);

    int saveBankInfo(AppUserBank appUserBank);

    AppUserBank getNewBankCardInfo(Long userId);

    int saveLoanInfo(AppUserLoanInfo loanInfo);

    AppUserJobInfo getUserExpand(Long userId);

    List<AppUserContact> getUserAllContact(Long userId);
    
    void deleteContactById(Long userId);
    
    int addUserInfo(AppUserBasicInfo userInfo);

    int updateUserInfo(AppUserJobInfo appUserJobInfo);

    int saveUserOrder(AppUserOrderInfo orderInfo);

    AppUserBasicInfo getBasicById(Long userId);

    List<AppUserJobInfo> getUserIdentityInfo(Long userId);

    List<AppUserContact> getContactInfoById(Long userId);

    AppUserBank getBankInfoById(Long userId);

    void updateBankInfo(Integer userId);

    AppUserOrderInfo getOrderById(Long userId);

    AppUserLoanInfo getLoanByLoanNum(String applyNum);

    UserBindCardStatus getUserDetailById(Long userId);

    List<AppFAQInfo> queryFAQInfo();

    AppFAQInfo queryFAQDetailById(Integer id);

    AppUserOrderInfo getOrderByOrderNum(String orderNum);

    AppUserImgInfo getBodyImgById(Long userId);

    int updateLiveData(AppUserImgInfo appUserImgInfo);

    int saveUserFeedback(PlatformUserFeedbackModel model);

    void saveUserData(UserDataStatisModel model);

    void updateOrderTime(Integer userId);
    //更新用户地址为未启用状态
    @Update("update user_address set status = 0 where user_id = #{userId} and address_type = #{workerUserType}")
    void updateAddressStatus(Long userId, Integer workerUserType);

    UserDataStatisModel findUserData(UserDataStatisModel model);

    int saveRepayMode(AppUserRepayModeEntity modeEntity);

    List<UserAddress> getUserAddressById(@Param("userId") Long userId);


   @Select("select  *  from   user_salary where  user_id=#{userId}   order by  id desc  limit 1 ")
    UserSalary  getUserSalaryById(@Param("userId") Long userId);

    List<AppUserImgInfoReq>   getAppUserImgInfoById(@Param("userId") Long userId);


    Integer   saveUserInfoHistory(AppUserInfoHistoryEntity  appUserInfoHistoryEntity);

    List<AppUserInfoHistoryEntity> queryOrderHistoryByUserId(@Param("userId") Long userId);

    @Select(" select  *  from  user_info_history  where   order_no=#{orderNo}")
    List<AppUserInfoHistoryEntity>   queryOrderHistoryByOrderNo(@Param("orderNo")  String  orderNo);

    int  updateOrderHistoryBankIdById(AppUserInfoHistoryEntity orderHistoryDto);

   int  updateOrderHistorySalaryIdById(AppUserInfoHistoryEntity orderHistoryDto);

   @Select("select loan_amount_level from user_info where id = #{userId}")
   Integer queryUserLoanLevel(Long userId);


   int  setUserContactEnable(@Param("id") Integer id,@Param("enabledState") Integer enabledState);

    @Select("select id, profile_url from user_info where cell_phone = #{userId}")
    AppUserBasicInfo queryAppUserIdAndAvatarByPhone(String phone);

}
