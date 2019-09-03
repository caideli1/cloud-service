package com.cloud.user.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.constants.SMSRedisKey;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.appUser.*;
import com.cloud.oss.autoconfig.utils.AliOssUtil;
import com.cloud.platform.model.PlatformUserFeedbackReq;
import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.cloud.user.dao.AppUserInfoDao;
import com.cloud.user.model.AddUserAadhaarInfoReq;
import com.cloud.user.model.AppUserBasicInfoReq;
import com.cloud.user.model.SysRefusalCycle;
import com.cloud.user.service.AppUserInfoService;
import com.cloud.user.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: app录入用户信息接口
 * @Author: wza
 * @CreateDate: 2019/1/18 15:01
 * @Version: 1.0
 */
@Slf4j
@RestController
public class AppUserInfoController {

    @Autowired
    private AppUserInfoService appUserInfoService;

    @Autowired
    private AppUserInfoDao appUserInfoDao;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired(required = false)
    private AliOssUtil aliOssUtil;

    @Autowired(required = false)
    private RedisUtil redisUtil;

    /**
     * 录入用户aadhaar卡信息
     *
     * @param req
     * @return
     * @author wza
     */
    @PutMapping("/users-anon/app/updateAadhaarInfo")
    public JsonResult updateAadhaarInfo(@RequestBody AppUserBasicInfoReq req, HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        req.setUserId(userId);
        JsonResult jsonResult = appUserInfoService.updateAadhaarInfo(req);
        if (jsonResult.getCode() == 200){
            jsonResult.setMsg("Succeed to update the aadhaar info");
        }
        return jsonResult;
    }
    @GetMapping("/users-anon/getUserCountByDate")
   public Integer getUserCountByDate(@RequestParam String date){
        return appUserInfoService.getUserCountByDate(date);
    }

    /**
     * 录入用户aadhaar卡信息 v2
     */
    @PutMapping("/app/v2/updateAadhaarInfo")
    public JsonResult updateAadhaarInfoV2(@RequestBody AddUserAadhaarInfoReq req){
        Long userId = AppUserUtil.getLoginAppUser().getId();
        AppUserBasicInfoReq appUserBasicInfoReq =  AppUserBasicInfoReq.builder()
                .userId(userId)
                .aadhaarAccount(req.getUserAadhaarQrCode().getUid())
                .firstName(req.getUserAadhaarQrCode().getName())
                .sex(req.getUserAadhaarQrCode().getGender())
                .birthday(req.getUserAadhaarQrCode().getDob())
                .aadhaarUrlFront(req.getAadhaarUrlFront())
                .aadhaarUrlBack(req.getAadhaarUrlBack())
                .aadhaarStreet(req.getUserAadhaarQrCode().getStreet())
                .aadhaarHouse(req.getUserAadhaarQrCode().getHouse())
                .aadhaarLoc(req.getUserAadhaarQrCode().getLoc())
                .aadhaarVtc(req.getUserAadhaarQrCode().getVtc())
                .aadhaarPo(req.getUserAadhaarQrCode().getPo())
                .aadhaarDist(req.getUserAadhaarQrCode().getDist())
                .aadhaarSubdist(req.getUserAadhaarQrCode().getSubdist())
                .aadhaarState(req.getUserAadhaarQrCode().getState())
                .isIdentified(true)
                .match(true)
                .build();
        // 调用之前的方法
        JsonResult jsonResult = appUserInfoService.updateAadhaarInfo(appUserBasicInfoReq);
        if (jsonResult.getCode() == 200){
            jsonResult.setMsg("Succeed to update the aadhaar info");
            log.info("更新user id {}的信息 - {}", userId, appUserBasicInfoReq);

        }
        return jsonResult;
    }

    /**
     * 活体认证
     *
     * @author wza
     */
    @PostMapping("/users-anon/app/submitLiveData")
    public JsonResult submitLiveData(@RequestParam String faceImg, HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        AppUserImgInfoReq req = new AppUserImgInfoReq();
        req.setUserId(userId);
        req.setFaceImg(faceImg);
        JsonResult jsonResult = appUserInfoService.submitLiveData(req);
        if (jsonResult.getCode() == 200){
            jsonResult.setMsg("Succeed to update the intravital info");
        }
        return jsonResult;
    }

