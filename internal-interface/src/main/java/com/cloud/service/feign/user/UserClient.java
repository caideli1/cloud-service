package com.cloud.service.feign.user;


import com.cloud.common.dto.JsonResult;
import com.cloud.dto.RiskKudosAccountNonSummary;
import com.cloud.model.appUser.*;
import com.cloud.model.cibil.CibilIdSegmentDto;
import com.cloud.model.kudosCibil.CreditreportEntity;
import com.cloud.model.kudosCibil.EnquiryEntity;
import com.cloud.model.user.*;
import com.cloud.service.fallback.UserFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@FeignClient(name = "user-center", fallbackFactory = UserFallBackFactory.class)
public interface UserClient {

    @GetMapping("/users-anon/getBasicInfoById")
    UserInfo getUserInfoByUserId(@RequestParam("userId") Long userId);
    @GetMapping("/users-anon/getUserIdByAddressItem")
   List<Long> getUserIdByAddressItem(@RequestParam  String state, @RequestParam  String district);
    @GetMapping("/users-anon/getUserAddressById")
    List<UserAddress> getUserAddressList(@RequestParam Long userId, @RequestParam Integer type);
    @GetMapping("/users-anon/getUserCountByDate")
    Integer getUserCountByDate(@RequestParam String date);
    @GetMapping("/users-anon/getUserAccountByAadhaarAccount")
    List<UserInfo> getUserAccountByAadhaarAccount(@RequestParam String aadhaarAccount);

    @GetMapping("/users-anon/getUserAccountByPancardAccount")
    List<UserInfoExpand> getUserAccountByPancardAccount(@RequestParam String pancardAccount);

    /**
     * @Description: 同单位名称匹配不同单位电话个数
     */
    @GetMapping("/users-anon/companyPhoneCountInSameName")
    int getCompanyPhoneCountWhenSameName(@RequestParam("orderNo") String orderNo);

    /**
     * @Description: 同单位电话匹配不同单位名称个数
     */
    @GetMapping("/users-anon/companyNameCountInSamePhone")
    int getCompanyNameCountWhenSamePhone(@RequestParam("orderNo") String orderNo);


    /**
     * @Description: 亲属联系人号码被用作申请号码个数
     */
    @GetMapping("/users-anon/relativePhoneAsApplyPhoneCount")
    int getRelativePhoneAsApplyPhoneCount(@RequestParam("userId") long userId);

    /**
     * @Description: 亲属联系人号码被用作联系人号码个数
     */
    @GetMapping("/users-anon/relativePhoneAsContactPhoneCount")
    int getRelativePhoneAsContactPhoneCount(@RequestParam("userId") long userId);

    @GetMapping("/users-anon/getUserAddressByOrderNum")
    List<AppUserAddressInfo> getUserAddressByUserId(@RequestParam("userId") String userId);
    
    //调用第三方cibil接口
    @GetMapping("/users-anon/cibilInfo")
	void kudosCibil(@RequestParam("orderNo") String orderNo);

    @GetMapping("/users-anon/cibil/score")
    String getUserCibleScoreByOrderNo(@RequestParam("orderNo") String orderNo);

    @GetMapping("/users-anon/cibil/idSegmentList")
    List<CibilIdSegmentDto> getUserIdSegmentListByOrderNoAndIdType(@RequestParam("orderNo") String orderNum, @RequestParam("idType") String idType);

    @GetMapping("/users-anon/cibil/queryEnquiryListByOrderNo")
    List<EnquiryEntity> queryEnquiryListByOrderNo(@RequestParam("orderNo") String orderNo);


    @GetMapping("/users-anon/cibil/kudosAccountNonSummaryList")
    List<RiskKudosAccountNonSummary> getRiskKudosAccountNonSummaryList(@RequestParam("orderNo") String orderNo);


    @GetMapping("/users-anon/cibil/getCreditreportInfo")
    CreditreportEntity getCreditreportInfo(@RequestParam("orderNo") String orderNo);

    @PostMapping("/users-anon/app/saveLoan")
    int saveLoan(@RequestBody AppUserLoanInfo req);

    @GetMapping("/users-anon/app/getBasicInfoById")
    AppUserBasicInfo getBasicInfoById(@RequestParam("userId") Long userId);

    //获得用户银行卡
    @GetMapping("/users-anon/payout/getBankCard")
    UserBankCard getBankCardByUserId(@RequestParam("userId") Long userId);

    //获得用户信息
    @GetMapping("/users-anon/payout/getUserInfo")
    UserInfo getUserInfo(@RequestParam("userId") Long userId);

    @GetMapping("/users-anon/contract/getContractTemplate")
    String getContractTemplate(@RequestParam("contractName") String contractName);

    @PostMapping("/users-anon/getUserIdAndRelation")
    Map<String,String>  getUserIdAndRelation(@RequestBody Map<String,Object> params);

    @GetMapping("/users-anon/getContactById")
    UserContact getContactById(@RequestParam("contractId") String  contractId);

    @GetMapping("/users-anon/app/getLoginUserByPhone")
    LoginAppUser getLoginUserByPhone(@RequestParam String phone);

    @GetMapping("/users-anon/sys/queryAllSysRefusalCycle")
    JsonResult queryAllSysRefusalCycle();

    @GetMapping("/users-anon/getUserLoanByOrderNo")
    AppUserLoanInfo getUserLoanByOrderNo(@RequestParam String orderNo);

    @GetMapping("/users-anon/app/getAppUserIdAndAvatarByPhone")
    AppUserBasicInfo getAppUserIdAndAvatarByPhone(@RequestParam String phone);

    @GetMapping("/users-anon/getPancardByUserId")
    UserPanCardModel getPancardByUserId(@RequestParam Long userId);

    @PostMapping("/users-anon/app/userDataStatistics")
    JsonResult addUserDataStatistics(@RequestParam Long userId, @RequestParam String loginImi, @RequestParam String channel);

    /**
     * 更新银行卡信息
     * @param bankAccount
     * @param ifsc
     * @param verifyReturnName
     */
    @PostMapping("/users-anon/updateBankCardInfo")
    void updateBankCardInfo(@RequestParam String bankAccount, @RequestParam String ifsc, @RequestParam String verifyReturnName);

    @GetMapping("/users-anon/companyName")
    String getCompanyNameByOrderNum(@RequestParam String orderNum);

    @GetMapping("/users-anon/firstAndSecondContactPhone")
    List<String> getFirstAndSecondContactPhoneByOrderNum(@RequestParam String orderNum);

    @GetMapping("/users-anon/whatsappAccount")
    String getUserWhatsappAccountByOrderNum(@RequestParam String orderNum);

    @GetMapping("/users-anon/userIds")
    List<Long> listGetUserIdsByPhones(@RequestParam List<String> phones);

    @GetMapping("/users-anon/addaharrAddress")
    String getAadharrAddressByOrderNum(@RequestParam String orderNum);

    @GetMapping("/users-anon/userRelatiedPhones")
    List<String> listGetUserRelatedPhonesByPhone(@RequestParam List<String> phoneList, @RequestParam(required = false) String excludedPhone);

}
