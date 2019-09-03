package com.cloud.order.service.impl;

import com.cloud.app.dao.AppLoanDao;
import com.cloud.kudos.utils.ImageUtils;
import com.cloud.model.FinanceLoanContract;
import com.cloud.model.user.UserAddress;
import com.cloud.order.config.KudosConfig;
import com.cloud.order.dao.*;
import com.cloud.order.model.KudosParamEntity;
import com.cloud.order.model.KudosResponseEntity;
import com.cloud.order.model.LoanCIBILEntity;
import com.cloud.order.model.LoanRequestEntity;
import com.cloud.order.model.kudos.*;
import com.cloud.order.model.req.*;
import com.cloud.order.service.KudosApiService;
import com.cloud.order.service.KudosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * kudos服务实现
 *
 * @author danquan.miao
 * @date 2019/8/23 0023
 * @since 1.0.0
 */
@Service
public class KudosServiceImpl implements KudosService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BasicDao basicDao;
    @Autowired
    private AppLoanDao appLoanDao;
    @Autowired
    private KudosDao kudosDao;

    @Autowired
    private KDLoanRequestDao kDLoanRequestDao;
    @Autowired
    private KDBorrowerInfoDao kDBorrowerInfoDao;
    @Autowired
    private KDDocumentUploadDao kDDocumentUploadDao;
    @Autowired
    private KDValidationGetDao kDValidationGetDao;
    @Autowired
    private KDValidationPostDao kDValidationPostDao;
    @Autowired
    private KDLoanDemandNoteDao kDLoanDemandNoteDao;
    @Autowired
    private KDReconciliationDao kDReconciliationDao;
    @Autowired
    private KDStatusCheckDao kDStatusCheckDao;
    @Autowired
    private KDNCCheckDao kDNCCheckDao;
    @Autowired
    private KDUpdateDocumentDao kDUpdateDocumentDao;
    @Autowired
    private KDLoanSettlementDao kDLoanSettlementDao;
    @Autowired
    private KDPGTxnNotifyDao kDPGTxnNotifyDao;

    @Autowired(required = false)
    private KudosApiService kudosApiService;

    @Autowired(required = false)
    private KudosConfig kudosConfig;

    @Override
    public int kudosApiFirstStep(String orderNo) {
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        if (loanRequestApiEntity == null) {
            loanRequestApiEntity = new LoanRequestApiEntity();
        }

        String name = "Query";
        String code = "1";
        String value = "";
        //kudos type
        KudosParamEntity kudosParam = kudosDao.queryKudosParams(name, code, value);
        //kudos url
        KudosParamEntity kudosParams = kudosDao.queryKudosParams("Sandbox", kudosConfig.getProduction(), value);//1测试url，2生产url
        KudosParamEntity kudosSecretkey = kudosDao.queryKudosParams("KUDOS", kudosConfig.getProduction(), value);//密钥
        LoanCIBILEntity loanCibil = orderDao.queryCibilInformation(orderNo);

        LoanRequestApiEntity resultApiEntity = new LoanRequestApiEntity();
        resultApiEntity.setPartnerBorrowId(loanRequestApiEntity.getPartnerBorrowId());

        String[] nameArea = loanRequestApiEntity.getBorrowerFName().split(" ");
        int count = 0;
        for (int i = 0; i < nameArea.length; i++) {
            count++;
        }
        if (count >= 3) {
            if (nameArea[0].length() == 1) {
                resultApiEntity.setBorrowerFName(nameArea[1].toUpperCase());
                resultApiEntity.setBorrowerLName(nameArea[2].toUpperCase());
                resultApiEntity.setBorrowerMName(nameArea[0].toUpperCase());
            } else {
                resultApiEntity.setBorrowerFName(nameArea[0].toUpperCase());
                resultApiEntity.setBorrowerLName(nameArea[1].toUpperCase());
                resultApiEntity.setBorrowerMName(nameArea[2].toUpperCase());
            }
        } else if (count == 2) {
            if (nameArea[0].length() == 1) {
                resultApiEntity.setBorrowerFName(nameArea[1].toUpperCase());
                resultApiEntity.setBorrowerMName(kudosConfig.getNA());
                resultApiEntity.setBorrowerLName(nameArea[0].toUpperCase());
            } else {
                resultApiEntity.setBorrowerFName(nameArea[0].toUpperCase());
                resultApiEntity.setBorrowerMName(kudosConfig.getNA());
                resultApiEntity.setBorrowerLName(nameArea[1].toUpperCase());
            }
        } else if (count == 1) {
            resultApiEntity.setBorrowerFName(nameArea[0].toUpperCase());
            resultApiEntity.setBorrowerMName(kudosConfig.getNA());
            resultApiEntity.setBorrowerLName(kudosConfig.getNA());
        }

        resultApiEntity.setBorrowerEmployerNme(loanCibil.getBorrowerCompanyName().toUpperCase());
        resultApiEntity.setBorrowerEmail(loanRequestApiEntity.getBorrowerEmail().toUpperCase());
        resultApiEntity.setBorrowerMob(loanRequestApiEntity.getBorrowerMob());

        String[] str = loanCibil.getBorrowerDOB().split("-");
        resultApiEntity.setBorrowerDob(str[2] + "-" + str[1] + "-" + str[0]);

        if (loanRequestApiEntity.getBorrowerSex().equals("0")) {
            resultApiEntity.setBorrowerSex("M");
        } else {
            resultApiEntity.setBorrowerSex("F");
        }

        resultApiEntity.setBorrowerPanNum(loanRequestApiEntity.getBorrowerPanNum().toUpperCase());
        resultApiEntity.setBorrowerAdhaarNum(loanRequestApiEntity.getBorrowerAdhaarNum());
        resultApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());
        resultApiEntity.setPartnerLoanStatus(loanRequestApiEntity.getPartnerLoanStatus());
        resultApiEntity.setPartnerLoanBucket("Others".toUpperCase());
        resultApiEntity.setLoanPurpose(loanRequestApiEntity.getLoanPurpose().toUpperCase());
        resultApiEntity.setLoanAmt(loanRequestApiEntity.getLoanAmt().replace(".00", ""));

        BigDecimal loanDisbAmount1 = new BigDecimal(loanRequestApiEntity.getLoanDisbursementAmt());
        String fees = new BigDecimal(loanRequestApiEntity.getLoanAmt()).subtract(loanDisbAmount1).toString();
        double fe = Double.valueOf(fees);
        resultApiEntity.setLoanProcFee(Math.round(fe) + "");
        resultApiEntity.setLoanConvFee("0");

        String loanDisbAmount = loanRequestApiEntity.getLoanDisbursementAmt();
        int index = loanDisbAmount.indexOf(".");
        resultApiEntity.setLoanDisbursementAmt(loanDisbAmount.substring(0, index));
        resultApiEntity.setLoanTyp("Bullet".toUpperCase());
        resultApiEntity.setLoanInstallmentNum("1");
        resultApiEntity.setLoanTenure(loanRequestApiEntity.getLoanTenure());
