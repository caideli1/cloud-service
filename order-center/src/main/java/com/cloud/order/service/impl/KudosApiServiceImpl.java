package com.cloud.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.kudos.utils.HttpUtils;
import com.cloud.order.dao.KDBorrowerInfoDao;
import com.cloud.order.dao.KDDocumentUploadDao;
import com.cloud.order.dao.KDLoanDemandNoteDao;
import com.cloud.order.dao.KDLoanRequestDao;
import com.cloud.order.dao.KDLoanScheduleDao;
import com.cloud.order.dao.KDLoanSettlementDao;
import com.cloud.order.dao.KDNCCheckDao;
import com.cloud.order.dao.KDPGTxnNotifyDao;
import com.cloud.order.dao.KDReconciliationDao;
import com.cloud.order.dao.KDStatusCheckDao;
import com.cloud.order.dao.KDTrancheAppendDao;
import com.cloud.order.dao.KDUpdateDocumentDao;
import com.cloud.order.dao.KDValidationGetDao;
import com.cloud.order.dao.KDValidationPostDao;
import com.cloud.order.model.LoanRoot;
import com.cloud.order.model.kudos.*;
import com.cloud.order.model.req.KDBorrowerInfoReq;
import com.cloud.order.model.req.KDDocumentUploadReq;
import com.cloud.order.model.req.KDLoanDemandNoteReq;
import com.cloud.order.model.req.KDLoanRequestReq;
import com.cloud.order.model.req.KDLoanScheduleReq;
import com.cloud.order.model.req.KDLoanSettlementReq;
import com.cloud.order.model.req.KDLoanStmtreqReq;
import com.cloud.order.model.req.KDNCCheckReq;
import com.cloud.order.model.req.KDPGTxnNotifyReq;
import com.cloud.order.model.req.KDReconciliationReq;
import com.cloud.order.model.req.KDRoot;
import com.cloud.order.model.req.KDStatusCheckReq;
import com.cloud.order.model.req.KDTrancheAppendReq;
import com.cloud.order.model.req.KDUpdateDocumentReq;
import com.cloud.order.model.req.KDValidationGetReq;
import com.cloud.order.model.req.KDValidationPostReq;
import com.cloud.order.service.KudosApiService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class KudosApiServiceImpl implements KudosApiService {

    private String PARTNER = "MONEED";

//    private String PARTNER_ID = "KUD-MND-3871";
    private String PARTNER_ID = "KUD-MND-77642";
    
    @Autowired
    private KDLoanRequestDao kDLoanRequestDao;
    @Autowired
    private KDBorrowerInfoDao kDBorrowerInfoDao;
    @Autowired
    private KDLoanScheduleDao kDLoanScheduleDao;
    @Autowired
    private KDDocumentUploadDao kDDocumentUploadDao;
    @Autowired
    private KDValidationGetDao kDValidationGetDao;
    @Autowired
    private KDValidationPostDao kDValidationPostDao;
    @Autowired
    private KDTrancheAppendDao kDTrancheAppendDao;
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
    private KDPGTxnNotifyDao kDPGTxtNotifyDao;
    @Override
    public int kudosLoanRequest(LoanRequestApiEntity lrApiEntity, String kudosType, String urlValue,String orderNo) {
        Map<String, String> params = new HashMap<>();
        params.put("partner_borrower_id", lrApiEntity.getPartnerBorrowId());
        params.put("borrower_fName", lrApiEntity.getBorrowerFName());
        params.put("borrower_mName", lrApiEntity.getBorrowerMName());
        params.put("borrower_lName", lrApiEntity.getBorrowerLName());
        params.put("borrower_employer_nme", lrApiEntity.getBorrowerEmployerNme());
        params.put("borrower_email", lrApiEntity.getBorrowerEmail());
        params.put("borrower_mob", lrApiEntity.getBorrowerMob());
        params.put("borrower_dob", lrApiEntity.getBorrowerDob());
        params.put("borrower_sex", lrApiEntity.getBorrowerSex());
        params.put("borrower_pan_num", lrApiEntity.getBorrowerPanNum());
        params.put("borrower_adhaar_num", lrApiEntity.getBorrowerAdhaarNum());
        params.put("partner_loan_id", lrApiEntity.getPartnerLoanId());
        params.put("partner_loan_status", lrApiEntity.getPartnerLoanStatus());
        params.put("partner_loan_bucket", lrApiEntity.getPartnerLoanBucket());
        params.put("loan_purpose", lrApiEntity.getLoanPurpose());
        params.put("loan_amt", lrApiEntity.getLoanAmt());
        params.put("loan_proc_fee", lrApiEntity.getLoanProcFee());
        params.put("loan_conv_fee", lrApiEntity.getLoanConvFee());
        params.put("loan_disbursement_amt", lrApiEntity.getLoanDisbursementAmt());
        params.put("loan_typ", lrApiEntity.getLoanTyp());
        params.put("loan_installment_num", lrApiEntity.getLoanInstallmentNum());
        params.put("loan_tenure", lrApiEntity.getLoanTenure());
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, lrApiEntity.getSecretKey());
        KDLoanRequestReq loanRequestReq = JSON.parseObject(HttpUtils.postUrlAsString(urlValue, params, requestHeaderMap), KDLoanRequestReq.class);
        loanRequestReq.setOrderNo(orderNo);
        loanRequestReq.setCreateTime(LocalDateTime.now());
        return kDLoanRequestDao.insert(loanRequestReq);
    }

    @Override
    public int kudosBorrowerInfo(BorrowerInfoApiEntity biApiEntity, String kudosType, String urlValue,String orderNo) {
        Map<String, String> params = Maps.newHashMap();
        params.put("partner_borrower_id", biApiEntity.getPartnerBorrowerId());
        params.put("kudos_borrower_id", biApiEntity.getKudosBorrowerId());
        params.put("borrower_email", biApiEntity.getBorrowerEmail());
        params.put("Borrower_Email_id", biApiEntity.getBorrowerEmailId());
        params.put("borrower_mob", biApiEntity.getBorrowerMob());
        params.put("borrower_curr_addr", biApiEntity.getBorrowerCurrAddr());
        params.put("borrower_curr_city", biApiEntity.getBorrowerCurrCity());
        params.put("borrower_curr_state", biApiEntity.getBorrowerCurrState());
        params.put("borrower_curr_pincode", biApiEntity.getBorrowerCurrPincode());
        params.put("borrower_perm_address", biApiEntity.getBorrowerPermAddress());
        params.put("borrower_perm_city", biApiEntity.getBorrowerPermCity());
        params.put("borrower_perm_state", biApiEntity.getBorrowerPermState());
        params.put("borrower_perm_pincode", biApiEntity.getBorrowerPermPincode());
        params.put("borrower_marital_status", biApiEntity.getBorrowerMaritalStatus());
        params.put("borrower_qualification", biApiEntity.getBorrowerQualification());
        params.put("borrower_employer_nme", biApiEntity.getBorrowerEmployerNme());
        params.put("borrower_employer_id", biApiEntity.getBorrowerEmployerId());
        params.put("borrower_salary", biApiEntity.getBorrowerSalary());
        params.put("borrower_credit_score", biApiEntity.getBorrowerCreditScore());
        params.put("borrower_foir", biApiEntity.getBorrowerFoir());
        params.put("borrower_ac_holder_nme", biApiEntity.getBorrowerAcHolderNme());
        params.put("borrower_bnk_nme", biApiEntity.getBorrowerBnkNme());
        params.put("borrower_ac_num", biApiEntity.getBorrowerAcNum());
        params.put("borrower_bnk_ifsc", biApiEntity.getBorrowerBnkIfsc());
        params.put("partner_loan_id", biApiEntity.getPartnerLoanId());
        params.put("kudos_loan_id", biApiEntity.getKudosLoanId());
        params.put("loan_typ", biApiEntity.getLoanTyp());
        params.put("loan_tenure", biApiEntity.getLoanTenure());
        params.put("loan_installment_num", biApiEntity.getLoanInstallmentNum());
        params.put("loan_emi_freq", biApiEntity.getLoanEmiFreq());
        params.put("loan_prin_amt", biApiEntity.getLoanPrinAmt());
        params.put("loan_proc_fee", biApiEntity.getLoanProcFee());
        params.put("loan_conv_fee", biApiEntity.getLoanConvFee());
        params.put("loan_coupon_amt", biApiEntity.getLoanCouponAmt());
        params.put("loan_amt_disbursed", biApiEntity.getLoanAmtDisbursed());
        params.put("loan_int_rt", biApiEntity.getLoanIntRt());
        params.put("loan_int_amt", biApiEntity.getLoanIntAmt());
        params.put("loan_emi_dte_1", biApiEntity.getLoanEmiDte1());
        params.put("loan_emi_amt_1", biApiEntity.getLoanEmiAmt1());
        params.put("loan_emi_amt_2", biApiEntity.getLoanEmiAmt2());
        params.put("loan_end_dte", biApiEntity.getLoanEndDte());
        params.put("loan_sanction_letter", biApiEntity.getLoanSanctionLetter());
        params.put("loan_disbursement_upd_status", biApiEntity.getLoanDisbursementUpdStatus());
        params.put("loan_disbursement_upd_dte", biApiEntity.getLoanDisbursementUpdDte());
        params.put("loan_disbursement_trans_dte", biApiEntity.getLoanDisbursementTransDte());
        params.put("disbursement_trans_trac_num", biApiEntity.getDisbursementTransTracNum());
        params.put("loan_emi_recd_num", biApiEntity.getLoanEmiRecdNum());
        params.put("kudos_loan_typ", biApiEntity.getKudosLoanTyp());
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, biApiEntity.getSecretKey());
//        String result = HttpUtils.postUrlAsString(urlValue, params, requestHeaderMap);
        KDBorrowerInfoReq borrowerInfoReq = JSON.parseObject(HttpUtils.postUrlAsString(urlValue, params, requestHeaderMap), KDBorrowerInfoReq.class);
        borrowerInfoReq.setOrderNo(orderNo);
        borrowerInfoReq.setCreateTime(LocalDateTime.now());
        return kDBorrowerInfoDao.insert(borrowerInfoReq);
    }

    @Override
    public int kudosLoanSchedule(LoanScheduleApiEntity lsApiEntity, String kudosType, String urlValue,String orderNo) {
        Map<String, String> params = Maps.newHashMap();
        params.put("partner_borrower_id", lsApiEntity.getPartnerBorrowerId());
        params.put("kudos_borrower_id", lsApiEntity.getKudosBorrowerId());
        params.put("loan_emi_amt_1", lsApiEntity.getLoanEmiAmt1());
        params.put("loan_emi_dte_1", lsApiEntity.getLoanEmiDte1());
        params.put("loan_emi_amt_2", lsApiEntity.getLoanEmiAmt2());
        params.put("loan_emi_dte_2", lsApiEntity.getLoanEmiDte2());
        params.put("loan_emi_amt_3", lsApiEntity.getLoanEmiAmt3());
        params.put("loan_emi_dte_3", lsApiEntity.getLoanEmiDte3());
        params.put("loan_emi_amt_4", lsApiEntity.getLoanEmiAmt4());
        params.put("loan_emi_dte_4", lsApiEntity.getLoanEmiDte4());
        params.put("loan_emi_amt_5", lsApiEntity.getLoanEmiAmt5());
        params.put("loan_emi_dte_5", lsApiEntity.getLoanEmiDte5());
        params.put("loan_emi_amt_6", lsApiEntity.getLoanEmiAmt6());
        params.put("loan_emi_dte_6", lsApiEntity.getLoanEmiDte6());
        params.put("loan_emi_amt_7", lsApiEntity.getLoanEmiAmt7());
        params.put("loan_emi_dte_7", lsApiEntity.getLoanEmiDte7());
        params.put("loan_emi_amt_8", lsApiEntity.getLoanEmiAmt8());
        params.put("loan_emi_dte_8", lsApiEntity.getLoanEmiDte8());
        params.put("loan_emi_amt_9", lsApiEntity.getLoanEmiAmt9());
        params.put("loan_emi_dte_9", lsApiEntity.getLoanEmiDte9());
        params.put("loan_emi_amt_10", lsApiEntity.getLoanEmiAmt10());
        params.put("loan_emi_dte_10", lsApiEntity.getLoanEmiDte10());
        params.put("loan_emi_amt_11", lsApiEntity.getLoanEmiAmt11());
        params.put("loan_emi_dte_11", lsApiEntity.getLoanEmiDte11());
        params.put("loan_emi_amt_12", lsApiEntity.getLoanEmiAmt12());
        params.put("loan_emi_dte_12", lsApiEntity.getLoanEmiDte12());
        params.put("loan_emi_amt_13", lsApiEntity.getLoanEmiAmt13());
        params.put("loan_emi_dte_13", lsApiEntity.getLoanEmiDte13());
        params.put("loan_emi_amt_14", lsApiEntity.getLoanEmiAmt14());
        params.put("loan_emi_dte_14", lsApiEntity.getLoanEmiDte14());
        params.put("loan_emi_amt_15", lsApiEntity.getLoanEmiAmt15());
        params.put("loan_emi_dte_15", lsApiEntity.getLoanEmiDte15());
        params.put("loan_emi_amt_16", lsApiEntity.getLoanEmiAmt16());
        params.put("loan_emi_dte_16", lsApiEntity.getLoanEmiDte16());
        params.put("loan_emi_amt_17", lsApiEntity.getLoanEmiAmt17());
        params.put("loan_emi_dte_17", lsApiEntity.getLoanEmiDte17());
        params.put("loan_emi_amt_18", lsApiEntity.getLoanEmiAmt18());
        params.put("loan_emi_dte_18", lsApiEntity.getLoanEmiDte18());
        params.put("loan_emi_amt_19", lsApiEntity.getLoanEmiAmt19());
        params.put("loan_emi_dte_19", lsApiEntity.getLoanEmiDte19());
        params.put("loan_emi_amt_20", lsApiEntity.getLoanEmiAmt20());
        params.put("loan_emi_dte_20", lsApiEntity.getLoanEmiDte20());
        params.put("loan_emi_amt_21", lsApiEntity.getLoanEmiAmt21());
        params.put("loan_emi_dte_21", lsApiEntity.getLoanEmiDte21());
        params.put("loan_emi_amt_22", lsApiEntity.getLoanEmiAmt22());
        params.put("loan_emi_dte_22", lsApiEntity.getLoanEmiDte22());
        params.put("loan_emi_amt_23", lsApiEntity.getLoanEmiAmt23());
        params.put("loan_emi_dte_23", lsApiEntity.getLoanEmiDte23());
        params.put("loan_emi_amt_24", lsApiEntity.getLoanEmiAmt24());
        params.put("loan_emi_dte_24", lsApiEntity.getLoanEmiDte24());
        params.put("loan_emi_amt_25", lsApiEntity.getLoanEmiAmt25());
        params.put("loan_emi_dte_25", lsApiEntity.getLoanEmiDte25());
        params.put("loan_emi_amt_26", lsApiEntity.getLoanEmiAmt26());
        params.put("loan_emi_dte_26", lsApiEntity.getLoanEmiDte26());
        params.put("loan_emi_amt_27", lsApiEntity.getLoanEmiAmt27());
        params.put("loan_emi_dte_27", lsApiEntity.getLoanEmiDte27());
        params.put("loan_emi_amt_28", lsApiEntity.getLoanEmiAmt28());
        params.put("loan_emi_dte_28", lsApiEntity.getLoanEmiDte28());
        params.put("loan_emi_amt_29", lsApiEntity.getLoanEmiAmt29());
        params.put("loan_emi_dte_29", lsApiEntity.getLoanEmiDte29());
        params.put("loan_emi_amt_30", lsApiEntity.getLoanEmiAmt30());
        params.put("loan_emi_dte_30", lsApiEntity.getLoanEmiDte30());
        params.put("loan_emi_amt_31", lsApiEntity.getLoanEmiAmt31());
        params.put("loan_emi_dte_31", lsApiEntity.getLoanEmiDte31());
        params.put("loan_emi_amt_32", lsApiEntity.getLoanEmiAmt32());
        params.put("loan_emi_dte_32", lsApiEntity.getLoanEmiDte32());
        params.put("loan_emi_amt_33", lsApiEntity.getLoanEmiAmt33());
        params.put("loan_emi_dte_33", lsApiEntity.getLoanEmiDte33());
        params.put("loan_emi_amt_34", lsApiEntity.getLoanEmiAmt34());
        params.put("loan_emi_dte_34", lsApiEntity.getLoanEmiDte34());
        params.put("loan_emi_amt_35", lsApiEntity.getLoanEmiAmt35());
        params.put("loan_emi_dte_35", lsApiEntity.getLoanEmiDte35());
        params.put("loan_emi_amt_36", lsApiEntity.getLoanEmiAmt36());
        params.put("loan_emi_dte_36", lsApiEntity.getLoanEmiDte36());
        params.put("loan_emi_amt_37", lsApiEntity.getLoanEmiAmt37());
        params.put("loan_emi_dte_37", lsApiEntity.getLoanEmiDte37());
        params.put("loan_emi_amt_38", lsApiEntity.getLoanEmiAmt38());
        params.put("loan_emi_dte_38", lsApiEntity.getLoanEmiDte38());
        params.put("loan_emi_amt_39", lsApiEntity.getLoanEmiAmt39());
        params.put("loan_emi_dte_39", lsApiEntity.getLoanEmiDte39());
        params.put("loan_emi_amt_40", lsApiEntity.getLoanEmiAmt40());
        params.put("loan_emi_dte_40", lsApiEntity.getLoanEmiDte40());
        params.put("loan_emi_amt_41", lsApiEntity.getLoanEmiAmt41());
        params.put("loan_emi_dte_41", lsApiEntity.getLoanEmiDte41());
        params.put("loan_emi_amt_42", lsApiEntity.getLoanEmiAmt42());
        params.put("loan_emi_dte_42", lsApiEntity.getLoanEmiDte42());
        params.put("loan_emi_amt_43", lsApiEntity.getLoanEmiAmt43());
        params.put("loan_emi_dte_43", lsApiEntity.getLoanEmiDte43());
        params.put("loan_emi_amt_44", lsApiEntity.getLoanEmiAmt44());
        params.put("loan_emi_dte_44", lsApiEntity.getLoanEmiDte44());
        params.put("loan_emi_amt_45", lsApiEntity.getLoanEmiAmt45());
        params.put("loan_emi_dte_45", lsApiEntity.getLoanEmiDte45());
        params.put("loan_emi_amt_46", lsApiEntity.getLoanEmiAmt46());
        params.put("loan_emi_dte_46", lsApiEntity.getLoanEmiDte46());
        params.put("loan_emi_amt_47", lsApiEntity.getLoanEmiAmt47());
        params.put("loan_emi_dte_47", lsApiEntity.getLoanEmiDte47());
        params.put("loan_emi_amt_48", lsApiEntity.getLoanEmiAmt48());
        params.put("loan_emi_dte_48", lsApiEntity.getLoanEmiDte48());
        params.put("loan_emi_amt_49", lsApiEntity.getLoanEmiAmt49());
        params.put("loan_emi_dte_49", lsApiEntity.getLoanEmiDte49());
        params.put("loan_emi_amt_50", lsApiEntity.getLoanEmiAmt50());
        params.put("loan_emi_dte_50", lsApiEntity.getLoanEmiDte50());
        params.put("loan_emi_amt_51", lsApiEntity.getLoanEmiAmt51());
        params.put("loan_emi_dte_51", lsApiEntity.getLoanEmiDte51());
        params.put("loan_emi_amt_52", lsApiEntity.getLoanEmiAmt52());
        params.put("loan_emi_dte_52", lsApiEntity.getLoanEmiDte52());
        params.put("loan_emi_amt_53", lsApiEntity.getLoanEmiAmt53());
        params.put("loan_emi_dte_53", lsApiEntity.getLoanEmiDte53());
        params.put("loan_post_disbursement_status", lsApiEntity.getLoanPostDisbursementStatus());
        params.put("loan_emi_recd_num", lsApiEntity.getLoanEmiRecdNum());
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, lsApiEntity.getSecretKey());
        String result = HttpUtils.postUrlAsString(urlValue, params, requestHeaderMap);
        System.out.println("result="+result);
        KDLoanScheduleReq scheduleReq = JSON.parseObject(HttpUtils.postUrlAsString(urlValue, params, requestHeaderMap), KDLoanScheduleReq.class);
        scheduleReq.setOrderNo(orderNo);
        scheduleReq.setCreateTime(LocalDateTime.now());
        /*JSONObject jsonObject = JSONObject.fromObject(result);
        KDRoot kdroot = (KDRoot) JSONObject.toBean(jsonObject, KDRoot.class);
        KDLoanScheduleReq loanScheduleReq = new KDLoanScheduleReq();
        if (kdroot.getResultCode().equals("200")) {
        	loanScheduleReq.setKudosborrowerid(kdroot.getValues().getKudosborrowerid());
        	loanScheduleReq.setKudosloanid(kdroot.getValues().getKudosloanid());
        	loanScheduleReq.setAccountStatus(kdroot.getValues().getAccountStatus());
        	loanScheduleReq.setOnboarded(kdroot.getValues().getOnboarded());
		}
        loanScheduleReq.setOrderNo(orderNo);
        loanScheduleReq.setMessage(kdroot.getMessage());
        loanScheduleReq.setStatus(kdroot.getStatus());
        loanScheduleReq.setResultCode(kdroot.getResultCode());
        loanScheduleReq.setInfo(kdroot.getValues().getInfo());
        loanScheduleReq.setReason(kdroot.getValues().getReason());
        loanScheduleReq.setCreateTime(LocalDateTime.now());*/
        return kDLoanScheduleDao.insert(scheduleReq);
    }

    @Override
    public int kudosUploadDocument(UploadDocumentApiEntity udApiEntity, String kudosType, String urlValue,String orderNo) {
        Map<String, String> params = getUploadDocumentBody(udApiEntity);
        params.put("partner_borrower_id", udApiEntity.getPartnerBorrowerId());
        params.put("kudos_borrower_id", udApiEntity.getKudosBorrowerId());
        params.put("borrower_pan_doc", udApiEntity.getBorrowerPanDoc());
        params.put("borrower_adhaar_doc", udApiEntity.getBorrowerAdhaarDoc());
        params.put("borrower_photo_doc", udApiEntity.getBorrowerPhotoDoc());
        params.put("borrower_cibil_doc", udApiEntity.getBorrowerCibilDoc());
        params.put("borrower_bnk_stmt_doc", udApiEntity.getBorrowerBnkStmtDoc());
        params.put("partner_loan_id", udApiEntity.getPartnerLoanId());
        params.put("kudos_loan_id", udApiEntity.getKudosLoanId());
        params.put("loan_sanction_letter", udApiEntity.getLoanSanctionLetterDoc());
        params.put("loan_agreement_doc", udApiEntity.getLoanAgreementDoc());
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, udApiEntity.getSecretKey());
//        String result = HttpUtils.postUrlAsString(urlValue, params, requestHeaderMap);
//        System.out.println("result="+result);
        KDDocumentUploadReq documentUploanReq = JSON.parseObject(HttpUtils.postUrlAsString(urlValue, params, requestHeaderMap), KDDocumentUploadReq.class);
        documentUploanReq.setOrderNo(orderNo);
        documentUploanReq.setCreateTime(LocalDateTime.now());
        return kDDocumentUploadDao.insert(documentUploanReq);
    }

    @Override
    public int kudosGetValidation(KudosBaseApiEntity vApiEntity, String kudosType, String urlValue,String orderNo) {
        Map<String, String> params = getRequestBody(vApiEntity);
        params.put("partner_borrower_id", vApiEntity.getPartnerBorrowerId());
        params.put("kudos_borrower_id", vApiEntity.getKudosBorrowerId());
        params.put("partner_loan_id", vApiEntity.getPartnerLoanId());
        params.put("kudos_loan_id", vApiEntity.getKudosLoanId());
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, vApiEntity.getSecretKey());
//        String result = HttpUtils.getUrlAsString(urlValue, params, requestHeaderMap);
//        System.out.println("result="+result);
        KDValidationGetReq validationGetReq = JSON.parseObject(HttpUtils.getUrlAsString(urlValue, params, requestHeaderMap), KDValidationGetReq.class);
        validationGetReq.setOrderNo(orderNo);
        validationGetReq.setCreateTime(LocalDateTime.now());
        return kDValidationGetDao.insert(validationGetReq);
    }

    @Override
    public int kudosPostValidation(ValidationPostApiEntity vApiEntity, String kudosType, String urlValue,String orderNo) {
//        Map<String, String> params = getRequestBody(vApiEntity);
    	Map<String, String> params = new HashMap<>();
    	params.put("partner_borrower_id", vApiEntity.getPartnerBorrowerId());
    	params.put("kudos_borrower_id", vApiEntity.getKudosBorrowerId());
    	params.put("borrower_pan_num", vApiEntity.getBorrowerPanNum());
    	params.put("borrower_adhaar_num", vApiEntity.getBorrowerAdhaarNum());
    	params.put("borrower_credit_score", vApiEntity.getBorrowerCreditScore());
    	params.put("partner_loan_id", vApiEntity.getPartnerLoanId());
    	params.put("kudos_loan_id", vApiEntity.getKudosLoanId());
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, vApiEntity.getSecretKey());
//        String result = HttpUtils.postUrlAsString(urlValue, params, requestHeaderMap);
//        System.out.println("result="+result);
        KDValidationPostReq validationPostReq = JSON.parseObject(HttpUtils.getUrlAsString(urlValue, params, requestHeaderMap), KDValidationPostReq.class);
        validationPostReq.setOrderNo(orderNo);
        validationPostReq.setCreateTime(LocalDateTime.now());
        return kDValidationPostDao.insert(validationPostReq);
    }

    @Override
    public int kudosLoanTrancheAppend(LoanTrancheAppendApiEntity ltAppendApiEntity, String kudosType, String urlValue,String orderNo) {
        Map<String, String> params = Maps.newHashMap();
        params.put("partner_loan_id", ltAppendApiEntity.getPartnerLoanId());
        params.put("kudos_loan_id", ltAppendApiEntity.getKudosLoanId());
        params.put("partner_borrower_id", ltAppendApiEntity.getPartnerBorrowerId());
        params.put("kudos_borrower_id", ltAppendApiEntity.getKudosBorrowerId());
        params.put("loan_tranche_id", ltAppendApiEntity.getLoanTrancheId());
        params.put("loan_disbursement_dte", ltAppendApiEntity.getLoanDisbursementDte());
        params.put("loan_tranche_num", ltAppendApiEntity.getLoanTrancheNum());
        params.put("loan_tranche_amt", ltAppendApiEntity.getLoanTrancheAmt());
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, ltAppendApiEntity.getSecretKey());
//        String result = HttpUtils.postUrlAsString(urlValue, params, requestHeaderMap);
//        System.out.println("result="+result);
        KDTrancheAppendReq trancheAppendReq = JSON.parseObject(HttpUtils.getUrlAsString(urlValue, params, requestHeaderMap), KDTrancheAppendReq.class);
        trancheAppendReq.setOrderNo(orderNo);
        trancheAppendReq.setCreateTime(LocalDateTime.now());
        return kDTrancheAppendDao.insert(trancheAppendReq);
    }

    @Override
    public int kudosLoanDemandNote(LoanDemandNoteApiEntity ldnApiEntity, String kudosType, String urlValue,String orderNo) {
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, ldnApiEntity.getSecretKey());
        Map<String, String> bodyMap = getRequestBody(ldnApiEntity);
        bodyMap.put("loan_demand_note_issued", ldnApiEntity.getLoanDemandNoteIssued());
