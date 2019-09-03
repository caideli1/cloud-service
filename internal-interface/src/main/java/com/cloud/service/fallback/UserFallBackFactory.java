package com.cloud.service.fallback;

import com.cloud.common.dto.JsonResult;
import com.cloud.dto.RiskKudosAccountNonSummary;
import com.cloud.model.appUser.*;
import com.cloud.model.cibil.CibilIdSegmentDto;
import com.cloud.model.kudosCibil.CreditreportEntity;
import com.cloud.model.kudosCibil.EnquiryEntity;
import com.cloud.model.user.*;
import com.cloud.service.feign.user.UserClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户中心服务降级类
 *
 * @author danquan.miao
 * @date 2019/7/24 0024
 * @since 1.0.0
 */
@Component
public class UserFallBackFactory implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {

            @Override
            public List<Long> getUserIdByAddressItem(String state, String district) {
                return null;
            }

            @Override
            public Integer getUserCountByDate(String date) {
                return null;
            }

            @Override
            public UserInfo getUserInfoByUserId(Long userId) {
                return null;
            }



            @Override
            public List<UserAddress> getUserAddressList(Long userId, Integer type) {
                return null;
            }

            @Override
            public List<UserInfo> getUserAccountByAadhaarAccount(String aadhaarAccount) {
                return null;
            }

            @Override
            public List<UserInfoExpand> getUserAccountByPancardAccount(String pancardAccount) {
                return null;
            }

            @Override
            public int getCompanyPhoneCountWhenSameName(String orderNo) {
                return 0;
            }

            @Override
            public int getCompanyNameCountWhenSamePhone(String orderNo) {
                return 0;
            }

            @Override
            public int getRelativePhoneAsApplyPhoneCount(long userId) {
                return 0;
            }

            @Override
            public int getRelativePhoneAsContactPhoneCount(long userId) {
                return 0;
            }

            @Override
            public List<AppUserAddressInfo> getUserAddressByUserId(String userId) {
                return null;
            }

            @Override
            public void kudosCibil(String orderNo) {

            }

            @Override
            public String getUserCibleScoreByOrderNo(String orderNo) {
                return null;
            }

            @Override
            public List<CibilIdSegmentDto> getUserIdSegmentListByOrderNoAndIdType(String orderNum, String idType) {
                return null;
            }

            @Override
            public List<EnquiryEntity> queryEnquiryListByOrderNo(String orderNo) {
                return null;
            }

            @Override
            public List<RiskKudosAccountNonSummary> getRiskKudosAccountNonSummaryList(String orderNo) {
                return null;
            }

            @Override
            public CreditreportEntity getCreditreportInfo(String orderNo) {
                return null;
            }

            @Override
            public int saveLoan(AppUserLoanInfo req) {
                return 0;
            }

            @Override
            public AppUserBasicInfo getBasicInfoById(Long userId) {
                return null;
            }

            @Override
            public UserBankCard getBankCardByUserId(Long userId) {
                return null;
            }

            @Override
            public UserInfo getUserInfo(Long userId) {
                return null;
            }

            @Override
            public String getContractTemplate(String contractName) {
                return null;
            }

            @Override
            public Map<String, String> getUserIdAndRelation(Map<String, Object> params) {
                return null;
            }

            @Override
            public UserContact getContactById(String contractId) {
                return null;
            }

            @Override
            public LoginAppUser getLoginUserByPhone(String phone) {
                return null;
            }

            @Override
            public JsonResult queryAllSysRefusalCycle() {
                return null;
            }

            @Override
            public AppUserLoanInfo getUserLoanByOrderNo(String orderNo) {
                return null;
            }

            @Override
            public AppUserBasicInfo getAppUserIdAndAvatarByPhone(String phone) {
                return null;
            }

            @Override
            public UserPanCardModel getPancardByUserId(Long userId) {
                return null;
            }

            @Override
            public JsonResult addUserDataStatistics(Long userId, String loginImi, String channel) {
                return null;
            }

            @Override
            public void updateBankCardInfo(String bankAccount, String ifsc, String verifyReturnName) {

            }
            @Override

            public String getCompanyNameByOrderNum(String orderNum) {
                return null;
            }
            @Override

            public List<String> getFirstAndSecondContactPhoneByOrderNum(String orderNum) {
                return null;
            }

            @Override
            public String getUserWhatsappAccountByOrderNum(String orderNum) {
                return null;
            }

            @Override
            public List<Long> listGetUserIdsByPhones(List<String> phones) {
                return null;
            }

            @Override
            public String getAadharrAddressByOrderNum(String orderNum) {
                return null;
            }

            @Override
            public List<String> listGetUserRelatedPhonesByPhone(List<String> phoneList, String excludedPhone) {
                return null;
            }


        };
    }
}