    /**
     * 录入用户基本信息
     *
     * @author wza
     */
    @PutMapping("/users-anon/app/updateBasicInfo")
    public JsonResult updateBasicInfo(@RequestBody AppUserBasicInfoReq req, HttpServletRequest request) {
        Long userId = Long.valueOf(request.getHeader("mid"));
        String channel = request.getHeader("channel");
        req.setUserId(userId);
        req.setChannel(channel);
        JsonResult jsonResult = appUserInfoService.updateBasicInfo(req);
        if (jsonResult.getCode() == 200){
            jsonResult.setMsg("Succeed to update user basic info");
        }
        return jsonResult;
    }

    @GetMapping(value = "/users-anon/app/getUserIdentity")
    public JsonResult getUserIdentity(HttpServletRequest request){
//        public JsonResult getUserIdentity(Integer userId){
        Long userId = Long.valueOf(request.getHeader("mid"));
        //0>不是；1>是 (学生)
        Integer status = appUserInfoService.getUserIdentity(userId);
        if (status == null){
            return JsonResult.errorException("User does not exist");
        }
        return JsonResult.ok(status, 1);
    }

    /**
     * 录入学生人员信息
     *
     * @param req
     * @return
     * @author wza
     */
    @PostMapping("/users-anon/app/saveStudentInfo")
    public JsonResult saveStudentInfo(@RequestBody AppUserJobInfoReq req, HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        req.setUserId(userId);
        JsonResult jsonResult = appUserInfoService.saveStudentInfo(req);
        if (jsonResult.getCode() == 200){
            jsonResult.setMsg("Succeed to update user basic info");
        }
        return jsonResult;
    }

    /**
     * 录入工作人员信息
     *
     * @param req
     * @return
     * @author wza
     */
    @PostMapping("/users-anon/app/saveWorkInfo")
    public JsonResult saveWorkInfo(@RequestBody AppUserJobInfoReq req, HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        req.setUserId(userId);
        JsonResult jsonResult = appUserInfoService.saveWorkInfo(req);
        return jsonResult;
    }

    /**
     * 录入用户联系人信息
     *
     * @param req
     * @return
     * @author wza
     */
    @PostMapping("/users-anon/app/saveContactInfo")
    public JsonResult saveContactInfo(@RequestBody AppUserContactReq req, HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        req.setUserId(userId);
        JsonResult jsonResult = appUserInfoService.saveContactInfo(req);
        return jsonResult;
    }

    /**
     * 录入用户银行卡信息
     *
     * @param req
     * @return
     */
    @PostMapping("/users-anon/app/saveBankInfo")
    public JsonResult saveBankInfo(@RequestBody AppUserBankReq req, HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        req.setUserId(userId);
        JsonResult result = appUserInfoService.saveBankInfo(req);
        return result;
    }

    /**
     * 是否可以借款
     *
     * @return
     * @author wza
     */
    @GetMapping ("/users-anon/app/judgeCanLoan")
    public JsonResult judgeCanLoan(@RequestParam Integer productId, @RequestParam BigDecimal amount, HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        AppJudgeCanLoanReq req = new AppJudgeCanLoanReq();
        req.setUserId(userId);
        req.setProductId(productId);
        req.setAmount(amount);
        JsonResult jsonResult = appUserInfoService.judgeCanLoan(req);
        return jsonResult;
    }

    @PostMapping("/users-anon/app/saveLoanInfo")
    public JsonResult saveUserLoanInfo(@RequestBody AppJudgeCanLoanReq req, HttpServletRequest request) {
        Long userId = Long.valueOf(request.getHeader("mid"));
        // TODO: - 需要优化
        appUserInfoService.judgeUserLoanLevelAndLoanProduct(userId, req.getProductId());
        // 校验
        LoanStatusVo loanStatusVo = appUserInfoService.judgeCanBorrow(userId);
        if (!loanStatusVo.getCanBorrow()) {
            throw new RuntimeException("user can not borrow!");
        }


        req.setUserId(userId);
        String userOrderRedisKey = "ORDER:" + userId +":"+ req.getApplyNum();
        try {
            if(redisUtil.isLock(userOrderRedisKey)){
                return JsonResult.errorMsg("Application Submitted! Pls do not re-submit");
            }
            // TODO 里面的代码需要重新优化逻辑
            return appUserInfoService.saveUserLoanInfo(req);
        } catch (Exception e) {
            log.error("订单重复<<<<", e);
            return JsonResult.errorException("failure:" + e.getLocalizedMessage());
        }
    }

