package com.cloud.user.dao;

import com.cloud.model.appUser.AppUserLoanInfo;
import com.cloud.model.product.OrderPartRateAmount;
import com.cloud.model.product.OrderRepayPart;
import com.cloud.model.user.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

/**
 * @author yoga
 * @Description: UserDao
 */
@Mapper
public interface UserDao {

    UserInfo getUserInfoById(Long id);
    
    @Select("select * from user_bank_card where user_id=#{userId} order by binding_time desc limit 1")
    UserBankCard  getBankCardByUserId(Long userId);

    @Select("select *  from   user_contact where  id= #{contactId}")
    UserContact getContactById(@Param("contactId")String contactId);

    List<UserInfo> getUserInfoList(@Param("id") String id,
                                   @Param("phone") String phone,
                                   @Param("aadhaar") String aadhaar,
                                   @Param("startTime") String startTime,
                                   @Param("endTime") String endTime,
                                   @Param("status") Integer status);

    UserLoan getUserLoanById(Integer id);

    List<UserLoan> getUserLoanList(@Param("loanNumber") String loanNumber,
                                   @Param("userId") String userId,
                                   @Param("phone") String phone,
                                   @Param("handlerType") Integer handlerType,
                                   @Param("loanStatus") Integer loanStatus,
                                   @Param("startTime") String startTime,
                                   @Param("endTime") String endTime);

    List<UserAddress> getUserAddressList(@Param("userId") long userId,
                                         @Param("type") int type);

    List<Long> getUserIdByAddressItem(@Param("state") String state,@Param("district") String district);
    List<UserInfo> getUserAccountByAadhaarAccount(@Param("aadhaarAccount") String aadhaarAccount);

    List<UserInfoExpand> getUserAccountByPancardAccount(@Param("pancardAccount") String pancardAccount);

    int getCompanyNameCountWhenSamePhone(@Param("orderNo") String orderNo);

    int getCompanyPhoneCountWhenSameName(@Param("orderNo") String orderNo);

    int getRelativePhoneAsContactPhoneCount(@Param("userId") long userId);

    int getRelativePhoneAsApplyPhoneCount(@Param("userId") long userId);

    Map<String, Object> getUserCreditExtension(@Param("userId") long userId);

    @Select("SELECT id, cell_phone, `password` FROM user_info WHERE cell_phone = #{phone} ")
    LoginAppUser getUserInfoByPhone(String phone);

    @Insert("UPDATE user_info SET `password` = #{password} WHERE cell_phone = #{phone}")
    int insertUserInfoPassword(String phone, String password);

    /**
     * @Description: 历史罚息总额 + 当前罚息总额 + 次数
     */
    Map<String, Object> getUserTotalDueAmount(@Param("userId") long userId);

    List<Map<String, Object>> getUserDueInfoList(@Param("userId") long userId, @Param("orderNo") String orderNo);

    List<Map<String, Object>> getUserOrderExtensionInfoList(@Param("orderNo") String orderNo);

    Map<String, Object> userLoanAmountComposition(@Param("orderNo") String orderNo);

    OrderRepayPart userLoanRepayComposition(@Param("orderNo") String orderNo);

    List<OrderPartRateAmount> userLoanRepayCompositionRateInfoList(@Param("orderNo") String orderNo);

    List<String>  queryRelationUserIdByMobile(@Param("mobile")String mobile);

    List<String> queryRelationUserIdByUserId(@Param("userId") String userId);


    @Select("select  *  from   user_loan where loan_number=#{orderNo} ")
    AppUserLoanInfo getUserLoanByOrderNo(@Param("orderNo") String orderNo);

    @Select("SELECT company_name FROM user_info_history WHERE order_no = #{orderNum}")
    String queryCompanyNameByOrderNum(String orderNum);

    @Select("SELECT uc.`phone` FROM user_info_history uih JOIN user_contact uc ON uc.id=uih.second_contact_id OR uc.id=uih.first_contact_id WHERE order_no=#{orderNum}")
    List<String> queryFirstAndSecondContactPhoneByOrderNum(String orderNum);

    @Select("SELECT whatsapp_account FROM user_info_history WHERE WHERE order_no = #{orderNum}")
    String queryUserWhatsappAccountByOrderNum(String orderNum);

    List<Long> listQueryUserIdsByPhones(List<String> ids);

    @Select("SELECT ua.address_detail FROM user_info_history uih JOIN user_address ua ON ua.id=uih.home_address_id WHERE uih.order_no=#{orderNo}")
    String queryAadharrAddressByOrderNum(String orderNum);

    List<String> listQueryUserPhoneAsContactByPhone(@Param("phoneList") List<String> phoneList, @Param("excludedPhone")String excludedPhone);

    List<String> listQueryUserContactPhoneByPhone(@Param("phoneList") List<String> phoneList, @Param("excludedPhone")String excludedPhone);
}