//        resultApiEntity.setSecretKey(kudosSecretkey.getValue());
        resultApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");

        return kudosApiService.kudosLoanRequest(resultApiEntity, kudosParam.getValue(), kudosParams.getValue(), orderNo);
    }

    @Override
    public int kudosApiSecondStep(String orderNo) {
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        if (loanRequestApiEntity == null) {
            loanRequestApiEntity = new LoanRequestApiEntity();
        }
        String name = "Query";
        String code = "2";
        String value = "";
        KudosParamEntity kudosParam = kudosDao.queryKudosParams(name, code, value);
        KudosParamEntity kudosParams = kudosDao.queryKudosParams("Sandbox", kudosConfig.getProduction(), value);
        KudosParamEntity kudosSecretkey = kudosDao.queryKudosParams("KUDOS", kudosConfig.getProduction(), value);
        String kudosType = "Loan-Request";
//        KudosResponseEntity kudosResponse = kudosDao.selectKudosResponse(orderNo, kudosType);版本1.0
        KDLoanRequestReq requestReq = new KDLoanRequestReq();
        requestReq.setOrderNo(orderNo);
        requestReq.setResultCode("200");
        KDLoanRequestReq kudosResponse = kDLoanRequestDao.selectOne(requestReq);//版本2.0
        LoanCIBILEntity loanCibil = orderDao.queryCibilInformation(orderNo);
        DecimalFormat df = new DecimalFormat("0.00");//设置保留位数
        String userId = loanCibil.getUserId();
        String adsType = "0";//0>家庭地址,1>单位地址
        String adsType1 = "1";
        UserAddress homeAddress = orderDao.queryUserAds(userId, adsType);
        UserAddress companyAddress = orderDao.queryUserAds(userId, adsType1);
        if (isInteger(loanCibil.getPostcode())) {
            if (loanCibil.getPostcode().length() == 6) {
            } else {
                loanCibil.setPostcode("000000");
            }
        } else {
            loanCibil.setPostcode("000000");
        }
        BorrowerInfoApiEntity biApiEntity = orderDao.queryBorrowerInfo(orderNo);
        if (biApiEntity == null) {
            biApiEntity = new BorrowerInfoApiEntity();
        }
        biApiEntity.setPartnerBorrowerId(loanRequestApiEntity.getPartnerBorrowId());
        biApiEntity.setKudosBorrowerId(kudosResponse.getKudosborrowerid());
        biApiEntity.setBorrowerEmail(loanCibil.getBorrowerEmail().toUpperCase());
        biApiEntity.setBorrowerEmailId(loanCibil.getBorrowerEmail().toUpperCase());
        biApiEntity.setBorrowerMob(loanCibil.getBorrowerPhone());
        biApiEntity.setBorrowerCurrAddr(companyAddress.getAddressDetail());
        biApiEntity.setBorrowerCurrCity(homeAddress.getDistrict().toUpperCase());
        biApiEntity.setBorrowerCurrPincode(loanCibil.getPostcode());
        biApiEntity.setBorrowerCurrState(homeAddress.getState().toUpperCase());
        biApiEntity.setBorrowerPermAddress(homeAddress.getAddressDetail());
        biApiEntity.setBorrowerPermCity(homeAddress.getDistrict());
        biApiEntity.setBorrowerPermPincode(loanCibil.getPostcode());
        biApiEntity.setBorrowerPermState(homeAddress.getState());
        //        Married unmarried widow and widower
        if (biApiEntity.getBorrowerMaritalStatus().equals("Unmarried")) {
            biApiEntity.setBorrowerMaritalStatus("unmarried".toUpperCase());
        }
        if (biApiEntity.getBorrowerMaritalStatus().equals("Married but childless")) {
            biApiEntity.setBorrowerMaritalStatus("Married".toUpperCase());
        }
        if (biApiEntity.getBorrowerMaritalStatus().equals("Married with children")) {
            biApiEntity.setBorrowerMaritalStatus("Married".toUpperCase());
        }
        if (biApiEntity.getBorrowerMaritalStatus().equals("Divorced/Widowed")) {
            if (biApiEntity.getSex().equals("0")) {
                biApiEntity.setBorrowerMaritalStatus("widower".toUpperCase());//0
            } else {
                biApiEntity.setBorrowerMaritalStatus("widow".toUpperCase());//1
            }
        }
        biApiEntity.setBorrowerEmployerNme(loanCibil.getBorrowerCompanyName().toUpperCase());
        biApiEntity.setBorrowerSalary(loanCibil.getBorrowerIncome().toUpperCase());

//        String cibilScore  = kudosDao.queryCibilScoreByOrderNo(orderNo);停用
        ValidationPost validationPost = kudosDao.queryValidationPostCibilScore(orderNo);
        if (validationPost == null) {
            validationPost = new ValidationPost();
            validationPost.setBorrowerCreditScore(kudosConfig.getNA());
        }
        biApiEntity.setBorrowerCreditScore(validationPost.getBorrowerCreditScore().replaceAll("^(0+)", ""));
        biApiEntity.setBorrowerFoir("40-60");
        biApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());
        biApiEntity.setKudosLoanId(kudosResponse.getKudosloanid());
        biApiEntity.setLoanTenure(loanRequestApiEntity.getLoanTenure());
        biApiEntity.setLoanInstallmentNum("1");
        biApiEntity.setLoanEmiFreq(kudosConfig.getNA());
        String loanAmount = loanRequestApiEntity.getLoanAmt();
        int index = loanAmount.indexOf(".");
        biApiEntity.setLoanPrinAmt(loanAmount.substring(0, index));
        BigDecimal loanDisbAmount1 = new BigDecimal(loanRequestApiEntity.getLoanDisbursementAmt());
        String fees = new BigDecimal(loanRequestApiEntity.getLoanAmt()).subtract(loanDisbAmount1).toString();
        double fe = Double.valueOf(fees);
        biApiEntity.setLoanProcFee(Math.round(fe) + "");
        biApiEntity.setLoanConvFee("0");
        biApiEntity.setLoanCouponAmt("0");
        String loanDisbAmount2 = loanRequestApiEntity.getLoanDisbursementAmt();
        double disbursed = Double.valueOf(loanDisbAmount2);
        biApiEntity.setLoanAmtDisbursed(Math.round(disbursed) + "");
        biApiEntity.setLoanIntRt("36");
        BigDecimal interestAmount = new BigDecimal(loanAmount).multiply(new BigDecimal(0.36f))
                .setScale(0, BigDecimal.ROUND_HALF_UP);
        biApiEntity.setLoanIntAmt(Math.round(interestAmount.doubleValue()) + "");
        String[] realCloseDate = loanRequestApiEntity.getRealClosingDate().split("-");
        biApiEntity.setLoanEmiDte1(realCloseDate[1] + "-" + realCloseDate[2] + "-" + realCloseDate[0]);
        Double emiAmount = new BigDecimal(loanAmount).add(interestAmount).doubleValue();
        biApiEntity.setLoanEmiAmt1(Math.round(emiAmount) + "");
        biApiEntity.setLoanEmiAmt2("0");
        String[] endTostr = loanRequestApiEntity.getLoanEndDate().split("-");
        biApiEntity.setLoanEndDte(endTostr[1] + "-" + endTostr[2] + "-" + endTostr[0]);
        String contractTypes = "B";
        FinanceLoanContract loanContracts = orderDao.queryContract(orderNo, contractTypes);
        biApiEntity.setLoanSanctionLetter(kudosConfig.getOssUrl() + loanContracts.getContractUrl());
        String[] startTostr = loanRequestApiEntity.getLoanStartDate().split("-");
        biApiEntity.setLoanDisbursementUpdStatus("DISBURSEMENT COMPLERED");
        biApiEntity.setLoanDisbursementUpdDte(startTostr[1] + "-" + startTostr[2] + "-" + startTostr[0]);
        biApiEntity.setLoanDisbursementTransDte(startTostr[1] + "-" + startTostr[2] + "-" + startTostr[0]);
        biApiEntity.setLoanEmiRecdNum("0");
        biApiEntity.setKudosLoanTyp("Bullet".toUpperCase());
        biApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");
        return kudosApiService.kudosBorrowerInfo(biApiEntity, kudosParam.getValue(), kudosParams.getValue(), orderNo);
    }

    @Override
    public int kudosApiThirdStep(String orderNo) {
        LoanScheduleApiEntity lsApiEntity = new LoanScheduleApiEntity();
        String name = "Query";
        String code = "3";
        String value = "";
        KDValidationPostReq validationPostReq = new KDValidationPostReq();
        validationPostReq.setOrderNo(orderNo);
        validationPostReq.setResultCode("200");
        KDValidationPostReq kdValidationPostReq = kDValidationPostDao.selectOne(validationPostReq);
        LoanCIBILEntity loanCibil = orderDao.queryCibilInformation(orderNo);
        KudosParamEntity kudosParam = kudosDao.queryKudosParams(name, code, value);
        KudosParamEntity kudosParams = kudosDao.queryKudosParams("Sandbox", kudosConfig.getProduction(), value);
        KudosParamEntity kudosSecretkey = kudosDao.queryKudosParams("KUDOS", kudosConfig.getProduction(), value);
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        LoanRequestEntity loanEntity = orderDao.queryUserInformation(orderNo);
        String[] startTostr = loanEntity.getRealClosingDate().split("-");
//        lsApiEntity.setPartnerBorrowerId(kudosResponse.getOrderNo());
        lsApiEntity.setPartnerBorrowerId(loanRequestApiEntity.getPartnerBorrowId());
        lsApiEntity.setKudosBorrowerId(kdValidationPostReq.getKudosborrowerid());
        lsApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());
        lsApiEntity.setKudosLoanId(kdValidationPostReq.getKudosloanid());
        lsApiEntity.setLoanEmiAmt1(loanCibil.getBorrowerRequestAmount().replace(".00", ""));
        lsApiEntity.setLoanEmiDte1(startTostr[1] + "-" + startTostr[2] + "-" + startTostr[0]);
        lsApiEntity.setLoanEmiAmt2("0");
        lsApiEntity.setLoanEmiAmt3("0");
        lsApiEntity.setLoanEmiAmt4("0");
        lsApiEntity.setLoanEmiAmt5("0");
        lsApiEntity.setLoanEmiAmt6("0");
        lsApiEntity.setLoanEmiAmt7("0");
        lsApiEntity.setLoanEmiAmt8("0");
        lsApiEntity.setLoanEmiAmt9("0");
        lsApiEntity.setLoanEmiAmt10("0");
        lsApiEntity.setLoanEmiAmt11("0");
        lsApiEntity.setLoanEmiAmt12("0");
        lsApiEntity.setLoanEmiAmt13("0");
        lsApiEntity.setLoanEmiAmt14("0");
        lsApiEntity.setLoanEmiAmt15("0");
        lsApiEntity.setLoanEmiAmt16("0");
        lsApiEntity.setLoanEmiAmt17("0");
        lsApiEntity.setLoanEmiAmt18("0");
        lsApiEntity.setLoanEmiAmt19("0");
        lsApiEntity.setLoanEmiAmt20("0");
        lsApiEntity.setLoanEmiAmt21("0");
        lsApiEntity.setLoanEmiAmt22("0");
        lsApiEntity.setLoanEmiAmt23("0");
        lsApiEntity.setLoanEmiAmt24("0");
        lsApiEntity.setLoanEmiAmt25("0");
        lsApiEntity.setLoanEmiAmt26("0");
        lsApiEntity.setLoanEmiAmt27("0");
        lsApiEntity.setLoanEmiAmt28("0");
        lsApiEntity.setLoanEmiAmt29("0");
        lsApiEntity.setLoanEmiAmt30("0");
        lsApiEntity.setLoanEmiAmt31("0");
        lsApiEntity.setLoanEmiAmt32("0");
        lsApiEntity.setLoanEmiAmt33("0");
        lsApiEntity.setLoanEmiAmt34("0");
        lsApiEntity.setLoanEmiAmt35("0");
        lsApiEntity.setLoanEmiAmt36("0");
        lsApiEntity.setLoanEmiAmt37("0");
        lsApiEntity.setLoanEmiAmt38("0");
        lsApiEntity.setLoanEmiAmt39("0");
        lsApiEntity.setLoanEmiAmt40("0");
        lsApiEntity.setLoanEmiAmt41("0");
        lsApiEntity.setLoanEmiAmt42("0");
        lsApiEntity.setLoanEmiAmt43("0");
        lsApiEntity.setLoanEmiAmt44("0");
        lsApiEntity.setLoanEmiAmt45("0");
        lsApiEntity.setLoanEmiAmt46("0");
        lsApiEntity.setLoanEmiAmt47("0");
        lsApiEntity.setLoanEmiAmt48("0");
        lsApiEntity.setLoanEmiAmt49("0");
        lsApiEntity.setLoanEmiAmt50("0");
        lsApiEntity.setLoanEmiAmt51("0");
        lsApiEntity.setLoanEmiAmt52("0");
        lsApiEntity.setLoanEmiAmt53("0");
        lsApiEntity.setLoanEmiDte2(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte3(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte4(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte5(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte6(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte7(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte8(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte9(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte10(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte11(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte12(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte13(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte14(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte15(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte16(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte17(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte18(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte19(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte20(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte21(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte22(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte23(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte24(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte25(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte26(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte27(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte28(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte29(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte30(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte31(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte32(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte33(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte34(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte35(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte36(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte37(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte38(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte39(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte40(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte41(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte42(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte43(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte44(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte45(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte46(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte47(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte48(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte49(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte50(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte51(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte52(kudosConfig.getNA());
        lsApiEntity.setLoanEmiDte53(kudosConfig.getNA());
        lsApiEntity.setLoanPostDisbursementStatus("open".toUpperCase());
        lsApiEntity.setLoanEmiRecdNum("1");
        lsApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");
        return kudosApiService.kudosLoanSchedule(lsApiEntity, kudosParam.getValue(), kudosParams.getValue(), orderNo);
    }

    @Override
    public int kudosApiFourthStep(String orderNo) {
        String name = "Query";
        String code = "8";
        String value = "";
        KudosParamEntity kudosParam = kudosDao.queryKudosParams(name, code, value);
        KudosParamEntity kudosParams = kudosDao.queryKudosParams("Sandbox", kudosConfig.getProduction(), value);
        KudosParamEntity kudosSecretkey = kudosDao.queryKudosParams("KUDOS", kudosConfig.getProduction(), value);
        KDBorrowerInfoReq borrowerInfoReq = new KDBorrowerInfoReq();
        borrowerInfoReq.setOrderNo(orderNo);
        borrowerInfoReq.setResultCode("200");
        KDBorrowerInfoReq kdBorrowerInfoReq = kDBorrowerInfoDao.selectOne(borrowerInfoReq);
        //借据合同
        String contractTypes = "B";
        FinanceLoanContract loanContracts = orderDao.queryContract(orderNo, contractTypes);
        UploadDocumentApiEntity udApiEntity = orderDao.queryUpdocumentApiInfo(orderNo);
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        udApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());
        udApiEntity.setKudosLoanId(kdBorrowerInfoReq.getKudosloanid());
        udApiEntity.setKudosBorrowerId(kdBorrowerInfoReq.getKudosborrowerid());
        udApiEntity.setBorrowerCibilDoc("KUD_CIBIL");
        udApiEntity.setLoanDataReqFlg("1");
        udApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");
        udApiEntity.setBorrowerAdhaarDoc(ImageUtils.fileToString(kudosConfig.getOssUrl() + udApiEntity.getBorrowerAdhaarDoc()));
        udApiEntity.setBorrowerBnkStmtDoc(ImageUtils.fileToString(kudosConfig.getOssUrl() + udApiEntity.getBorrowerBnkStmtDoc()));
        udApiEntity.setBorrowerPanDoc(ImageUtils.fileToString(kudosConfig.getOssUrl() + udApiEntity.getBorrowerPanDoc()));
        udApiEntity.setBorrowerPhotoDoc(ImageUtils.fileToString(kudosConfig.getOssUrl() + udApiEntity.getBorrowerPhotoDoc()));
        udApiEntity.setLoanSanctionLetterDoc(ImageUtils.fileToString(kudosConfig.getOssUrl() + loanContracts.getContractUrl()));
        udApiEntity.setLoanAgreementDoc(ImageUtils.fileToString(kudosConfig.getOssUrl() + loanContracts.getContractUrl()));
        return kudosApiService.kudosUploadDocument(udApiEntity, kudosParam.getValue(), kudosParams.getValue(), orderNo);
    }

    @Override
    public int kudosApiGetFifthStep(String orderNo) {
        String name = "Query";
        String code = "9";
        String value = "";
        KudosParamEntity kudosParam = kudosDao.queryKudosParams(name, code, value);
        KudosParamEntity kudosParams = kudosDao.queryKudosParams("Sandbox", kudosConfig.getProduction(), value);
        KudosBaseApiEntity kudosBaseApiEntity = new KudosBaseApiEntity();
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        KDDocumentUploadReq kdDocumentUploadReq = new KDDocumentUploadReq();
        kdDocumentUploadReq.setOrderNo(orderNo);
        kdDocumentUploadReq.setResultCode("200");
        KDDocumentUploadReq documentUploadReq = kDDocumentUploadDao.selectOne(kdDocumentUploadReq);
        kudosBaseApiEntity.setKudosBorrowerId(documentUploadReq.getKudosborrowerid());
        kudosBaseApiEntity.setKudosLoanId(documentUploadReq.getKudosloanid());
        kudosBaseApiEntity.setPartnerBorrowerId(loanRequestApiEntity.getPartnerBorrowId());
        kudosBaseApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());
        kudosBaseApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");
        return kudosApiService.kudosGetValidation(kudosBaseApiEntity, kudosParam.getValue(), kudosParams.getValue(), orderNo);
    }

    @Override
    public int kudosApiPostFifthStep(String orderNo) {
        String name = "Query";
        String code = "10";
        String value = "";
        KudosParamEntity kudosParam = kudosDao.queryKudosParams(name, code, value);
        KudosParamEntity kudosParams = kudosDao.queryKudosParams("Sandbox", kudosConfig.getProduction(), value);
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        KDValidationGetReq validationGetReq = new KDValidationGetReq();
        validationGetReq.setOrderNo(orderNo);
        validationGetReq.setResultCode("200");
        KDValidationGetReq kdValidationGetReq = kDValidationGetDao.selectOne(validationGetReq);
        ValidationPost validationPost = kudosDao.queryValidationPostParams(orderNo);
        ValidationPost validationPostScore = kudosDao.queryValidationPostCibilScore(orderNo);
        if (validationPostScore == null) {
            validationPostScore = new ValidationPost();
            validationPostScore.setBorrowerCreditScore(kudosConfig.getNA());
        }
        ValidationPostApiEntity validationPostApiEntity = new ValidationPostApiEntity();
        validationPostApiEntity.setBorrowerAdhaarNum(validationPost.getBorrowerAdhaarNum());
        validationPostApiEntity.setBorrowerCreditScore(validationPostScore.getBorrowerCreditScore().replaceAll("^(0+)", ""));
        validationPostApiEntity.setBorrowerPanNum(validationPost.getBorrowerPanNum());
        validationPostApiEntity.setKudosBorrowerId(kdValidationGetReq.getKudosborrowerid());
        validationPostApiEntity.setPartnerBorrowerId(loanRequestApiEntity.getPartnerBorrowId());
        validationPostApiEntity.setKudosLoanId(kdValidationGetReq.getKudosloanid());
        validationPostApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());

        validationPostApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");
        return kudosApiService.kudosPostValidation(validationPostApiEntity, kudosParam.getValue(), kudosParams.getValue(), orderNo);
    }

    @Override
    public int kudosApiSixthStep(String orderNo) {
        String name = "Query";
        String code = "11";
        String value = "";
//        String kudosType = "Loan-Request";
        KudosParamEntity kudosParam = kudosDao.queryKudosParams(name, code, value);
        KudosParamEntity kudosParams = kudosDao.queryKudosParams("Sandbox", kudosConfig.getProduction(), value);
        KudosParamEntity kudosSecretkey = kudosDao.queryKudosParams("KUDOS", kudosConfig.getProduction(), value);
//        KudosResponseEntity kudosResponse = kudosDao.selectKudosResponse(orderNo, kudosType);kudos1.0
        KDValidationPostReq kdValidationPostReq = new KDValidationPostReq();
        kdValidationPostReq.setOrderNo(orderNo);
        kdValidationPostReq.setResultCode("200");
        KDValidationPostReq validationPostReq = kDValidationPostDao.selectOne(kdValidationPostReq);
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        LoanTrancheAppendApiEntity ltaApiEntity = new LoanTrancheAppendApiEntity();
        List<LoanRspParams> loanList = new ArrayList<>();
        ltaApiEntity.setKudosBorrowerId(validationPostReq.getKudosborrowerid());
        ltaApiEntity.setKudosLoanId(validationPostReq.getKudosloanid());
        //需要统计的数据从审核订单队列中提取，prod待解决
        //测试先调通
        ltaApiEntity.setLoanDisbursementDte("16-07-2019");
        ltaApiEntity.setLoanTrancheAmt("3980.10");
        ltaApiEntity.setLoanTrancheId("KUD1607201922072019");
        ltaApiEntity.setLoanTrancheNum("1");

        ltaApiEntity.setPartnerBorrowerId(loanRequestApiEntity.getPartnerBorrowId());
        ltaApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());
        ltaApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");
        return kudosApiService.kudosLoanTrancheAppend(ltaApiEntity, kudosParam.getValue(), kudosParams.getValue(), orderNo);
    }

    @Override
    public int kudosLoanDemandNote(String orderNo) {
        String name = "Query";
        String code = "12";
        String value = "";
        KudosParamEntity kudosParam = kudosDao.queryKudosParams(name, code, value);
        KudosParamEntity kudosParams = kudosDao.queryKudosParams("Sandbox", kudosConfig.getProduction(), value);
        KudosParamEntity kudosSecretkey = kudosDao.queryKudosParams("KUDOS", kudosConfig.getProduction(), value);
        KDValidationPostReq kdValidationPostReq = new KDValidationPostReq();
        kdValidationPostReq.setOrderNo(orderNo);
        kdValidationPostReq.setResultCode("200");
        KDValidationPostReq validationPostReq = kDValidationPostDao.selectOne(kdValidationPostReq);
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        LoanDemandNoteApiEntity ldnApiEntity = new LoanDemandNoteApiEntity();
        ldnApiEntity.setKudosBorrowerId(validationPostReq.getKudosborrowerid());
        ldnApiEntity.setKudosLoanId(validationPostReq.getKudosloanid());
        ldnApiEntity.setPartnerBorrowerId(loanRequestApiEntity.getPartnerBorrowId());
        ldnApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());
        ldnApiEntity.setLoanDemandNoteIssued("ISSUED");
        ldnApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");
        return kudosApiService.kudosLoanDemandNote(ldnApiEntity, kudosParam.getValue(), kudosParams.getValue(), orderNo);
    }

    public String queryKudosParam(String name, String code, String value) {
        KudosParamEntity kudosParam = kudosDao.queryKudosParams(name, code, value);
        if (kudosParam == null) {
            return "";
        }
        return kudosParam.getValue();
    }

    public LoanRequestApiEntity queryLoanRequestApi(String orderNo) {
        LoanRequestApiEntity requestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        if (requestApiEntity == null) {
            return new LoanRequestApiEntity();
        }
        return requestApiEntity;
    }

    @Override
    public int kudosReconciliation(String orderNo) {
        String name = "Query";
        String code = "13";
        String value = "";
        KDLoanDemandNoteReq kdLoanDemandNoteReq = new KDLoanDemandNoteReq();
        kdLoanDemandNoteReq.setOrderNo(orderNo);
        kdLoanDemandNoteReq.setResultCode("200");
        KDLoanDemandNoteReq noteReq = kDLoanDemandNoteDao.selectOne(kdLoanDemandNoteReq);
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        ReconciliationApiEntity rApiEntity = new ReconciliationApiEntity();
        rApiEntity.setKudosBorrowerId(noteReq.getKudosborrowerid());
        rApiEntity.setKudosLoanId(noteReq.getKudosloanid());
        rApiEntity.setLoanReconDte(LocalDate.now() + "");//暂时获取当前时间，时间待确认
        rApiEntity.setLoanReconStatus("Loan Repayment Extension");//状态暂定
        rApiEntity.setPartnerBorrowerId(loanRequestApiEntity.getPartnerBorrowId());
        rApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());
        rApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");
        return kudosApiService.kudosReconciliation(rApiEntity, queryKudosParam(name, code, value), queryKudosParam("Sandbox", kudosConfig.getProduction(), value), orderNo);
    }

    @Override
    public int kudosStatusCheck(String orderNo) {
        String name = "Query";
        String code = "15";
        String value = "";
        KDReconciliationReq kdReconciliationReq = new KDReconciliationReq();
        kdReconciliationReq.setOrderNo(orderNo);
        kdReconciliationReq.setResultCode("200");
        KDReconciliationReq reconciliationReq = kDReconciliationDao.selectOne(kdReconciliationReq);
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        StatusCheckApiEntity statusCheckApiEntity = new StatusCheckApiEntity();
        statusCheckApiEntity.setKudosBorrowerId(reconciliationReq.getKudosborrowerid());
        statusCheckApiEntity.setKudosLoanId(reconciliationReq.getKudosloanid());
        String loanDisbursementUpdStatus = "DISBUSED-" + LocalDate.now();
        statusCheckApiEntity.setLoanDisbursementUpdStatus(loanDisbursementUpdStatus);//如果没有更新，则参数为null
        statusCheckApiEntity.setLoanReconStatus("PENDING");//Pending / Active / Closed
        statusCheckApiEntity.setPartnerBorrowerId(loanRequestApiEntity.getPartnerBorrowId());
        statusCheckApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());
        statusCheckApiEntity.setPartnerLoanComments("loan has been given to customer, customer recieved");//This is a comment.
        statusCheckApiEntity.setPartnerLoanStatus("PENDING");//Active /Disbused/ Repayment/ Extension/ EMISkipped/ PreClosure/ Closed/ RepaymentStarted/ EMIRecieved
        statusCheckApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");
        return kudosApiService.kudosStatusCheck(statusCheckApiEntity, queryKudosParam(name, code, value), queryKudosParam("Sandbox", kudosConfig.getProduction(), value), orderNo);
    }

    @Override
    public int kudosNCCheck(String orderNo) {
        String name = "Query";
        String code = "16";
        String value = "";
        KDStatusCheckReq kdStatusCheckReq = new KDStatusCheckReq();
        kdStatusCheckReq.setOrderNo(orderNo);
        kdStatusCheckReq.setResultCode("200");
        KDStatusCheckReq statusCheckReq = kDStatusCheckDao.selectOne(kdStatusCheckReq);
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        NCCheckApiEntity ncCheckApiEntity = new NCCheckApiEntity();
        ncCheckApiEntity.setKudosBorrowerId(statusCheckReq.getKudosborrowerid());
        ncCheckApiEntity.setKudosLoanId(statusCheckReq.getKudosloanid());
        ncCheckApiEntity.setLoanNcStatus("CHECK");//CHECK/UPDATE
        ncCheckApiEntity.setPartnerBorrowerId(loanRequestApiEntity.getPartnerBorrowId());
        ncCheckApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());
        ncCheckApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");
        return kudosApiService.kudosNCCheck(ncCheckApiEntity, queryKudosParam(name, code, value), queryKudosParam("Sandbox", kudosConfig.getProduction(), value), orderNo);
    }

    /**
     * document-update   接口参数变更；
     * update-document共用
     */
    @Override
    public int kudosUpdateDocument(String orderNo) {
        String name = "Query";
        String code = "17";
        String value = "";
        KDNCCheckReq kdncCheckReq = new KDNCCheckReq();
        kdncCheckReq.setOrderNo(orderNo);
        kdncCheckReq.setResultCode("200");
        KDNCCheckReq checkReq = kDNCCheckDao.selectOne(kdncCheckReq);

        //借据合同
        String contractTypes = "B";
        FinanceLoanContract loanContracts = orderDao.queryContract(orderNo, contractTypes);
        UploadDocumentApiEntity udApiEntity = orderDao.queryUpdocumentApiInfo(orderNo);
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        udApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());
        udApiEntity.setPartnerBorrowerId(loanRequestApiEntity.getPartnerBorrowId());
        udApiEntity.setKudosLoanId(checkReq.getKudosloanid());
        udApiEntity.setKudosBorrowerId(checkReq.getKudosborrowerid());
//        udApiEntity.setLoanDataReqFlg("1");
        udApiEntity.setBorrowerCibilDoc("KUD_CIBIL");
        udApiEntity.setBorrowerAdhaarDoc(ImageUtils.fileToString(kudosConfig.getOssUrl() + udApiEntity.getBorrowerAdhaarDoc()));
        udApiEntity.setBorrowerBnkStmtDoc(ImageUtils.fileToString(kudosConfig.getOssUrl() + udApiEntity.getBorrowerBnkStmtDoc()));
        udApiEntity.setBorrowerPanDoc(ImageUtils.fileToString(kudosConfig.getOssUrl() + udApiEntity.getBorrowerPanDoc()));
        udApiEntity.setBorrowerPhotoDoc(ImageUtils.fileToString(kudosConfig.getOssUrl() + udApiEntity.getBorrowerPhotoDoc()));
        udApiEntity.setLoanSanctionLetterDoc(ImageUtils.fileToString(kudosConfig.getOssUrl() + loanContracts.getContractUrl()));
        udApiEntity.setLoanAgreementDoc(ImageUtils.fileToString(kudosConfig.getOssUrl() + loanContracts.getContractUrl()));
        udApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");
        return kudosApiService.kudosUpdateDocument(udApiEntity, queryKudosParam(name, code, value), queryKudosParam("Sandbox", kudosConfig.getProduction(), value), orderNo);
    }

    /**
     * http://api.kudosfinance.in/docs/partners/apiv2/loan_settlement.html
     */
    @Override
    public int kudosLoanSettlement(String orderNo) {
        String name = "Query";
        String code = "18";
        String value = "";
        KDUpdateDocumentReq kdUpdateDocumentReq = new KDUpdateDocumentReq();
        kdUpdateDocumentReq.setOrderNo(orderNo);
        kdUpdateDocumentReq.setResultCode("200");
        KDUpdateDocumentReq updateDocumentReq = kDUpdateDocumentDao.selectOne(kdUpdateDocumentReq);
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);

        PartnerLoanSettleApiEntity plsApiEntity = new PartnerLoanSettleApiEntity();
        plsApiEntity.setKudosBorrowerId(updateDocumentReq.getKudosborrowerid());
        plsApiEntity.setKudosLoanId(updateDocumentReq.getKudosloanid());
        plsApiEntity.setPartnerBorrowerId(loanRequestApiEntity.getPartnerBorrowId());
        plsApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());

        plsApiEntity.setLoanOutstAmt("0");//This contains the balance outstanding (if any).
        plsApiEntity.setLoanOutstDays("0");//This contains the no. of days utilised over and above tenor of the loan.

        BigDecimal loanDisbAmount1 = new BigDecimal(loanRequestApiEntity.getLoanDisbursementAmt());
        String fees = new BigDecimal(loanRequestApiEntity.getLoanAmt()).subtract(loanDisbAmount1).toString();
        double fe = Double.valueOf(fees);
//        biApiEntity.setLoanProcFee(Math.round(fe) + "");   borrower info 接口的

        plsApiEntity.setKudosLoanProcFee(Math.round(fe) + "");
        plsApiEntity.setLoanProcFee(Math.round(fe) + "");
        plsApiEntity.setLoanProcFeeDueAmt("0");//This contains the amount to be paid to the partner as part of the processing fee on the loan (if processing fee is collected up front this value should be '0').
        plsApiEntity.setLoanProcFeeDueDte(loanRequestApiEntity.getLoanStartDate());//This contains the date on which the Processing fee is due to the partner (if any).
        plsApiEntity.setLoanProcFeeDueFlg("Collected".toLowerCase());//This shows whether the processing fee has been collected at the begining (Collected) or if it will be charged during repayment (Pending).  Collected / Pending
        plsApiEntity.setLoanRepayAmt(loanRequestApiEntity.getLoanAmt());//This contains the amount repaid by the borrower.
        plsApiEntity.setLoanRepayDte(loanRequestApiEntity.getLoanEndDate());
//        getRandom()+
        plsApiEntity.setLoanTrancheId("KUDPAR" + loanRequestApiEntity.getLoanStartDate().replaceAll("-", "") + loanRequestApiEntity.getLoanEndDate().replaceAll("-", ""));//This contains the tranche ID.
        plsApiEntity.setPartnerLoanIntAmt(Math.round(fe) + "");//This contains the amount of partner's share of interest on the loan.
        plsApiEntity.setPartnerLoanProcFee(Math.round(fe) + "");//This contains partner's share of the Processing Fee amount.

        plsApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");

        return kudosApiService.kudosPartnerLoanSettle(plsApiEntity, queryKudosParam(name, code, value), queryKudosParam("Sandbox", kudosConfig.getProduction(), value), orderNo);
    }

    //随机8位数
    public String getRandom() {
        String result = "";
        //下面的6改成8就是8位随机数字
        while (result.length() < 8) {
            String str = String.valueOf((int) (Math.random() * 10));
            if (result.indexOf(str) == -1) {
                result += str;
            }
        }
        return result;
    }

    @Override
    public int kudosPGTxnNotify(String orderNo) {
        String name = "Query";
        String code = "21";
        String value = "";
        KDLoanSettlementReq kdLoanSettlementReq = new KDLoanSettlementReq();
        kdLoanSettlementReq.setOrderNo(orderNo);
        kdLoanSettlementReq.setResultCode("200");
        KDLoanSettlementReq loanSettlementReq = kDLoanSettlementDao.selectOne(kdLoanSettlementReq);
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        PGOrderIdParamEntity pgOrderIdParamEntity = orderDao.queryOrderIdParam(orderNo);
        if (pgOrderIdParamEntity == null) {
            new PGOrderIdParamEntity();
        }
        PGTxnNotifyApiEntity pgTxnNotifyApiEntity = new PGTxnNotifyApiEntity();
        pgTxnNotifyApiEntity.setKudosBorrowerId(loanSettlementReq.getKudosborrowerid());
        pgTxnNotifyApiEntity.setKudosLoanId(loanSettlementReq.getKudosloanid());
//        getRandom()+
        pgTxnNotifyApiEntity.setLoanTrancheId("KUDPAR" + loanRequestApiEntity.getLoanStartDate().replaceAll("-", "") + loanRequestApiEntity.getLoanEndDate().replaceAll("-", ""));
        pgTxnNotifyApiEntity.setOrderId(pgOrderIdParamEntity.getSerialNumber());//流水单号
        pgTxnNotifyApiEntity.setPaidAmnt(loanRequestApiEntity.getLoanDisbursementAmt());
        pgTxnNotifyApiEntity.setPartnerBorrowerId(loanRequestApiEntity.getPartnerBorrowId());
        pgTxnNotifyApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());
        pgTxnNotifyApiEntity.setPmntTimestmp(LocalDateTime.now() + "");
        pgTxnNotifyApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");
        return kudosApiService.kudosPGTxnNotify(pgTxnNotifyApiEntity, queryKudosParam(name, code, value), queryKudosParam("Sandbox", kudosConfig.getProduction(), value), orderNo);
    }

    //未调通，暂放着后面处理
    @Override
    public int kudosOfflineTransactionNotify(String orderNo) {

//		KDPGTxnNotifyReq kdpgTxnNotifyReq = new KDPGTxnNotifyReq();
//		kdpgTxnNotifyReq.setOrderNo(orderNo);
//		kdpgTxnNotifyReq.setResultCode("200");
//		KDPGTxnNotifyReq txnNotifyReq = kDPGTxnNotifyDao.selectOne(kdpgTxnNotifyReq);//返回的数据没有kudosBorrowerId、kudosloanid

        KDLoanSettlementReq kdLoanSettlementReq = new KDLoanSettlementReq();
        kdLoanSettlementReq.setOrderNo(orderNo);
        kdLoanSettlementReq.setResultCode("200");
        KDLoanSettlementReq loanSettlementReq = kDLoanSettlementDao.selectOne(kdLoanSettlementReq);
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        KudosOfflineRepayEntity offlineRepayEntity = new KudosOfflineRepayEntity();
        offlineRepayEntity.setKudosBorrowerId(loanSettlementReq.getKudosborrowerid());
        offlineRepayEntity.setKudosLoanId(loanSettlementReq.getKudosloanid());
        offlineRepayEntity.setLoanTrancheId("KUDPAR" + loanRequestApiEntity.getLoanStartDate().replaceAll("-", "") + loanRequestApiEntity.getLoanEndDate().replaceAll("-", ""));
        offlineRepayEntity.setPaidAmnt(loanRequestApiEntity.getLoanDisbursementAmt());
        offlineRepayEntity.setPartnerBorrowerId(loanRequestApiEntity.getPartnerBorrowId());
        offlineRepayEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());
        offlineRepayEntity.setPmntTimestmp(LocalDateTime.now() + "");
        offlineRepayEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");
        return kudosApiService.kudosOfflineTransactionNotify(offlineRepayEntity, orderNo);
    }

    @Override
    public int kudosLoanStateRequest(String orderNo) {
        String name = "Query";
        String code = "21";
        String value = "";
        KDLoanSettlementReq kdLoanSettlementReq = new KDLoanSettlementReq();
        kdLoanSettlementReq.setOrderNo(orderNo);
        kdLoanSettlementReq.setResultCode("200");
        KDLoanSettlementReq loanSettlementReq = kDLoanSettlementDao.selectOne(kdLoanSettlementReq);
        LoanRequestApiEntity loanRequestApiEntity = orderDao.queryLoanRequestInfo(orderNo);
        LoanStateRequestApiEntity loanStateRequestApiEntity = new LoanStateRequestApiEntity();
        loanStateRequestApiEntity.setKudosBorrowerId(kdLoanSettlementReq.getKudosborrowerid());
        loanStateRequestApiEntity.setKudosLoanId(kdLoanSettlementReq.getKudosloanid());
        loanStateRequestApiEntity.setLoanStmtReqFlg("1");//This contains the value 1 when requesting for data from kudos. Response will contain exhaustive list of the loan data of the borrower. Any other value will be treated as mismatch and will be logged.
        loanStateRequestApiEntity.setPartnerBorrowerId(loanRequestApiEntity.getPartnerBorrowId());
        loanStateRequestApiEntity.setPartnerLoanId(loanRequestApiEntity.getPartnerLoanId());
        loanStateRequestApiEntity.setSecretKey("bee592bc597cdf084fcd78a94783228914232086");
        return kudosApiService.kudosLoanStateRequest(loanStateRequestApiEntity, queryKudosParam(name, code, value), queryKudosParam("Sandbox", kudosConfig.getProduction(), value), orderNo);
    }


    private KudosBaseApiEntity getKudosBaseApiEntity(String orderNo, String kudosType) {
        String value = "";
        KudosParamEntity kudosSecretkey = kudosDao.queryKudosParams("KUDOS", kudosConfig.getProduction(), value);
        KudosResponseEntity kudosResponse = kudosDao.selectKudosResponse(orderNo, kudosType);
        String userId = orderDao.queryUserIdByOrderNo(orderNo);

        KudosBaseApiEntity kudosBaseApiEntity = new KudosBaseApiEntity();
        kudosBaseApiEntity.setKudosBorrowerId(kudosResponse.getKudosclientid());
        kudosBaseApiEntity.setKudosLoanId(kudosResponse.getKudosloanid());
        kudosBaseApiEntity.setPartnerBorrowerId(userId);
        kudosBaseApiEntity.setPartnerLoanId(orderNo);
        kudosBaseApiEntity.setSecretKey(kudosSecretkey.getValue());

        return kudosBaseApiEntity;
    }

    //校验邮编是否为数字
    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