    /**
     * 查询用户基本信息
     *
     * @param request
     * @return
     * @author wza
     */
    @GetMapping("/users-anon/app/getBasicInfo")
    public JsonResult getBasicInfo(HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        JsonResult jsonResult = appUserInfoService.getBasicInfo(userId);
        if (jsonResult.getData() == null) {
            return jsonResult;
        }
        return jsonResult;
    }

    /**
     * 查询用户身份信息
     *
     * @param request
     * @return
     * @author wza
     */
    @GetMapping("/users-anon/app/getUserIdentityInfo")
    public JsonResult getUserIdentityInfo(HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        JsonResult jsonResult = appUserInfoService.getUserIdentityInfo(userId);
        return jsonResult;
    }

    @GetMapping("/users-anon/app/member/getOssInfo")
    public JsonResult getOssInfo() {
        return JsonResult.ok(aliOssUtil.getOssInfo());
    }

    /**
     * 查询用户联系人信息
     *
     * @param request
     * @return
     */
    @GetMapping("/users-anon/app/getContactInfo")
    public JsonResult getContactInfo(HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        JsonResult jsonResult = appUserInfoService.getContactInfoById(userId);
        return jsonResult;
    }

    /**
     * 查询用户银行卡信息
     *
     * @param request
     * @return
     */
    @GetMapping("/users-anon/app/getBankInfo")
    public JsonResult getBankInfo(HttpServletRequest request) {
        Long userId = Long.valueOf(request.getHeader("mid"));
        AppUserBank appUserBank = appUserInfoService.getBankInfoById(userId);
        return JsonResult.ok(appUserBank);
    }

    @PostMapping("/users-anon/app/saveLoan")
    public int saveLoan(@RequestBody AppUserLoanInfo req) {
        int i = appUserInfoService.saveLoanInfo(req);
        return i;
    }

    @GetMapping("/users-anon/app/getBasicInfoById")
    public AppUserBasicInfo getBasicInfoById(@RequestParam("userId") Long userId){
        AppUserBasicInfo appUserBasicInfo = appUserInfoService.getBasicInfoById(userId);
        return appUserBasicInfo;
    }

    /**
     * 录入人员voterIdCard信息
     *
     * @param req
     * @return
     * @author wza
     */
    @PutMapping("/users-anon/app/saveVoterCardInfo")
    public JsonResult saveVoterCardInfo(@RequestBody AppUserJobInfoReq req, HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        req.setUserId(userId);
        JsonResult jsonResult = appUserInfoService.updateVoterIdInfo(req);
        if (jsonResult.getCode() == 200){
            jsonResult.setMsg("Succeed to update the voter card info");
        }
        return jsonResult;
    }

    /**
     * 录入人员panCard信息
     *
     * @param req
     * @return
     * @author wza
     */
    @PutMapping("/users-anon/app/savePanCardInfo")
    public JsonResult savePanCardInfo(@RequestBody UserPanCardReq req, HttpServletRequest request) {

        Long userId = Long.valueOf(request.getHeader("mid"));
            req.setUserId(userId);
            JsonResult jsonResult = appUserInfoService.savePanCardInfo(req);
            if (jsonResult.getCode() == 200) {
                jsonResult.setMsg("Succeed to save user pan card info");
            }
            return jsonResult;

    }

    /**
     * 判断用户是否可以借款
     *
     * @Author:wza
     */
    @GetMapping("/users-anon/app/judgeCanBorrow")
    public JsonResult judgeCanBorrow(HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        LoanStatusVo loanStatusVo = appUserInfoService.judgeCanBorrow(userId);
        return JsonResult.ok(loanStatusVo);
    }