//        String result = HttpUtils.postUrlAsString(urlValue, bodyMap, requestHeaderMap);
//        System.out.println("result="+result);
        KDLoanDemandNoteReq loanDemandNoteReq = JSON.parseObject(HttpUtils.postUrlAsString(urlValue, bodyMap, requestHeaderMap), KDLoanDemandNoteReq.class);
        loanDemandNoteReq.setOrderNo(orderNo);
        loanDemandNoteReq.setCreateTime(LocalDateTime.now());
        return kDLoanDemandNoteDao.insert(loanDemandNoteReq);
    }

    @Override
    public int kudosReconciliation(ReconciliationApiEntity rApiEntity, String kudosType, String urlValue,String orderNo) {
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, rApiEntity.getSecretKey());
        Map<String, String> bodyMap = getRequestBody(rApiEntity);
        bodyMap.put("loan_recon_status", rApiEntity.getLoanReconStatus());
        bodyMap.put("loan_recon_dte", rApiEntity.getLoanReconDte());
        KDReconciliationReq reconciliationReq = JSON.parseObject(HttpUtils.postUrlAsString(urlValue, bodyMap, requestHeaderMap), KDReconciliationReq.class);
        reconciliationReq.setOrderNo(orderNo);
        reconciliationReq.setCreateTime(LocalDateTime.now());
        reconciliationReq.setUpdateTime(LocalDateTime.now());
        return kDReconciliationDao.insert(reconciliationReq);
    }

    @Override
    public String kudosIncreaseFLDG(IncreaseFldgApiEntity ifApiEntiy, String kudosType, String urlValue) {
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, ifApiEntiy.getSecretKey());
        Map<String, String> bodyMap = new HashMap<>(4);
        bodyMap.put("partner_fldg_perc", ifApiEntiy.getPartnerFldgPerc());
        bodyMap.put("partner_disbursement_tot", ifApiEntiy.getPartnerDisbursementTot());
        bodyMap.put("partner_limit_ext", ifApiEntiy.getPartnerLimitExt());
        bodyMap.put("partner_strt_dte", ifApiEntiy.getPartnerStrtDte());

        return HttpUtils.postUrlAsString(urlValue, bodyMap, requestHeaderMap);
    }

    @Override
    public int kudosStatusCheck(StatusCheckApiEntity statusCheckApiEntity, String kudosType, String urlValue,String orderNo) {
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, statusCheckApiEntity.getSecretKey());
        Map<String, String> bodyMap = getRequestBody(statusCheckApiEntity);
        bodyMap.put("loan_disbursement_upd_status", statusCheckApiEntity.getLoanDisbursementUpdStatus());
        bodyMap.put("loan_recon_status", statusCheckApiEntity.getLoanReconStatus());
        bodyMap.put("partner_loan_status", statusCheckApiEntity.getPartnerLoanStatus());
        bodyMap.put("partner_loan_comments", statusCheckApiEntity.getPartnerLoanComments());
        KDStatusCheckReq statusCheckReq = JSON.parseObject(HttpUtils.postUrlAsString(urlValue, bodyMap, requestHeaderMap), KDStatusCheckReq.class);
        statusCheckReq.setOrderNo(orderNo);
        statusCheckReq.setCreateTime(LocalDateTime.now());
        statusCheckReq.setUpdateTime(LocalDateTime.now());
        return kDStatusCheckDao.insert(statusCheckReq);
    }

    @Override
    public int kudosNCCheck(NCCheckApiEntity nccApiEntity, String kudosType, String urlValue,String orderNo) {
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, nccApiEntity.getSecretKey());
        Map<String, String> bodyMap = getRequestBody(nccApiEntity);
        bodyMap.put("loan_nc_status", nccApiEntity.getLoanNcStatus());
        KDNCCheckReq kdncCheckReq = JSON.parseObject(HttpUtils.postUrlAsString(urlValue, bodyMap, requestHeaderMap), KDNCCheckReq.class);
        kdncCheckReq.setOrderNo(orderNo);
        kdncCheckReq.setCreateTime(LocalDateTime.now());
        kdncCheckReq.setUpdateTime(LocalDateTime.now());
        return kDNCCheckDao.insert(kdncCheckReq);
    }

    @Override
    public int kudosUpdateDocument(UploadDocumentApiEntity udApiEntity, String kudosType, String urlValue,String orderNo) {
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, udApiEntity.getSecretKey());
        Map<String, String> bodyMap = getUploadDocumentBody(udApiEntity);
        KDUpdateDocumentReq kdUpdateDocumentReq = JSON.parseObject(HttpUtils.postUrlAsString(urlValue, bodyMap, requestHeaderMap), KDUpdateDocumentReq.class);
        kdUpdateDocumentReq.setOrderNo(orderNo);
        kdUpdateDocumentReq.setCreateTime(LocalDateTime.now());
        kdUpdateDocumentReq.setUpdateTime(LocalDateTime.now());
        return kDUpdateDocumentDao.insert(kdUpdateDocumentReq);
    }

    @Override
    public int kudosLoanStateRequest(LoanStateRequestApiEntity lsrApiEntity, String kudosType, String urlValue,String orderNo) {
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, lsrApiEntity.getSecretKey());
        Map<String, String> bodyMap = getRequestBody(lsrApiEntity);
        bodyMap.put("loan_stmt_req_flg", lsrApiEntity.getLoanStmtReqFlg());
        KDLoanStmtreqReq loanStmtreqReq = JSON.parseObject(HttpUtils.postUrlAsString(urlValue, bodyMap, requestHeaderMap), KDLoanStmtreqReq.class);
        loanStmtreqReq.setOrderNo(orderNo);
        loanStmtreqReq.setCreateTime(LocalDateTime.now());
        loanStmtreqReq.setUpdateTime(LocalDateTime.now());
        return 0;
    }

    @Override
    public int kudosPartnerLoanSettle(PartnerLoanSettleApiEntity plsApiEntity, String kudosType, String urlValue,String orderNo) {
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, plsApiEntity.getSecretKey());
        Map<String, String> bodyMap = getRequestBody(plsApiEntity);
        //交易id KUDPARDDMMYYYYDDMMYYYY
        bodyMap.put("loan_tranche_id", plsApiEntity.getLoanTrancheId());
        bodyMap.put("loan_repay_dte", plsApiEntity.getLoanRepayDte());
        bodyMap.put("loan_repay_amt", plsApiEntity.getLoanRepayAmt());
        bodyMap.put("loan_outst_amt", plsApiEntity.getLoanOutstAmt());
        bodyMap.put("loan_outst_days", plsApiEntity.getLoanOutstDays());
        bodyMap.put("loan_proc_fee", plsApiEntity.getLoanProcFee());
        bodyMap.put("kudos_loan_proc_fee", plsApiEntity.getKudosLoanProcFee());
        bodyMap.put("partner_loan_proc_fee", plsApiEntity.getPartnerLoanProcFee());
        bodyMap.put("loan_proc_fee_due_flg", plsApiEntity.getLoanProcFeeDueFlg());
        bodyMap.put("loan_proc_fee_due_dte", plsApiEntity.getLoanProcFeeDueDte());
        bodyMap.put("loan_proc_fee_due_amt", plsApiEntity.getLoanProcFeeDueAmt());
        bodyMap.put("partner_loan_int_amt", plsApiEntity.getPartnerLoanIntAmt());
        KDLoanSettlementReq kdLoanSettlementReq = JSON.parseObject(HttpUtils.postUrlAsString(urlValue, bodyMap, requestHeaderMap), KDLoanSettlementReq.class);
        kdLoanSettlementReq.setOrderNo(orderNo);
        kdLoanSettlementReq.setCreateTime(LocalDateTime.now());
        kdLoanSettlementReq.setUpdateTime(LocalDateTime.now());
        return kDLoanSettlementDao.insert(kdLoanSettlementReq);
    }

    @Override
    public String kudosPeriodicUpd(PeriodicUpdApiEntity pUpdApiEntity, String kudosType, String urlValue) {
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, pUpdApiEntity.getSecretKey());

        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("comp_bnk_stmt_doc", pUpdApiEntity.getCompBnkStmtDoc());
        bodyMap.put("comp_gst_stmt_doc", pUpdApiEntity.getCompGstStmtDoc());
        bodyMap.put("comp_bs_doc", pUpdApiEntity.getCompBsDoc());
        bodyMap.put("comp_pnl_doc", pUpdApiEntity.getCompPnlDoc());
        bodyMap.put("comp_cf_doc", pUpdApiEntity.getCompCfDoc());
        bodyMap.put("comp_itr_doc", pUpdApiEntity.getCompItrDoc());
        bodyMap.put("comp_net_worth_cert_doc", pUpdApiEntity.getCompNetWorthCertDoc());
        bodyMap.put("comp_promoter_shares_doc", pUpdApiEntity.getCompPromoterSharesDoc());

        return HttpUtils.postUrlAsString(urlValue, bodyMap, requestHeaderMap);
    }

    @Override
    public int kudosPGTxnNotify(PGTxnNotifyApiEntity pgtnApiEntity, String kudosType, String urlValue,String orderNo) {
        Map<String, String> requestHeaderMap = getRequestHeaderMap(kudosType, pgtnApiEntity.getSecretKey());
        Map<String, String> bodyMap = getRequestBody(pgtnApiEntity);
        //Reference Order ID provided by Payment Gateway
        bodyMap.put("orderid", pgtnApiEntity.getOrderId());
        bodyMap.put("loan_tranche_id", pgtnApiEntity.getLoanTrancheId());
        bodyMap.put("paid_amnt", pgtnApiEntity.getPaidAmnt());
        bodyMap.put("pmnt_timestmp", pgtnApiEntity.getPmntTimestmp());
        KDPGTxnNotifyReq kdpgTxnNotifyReq = JSON.parseObject(HttpUtils.postUrlAsString(urlValue, bodyMap, requestHeaderMap), KDPGTxnNotifyReq.class);
        kdpgTxnNotifyReq.setOrderNo(orderNo);
        kdpgTxnNotifyReq.setCreateTime(LocalDateTime.now());
        kdpgTxnNotifyReq.setUpdateTime(LocalDateTime.now());
        return kDPGTxtNotifyDao.insert(kdpgTxnNotifyReq);
    }

    @Override
    public int kudosOfflineTransactionNotify(KudosOfflineRepayEntity offlineRepayEntity,String orderNo) {
        //创建还款交易记录
    	
        //还款成功处理
    	
    	
    	
        return 0;
    }

    @Override
    public Boolean offlineTransCloseLoan(KudosOfflineRepayEntity offlineRepayEntity) {
        //关闭kudos借据状态
        return Boolean.TRUE;
    }

    private Map<String, String> getRequestHeaderMap(String kudosType, String secretKey) {
        Map<String, String> headerMap = Maps.newHashMap();
        headerMap.put("Partner", PARTNER);
        headerMap.put("Authkey", secretKey);
        headerMap.put("Partnerid", PARTNER_ID);
        headerMap.put("Query", kudosType);
        return headerMap;
    }

    private Map<String, String> getRequestBody(KudosBaseApiEntity vApiEntity) {
        Map<String, String> params = Maps.newHashMap();
        params.put("partner_loan_id", vApiEntity.getPartnerLoanId());
        params.put("kudos_loan_id", vApiEntity.getKudosLoanId());
        params.put("partner_borrower_id", vApiEntity.getPartnerBorrowerId());
        params.put("kudos_borrower_id", vApiEntity.getKudosBorrowerId());
        return params;
    }

    /**
     * 获取用户证件更新body
     *
     * @param udApiEntity
     * @return
     */
    private Map<String, String> getUploadDocumentBody(UploadDocumentApiEntity udApiEntity) {
        Map<String, String> bodyMap = getRequestBody(udApiEntity);
        bodyMap.put("borrower_pan_doc", udApiEntity.getBorrowerPanDoc());
        bodyMap.put("borrower_adhaar_doc", udApiEntity.getBorrowerAdhaarDoc());
        bodyMap.put("borrower_photo_doc", udApiEntity.getBorrowerPhotoDoc());
        bodyMap.put("borrower_cibil_doc", udApiEntity.getBorrowerCibilDoc());
        bodyMap.put("borrower_bnk_stmt_doc", udApiEntity.getBorrowerBnkStmtDoc());
        bodyMap.put("loan_sanction_letter_doc", udApiEntity.getLoanSanctionLetterDoc());
        bodyMap.put("loan_agreement_doc", udApiEntity.getLoanAgreementDoc());
        return bodyMap;
    }


}
