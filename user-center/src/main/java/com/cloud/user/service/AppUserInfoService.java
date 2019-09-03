package com.cloud.user.service;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.appUser.*;
import com.cloud.platform.model.PlatformUserFeedbackReq;
import com.cloud.user.model.AppUserBasicInfoReq;

import java.util.Date;
import java.util.List;

public interface AppUserInfoService {
    /**
     * 根据id更新用户基本信息
     * @param appUserBasicInfoReq
     * @return
     */
    JsonResult updateBasicInfo(AppUserBasicInfoReq appUserBasicInfoReq);
    /**
     * 保存活体认证数据
     * @param req
     * @return
     */
    JsonResult submitLiveData(AppUserImgInfoReq req);
    Integer getUserCountByDate(String date);

    /**
     * 根据id更新用户aadhaar卡信息
     * @param req
     * @return
     */
    JsonResult updateAadhaarInfo(AppUserBasicInfoReq req);

    /**
     * 保存用户（学生）信息
     * @param req
     * @return
     */
    JsonResult saveStudentInfo(AppUserJobInfoReq req);

    /**
     * 获取用户身份
     * 1：学生 0：工作人员
     * @param id
     * @return
     */
    Integer getUserIdentity(Long id);

    /**
     * 保存工作人员信息
     * @param req
     * @return
     */
    JsonResult saveWorkInfo(AppUserJobInfoReq req);

    /**
     * 保存联系人信息
     * @param req
     * @return
     */
    JsonResult saveContactInfo(AppUserContactReq req);

    /**
     * 保存用户银行卡信息
     * @param req
     * @return
     */
    JsonResult saveBankInfo(AppUserBankReq req);

    /**
     * 判断用户是否能借款;回显产品详情
     * @param req
     * @return
     */
    JsonResult judgeCanLoan(AppJudgeCanLoanReq req);


    JsonResult saveUserLoanInfo(AppJudgeCanLoanReq req);
    
    Long registerUserInfo(AppUserBasicInfo appUser);

    void saveUserDataStatistics(Long userId, String loginImi, String channel);

    JsonResult getBasicInfo(Long userId);

    AppUserBasicInfo getAppUserIdAndAvatarByPhone(String phone);

    JsonResult getUserIdentityInfo(Long userId);

    JsonResult getContactInfoById(Long userId);

    AppUserBank getBankInfoById(Long userId);

    int saveLoanInfo(AppUserLoanInfo req);

    AppUserBasicInfo getBasicInfoById(Long userId);

    JsonResult updateVoterIdInfo(AppUserJobInfoReq req);

    JsonResult savePanCardInfo(UserPanCardReq req);

    LoanStatusVo judgeCanBorrow(Long userId);

    AppAccreditVo getAccredit(Long userId);

    List<AppFAQInfo> getFAQInfo();

    AppFAQInfo getFAQDetailById(Integer id);

    JsonResult saveUserFeedback(PlatformUserFeedbackReq feedbackReq);

    String strTokudos7(String orderNo);
    /**
     * 获取 银行卡跟新信息
     * @param userId
     * @return
     */
    JsonResult getFlagBankInfo(Long userId);

    /**
     * 更新银行卡信息
     * @param userId
     * @return
     */
    JsonResult renewBankInfoTail(Long userId, String bankCode);

    /**
     * 保存用户还款方式
     * @param req
     * @return
     */
    void saveRepaymentMode(AppUserRepayModeReq req);

    Boolean isAppUserRegistered(String phone);

    Boolean isSetPassword(String phone);

    Integer getUserLoanLevel(Long userId);

    void judgeUserLoanLevelAndLoanProduct(Long userId, Integer productId);

}