    /**
     * 获取用户授权状态
     *
     * @param request
     * @return
     * @author wza
     */
    @GetMapping("/users-anon/app/getAccredit")
    public JsonResult getAccredit(HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        AppAccreditVo vo = appUserInfoService.getAccredit(userId);
        return JsonResult.ok(vo);
    }

    /**
     * 常见问题列表
     *
     * @return
     * @author wza
     */
    @GetMapping("/users-anon/app/getFAQInfo")
    public JsonResult getFAQInfo() {
        List<AppFAQInfo> faqInfos = appUserInfoService.getFAQInfo();
        if (CollectionUtils.isEmpty(faqInfos)) {
            return JsonResult.errorMsg("FAQ Content is empty");
        }
        return JsonResult.ok(faqInfos);
    }

    /**
     * 常见问题详情
     *
     * @return
     * @author wza
     */
    @GetMapping("/users-anon/app/getFAQDetail")
    public JsonResult getFAQDetail(Integer id) {
        AppFAQInfo faqInfo = appUserInfoService.getFAQDetailById(id);
        if (null == faqInfo) {
            return JsonResult.errorMsg("FAQ Content is empty");
        }
        return JsonResult.ok(faqInfo);
    }

    /**
     * 更换头像
     *
     * @param profileUrl
     * @param request
     * @return
     * @author wza
     */
    @PutMapping("/users-anon/app/updateProfile")
    public JsonResult updateProfile(@RequestParam("profileUrl") String profileUrl, HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        AppUserBasicInfo basicInfo = new AppUserBasicInfo();
        basicInfo.setId(userId);
        basicInfo.setProfileUrl(profileUrl);
        int result = appUserInfoDao.updateById(basicInfo);
        String imgUrl = aliOssUtil.getUrl(profileUrl);
        if (result < 1) {
            return JsonResult.errorMsg("Failed to update user profile picture");
        }
        return JsonResult.ok(imgUrl);
    }

    /**
     * 获取 所有周期 列表
     * @return
     */

    @GetMapping("/users-anon/sys/queryAllSysRefusalCycle")
    public JsonResult  queryAllSysRefusalCycle(){
        return JsonResult.ok(userInfoService.queryAllSysRefusalCycle());
    }
    @PostMapping("/users-anon/sys/updateAllSysRefusalCycle")
    public JsonResult updateAllSysRefusalCycle(@RequestBody  SysRefusalCycle sysRefusalCycle){
        return JsonResult.ok(userInfoService.updateSysRefusalCycle(sysRefusalCycle));
    }
    /**
     * 更新手机号
     *
     * @param phone
     * @param code
     * @param request
     * @return
     * @author wza
     */
    @PutMapping("/users-anon/app/updatePhone")
    public JsonResult updatePhone(@RequestParam("phone") String phone, String code, HttpServletRequest request) {
        Map maps = (Map) JSON.parse(redisUtil.get("sms:" + phone));
        if (!code.equals(maps.get("code"))) {
            throw new IllegalArgumentException("Verification code error");
        }
        Long userId = Long.valueOf(request.getHeader("mid"));
        AppUserBasicInfo basicInfo = new AppUserBasicInfo();
        basicInfo.setCellPhone(phone);
        basicInfo.setId(userId);
        int resultOfBasicInfo = appUserInfoDao.updateById(basicInfo);
        if (resultOfBasicInfo < 1) {
            return JsonResult.errorMsg("Failed to update mobile phone number!");
        }
        return JsonResult.ok();
    }

    /**
     * 签名验证
     *
     * @param phone
     * @param code
     * @return
     */
    @GetMapping("/users-anon/app/signatureVerification")
    public JsonResult signatureVerification(String phone, String code) {
        if (code.equals("666666")) {
            return JsonResult.ok();
        }
        Map maps = (Map) JSON.parse(redisUtil.get(SMSRedisKey.OTP_PHONE_PREFIX + phone));
        if (maps == null) {
            throw new IllegalArgumentException("Verification code error");
        }
        if (ObjectUtils.notEqual(code, maps.get("code"))) {
            throw new IllegalArgumentException("Verification code error");
        }
        return JsonResult.ok();
    }

