package com.cloud.order.service;

import com.cloud.order.model.kudos.*;
import org.springframework.web.bind.annotation.RequestBody;

public interface KudosApiService {
    /**
     * fisrt step:loan request
     *
     * @param lrApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosLoanRequest(LoanRequestApiEntity lrApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * second-step: borrower info
     *
     * @param biApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosBorrowerInfo(BorrowerInfoApiEntity biApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * third-step: loan schedule
     *
     * @param lsApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosLoanSchedule(LoanScheduleApiEntity lsApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * fourth-step: upload document
     *
     * @param udApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosUploadDocument(UploadDocumentApiEntity udApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * fifth-step: update document(get)
     *
     * @param vApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosGetValidation(KudosBaseApiEntity vApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * fifth-step: update document(post)
     *
     * @param vApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosPostValidation(ValidationPostApiEntity vApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * Loan Tranche Append
     *
     * @param ltAppendApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosLoanTrancheAppend(LoanTrancheAppendApiEntity ltAppendApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * @param ldnApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosLoanDemandNote(LoanDemandNoteApiEntity ldnApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * 对账
     *
     * @param rApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosReconciliation(ReconciliationApiEntity rApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * @param ifApiEntiy
     * @param kudosType
     * @param urlValue
     * @return
     */
    String kudosIncreaseFLDG(IncreaseFldgApiEntity ifApiEntiy, String kudosType, String urlValue);

    /**
     * @param statusCheckApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosStatusCheck(StatusCheckApiEntity statusCheckApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * @param nccApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosNCCheck(NCCheckApiEntity nccApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * 更新用户证件(SanctionLetterDoc与loanAgreementDoc一致)
     *
     * @param udApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosUpdateDocument(UploadDocumentApiEntity udApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * 贷款状态查询
     *
     * @param lsrApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosLoanStateRequest(LoanStateRequestApiEntity lsrApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * 结清
     *
     * @param plsApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosPartnerLoanSettle(PartnerLoanSettleApiEntity plsApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * @param pUpdApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    String kudosPeriodicUpd(PeriodicUpdApiEntity pUpdApiEntity, String kudosType, String urlValue);

    /**
     * 线上支付成功，通知kudos
     *
     * @param pgtnApiEntity
     * @param kudosType
     * @param urlValue
     * @return
     */
    int kudosPGTxnNotify(PGTxnNotifyApiEntity pgtnApiEntity, String kudosType, String urlValue,String orderNo);

    /**
     * kudos线下还款通知
     * @param offlineRepayEntity
     * @return
     */
    int kudosOfflineTransactionNotify(KudosOfflineRepayEntity offlineRepayEntity,String orderNo);

    /**
     * 线下还款成功-关闭借据
     * @param offlineRepayEntity
     * @return
     */
    Boolean offlineTransCloseLoan(@RequestBody KudosOfflineRepayEntity offlineRepayEntity);
}
