package com.cloud.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.component.utils.DateUtil;
import com.cloud.common.component.utils.key.SnowflakeIdWorker;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.enums.FunctionStatusEnum;
import com.cloud.common.enums.NoOffStatusEnum;
import com.cloud.common.utils.PhoneUtil;
import com.cloud.model.appUser.*;
import com.cloud.model.cibil.KudosParamEntity;
import com.cloud.model.cibil.LoanCIBILEntity;
import com.cloud.model.common.CheckStatus;
import com.cloud.model.order.OrderOntherRefuse;
import com.cloud.model.product.InterestPenaltyModel;
import com.cloud.model.product.LoanProductModel;
import com.cloud.model.product.RateType;
import com.cloud.model.product.constant.ProductConstants;
import com.cloud.model.risk.RiskExecuteRequestDto;
import com.cloud.model.risk.dto.AppBaseDto;
import com.cloud.model.user.UserAddress;
import com.cloud.model.user.UserDataStatisModel;
import com.cloud.model.user.UserSalary;
import com.cloud.mq.sender.RiskExecuteSender;
import com.cloud.oss.autoconfig.utils.AliOssUtil;
import com.cloud.platform.model.PlatformUserFeedbackModel;
import com.cloud.platform.model.PlatformUserFeedbackReq;
import com.cloud.service.feign.backend.BackendClient;
import com.cloud.service.feign.order.OrderClient;
import com.cloud.service.feign.pay.PayClient;
import com.cloud.user.config.KudosConfig;
import com.cloud.user.dao.*;
import com.cloud.user.model.AppUserBasicInfoReq;
import com.cloud.user.model.AppUserInfoHistoryEntity;
import com.cloud.user.model.AppUserRepayModeEntity;
import com.cloud.user.model.SysRefusalCycle;
import com.cloud.user.service.AppUserInfoService;
import com.cloud.user.service.KudosService;
import com.cloud.user.service.UserInfoService;
import com.cloud.user.utils.ImageToPdf;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AppUserInfoServiceImpl implements AppUserInfoService {

    @Autowired
    private AppUserInfoDao appUserInfoDao;

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private TaskExecutor taskExecutor;

    public static String FILE_BASE_URL = "/data/jpgImage/temp";

    @Autowired
    private CibilOrderDao orderDao;

    @Autowired
    private KudosService kudosService;

    @Autowired
    private KudosDao kudosDao;

    @Autowired
    private IfscDao ifscDao;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RiskExecuteSender riskExecuteSender;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired(required = false)
    private AliOssUtil aliOssUtil;

    @Autowired(required = false)
    private KudosConfig kudosConfig;

    @Autowired
    private UserPanCardDao userPanCardDao;

    @Autowired(required = false)
    private BackendClient backendClient;

    @Autowired(required = false)
    private PayClient payClient;

    /**
     * 保存用户Aadhaar卡账号信息
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult updateAadhaarInfo(AppUserBasicInfoReq req) {
        if (!req.getIsIdentified()) {
            if (StringUtils.isBlank(req.getAadhaarAccount())) {
                return JsonResult.errorException("Pls input your aadhaar ID");
            }
            if (StringUtils.isBlank(req.getAadhaarUrlFront())) {
                return JsonResult.errorException("Front photo of your aadhaar can’t be empty");
            }
            if (StringUtils.isBlank(req.getAadhaarUrlBack())) {
                return JsonResult.errorException("Back photo of your aadhaar can’t be empty");
            }
            if (StringUtils.isBlank(req.getFirstName())) {
                return JsonResult.errorException("Pls input the user’s real name");
            }

            if (StringUtils.isBlank(req.getBirthday())) {
                return JsonResult.errorException("Birth date can’t be empty");
            }
        }
        AppUserBasicInfo appUserBasicInfo = new AppUserBasicInfo();
        BeanUtils.copyProperties(req, appUserBasicInfo);
        appUserBasicInfo.setId(req.getUserId());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date birthday = null;
        Boolean isParse = true;
        try {
            birthday = sdf.parse("01/01/1970");
            if (StringUtils.isNotEmpty(req.getBirthday())) {
                birthday = sdf.parse(req.getBirthday());
            }
        } catch (ParseException e) {
            isParse = false;
            log.info(e.getMessage() + "<<<<<<<<<<<<<<<<<<<<<<");
        } finally {
            if (!isParse) {
                try {
                    birthday = format.parse(req.getBirthday());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            appUserBasicInfo.setBirthday(birthday);
        }

        if (!req.getIsIdentified()) {
            appUserBasicInfo.setIsIdentified(0);
        } else {
            appUserBasicInfo.setIsIdentified(1);
        }
        if (!req.getMatch()) {
            appUserBasicInfo.setMatch(0);
        } else {
            appUserBasicInfo.setMatch(1);
        }
        if(req.getSex()==null){
            appUserBasicInfo.setSex(0);
        }else if (req.getSex().equals("F")) {
            appUserBasicInfo.setSex(1);
        } else {
            appUserBasicInfo.setSex(0);
        }

        String aadFrontPdf = FILE_BASE_URL + "/" + req.getAadhaarUrlFront().substring(0, req.getAadhaarUrlFront().length() - 3) + "pdf";
        String aadBackPdf = FILE_BASE_URL + "/" + req.getAadhaarUrlBack().substring(0, req.getAadhaarUrlBack().length() - 3) + "pdf";

        File fileOfAadFront = new File(aadFrontPdf);
        File fileOfAadBack = new File(aadBackPdf);
        // 判断路径是否存在，如果不存在就创建一个
        if (!fileOfAadFront.getParentFile().exists() || !fileOfAadBack.getParentFile().exists()) {
            fileOfAadFront.getParentFile().mkdirs();
        } else {
            fileOfAadFront.delete();
            fileOfAadBack.delete();
            fileOfAadFront.getParentFile().mkdirs();
        }

        ArrayList<String> frontJpg = new ArrayList<>(Arrays.asList(aliOssUtil.getUrl(req.getAadhaarUrlFront())));
        ArrayList<String> backJpg = new ArrayList<>(Arrays.asList(aliOssUtil.getUrl(req.getAadhaarUrlBack())));

        File frontPdf = ImageToPdf.Pdf(frontJpg, aadFrontPdf);
        File backPdf = ImageToPdf.Pdf(backJpg, aadBackPdf);

        String imgUrlOfFront = aliOssUtil.uploadObject2OSS(frontPdf, "pdf/aadhaar/");
        String imgUrlOfBack = aliOssUtil.uploadObject2OSS(backPdf, "pdf/aadhaar/");

        appUserBasicInfo.setAadhaarPdfFront(imgUrlOfFront);
        appUserBasicInfo.setAadhaarPdfBack(imgUrlOfBack);
        //0:新建用户
//        appUserBasicInfo.setStatus(0);
        int result = appUserInfoDao.updateById(appUserBasicInfo);
        if (result < 1) {
//            return JsonResult.errorMsg("Faild to save the aadhaar information");
            throw new IllegalArgumentException("Faild to save the aadhaar information");
        }
        return JsonResult.ok();
    }

    /**
     * 保存活体认证数据
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult submitLiveData(AppUserImgInfoReq req) {
        Date now = DateUtil.getDate();
        if (null == appUserInfoDao.getByUserId(req.getUserId())) {
            return JsonResult.errorException("User does not exist");
        }
        /*if (StringUtils.isBlank(req.getBlinkImg()) || StringUtils.isBlank(req.getHeadImg()) || StringUtils.isBlank(req.getMouthImg()) || StringUtils.isBlank(req.getNodImg())) {
            return JsonResult.errorException("用户信息缺失，请补全后提交");
        }*/
        if (StringUtils.isBlank(req.getFaceImg())) {
            return JsonResult.errorException("User’s  information is missing, pls re-submit after supplement");
        }

        //去掉文件jpg后缀
//        String facePdf = FILE_BASE_URL + "/" + req.getFaceImg().substring(req.getFaceImg().lastIndexOf("/") + 1, req.getFaceImg().length() - 3) + "pdf";
        String facePdf = FILE_BASE_URL + "/" + req.getFaceImg().substring(0, req.getFaceImg().length() - 3) + "pdf";
        File fileOfFace = new File(facePdf);
        // 判断路径是否存在，如果不存在就创建一个
        if (!fileOfFace.getParentFile().exists()) {
            fileOfFace.getParentFile().mkdirs();
        } else {
            fileOfFace.delete();
            fileOfFace.getParentFile().mkdirs();
        }

        ArrayList<String> faceJpg = new ArrayList<>(Arrays.asList(aliOssUtil.getUrl(req.getFaceImg())));
        //jpg转pdf
        File pdfFile = ImageToPdf.Pdf(faceJpg, facePdf);

        String imgUrl = aliOssUtil.uploadObject2OSS(pdfFile, "pdf/face/");

        req.setFacePdf(imgUrl);
        AppUserImgInfo appUserImgInfo = new AppUserImgInfo();
        BeanUtils.copyProperties(req, appUserImgInfo);
        appUserImgInfo.setCreateTime(now);
        AppUserImgInfo userImgInfo = appUserInfoDao.getBodyImgById(req.getUserId());
        int result = 0;
        if (null == userImgInfo) {
            result = appUserInfoDao.saveLiveData(appUserImgInfo);
        } else {
            result = appUserInfoDao.updateLiveData(appUserImgInfo);
        }
        if (result < 1) {
            throw new IllegalArgumentException("Failed to save the intravital info");
//            return JsonResult.errorMsg("Failed to save the intravital info");
        }
        fileOfFace.delete();
        return JsonResult.ok();
    }

    /**
     * 保存用户信息
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult updateBasicInfo(AppUserBasicInfoReq req) {
        if (StringUtils.isBlank(req.getEmail())) {
            return JsonResult.errorException("Pls input user’s email ID");
        }
        if (!com.cloud.common.utils.StringUtils.isEmail(req.getEmail())) {
            return JsonResult.errorException("Pls input correct email ID");
        }

        if (StringUtils.isBlank(req.getMarriageStatus())) {
            return JsonResult.errorException("Pls select the marital status");
        }
        if (StringUtils.isBlank(req.getReligion())) {
            return JsonResult.errorException("Religion info can’t be empty");
        }
        if (StringUtils.isBlank(req.getLoanPurpose())) {
            return JsonResult.errorException("Purpose of loan can’t be empty");
        }

        if (StringUtils.isBlank(req.getPostcode())) {
            return JsonResult.errorException("Pls input the zip code");
        }

        if (!com.cloud.common.utils.StringUtils.isDigits(req.getPostcode())){
            return JsonResult.errorException("pin code can only be number");
        }

        //去掉文件jpg后缀
        String bankPdf = FILE_BASE_URL + "/" + req.getBankStatementUrl().substring(0, req.getBankStatementUrl().length() - 3) + "pdf";

        File fileOfBank = new File(bankPdf);
        // 判断路径是否存在，如果不存在就创建一个
        if (!fileOfBank.getParentFile().exists()) {
            fileOfBank.getParentFile().mkdirs();
        } else {
            fileOfBank.delete();
            fileOfBank.getParentFile().mkdirs();
        }

        ArrayList<String> bankJpg = new ArrayList<>(Arrays.asList(aliOssUtil.getUrl(req.getBankStatementUrl())));
        //jpg转pdf
        File pdfFileOfBank = ImageToPdf.Pdf(bankJpg, bankPdf);

        String imgUrlOfBank = aliOssUtil.uploadObject2OSS(pdfFileOfBank, "pdf/bank-statement/");
        req.setBankStatementPdf(imgUrlOfBank);
        AppUserBasicInfo basicInfo = new AppUserBasicInfo();
        BeanUtils.copyProperties(req, basicInfo);
        basicInfo.setId(req.getUserId());
        Date now = DateUtil.getDate();

        basicInfo.setRegisterTime(now);
        int result = appUserInfoDao.updateById(basicInfo);
        if (result < 1) {
//            return JsonResult.errorMsg("Failed to save the user’s data");
            throw new IllegalArgumentException("Failed to save the user’s data");
        }
        return JsonResult.ok();
    }

    /**
     * 根据ID判断是否是学生
     *
     * @param id
     * @return
     */
    @Override
    public Integer getUserIdentity(Long id) {
        Integer status = appUserInfoDao.getUserStatus(id);
        return status;
    }

    /**
     * 保存学生信息
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult saveStudentInfo(AppUserJobInfoReq req) {
        if (StringUtils.isBlank(req.getSchool())) {
            return JsonResult.errorException("School name can’t be empty");
        }

        if (StringUtils.isBlank(req.getSpecialty())) {
            return JsonResult.errorException("Pls input your profession name");
        }

        if (StringUtils.isBlank(req.getTown()) || StringUtils.isBlank(req.getCounty()) || StringUtils.isBlank(req.getDistrict()) || StringUtils.isBlank(req.getState())) {
            return JsonResult.errorException("Pls input your school address");
        }

        if (StringUtils.isBlank(req.getAddressDetail())) {
            return JsonResult.errorException("Pls input your school detail address");
        }

        if (StringUtils.isBlank(req.getEnrollmentYear())) {
            return JsonResult.errorException("Pls select school enrollment year");
        }

        if (StringUtils.isBlank(req.getAlimony())) {
            return JsonResult.errorException("Living expenses can’t be empty");
        }
        if (StringUtils.isBlank(req.getWorkCard())) {
            return JsonResult.errorException("Pls upload student ID");
        }
        if (null == appUserInfoDao.getByUserId(req.getUserId())) {
            return JsonResult.errorException("User does not exist");
        }
        //2:学校地址
        JsonResult jsonResult = this.saveUserInfo(req, ProductConstants.SCHOOL_ADDRESS_TYPE);
        return jsonResult;
    }

    /**
     * 保存用户工作信息
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult saveWorkInfo(AppUserJobInfoReq req) {
        if (StringUtils.isBlank(req.getEducationDegree())) {
            return JsonResult.errorException("Pls select the education level");
        }

        if (StringUtils.isBlank(req.getSchool())) {
            return JsonResult.errorException("School name can’t be empty");
        }

        if (StringUtils.isBlank(req.getTown()) || StringUtils.isBlank(req.getCounty()) || StringUtils.isBlank(req.getDistrict()) || StringUtils.isBlank(req.getState())) {
            return JsonResult.errorException("Pls select company address");
        }

        if (StringUtils.isBlank(req.getAddressDetail())) {
            return JsonResult.errorException("Pls input company detail address");
        }

        if (StringUtils.isBlank(req.getAddressDetailOfHome())) {
            return JsonResult.errorException("Pls input domicile address");
        }
        if (StringUtils.isBlank(req.getTownOfHome()) || StringUtils.isBlank(req.getCountyOfHome()) || StringUtils.isBlank(req.getDistrictOfHome()) || StringUtils.isBlank(req.getStateOfHome())) {
            return JsonResult.errorException("Pls select domicile address");
        }

        if (StringUtils.isBlank(req.getProfession())) {
            return JsonResult.errorException("Pls select industry");
        }

        if (StringUtils.isBlank(req.getPosition())) {
            return JsonResult.errorException("Pls input the position");
        }

        if (StringUtils.isBlank(req.getCompanyName())) {
            return JsonResult.errorException("Pls input company name");
        }

        if (StringUtils.isBlank(req.getWorkYear())) {
            return JsonResult.errorException("Pls select working seniority");
        }

        if (StringUtils.isBlank(req.getSalary())) {
            return JsonResult.errorException("Monthly income can’t be empty");
        }
        //1:保存工作地址
        JsonResult jsonResult = this.saveUserInfo(req, ProductConstants.WORK_ADDRESS_TYPE);
        Date now = DateUtil.getDate();
        AppUserSalary userSalary = new AppUserSalary();
        BeanUtils.copyProperties(req, userSalary);
        userSalary.setCreateTime(now);
        //用户薪资录入
        int result = appUserInfoDao.saveUserSalary(userSalary);

        if (result < 1) {
            throw new IllegalArgumentException("Failed to save user’s income info");

        } else {//判定是否 薪资属性为空
            List<AppUserInfoHistoryEntity> appUserInfoHistoryEntities = appUserInfoDao.queryOrderHistoryByUserId(userSalary.getUserId());
            if (appUserInfoHistoryEntities != null && appUserInfoHistoryEntities.size() > 0) {
                UserSalary userSalary1 = appUserInfoDao.getUserSalaryById(userSalary.getUserId());
                Integer salaryId = userSalary1.getId();

                for (AppUserInfoHistoryEntity appUserInfoHistoryEntity : appUserInfoHistoryEntities) {
                    if (appUserInfoHistoryEntity.getSalaryId() == null) {
                        appUserInfoHistoryEntity.setSalaryId(salaryId);
                        appUserInfoDao.updateOrderHistorySalaryIdById(appUserInfoHistoryEntity);
                    }
                }

            }


        }
        return jsonResult;
    }

    /**
     * 录入联系人信息
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult saveContactInfo(AppUserContactReq req) {
        if (null == appUserInfoDao.getByUserId(req.getUserId())) {
            return JsonResult.errorException("User does not exist");
        }
        if (StringUtils.isBlank(req.getName())) {
            return JsonResult.errorException("Relative’s name can’t be empty");
        }

        if (StringUtils.isBlank(req.getRelation())) {
            return JsonResult.errorException("Relative’s name can’t be empty");
        }

        if (StringUtils.isBlank(req.getPhone())) {
            return JsonResult.errorException("Relative’s phone number can’t be empty");
        }

        if (StringUtils.isBlank(req.getAnotherName())) {
            return JsonResult.errorException("Other contacts’ name can’t be empty");
        }

        if (StringUtils.isBlank(req.getAnotherRelation())) {
            return JsonResult.errorException("Relationship of other contacts can’t be empty");
        }

        if (StringUtils.isBlank(req.getAnotherPhone())) {
            return JsonResult.errorException("Other contacts’ phone number can’t be empty");
        }
        List<AppUserContact> contacts = appUserInfoDao.getUserAllContact(req.getUserId());
        //先删除表中原有联系人数据
        if (CollectionUtils.isNotEmpty(contacts)) {
            appUserInfoDao.deleteContactById(req.getUserId());
        }
        AppUserJobInfo appUserJobInfo = new AppUserJobInfo();
        AppUserContact appUserContact = new AppUserContact();
        AppUserContact anotherAppUser = new AppUserContact();
        BeanUtils.copyProperties(req, appUserContact);
        BeanUtils.copyProperties(req, appUserJobInfo);
        Date now = DateUtil.getDate();
        appUserContact.setCreateTime(now);
        //去除手机号之间的空格
        appUserContact.setPhone(req.getPhone().replace(" ", ""));
        appUserContact.setName(JSON.toJSONString(req.getName()).replace('"', ' '));
        appUserContact.setEnabledState(FunctionStatusEnum.DISABLED.code);
        //其他联系人信息
        anotherAppUser.setCreateTime(now);
        anotherAppUser.setName(JSON.toJSONString(req.getAnotherName()).replace('"', ' '));
        anotherAppUser.setRelation(req.getAnotherRelation());
        anotherAppUser.setUserId(req.getUserId());
        anotherAppUser.setEnabledState(FunctionStatusEnum.DISABLED.code);
        anotherAppUser.setPhone(req.getAnotherPhone().replace(" ", ""));
        int result = appUserInfoDao.saveUserContact(appUserContact);
        int anotherResult = appUserInfoDao.saveUserContact(anotherAppUser);
        if (StringUtils.isNotEmpty(appUserJobInfo.getFacebookAccount()) || StringUtils.isNotEmpty(appUserJobInfo.getWhatsappAccount()) || StringUtils.isNotEmpty(appUserJobInfo.getSkypeAccount())) {
            appUserInfoDao.updateUserInfo(appUserJobInfo);

        }

        if (result < 1 || anotherResult < 1) {
            throw new IllegalArgumentException("Failed to save user’s contacts info");
//            return JsonResult.errorMsg("Failed to save user’s contacts info");
        }
        return JsonResult.ok();
    }

    /**
     * 保存用户银行卡信息
     *
     * @param req
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult saveBankInfo(AppUserBankReq req) {
        //校验ifsc
        int validIfsc = ifscDao.isValidIfsc(req.getIfscCode());
        if (validIfsc != 1) {
            return JsonResult.errorMsg("Your IFSC code is not avaliable");
        }

        Date nowTime = DateUtil.getDate();
        AppUserBasicInfo basicInfo = appUserInfoDao.getByUserId(req.getUserId());
        if (null == basicInfo) {
            return JsonResult.errorException("User does not exist");
        }
        if (StringUtils.isBlank(req.getAccountName())) {
            return JsonResult.errorException("Account name can’t be empty");
        }
        if (StringUtils.isBlank(req.getBankAccount())) {
            return JsonResult.errorException("Bank account can’t be empty");
        }
        if (StringUtils.isBlank(req.getBankName())) {
            return JsonResult.errorException("Bank name can’t be empty");
        }
        if (StringUtils.isBlank(req.getIfscCode())) {
            return JsonResult.errorException("IFSC code can’t be empty");
        }

        AppUserBank appUserBank = new AppUserBank();
        BeanUtils.copyProperties(req, appUserBank);
        appUserBank.setAccountName(basicInfo.getFirstName() + basicInfo.getLastName());
        appUserBank.setBindingTime(nowTime);
        //0:未使用
        appUserBank.setStatus(0);

        boolean validFlag = backendClient.queryNoOffInfo(NoOffStatusEnum.CASHFREE_VALID_BANKCARD.getMessage());
        Boolean validResult = Boolean.TRUE;
        if (validFlag) {
            validResult = payClient.validBankCard(appUserBank.getAccountName(), basicInfo.getCellPhone(), appUserBank.getBankAccount(), appUserBank.getIfscCode());
        }
        if (!validResult) {
            return JsonResult.errorException("bank card valid failed");
        }

        int result = appUserInfoDao.saveBankInfo(appUserBank);
        if (result < 1) {
            throw new IllegalArgumentException("Failed to save the bank account info");
        }
        return JsonResult.ok();
    }

    /**
     * 校验产品是否存在
     *
     * @param req
     * @return
     */
    @Override
    public JsonResult judgeCanLoan(AppJudgeCanLoanReq req) {
        //校验是否可以借款
        try {
            if (!orderClient.finalJudgeForLoanOrder(req.getUserId().intValue())) {
                log.info("用户【{}】校验不通过 不可以借款", req.getUserId());
                return JsonResult.errorMsg("Sorry your application hasn't been approved, look forward to your re-application");
            } else {
                log.info("用户【{}】校验通过 可以借款", req.getUserId());
            }
        } catch (Exception ex) {
            log.error("调用order模块校验用户是否可以借款出错, 请求参数= {}", JSONObject.toJSONString(req), ex);
        }


        LoanProductModel productModel = orderClient.getLoanProductById(req.getProductId());
        if (null == productModel || !productModel.getStatus().equals(ProductConstants.STATUS_ACTIVE)) {
            return JsonResult.errorException("Loan product is not exist!");
        }
        if (!productModel.getRepaymentType().equals(ProductConstants.REPAYMENT_TYPE_BEFORE_INTEREST_AFTER_PRINCIPAL)) {
            return JsonResult.errorException("Currently no other repayment method");
        }
        //获取总利息与罚息利率==
        AppJudgeCanLoanResp loanResp = this.excuteLoanAmountAndDate(productModel, req);
        if (null == loanResp) {
            return JsonResult.errorException("Loan amount is not match the product’s set");
        }
        long orderNo = snowflakeIdWorker.nextId();
        //生成订单号
        loanResp.setApplyNum(String.valueOf(orderNo));
        //生成sessionId
        loanResp.setSessionId(String.valueOf(orderNo));
        return JsonResult.ok(loanResp, 1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult saveUserLoanInfo(AppJudgeCanLoanReq req) {
        JsonResult jsonResult = this.judgeCanLoan(req);
        if (jsonResult.getCode() != HttpStatus.OK.value()) {
            return jsonResult;
        }
        AppUserLoanInfo loanInfo = this.updateUserExpand(req);
        if (null == loanInfo) {
            return JsonResult.errorMsg("Failed to create loan info");
        }
        taskExecutor.execute(() -> asyncExecuteRiskRule(loanInfo));
        return JsonResult.ok();
    }

    public void asyncExecuteRiskRule(AppUserLoanInfo loanInfo) {
        //这里只需要执行一下风控规则 并不需要根据返回结果来判断是否通过
        RiskExecuteRequestDto requestDto = new RiskExecuteRequestDto();
        AppBaseDto baseDto = new AppBaseDto();
        baseDto.setOrderNum(loanInfo.getApplyNum());
        baseDto.setPhone(loanInfo.getUserPhone());
        baseDto.setUserId(loanInfo.getUserId());
        baseDto.setAppSourceChannel("alipay");
        requestDto.setAppBaseDto(baseDto);
        riskExecuteSender.send(requestDto);
    }

    //cibil api
    @Override
    public String strTokudos7(String orderNo) {
        LoanCIBILEntity loanCibil = orderDao.queryCibilInformation(orderNo);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>进入到cibil接口", orderNo);
        String adsType = "0";//0>家庭地址,1>单位地址
        String adsType1 = "1";
        String userId = loanCibil.getUserId();
        UserAddress userAds = orderDao.queryUserAds(userId, adsType);
        UserAddress userAdsname = orderDao.queryUserAds(userId, adsType1);
        String name = "State";
        String code = "";
        String value = userAds.getState();
        KudosParamEntity kudosParam = kudosDao.queryKudosParams(name, code, value.replaceAll("&", "and"));

        String name1 = "Query";
        String code1 = "7";
        String value1 = "";
        KudosParamEntity kudosParam1 = kudosDao.queryKudosParams(name1, code1, value1);
        KudosParamEntity kudosParams = kudosDao.queryKudosParams("CIBILURL", kudosConfig.getProduction(), "");//1c,2s
        KudosParamEntity kudosSecretkey = kudosDao.queryKudosParams("CIBIL", kudosConfig.getProduction(), "");
        LoanCIBILEntity lcEntity = new LoanCIBILEntity();
        String[] nameArea = loanCibil.getBorrowerFName().split(" ");
        int count = 0;
        for (int i = 0; i < nameArea.length; i++) {
            count++;
        }
        lcEntity.setBorrowerAddr1(userAds.getAddressDetail());
        lcEntity.setBorrowerAddr2(userAdsname.getAddressDetail());
        lcEntity.setBorrowerAddr3(kudosConfig.getNA());
        lcEntity.setBorrowerAddr4(kudosConfig.getNA());
        lcEntity.setBorrowerAddr5(kudosConfig.getNA());
        lcEntity.setBorrowerAddrType("02");//借款人地址类型参见附录3
        lcEntity.setBorrowerCity(userAds.getDistrict());//城市
        lcEntity.setBorrowerIncome(loanCibil.getBorrowerIncome());
        lcEntity.setBorrowerLoanPurpose("05");//借款人贷款目的{请使用值05}study
        lcEntity.setBorrowerPincode(loanCibil.getPostcode());//邮编
        lcEntity.setBorrowerRepaymentPerMnths(kudosConfig.getSandBox());//借款人还款期限为几个月   1
        lcEntity.setBorrowerRequestAmount(loanCibil.getBorrowerRequestAmount().replace(".00", ""));
        lcEntity.setBorrowerResiType("02");//借款人居住类型
        lcEntity.setBorrowerStateCode(kudosParam.getCode());
        lcEntity.setBorrowerCompanyName(loanCibil.getBorrowerCompanyName());
        String[] str = loanCibil.getBorrowerDOB().split("/");
//		System.out.println("str="+str[2]+str[1]+str[0]);
        lcEntity.setBorrowerDOB(str[0] + str[1] + str[2]);
        lcEntity.setBorrowerEmail(loanCibil.getBorrowerEmail());
        if (loanCibil.getBorrowerGender().equals("0")) {
            lcEntity.setBorrowerGender("M");
        } else {
            lcEntity.setBorrowerGender("F");
        }
        if (count >= 3) {
            lcEntity.setBorrowerFName(nameArea[0]);
            lcEntity.setBorrowerLName(nameArea[1]);
            lcEntity.setBorrowerMName(nameArea[2]);
        } else if (count == 2) {
            lcEntity.setBorrowerFName(nameArea[0]);
            lcEntity.setBorrowerLName(nameArea[1]);
            lcEntity.setBorrowerMName(kudosConfig.getNA());
        } else if (count == 1) {
            lcEntity.setBorrowerFName(nameArea[0]);
            lcEntity.setBorrowerLName(kudosConfig.getNA());
            lcEntity.setBorrowerMName(kudosConfig.getNA());
        }
        lcEntity.setBorrowerPhone(loanCibil.getBorrowerPhone());
        lcEntity.setBorrowerPhoneType("01");//借款人电话号码类型 - 参见附录2
        lcEntity.setConsumerConsentForUIDAIAuthentication("Y");
        lcEntity.setgSTStateCode(kudosParam.getCode());
        lcEntity.setIdnumber(loanCibil.getIdnumber());
        lcEntity.setIdtype("01");//借款人ID类型有关不同类型的ID，请参阅附录1   01/03
        lcEntity.setRequestReferenceNum(loanCibil.getBorrowerPhone());
        lcEntity.setUserId(userId);
        lcEntity.setSecretKet(kudosSecretkey.getValue());
        return kudosService.kudosCibil(lcEntity, kudosParam1.getValue(), orderNo, kudosParams.getValue());
    }
    
    @Transactional(rollbackFor = Exception.class)
    AppUserLoanInfo updateUserExpand(AppJudgeCanLoanReq req) {
        AppUserBasicInfo userBasicInfo = appUserInfoDao.getByUserId(req.getUserId());
        String loanStart = "MN00";
        String loanEnd = "PD";
        if (null == userBasicInfo.getAadhaarAccount() || null == userBasicInfo.getAadhaarUrlFront() || null == userBasicInfo.getAadhaarUrlBack()) {
            return null;
        }
        if (null == appUserInfoDao.getNewBankCardInfo(req.getUserId())) {
            return null;
        }
        Date now = DateUtil.getDate();
        LoanProductModel productModel = orderClient.getLoanProductById(req.getProductId());
        AppJudgeCanLoanResp resp = this.excuteLoanAmountAndDate(productModel, req);
        AppUserLoanInfo loanInfo = new AppUserLoanInfo();
        AppUserBasicInfo basicInfo = new AppUserBasicInfo();
        loanInfo.setUserId(userBasicInfo.getId());
        loanInfo.setProductId(req.getProductId());
        loanInfo.setUserName(resp.getUserName());
        //生成订单编号
        loanInfo.setLoanNumber(loanStart + userBasicInfo.getCellPhone() + (int) ((Math.random() * 9 + 1) * 100000) + loanEnd);
        loanInfo.setUserPhone(userBasicInfo.getCellPhone());
        loanInfo.setBorrowAmount(resp.getLoanAmount());
        loanInfo.setSessionId(req.getSessionId());
        loanInfo.setApplyNum(req.getApplyNum());
        loanInfo.setCreateTime(now);
        //借款时间
        loanInfo.setLoanStartDate(now);
        loanInfo.setSignatureUrl(req.getSignatureUrl());

        //用户状态更新 1：已激活
        basicInfo.setId(userBasicInfo.getId());
        basicInfo.setStatus(1);
        appUserInfoDao.updateById(basicInfo);
        int orderResult = this.saveOrderInfo(loanInfo);
        try {
            int historyResult = this.saveUserHistoryInfo(loanInfo.getUserId(), loanInfo.getApplyNum());
            if (historyResult < 1) {
                log.info("failed create  history   info   >>>>>>>>>{}", loanInfo.getApplyNum());
                throw new IllegalArgumentException("failed create  history   info    ");
            }
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());

            //生成订单失败
            if (orderResult == -1) {
                log.info("生成订单失败>>>>>>>>>{}", loanInfo.getApplyNum());
                throw new IllegalArgumentException("Failed to generate order");
            }
            if (orderResult < 1) {
                log.info("更新/录入状态失败 >>>>>>>>>{}", loanInfo.getApplyNum());
                throw new IllegalArgumentException("update fail");
            }

            return loanInfo;
        }

        //生成订单失败
        if (orderResult == -1) {
            log.info("生成订单失败>>>>>>>>>{}", loanInfo.getApplyNum());
            throw new IllegalArgumentException("Failed to generate order");
        }
        if (orderResult < 1) {
            log.info("更新/录入状态失败 >>>>>>>>>{}", loanInfo.getApplyNum());
            throw new IllegalArgumentException("update fail");
        }

        return loanInfo;
    }

    private int saveOrderInfo(AppUserLoanInfo loanInfo) {
        int result = 0;
        if (null != loanInfo) {
            log.info("生成申请订单{}", loanInfo.getApplyNum());
            AppUserOrderInfo orderInfo = new AppUserOrderInfo();
            Date now = DateUtil.getDate();
            orderInfo.setUserId(loanInfo.getUserId());
            orderInfo.setProductId(loanInfo.getProductId());
            //订单编号
            orderInfo.setOrderNum(loanInfo.getApplyNum());
            orderInfo.setSessionId(loanInfo.getSessionId());
            orderInfo.setApplyNum(loanInfo.getLoanNumber());
            //审核中状态
            orderInfo.setCheckStatus(ProductConstants.IN_REVIEW);
            //-1代表无
            orderInfo.setManagerId(ProductConstants.UNMANNED);
            orderInfo.setAuditorId(ProductConstants.UNMANNED);
            orderInfo.setLoanAmount(loanInfo.getBorrowAmount());
            orderInfo.setUserName(loanInfo.getUserName());
            orderInfo.setUserPhone(loanInfo.getUserPhone());
            orderInfo.setCreateTime(now);
            orderInfo.setSignatureUrl(loanInfo.getSignatureUrl());

            log.info("最后一步校验开始");

            try {
                if (!orderClient.finalJudgeForLoanOrder(loanInfo.getUserId().intValue())) {
                    log.info("用户【{}】校验不通过 不可以借款", loanInfo.getUserId());
                    return -1;
                } else {
                    log.info("用户【{}】校验通过 可以借款", loanInfo.getUserId());
                    log.info("生成订单结束,订单编号为{}", loanInfo.getApplyNum());
                    return appUserInfoDao.saveUserOrder(orderInfo);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                log.info("调用order模块校验用户是否可以借款出错");
                return -1;
            }

            /*AppUserOrderInfo orderById = appUserInfoDao.getOrderById(orderInfo.getUserId());
            AppUserLoanInfo loanNum = appUserInfoDao.getLoanByLoanNum(orderInfo.getOrderNum());
            if ((null == loanNum && null == orderById) || (null != loanNum && ProductConstants.FINISH.equals(loanNum.getLoanStatus()))) {
                result = appUserInfoDao.saveUserOrder(orderInfo);
                //订单状态为拒绝并且7天后的时间小于当前时间
            } else if ((orderById.getCheckStatus() == 2 || orderById.getCheckStatus() == 4 || orderById.getCheckStatus() == 6) && DateUtil.getDate(orderById.getFinalAuditTime(), 6).before(DateUtil.initDateByDay())) {
                result = appUserInfoDao.saveUserOrder(orderInfo);
            } else {
                return -1;
            }*/
        }

        return result;
    }


    /**
     * 保存用户历史信息
     *
     * @param userId
     * @return
     */
    public int saveUserHistoryInfo(Long userId, String orderNo) {
        List<AppUserInfoHistoryEntity> result = appUserInfoDao.queryOrderHistoryByOrderNo(orderNo);
        if (result.size() > 0) {
            return 1;
        }

        //获取用户最新的pancard信息
        UserPanCardModel panCardInfo = userPanCardDao.getPanCardInfo(userId);
        //用户扩展信息
        AppUserJobInfo userExpand = appUserInfoDao.getUserExpand(userId);
        //用户基本信息
        AppUserBasicInfo userInfo = appUserInfoDao.getByUserId(userId);
        //用户最新的银行卡账号
        AppUserBank userBankCard = appUserInfoDao.getNewBankCardInfo(userId);
        //用户联系人信息
        List<AppUserContact> contacts = appUserInfoDao.getContactInfoById(userId);
        //用户住址信息
        List<UserAddress> userAddress = appUserInfoDao.getUserAddressById(userId);
        //用户最新的薪资信息
        UserSalary userSalary = appUserInfoDao.getUserSalaryById(userId);

        List<AppUserImgInfoReq> userImgInfoReqs = appUserInfoDao.getAppUserImgInfoById(userId);

        //判定是否續貸 邏輯
        Integer renewalState = this.checkRenewalState(userId);
        AppUserBasicInfo appUserBasicInfo = new AppUserBasicInfo();
        appUserBasicInfo.setId(userInfo.getId());
        appUserBasicInfo.setRenewalState(renewalState);
        appUserInfoDao.updateById(appUserBasicInfo);

        Integer schoolAddressId = null;
        Integer homeAddressId = null;
        Integer companyAddressId = null;
        Integer firstContactId = null;
        Integer secondContactId = null;
        Integer salaryId = null;

        String faceImg = "";

        //生成历史 表时用户应该是激活
        if (userInfo.getStatus() != 1) {
            userInfo.setStatus(1);
            appUserInfoDao.updateById(userInfo);
        }
        if (CollectionUtils.isNotEmpty(userAddress)) {
            for (UserAddress address : userAddress) {
                if (ProductConstants.SCHOOL_ADDRESS_TYPE.toString().equals(address.getAddressType()) && null != address) {
                    schoolAddressId = address.getId();
                }
                if (ProductConstants.HOME_ADDRESS_TYPE.toString().equals(address.getAddressType()) && null != address) {
                    homeAddressId = address.getId();
                }
                if (ProductConstants.WORK_ADDRESS_TYPE.toString().equals(address.getAddressType()) && null != address) {
                    companyAddressId = address.getId();
                }
            }
        }
        if (CollectionUtils.isNotEmpty(contacts)) {

            firstContactId = contacts.get(0).getId();
            secondContactId = contacts.get(contacts.size() - 1).getId();

            //设啓動
            appUserInfoDao.setUserContactEnable(firstContactId, FunctionStatusEnum.ENABLED.code);
            appUserInfoDao.setUserContactEnable(secondContactId, FunctionStatusEnum.ENABLED.code);


        } else {
            contacts = appUserInfoDao.getUserAllContact(userId);
            firstContactId = contacts.get(0).getId();
            secondContactId = contacts.get(1).getId();
        }

        if (userImgInfoReqs != null && userImgInfoReqs.size() > 0) {
            faceImg = userImgInfoReqs.get(userImgInfoReqs.size() - 1).getFaceImg();

        }

        if (userSalary != null && userSalary.getId() != null) {
            salaryId = userSalary.getId();
        }
        return appUserInfoDao.saveUserInfoHistory(AppUserInfoHistoryEntity.builder().
                orderNo(orderNo).panCardId(panCardInfo.getId()).bankId(userBankCard.getId())
                .cardFrontUrlPan(panCardInfo.getCardFrontUrl())
                .cardBackUrlPan(panCardInfo.getCardBackUrl())
                .cardFrontPdfPan(panCardInfo.getCardFrontPdf())
                .cardBackPdfPan(panCardInfo.getCardBackPdf())
                .holdsFullNamePan(panCardInfo.getHoldsFullName())
                .fathersNamePan(panCardInfo.getFathersName())
                .permanentNoPan(panCardInfo.getPermanentNo())
                .isIdentifiedPan(panCardInfo.getIsIdentified())
                .birthdayPan(panCardInfo.getBirthday())
                .matchPan(panCardInfo.getMatch())
                .matchScorePan(panCardInfo.getMatchScore())
                .aadhaarUrlFront(userInfo.getAadhaarUrlFront())
                .aadhaarUrlBack(userInfo.getAadhaarUrlBack())
                .aadhaarDist(userInfo.getAadhaarDist())
                .aadhaarHouse(userInfo.getAadhaarHouse())
                .aadhaarLoc(userInfo.getAadhaarLoc())
                .aadhaarPo(userInfo.getAadhaarPo())
                .aadhaarState(userInfo.getAadhaarState())
                .aadhaarStreet(userInfo.getAadhaarStreet())
                .aadhaarSubdist(userInfo.getAadhaarSubdist())
                .aadhaarVtc(userInfo.getAadhaarVtc())
                .aadhaarAccount(userInfo.getAadhaarAccount()).
                        match(userInfo.getMatch()).
                        voterFrontUrl(userExpand.getVoterFrontUrl()).voterBackUrl(userExpand.getVoterBackUrl()).
                        sex(userInfo.getSex())
                .mobile(userInfo.getCellPhone()).
                        marriageStatus(userInfo.getMarriageStatus())
                .isStudent(userInfo.getStudentStatus())
                .email(userInfo.getEmail())
                .birthday(userInfo.getBirthday()).
                        facebookAccount(userExpand.getFacebookAccount()).
                        skypeAccount(userExpand.getSkypeAccount())
                .whatsappAccount(userExpand.getWhatsappAccount()).
                        religion(userInfo.getReligion())
                .voterIdCard(userExpand.getVoterIdCard()).
                        postCode(userInfo.getPostcode()).
                        loanPurpose(userInfo.getLoanPurpose()).
                        profession(userExpand.getProfession()).
                        position(userExpand.getPosition()).
                        companyName(userExpand.getCompanyName()).
                        educationDegree(userExpand.getEducationDegree()).school(userExpand.getSchool()).
                        workYear(userExpand.getWorkYear()).workCard(userExpand.getWorkCard())
                .bankStatementUrl(userInfo.getBankStatementUrl()).
                        electorsName(userExpand.getElectorsName())
                .salaryId(salaryId).firstContactId(firstContactId).secondContactId(secondContactId)
                .schoolAddressId(schoolAddressId)
                .homeAddressId(homeAddressId).companyAddressId(companyAddressId)
                .faceImg(faceImg).relationsName(userExpand.getRelationsName())
                .alimony(userExpand.getAlimony())
                .enrollmentYear(userExpand.getEnrollmentYear())
                .userId(userId)
                .specialty(userExpand.getSpecialty())
                .paydays(userExpand.getPayday())
                .renewalState(renewalState)
                .build());
    }

    /**
     * 判定是否續貸 邏輯
     * <p>
     * 1.(订单详情) 是否续贷：申请通过 >= 1(不管放款成功或失败)
     *
     * @param userId
     * @return
     */
    private Integer checkRenewalState(Long userId) {
        //判定是否續貸 邏輯 1.(订单详情) 是否续贷：申请通过 >= 1(不管放款成功或失败)
        long passedOrderCount = orderClient.getOrderListByUserId(userId)
                .stream()
                .filter(appUserOrderInfo -> appUserOrderInfo.getCheckStatus() == CheckStatus.PASSED.toNum())
                .count();
        return passedOrderCount > 0 ? FunctionStatusEnum.ENABLED.getCode() : FunctionStatusEnum.DISABLED.getCode();
    }

    /**
     * 提取可重用代码
     * saveUserInfo 保存用户职业信息
     * saveAddress 保存不同用户的详情地址信息
     *
     * @param req
     * @param status 1:工作地址 2:学生地址
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public JsonResult saveUserInfo(AppUserJobInfoReq req, int status) {
        Date now = new Date();
        if (null == appUserInfoDao.getByUserId(req.getUserId())) {
            return JsonResult.errorException("User does not exist");
        }
        //将原证明删除
        if (null != req.getWorkCard() && null != appUserInfoDao.getUserExpand(req.getUserId()) && null != appUserInfoDao.getUserExpand(req.getUserId()).getWorkCard()) {
            appUserInfoDao.updateWorkCardById(req.getUserId());
        }
        if (StringUtils.isNotBlank(req.getVoterIdCard())) {
            req.setVoterIdCard(req.getVoterIdCard().trim());
        }

        if (StringUtils.isNotBlank(req.getPancardAccount())) {
            req.setPancardAccount(req.getPancardAccount().trim());
        }

        AppUserJobInfo appUserJobInfo = new AppUserJobInfo();
        BeanUtils.copyProperties(req, appUserJobInfo);
        appUserJobInfo.setCreateTime(now);
        AppUserJobInfo userExpand = appUserInfoDao.getUserExpand(req.getUserId());
        int resultOfJob = 0;
        if (null != userExpand) {
            resultOfJob = appUserInfoDao.updateUserInfo(appUserJobInfo);
        } else {
            resultOfJob = appUserInfoDao.saveUserInfo(appUserJobInfo);
        }

        //只有工作人员才录入家庭住址
        if (status == ProductConstants.WORKER_USER_TYPE) {
            AppUserAddressInfo addressInfoOfHome = new AppUserAddressInfo();
            BeanUtils.copyProperties(req, addressInfoOfHome);
            addressInfoOfHome.setCreateTime(now);
            //家庭地址类型
            addressInfoOfHome.setAddressType(ProductConstants.HOME_ADDRESS_TYPE);
            //用户地址状态：已启用
            addressInfoOfHome.setStatus(1);
            //更新之前的家庭地址为未启用
            appUserInfoDao.updateAddressStatus(req.getUserId(), ProductConstants.HOME_ADDRESS_TYPE);
            int resultOfHome = appUserInfoDao.saveAddress(addressInfoOfHome);

            //1:录入工作地址
            AppUserAddressInfo addressInfo = new AppUserAddressInfo();
            addressInfo.setUserId(req.getUserId());
            addressInfo.setState(req.getStateOfHome());
            addressInfo.setCounty(req.getCountyOfHome());
            addressInfo.setDistrict(req.getDistrictOfHome());
            addressInfo.setTown(req.getTownOfHome());
            addressInfo.setAddressDetail(req.getAddressDetailOfHome());
            addressInfo.setCompanyPhone(req.getCompanyPhone());
            //工作地址类型 status = 1
            addressInfo.setAddressType(status);
            addressInfo.setStatus(1);
            //在保存用户地址之前先更新此ID对应类型的所有地址为未启用状态
            appUserInfoDao.updateAddressStatus(req.getUserId(), status);
            int resultOfAddr = appUserInfoDao.saveAddress(addressInfo);
            if (resultOfJob < 1 || resultOfAddr < 1 || resultOfHome < 1) {
//                return JsonResult.errorMsg("Failed to save user’s work info");
                throw new IllegalArgumentException("Failed to save user’s work info");
            }
        } else {

            AppUserAddressInfo addressInfoOfSchool = new AppUserAddressInfo();

            BeanUtils.copyProperties(req, addressInfoOfSchool);
            addressInfoOfSchool.setCreateTime(now);
            addressInfoOfSchool.setAddressType(status);
            //用户地址状态：已启用
            addressInfoOfSchool.setStatus(1);
            appUserInfoDao.updateAddressStatus(req.getUserId(), status);
            int resultOfStudent = appUserInfoDao.saveAddress(addressInfoOfSchool);
            if (resultOfJob < 1 || resultOfStudent < 1) {
//                return JsonResult.errorMsg("Failed to save user’s school info");
                throw new IllegalArgumentException("Failed to save user’s school info");
            }
        }
        return JsonResult.ok();
    }

    private AppJudgeCanLoanResp excuteLoanAmountAndDate(LoanProductModel productModel, AppJudgeCanLoanReq req) {
        InterestPenaltyModel penaltyById = null;
        if (productModel.getIsCollectPenalty().equals(ProductConstants.CHARGE_PENALTY)) {
            //利息
            penaltyById = orderClient.getInterestPenaltyById(productModel.getInterestId());
        }
        InterestPenaltyModel penaltyModel = null;
        if (null != productModel.getPenaltyInterestId()) {
            //罚息表Info
            penaltyModel = orderClient.getInterestPenaltyById(productModel.getPenaltyInterestId());
        }
        AppJudgeCanLoanResp resp = new AppJudgeCanLoanResp();
        BigDecimal totalRate = BigDecimal.ZERO;
        BigDecimal loanAmount = BigDecimal.ZERO;
//        BigDecimal addedValue = CommonConfig.ADDEDVALUE;
        BigDecimal addedValue = orderClient.getProductGst(productModel.getId());

        BigDecimal term = new BigDecimal(productModel.getTerm());
        //总服务费利率
        if (null != productModel.getRateTypeList()) {
            for (RateType rate : productModel.getRateTypeList()) {
                totalRate = totalRate.add(rate.getRate());
            }
        }
//        if (productModel.getLoanAmountType().equals(ProductConstants.INTERVAL_LINES)) {
//            loanAmount = productModel.getMaxAmount().compareTo(req.getAmount()) == 0 ? req.getAmount() : productModel.getMinAmount();
//        } else if (req.getAmount().compareTo(productModel.getMaxAmount()) == -1 || req.getAmount().compareTo(productModel.getMinAmount()) == 1) {
//            loanAmount = req.getAmount();
//        } else {
//            return null;
//        }

        if (req.getAmount().compareTo(productModel.getMaxAmount()) == 0 || req.getAmount().compareTo(productModel.getMinAmount()) == 0) {
            loanAmount = req.getAmount();
        } else {
            return null;
        }

        BigDecimal exceptRate = new BigDecimal("0.00");
        Date now = DateUtil.getDate();
        if (null != penaltyById) {
            exceptRate = penaltyById.getRate() == null ? exceptRate : penaltyById.getRate();
        }
//        BigDecimal totalFree = loanAmount.multiply(totalRate).add(loanAmount.multiply(exceptRate.multiply(term)));
        BigDecimal totalFree = loanAmount.multiply(exceptRate.multiply(term)).add(loanAmount.multiply(totalRate)).add(loanAmount.multiply(totalRate).multiply(addedValue));

        AppUserBasicInfo basic = appUserInfoDao.getBasicById(req.getUserId());
        resp.setUserName(basic.getFirstName() + basic.getLastName());
        resp.setLoanAmount(loanAmount);
        resp.setLoanTerm(term + "");
        resp.setTotalFee(totalFree);
        resp.setRepaymentDate(DateUtil.getStringDate(DateUtil.getDate(now, productModel.getTerm() - 1), DateUtil.DateFormat2));
        if (null != penaltyModel && null != penaltyModel.getRate()) {
            resp.setPenaltyInterest(penaltyModel.getRate());
        }
        return resp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long registerUserInfo(AppUserBasicInfo userInfo) {

        AppUserBasicInfo appUser = appUserInfoDao.getByMobile(userInfo.getCellPhone());
        if (appUser == null) {
            int result = appUserInfoDao.addUserInfo(userInfo);
            if (result > 0) {
                log.info("Success registered");
                return userInfo.getId();
            }
        }
        return appUser.getId();
    }

    @Override
    public void saveUserDataStatistics(Long userId, String loginImi, String channel) {

        UserDataStatisModel model = new UserDataStatisModel();
        model.setLoginImi(loginImi);
        model.setUserId(userId);
        model.setChannel(channel);
        UserDataStatisModel statisModel = appUserInfoDao.findUserData(model);
        if (null == statisModel) {
            appUserInfoDao.saveUserData(model);
        }
    }

    @Override
    public JsonResult getBasicInfo(Long userId) {
        AppUserBasicInfo basicInfo = appUserInfoDao.getBasicById(userId);
        AppUserBasicInfoReq req = new AppUserBasicInfoReq();
        BeanUtils.copyProperties(basicInfo, req);
        if (basicInfo.getIsIdentified() == 0) {
            req.setIsIdentified(false);
        } else {
            req.setIsIdentified(true);
        }
        if (basicInfo.getMatch() == 0) {
            req.setMatch(false);
        } else {
            req.setMatch(true);
        }
        if (basicInfo.getSex() == 1) {
            req.setSex("F");
        } else {
            req.setSex("M");
        }

        if (StringUtils.isEmpty(basicInfo.getLastName()) && StringUtils.isEmpty(basicInfo.getEmail()) &&
                StringUtils.isEmpty(basicInfo.getChannel()) && StringUtils.isEmpty(basicInfo.getReligion()) && StringUtils.isEmpty(basicInfo.getLoanPurpose()) && StringUtils.isEmpty(basicInfo.getPostcode())) {
            req = null;
        }

        return JsonResult.ok(req);
    }

    @Override
    public AppUserBasicInfo getAppUserIdAndAvatarByPhone(String phone) {
        AppUserBasicInfo basicInfo = appUserInfoDao.queryAppUserIdAndAvatarByPhone(phone);
        String avatar = basicInfo.getProfileUrl();
        if (StringUtils.isNotBlank(avatar)) {
            basicInfo.setProfileUrl(aliOssUtil.getUrl(avatar));
        }
        return basicInfo;
    }

    @Override
    public JsonResult getUserIdentityInfo(Long userId) {
        List<AppUserJobInfo> jobInfos = appUserInfoDao.getUserIdentityInfo(userId);
        AppUserJobInfo userJobInfo = new AppUserJobInfo();
        if (CollectionUtils.isNotEmpty(jobInfos)) {
            if (StringUtils.isAllEmpty(jobInfos.get(0).getAddressDetail(), jobInfos.get(0).getState(), jobInfos.get(0).getDistrict(), jobInfos.get(0).getCounty(),
                    jobInfos.get(0).getTown())) {
                return JsonResult.ok(null);
            }
            userJobInfo.setAddressDetail(jobInfos.get(0).getAddressDetail());
            userJobInfo.setState(jobInfos.get(0).getState() == null ? "" : jobInfos.get(0).getState());
            userJobInfo.setDistrict(jobInfos.get(0).getDistrict() == null ? "" : jobInfos.get(0).getDistrict());
            userJobInfo.setCounty(jobInfos.get(0).getCounty() == null ? "" : jobInfos.get(0).getCounty());
            userJobInfo.setTown(jobInfos.get(0).getTown() == null ? "" : jobInfos.get(0).getTown());
            userJobInfo.setUserId(jobInfos.get(0).getUserId());
            userJobInfo.setVoterIdCard(jobInfos.get(0).getVoterIdCard());
            userJobInfo.setPancardAccount(jobInfos.get(0).getPancardAccount());
            userJobInfo.setEducationDegree(jobInfos.get(0).getEducationDegree());
            userJobInfo.setProfession(jobInfos.get(0).getProfession());
            userJobInfo.setPosition(jobInfos.get(0).getPosition());
            userJobInfo.setWorkYear(jobInfos.get(0).getWorkYear());
            userJobInfo.setCompanyName(jobInfos.get(0).getCompanyName());
            userJobInfo.setSalary(jobInfos.get(0).getSalary());
            userJobInfo.setCompanyPhone(jobInfos.get(0).getCompanyPhone());
            userJobInfo.setSchool(jobInfos.get(0).getSchool());
            userJobInfo.setSpecialty(jobInfos.get(0).getSpecialty());
            userJobInfo.setAlimony(jobInfos.get(0).getAlimony());
            userJobInfo.setEnrollmentYear(jobInfos.get(0).getEnrollmentYear());
            String imageUrl = null;
            if (StringUtils.isNotEmpty(jobInfos.get(0).getWorkCard())) {
                String[] strings = jobInfos.get(0).getWorkCard().split(",");
                imageUrl = "";
                for (int i = 0; i < strings.length; i++) {
                    String imgUrl = aliOssUtil.getUrl(strings[i]);
                    imageUrl += imgUrl + ",";
                }
            }
            userJobInfo.setWorkCard(imageUrl);
            for (AppUserJobInfo jobInfo : jobInfos) {
                //家庭住址类型:0
                if (null != jobInfo.getAddressType() && jobInfo.getAddressType().equals(ProductConstants.HOME_ADDRESS_TYPE)) {
                    userJobInfo.setAddressDetailOfHome(jobInfo.getAddressDetail());
                    userJobInfo.setStateOfHome(jobInfo.getState() == null ? "" : jobInfo.getState());
                    userJobInfo.setDistrictOfHome(jobInfo.getDistrict() == null ? "" : jobInfo.getDistrict());
                    userJobInfo.setCountyOfHome(jobInfo.getCounty() == null ? "" : jobInfo.getCounty());
                    userJobInfo.setTownOfHome(jobInfo.getTown() == null ? "" : jobInfo.getTown());
                    return JsonResult.ok(userJobInfo);
                }
            }
        }
        return JsonResult.ok(userJobInfo);
    }

    @Override
    public JsonResult getContactInfoById(Long userId) {
        List<AppUserContact> contacts = appUserInfoDao.getUserAllContact(userId);
        UserBindCardStatus detail = appUserInfoDao.getUserDetailById(userId);
        AppUserContactReq req = new AppUserContactReq();
        if (CollectionUtils.isNotEmpty(contacts)) {
            AppUserContact contact = new AppUserContact();
            AppUserContact anotherContact = new AppUserContact();
            if (contacts.get(1).getRelation().equals("Colleague") || contacts.get(1).getRelation().equals("Friend") || contacts.get(1).getRelation().equals("Classmate")) {
                //第一联系人
                contact = contacts.get(0);
                //第二联系人
                anotherContact = contacts.get(1);
            } else { //第一联系人
                contact = contacts.get(1);
                anotherContact = contacts.get(0);
            }


            req.setName(contact.getName());
            req.setPhone(contact.getPhone());
            req.setRelation(contact.getRelation());
            req.setUserId(contact.getUserId());

            req.setAnotherPhone(anotherContact.getPhone());
            req.setAnotherName(anotherContact.getName());
            req.setAnotherRelation(anotherContact.getRelation());
            if (null != detail) {
                req.setFacebookAccount(detail.getFacebookAccount());
                req.setWhatsappAccount(detail.getWhatsappAccount());
                req.setSkypeAccount(detail.getSkypeAccount());
            }
        }
        return JsonResult.ok(req);
    }

    // FIXME： app端在上传银行卡信息 某一步需要返回username + 这样写会导致app银行卡列表问题
    // app端应换个调用api
    @Override
    public AppUserBank getBankInfoById(Long userId) {

//        Optional<AppUserBank> appUserBank= Optional.ofNullable(appUserInfoDao.getBankInfoById(userId));
        AppUserBank appUserBank = Optional.ofNullable(appUserInfoDao.getBankInfoById(userId)).orElse(new AppUserBank());


//        if (!appUserBank.isPresent()){
//            return null;
//        }

        Optional.ofNullable(appUserInfoDao.getBasicById(userId))
                .ifPresent(userBasicInfo ->
                                appUserBank.setAccountName(userBasicInfo.getFirstName() + userBasicInfo.getLastName())
//                                appUserBank.get().setAccountName(userBasicInfo.getFirstName() + userBasicInfo.getLastName())
                );

//        return appUserBank.get();
        return appUserBank;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveLoanInfo(AppUserLoanInfo req) {
        int i = appUserInfoDao.saveLoanInfo(req);
        return i;
    }

    @Override
    public AppUserBasicInfo getBasicInfoById(Long userId) {
        AppUserBasicInfo basicInfo = appUserInfoDao.getBasicById(userId);
        return basicInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult updateVoterIdInfo(AppUserJobInfoReq req) {
        if (null == appUserInfoDao.getByUserId(req.getUserId())) {
            return JsonResult.errorException("user does not exist");
        }

        if (StringUtils.isBlank(req.getVoterIdCard())) {
            return JsonResult.errorException("Account name cannot be empty");
        }
        if (StringUtils.isBlank(req.getVoterFrontUrl()) || StringUtils.isBlank(req.getVoterBackUrl())) {
            return JsonResult.errorException("voterCard ID photo cannot be empty");
        }
        if (StringUtils.isBlank(req.getElectorsName())) {
            return JsonResult.errorException("Voter’s name can’t be empty");
        }
        if (StringUtils.isBlank(req.getRelationsName())) {
            return JsonResult.errorException("Relations name can’t be empty");
        }

        //去掉文件jpg后缀
        String frontUrl = FILE_BASE_URL + "/" + req.getVoterFrontUrl().substring(0, req.getVoterFrontUrl().length() - 3) + "pdf";
        String backUrl = FILE_BASE_URL + "/" + req.getVoterBackUrl().substring(0, req.getVoterBackUrl().length() - 3) + "pdf";

        File fileOfFront = new File(frontUrl);
        File fileOfBack = new File(backUrl);
        // 判断路径是否存在，如果不存在就创建一个
        if (!fileOfFront.getParentFile().exists() || !fileOfBack.getParentFile().exists()) {
            fileOfFront.getParentFile().mkdirs();
        } else {
            fileOfFront.delete();
            fileOfBack.delete();
            fileOfFront.getParentFile().mkdirs();
        }

        ArrayList<String> frontJpg = new ArrayList<>(Arrays.asList(aliOssUtil.getUrl(req.getVoterFrontUrl())));
        ArrayList<String> backJpg = new ArrayList<>(Arrays.asList(aliOssUtil.getUrl(req.getVoterBackUrl())));
        //jpg转pdf
        File pdfFileOfFront = ImageToPdf.Pdf(frontJpg, frontUrl);
        File pdfFileOfBack = ImageToPdf.Pdf(backJpg, backUrl);

        String imgUrlOfFront = aliOssUtil.uploadObject2OSS(pdfFileOfFront, "pdf/voter/");
        String imgUrlOfBack = aliOssUtil.uploadObject2OSS(pdfFileOfBack, "pdf/voter/");

        req.setVoterFrontPdf(imgUrlOfFront);
        req.setVoterBackPdf(imgUrlOfBack);

        AppUserJobInfo appUserJobInfo = new AppUserJobInfo();
        BeanUtils.copyProperties(req, appUserJobInfo);
        int result = 0;
        AppUserJobInfo userExpand = appUserInfoDao.getUserExpand(req.getUserId());
//        if (null != userExpand && StringUtils.isNotEmpty(userExpand.getVoterIdCard())) {
        if (null != userExpand) {
            result = appUserInfoDao.updateUserInfo(appUserJobInfo);
        } else {
            result = appUserInfoDao.saveUserInfo(appUserJobInfo);
        }
        if (result < 1) {
            log.info("Failed to save user’s voter card info");
            throw new IllegalArgumentException("Failed to save user’s voter card info");
//            return JsonResult.errorMsg("Failed to save user’s voter card info");
        }
        return JsonResult.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult savePanCardInfo(UserPanCardReq req) {
        if (StringUtils.isBlank(req.getHoldsFullName())) {
            return JsonResult.errorException("accountName can’t be empty");
        }
        if (StringUtils.isBlank(req.getCardFrontUrl()) || StringUtils.isBlank(req.getCardBackUrl())) {
            return JsonResult.errorException("No ID photo");
        }
        if (StringUtils.isBlank(req.getFathersName())) {
            return JsonResult.errorException("Father’s name can’t be empty");
        }
        if (StringUtils.isBlank(req.getPermanentNo())) {
            return JsonResult.errorException("Permanent account cannot be empty");
        }

        //去掉文件jpg后缀
        String frontUrl = FILE_BASE_URL + "/" + req.getCardFrontUrl().substring(0, req.getCardFrontUrl().length() - 3) + "pdf";
        String backUrl = FILE_BASE_URL + "/" + req.getCardBackUrl().substring(0, req.getCardBackUrl().length() - 3) + "pdf";

        File fileOfFront = new File(frontUrl);
        File fileOfBack = new File(backUrl);
        // 判断路径是否存在，如果不存在就创建一个
        if (!fileOfFront.getParentFile().exists() || !fileOfBack.getParentFile().exists()) {
            fileOfFront.getParentFile().mkdirs();
        } else {
            fileOfFront.delete();
            fileOfBack.delete();
            fileOfFront.getParentFile().mkdirs();
        }

        ArrayList<String> frontJpg = new ArrayList<>(Arrays.asList(aliOssUtil.getUrl(req.getCardFrontUrl())));
        ArrayList<String> backJpg = new ArrayList<>(Arrays.asList(aliOssUtil.getUrl(req.getCardBackUrl())));
        //jpg转pdf
        File pdfFileOfFront = ImageToPdf.Pdf(frontJpg, frontUrl);
        File pdfFileOfBack = ImageToPdf.Pdf(backJpg, backUrl);

        String imgUrlOfFront = aliOssUtil.uploadObject2OSS(pdfFileOfFront, "pdf/pancard/");
        String imgUrlOfBack = aliOssUtil.uploadObject2OSS(pdfFileOfBack, "pdf/pancard/");

        req.setCardFrontPdf(imgUrlOfFront);
        req.setCardBackPdf(imgUrlOfBack);
        UserPanCardModel model = new UserPanCardModel();
        BeanUtils.copyProperties(req, model);
        model.setCreateTime(DateUtil.getDate());

        if (req.getIsIdentified() != null) {
            if (req.getIsIdentified()) {
                model.setIsIdentified(1);
            } else {
                model.setIsIdentified(0);
            }
        }
        model.setBirthday(req.getBirthday());
        /*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            model.setDateOfBirth(sdf.parse(req.getDateOfBirth()));
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        UserPanCardModel cardModel = userPanCardDao.getPanCardInfo(req.getUserId());
        model.setMatchScore(req.getMatchScore());
        model.setMatch(req.getMatch());
        int result = 0;
        if (null == cardModel) {
            result = userPanCardDao.savePanCardInfo(model);
        } else {
            result = userPanCardDao.updatePanCardInfo(model);
        }
        AppUserJobInfo jobInfo = new AppUserJobInfo();
        jobInfo.setUserId(model.getUserId());
        jobInfo.setPancardAccount(model.getPermanentNo().trim());
        AppUserJobInfo userExpand = appUserInfoDao.getUserExpand(model.getUserId());
        AppUserBasicInfo appUserBasicInfo= new AppUserBasicInfo();
        appUserBasicInfo.setId(model.getUserId());
        appUserBasicInfo.setFirstName(model.getHoldsFullName());
        appUserInfoDao.updateById(appUserBasicInfo);
        if (null != userExpand) {
            jobInfo.setUpdateTime(userExpand.getUpdateTime());

            appUserInfoDao.updateUserInfo(jobInfo);
        } else {
            appUserInfoDao.saveUserInfo(jobInfo);
        }
        if (result < 1) {
            throw new IllegalArgumentException("Failed to save pan card info");
//            return JsonResult.errorMsg("Failed to save pan card info");
        }
        return JsonResult.ok();
    }

    @Override
    public void judgeUserLoanLevelAndLoanProduct(Long userId, Integer productId) {
        log.info("开始检查用户可借额度与产品金额");
        BigDecimal productAmount = orderClient.getLoanProductById(productId).getMaxAmount(); // max = min
        if (productAmount == null) {
            throw new IllegalArgumentException("something error, can't find the product");
        }
        Integer userLoanLevel = appUserInfoDao.queryUserLoanLevel(userId);
        log.info("user loan level: {}, product amount: {}", userLoanLevel, productAmount);
        if (productAmount.compareTo(new BigDecimal(userLoanLevel * 1000)) > 0) {
            throw new IllegalArgumentException("the product is locked to you");
        }
    }

    @Override
    public LoanStatusVo judgeCanBorrow(Long userId) {
        AppUserOrderInfo orderInfo = appUserInfoDao.getOrderById(userId);
        List<SysRefusalCycle> sysRefusalCycles = userInfoService.queryAllSysRefusalCycle();

        /**
         * 审批拒绝类型
         * 放款失败类型
         * 其他类型
         */

        SysRefusalCycle auditRefusalCycle = sysRefusalCycles.stream().filter(sysRefusalCycle1 -> sysRefusalCycle1.getCode() == 1)
                .collect(Collectors.toList()).get(0);
        SysRefusalCycle loanFialRefusalCycle = sysRefusalCycles.stream().filter(sysRefusalCycle1 -> sysRefusalCycle1.getCode() == 2)
                .collect(Collectors.toList()).get(0);
        SysRefusalCycle ontherRefusalCycle = sysRefusalCycles.stream().filter(sysRefusalCycle1 -> sysRefusalCycle1.getCode() == 3)
                .collect(Collectors.toList()).get(0);
        LoanStatusVo vo = new LoanStatusVo();
        if (null != orderInfo) {

            List<OrderOntherRefuse> orderOntherRefuses = orderClient.queryAllOrderOntherRefuse(orderInfo.getOrderNum());

            switch (orderInfo.getCheckStatus()) {
                //审批中
                case 1:
                    // 当前借款状态 0:无借款行为 1-审批中 2-拒绝 3-逾期 4-完成 5-未激活 6-已处置(催收) 7-展期申请中 8-还款中
                    vo.setLoanStatus(1);
                    break;
                //机审拒绝
                case 2:
                    vo.setLoanStatus(2);
                    if (CollectionUtils.isNotEmpty(orderOntherRefuses)) {
                        vo.setReOpenTime(DateUtil.getDate(orderInfo.getFinalAuditTime(), ontherRefusalCycle.getCycleDayCount()));
                    } else {
                        vo.setReOpenTime(DateUtil.getDate(orderInfo.getFinalAuditTime(), auditRefusalCycle.getCycleDayCount()));
                    }

                    break;
                //待初审
                case 3:
                    vo.setLoanStatus(1);
                    break;
                //人工初审拒绝
                case 4:
                    vo.setLoanStatus(2);
                    if (CollectionUtils.isNotEmpty(orderOntherRefuses)) {
                        vo.setReOpenTime(DateUtil.getDate(orderInfo.getFinalAuditTime(), ontherRefusalCycle.getCycleDayCount()));
                    } else {
                        vo.setReOpenTime(DateUtil.getDate(orderInfo.getFinalAuditTime(), auditRefusalCycle.getCycleDayCount()));
                    }

                    break;
                //待终审
                case 5:
                    vo.setLoanStatus(1);
                    break;
                //人工终审拒绝
                case 6:
                    vo.setLoanStatus(2);
                    if (CollectionUtils.isNotEmpty(orderOntherRefuses)) {
                        vo.setReOpenTime(DateUtil.getDate(orderInfo.getFinalAuditTime(), ontherRefusalCycle.getCycleDayCount()));
                    } else {
                        vo.setReOpenTime(DateUtil.getDate(orderInfo.getFinalAuditTime(), auditRefusalCycle.getCycleDayCount()));
                    }

                    break;
                //通过
                case 7:
                    //通过订单编号来关联user_loan的申请编号
                    AppUserLoanInfo userLoanInfo = appUserInfoDao.getLoanByLoanNum(orderInfo.getOrderNum());
                    if (null == userLoanInfo) {
                        vo.setLoanStatus(5);
                        break;
                    }
                    //还款中
                    if (ProductConstants.PAYMENT.equals(userLoanInfo.getLoanStatus())) {
                        vo.setLoanStatus(8);
                    }
                    //展期申请中
                    if (!ProductConstants.NOT_OVERDUE.equals(userLoanInfo.getOverdueStatus())) {
                        vo.setLoanStatus(7);
                    }
                    //逾期
                    if (ProductConstants.OVERDUE.equals(userLoanInfo.getLoanStatus())) {
                        vo.setLoanStatus(3);
                    }
                    //未激活
                    if (ProductConstants.NOT_ACTIVATED.equals(userLoanInfo.getLoanStatus())) {
                        vo.setLoanStatus(5);
                        if (CollectionUtils.isNotEmpty(orderOntherRefuses)) {
                            vo.setReOpenTime(DateUtil.getDate(orderInfo.getFinalAuditTime(), ontherRefusalCycle.getCycleDayCount()));
                        } else {
                            vo.setReOpenTime(DateUtil.getDate(orderInfo.getFinalAuditTime(), loanFialRefusalCycle.getCycleDayCount()));
                        }


                    }
                    //完成状态
                    if (ProductConstants.FINISH.equals(userLoanInfo.getLoanStatus())) {
                        vo.setLoanStatus(4);
                    }
                    //借据已失效  可以重新申请
                    if (ProductConstants.IS_INVALID.equals(userLoanInfo.getLoanStatus())) {
                        vo.setLoanStatus(4);
                    }
                    //已处置
                    if (ProductConstants.IS_DISPOSA.equals(userLoanInfo.getLoanStatus())) {
                        vo.setLoanStatus(6);
                    }
                    break;
                case 8:
                    if (orderInfo.getIsStock()==null||orderInfo.getIsStock()==0){
                        vo.setLoanStatus(5);
                    }else{
                        vo.setLoanStatus(0);
                    }

                    break;
                default:
                    break;
            }
        } else {
            vo.setLoanStatus(0);
        }
        return vo;
    }

    @Override
    public AppAccreditVo getAccredit(Long userId) {
        UserBindCardStatus bindCardStatus = appUserInfoDao.getUserDetailById(userId);

        AppAccreditVo vo = new AppAccreditVo();
        vo.setAadhaarStatus(true);
        if (null != bindCardStatus) {
            Date now = DateUtil.getDate();
            UserPanCardModel userPanCardModel = userPanCardDao.getPanCardInfo(userId);

            if ( StringUtils.isEmpty(bindCardStatus.getPanCardAccount())) {

                if (StringUtils.isNotEmpty(bindCardStatus.getVoterIdCard())) {
                    vo.setVoterIdCardStatus(true);
                }
                if (StringUtils.isNotEmpty(bindCardStatus.getPanCardAccount())) {
                    vo.setPanCardStatus(true);
                }
                vo.setNeedCertify(true);
            }
            if ( StringUtils.isNotEmpty(bindCardStatus.getPanCardAccount())) {
                vo.setPanCardStatus(true);
                if (StringUtils.isNotEmpty(bindCardStatus.getVoterIdCard())) {
                    vo.setVoterIdCardStatus(true);
                }
                AppUserOrderInfo order = appUserInfoDao.getOrderById(userId);
                String currentDate = DateUtil.getStringDate(now, DateUtil.DateFormat3);
                String bingDateVo = DateUtil.getStringDate(bindCardStatus.getUpdateTime(), DateUtil.DateFormat3);
                String bingDateAd = DateUtil.getStringDate(bindCardStatus.getUserUpdateTime(), DateUtil.DateFormat3);
                String bingDatePan = null;
                if (userPanCardModel != null) {
                    bingDatePan = DateUtil.getStringDate(userPanCardModel.getCreateTime(), DateUtil.DateFormat3);
                }
                if (null == order) {
                    vo.setNeedCertify(false);
                } else if (order.getCheckStatus() == 1 || order.getCheckStatus() == 2 || order.getCheckStatus() == 3 || order.getCheckStatus() == 4 || order.getCheckStatus() == 5 || order.getCheckStatus() == 6) {
                    Date notAuthorizaDate = DateUtil.getDate(order.getFinalAuditTime(), 29);

                    //    && !currentDate.equals(bingDate)&& !currentDate.equals(bingDate2)&& !currentDate.equals(bingDate3)
                    if (now.getTime() > notAuthorizaDate.getTime()) {
//                        if (!currentDate.equals(bingDateAd)) {
//                            vo.setAadhaarStatus(false);
//                        }
                        if (!currentDate.equals(bingDateVo)) {
                            vo.setVoterIdCardStatus(false);
                        }
                        if (!currentDate.equals(bingDatePan)) {
                            vo.setPanCardStatus(false);
                        }
                        if (!currentDate.equals(bingDatePan)) {
                            vo.setNeedCertify(true);
                        }

                    }
                } else if (order.getCheckStatus() == 7||order.getCheckStatus() ==8) {
                    Date notAuthorizaDate = DateUtil.getDate(order.getFinalAuditTime(), 89);
                    if (now.getTime() > notAuthorizaDate.getTime()) {
//                        if (!currentDate.equals(bingDateAd)) {
//                            vo.setAadhaarStatus(false);
//                        }
                        if (!currentDate.equals(bingDateVo)) {
                            vo.setVoterIdCardStatus(false);
                        }
                        if (!currentDate.equals(bingDatePan)) {
                            vo.setPanCardStatus(false);
                        }
                        if (!currentDate.equals(bingDatePan)) {
                            vo.setNeedCertify(true);
                        }
                    }
                }
            }
        } else {
            //需要认证
            vo.setNeedCertify(true);
        }
        return vo;
    }

    @Override
    public List<AppFAQInfo> getFAQInfo() {
        log.info("常见问题列表");
        List<AppFAQInfo> appFAQInfos = appUserInfoDao.queryFAQInfo();
        if (CollectionUtils.isEmpty(appFAQInfos)) {
            log.error("FAQ Content is empty");
            return null;
        }
        return appFAQInfos;
    }

    @Override
    public AppFAQInfo getFAQDetailById(Integer id) {
        log.info("常见问题详情 {}", id);
        AppFAQInfo appFAQInfo = appUserInfoDao.queryFAQDetailById(id);
        String[] split = null;
        if (StringUtils.isNotEmpty(appFAQInfo.getAnswerContext())) {
            split = appFAQInfo.getAnswerContext().split("\n\n");
        }
        appFAQInfo.setContentArray(split);
        if (null == appFAQInfo) {
            log.error("FAQ Content is empty");
            return null;
        }
        return appFAQInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult saveUserFeedback(PlatformUserFeedbackReq feedbackReq) {
        log.info("start api -saveUserFeedBack {}", feedbackReq.getUserId());
        PlatformUserFeedbackModel model = new PlatformUserFeedbackModel();
        BeanUtils.copyProperties(feedbackReq, model);
        int result = appUserInfoDao.saveUserFeedback(model);
        if (result < 1) {
            log.error("save userFeedBack error");
            throw new IllegalArgumentException("save userFeedBack error");
//            return JsonResult.errorMsg("save userFeedBackw error");
        }
        return JsonResult.ok("success!");
    }

    @Override
    public JsonResult getFlagBankInfo(Long userId) {
        List<Map<String, Object>> tmpMapList = orderClient.findFailFianceLoanByUserIdAndReason(userId);

        if (tmpMapList == null || tmpMapList.isEmpty()) {
            return JsonResult.ok(false);
        }
        return JsonResult.ok(true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult renewBankInfoTail(Long userId, String bankCode) {
        List<AppUserInfoHistoryEntity> appUserInfoHistoryEntities = appUserInfoDao.queryOrderHistoryByUserId(userId);
        AppUserInfoHistoryEntity appUserInfoHistoryEntity = appUserInfoHistoryEntities.get(appUserInfoHistoryEntities.size() - 1);
        AppUserBank appUserBank = appUserInfoDao.getBankInfoById(userId);
        appUserInfoHistoryEntity.setBankId(appUserBank.getId());
        appUserInfoDao.updateOrderHistoryBankIdById(appUserInfoHistoryEntity);
        return orderClient.renewBankInfoTail(userId, bankCode);
    }

    @Override
    public void saveRepaymentMode(AppUserRepayModeReq req) {

        AppUserBasicInfo userBasicInfo = Optional.ofNullable(appUserInfoDao.getByUserId(req.getUserId()))
                .orElseThrow(() -> new IllegalArgumentException("user does not exist"));

        AppUserRepayModeEntity modeEntity = new AppUserRepayModeEntity(req.getRepaymentType());
        modeEntity.setMobile(userBasicInfo.getCellPhone());
        modeEntity.setNetbankingName(req.getNetBankingName());
        modeEntity.setOtherRepayMode(req.getOtherRepaymentMethod());
        modeEntity.setCreateTime(new Date());
        modeEntity.setUpdateTime(modeEntity.getCreateTime());
        appUserInfoDao.saveRepayMode(modeEntity);
    }

    @Override
    public Boolean isAppUserRegistered(String phone) {
        if (!PhoneUtil.checkPhone(phone)) {
            throw new IllegalArgumentException("Phone number format is incorrect");
        }
        return null != appUserInfoDao.getByMobile(phone);
    }

    @Override
    public Boolean isSetPassword(String phone) {
        String pw = appUserInfoDao.getPwByPhone(phone);
        return StringUtils.isNotBlank(pw);
    }

    @Override
    public Integer getUserLoanLevel(Long userId) {
        return appUserInfoDao.queryUserLoanLevel(userId);
    }

    @Override
    public Integer getUserCountByDate(String date) {
        return appUserInfoDao.getUserCountByDate(date);
    }
}