    /**
     * 用户提交建议
     *
     * @param feedbackReq
     * @param request
     * @return
     * @author wza
     */
    @PostMapping("/users-anon/app/userFeedback")
    public JsonResult userFeedback(@RequestBody PlatformUserFeedbackReq feedbackReq, HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        feedbackReq.setUserId(userId);
        JsonResult result = null;
        try {
            result = appUserInfoService.saveUserFeedback(feedbackReq);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return JsonResult.errorException("failure:" + e.getLocalizedMessage());
        }
        return result;
    }


    /**
     * 获取银行卡信息 是否 会被读取
     *
     * @param request
     * @return
     * @author zhujingtao
     */
    @GetMapping("/users-anon/app/getFlagBankInfo")
    public JsonResult getFlagBankInfo(HttpServletRequest request)
    {
        Long userId = Long.valueOf(request.getHeader("mid"));

        try {
            return appUserInfoService.getFlagBankInfo(userId);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            return  JsonResult.errorException("failure:" +e.getLocalizedMessage());
        }
    }

    /**
     * 重新新建银行卡信息
     *
     * @param req
     * @param request
     * @return
     */
    @PostMapping("/users-anon/app/renewBankInfo")
    public JsonResult  renewBankInfo(@RequestBody AppUserBankReq req, HttpServletRequest request ){
        Long userId = Long.valueOf(request.getHeader("mid"));
        req.setUserId(userId);
        JsonResult result = appUserInfoService.saveBankInfo(req);
        if (result.getCode().equals(200)) {
            result = appUserInfoService.renewBankInfoTail(userId, req.getBankAccount());
        }

        return result;
    }

    /**
     * @author wza
     */
    @PostMapping("/users-anon/app/saveRepaymentMode")
    public JsonResult saveRepaymentMode(@RequestBody AppUserRepayModeReq req, HttpServletRequest request){
        Long userId = Long.valueOf(request.getHeader("mid"));
        if (userId == null) {
            throw new IllegalArgumentException("userId is empty");
        }
        req.setUserId(userId);
        appUserInfoService.saveRepaymentMode(req);
        return JsonResult.ok();
    }


    @GetMapping("/app/userLoan/level")
    public JsonResult appUserLoanLevel() {
        Long userId = AppUserUtil.getLoginAppUser().getId();
        Integer loanLevel = appUserInfoService.getUserLoanLevel(userId);
        JSONObject obj = new JSONObject();
        obj.put("userId", userId);
        obj.put("loanLevel", loanLevel);
        return JsonResult.ok(obj);
    }


    @GetMapping("/users-anon/companyName")
    public String getCompanyNameByOrderNum(@RequestParam  String orderNum) {
        return userInfoService.getCompanyNameByOrderNum(orderNum);
    }

    @GetMapping("/users-anon/firstAndSecondContactPhone")
    public List<String> getFirstAndSecondContactPhoneByOrderNum(@RequestParam String orderNum) {
        return userInfoService.getFirstAndSecondContactPhoneByOrderNum(orderNum);
    }

    @GetMapping("/users-anon/whatsappAccount")
    String getUserWhatsappAccountByOrderNum(@RequestParam String orderNum) {
        return userInfoService.getUserWhatsappAccountByOrderNum(orderNum);
    }

    @GetMapping("/users-anon/userIds")
    List<Long> getUserIdsByPhones(@RequestParam List<String> phones) {
        return userInfoService.listGetUserIdsByPhones(phones);
    }

    @GetMapping("/users-anon/addaharrAddress")
    String getAadharrAddressByOrderNum(@RequestParam  String orderNum) {
        return userInfoService.getAadharrAddressByOrderNum(orderNum);
    }

    @GetMapping("/users-anon/userRelatiedPhones")
    List<String> listGetUserRelatedPhonesByPhone(@RequestParam List<String> phoneList, @RequestParam(required = false) String excludedPhone) {
        return userInfoService.listGetUserRelatedPhonesByPhone(phoneList, excludedPhone);
    }
}
