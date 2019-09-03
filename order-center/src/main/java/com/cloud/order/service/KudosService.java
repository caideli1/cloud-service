package com.cloud.order.service;

/**
 * kudos服务
 *
 * @author danquan.miao
 * @date 2019/8/23 0023
 * @since 1.0.0
 */
public interface KudosService {
    /**
     *
     * @param orderNo
     * @return
     */
    int kudosApiFirstStep(String orderNo);

    int kudosApiSecondStep(String orderNo);

    int kudosApiThirdStep(String orderNo);

    /**
     * update document
     * @param orderNo
     * @return
     */
    int kudosApiFourthStep(String orderNo);

    int kudosApiGetFifthStep(String orderNo);

    int kudosApiPostFifthStep(String orderNo);

    int kudosApiSixthStep(String orderNo);

    /**
     * 第二阶段
     * Release 2: Update loan demand note and status check/update request and NC check APIs
     * */
    int kudosLoanDemandNote(String orderNo);
    int kudosReconciliation(String orderNo);

    int kudosStatusCheck(String orderNo);

    int kudosNCCheck(String orderNo);
    int kudosUpdateDocument(String orderNo);
    /**
     * Release 3: Partner loan settlement, transaction, request for loan settlement (14th July)
     *
     * */
    int kudosLoanSettlement(String orderNo);

    int kudosPGTxnNotify(String orderNo);
    int kudosOfflineTransactionNotify(String orderNo);

    //   int kudosLoanStmtReq(String orderNo);
    int kudosLoanStateRequest(String orderNo);

    /**
     * Release 4: Partner to update statements periodically and request to increase FLDG APIs (15th July)
     *
     * */
}
