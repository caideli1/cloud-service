package com.cloud.order.test.kudos;

import com.alibaba.fastjson.JSON;
import com.cloud.order.model.kudos.LoanDemandNoteApiEntity;
import com.cloud.order.model.req.KDLoanRequestReq;
import com.cloud.order.model.req.KDTrancheAppendReq;
import com.cloud.order.service.KudosApiService;
import com.cloud.order.service.KudosService;
import com.cloud.order.test.OrderCenterApplicationTest;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * kudos 2.0 api 测试
 *
 * @author danquanmiao
 * @version V1.0
 * @since 2019-05-22 15:00
 */
public class KudisApiServiceImplTest extends OrderCenterApplicationTest {
    @Autowired
    private KudosService kudosService;

    @Autowired(required = false)
    private KudosApiService kudosApiService;

    private static final String URL_VALUE = "http://api.kudosfinance.in/partners/apiv2/index.php";

    private static final String orderNo = "570998025212657664";
    /**
     * loan-request第一步
     * 1
     *568955578588594176
     *569951006050222080
     *569938298949074944
     *570998025212657664
     */
//    @Test
    public void loanRequest() {
//        String orderNo = "570998025212657664";
        int response = kudosService.kudosApiFirstStep(orderNo);
        System.out.println("result: " + response);
    }
    
//    @Test
    public void testReq(){
    	String loanreq = "{\"status\":\"true\",\"result_code\":\"200\",\"message\":\"Success\",\"kudosloanid\":\"KUD-MND-776420aa1870779e76030f1ed4ab976f4dfa13ad3d7f6\",\"kudosborrowerid\":\"KUD-MND-77642e787da98913e014981314919e556828df2f80cbf\",\"onboarded\":\"YES\",\"account_status\":\"ACTIVE\",\"va_acc\":\"75461000882370000010627094422\",\"ifsc\":\"IDFB0041351\",\"bankname\":\"IDFC\",\"info\":\"Loan has been Created!\",\"TimestampofRequest\":\"2019-06-27 11:09:50\"}";
    	JSONObject jsonObject = JSONObject.fromObject(loanreq);
        
//    	KDLoanRequestReq loanRequestReq = new KDLoanRequestReq();
//    	loanRequestReq = (KDLoanRequestReq) JSONObject.toBean(jsonObject, KDLoanRequestReq.class);
//    	System.out.println("loanRequestReq="+loanRequestReq.getTimestampofRequest());
    	
    	KDLoanRequestReq loanRequestReq2 = JSON.parseObject(loanreq, KDLoanRequestReq.class);
    }
    /**
     * borrower-info
     * 2
     */
//    @Test
    public void borrowerInfo() {
        int response = kudosService.kudosApiSecondStep(orderNo);
        System.out.println("result: " + response);
    }


    /**
     * Document-Upload
     * 3
     */
//    @Test
    public void documentUpload() {
        int response = kudosService.kudosApiFourthStep(orderNo);
        System.out.println("result: " + response);
    }
    /**
     * Validation-Get
     * 4
     */
//    @Test
    public void validationGet() {
        int response = kudosService.kudosApiGetFifthStep(orderNo);
        System.out.println("result: " + response);
    }
    /**
     * Validation-Post
     * 5
     */
//    @Test
    public void validationPost() {
        int response = kudosService.kudosApiPostFifthStep(orderNo);
        System.out.println("result: " + response);
    }
    /**
     * loan schedule
     * 6
     *
     */
//    @Test
    public void loanSchedule() {
        int response = kudosService.kudosApiThirdStep(orderNo);
        System.out.println("result: " + response);
    }
    /**
     * TrancheAppend
     * 7
     *
     */
//    @Test
    public void loanTrancheAppend() {
        int resposne = kudosService.kudosApiSixthStep(orderNo);
        System.out.println("result: " + resposne);
    }
    /**
     * Loan-DemandNote
     * 8
     * */
//    @Test
    public void loanDemandNote(){
    	int resposne = kudosService.kudosLoanDemandNote(orderNo);
    	System.out.println("result:"+resposne);
    }

    /**
     * Reconciliation
     * 9
     *
     * */
//    @Test
    public void reconciliation(){
    	int resposne = kudosService.kudosReconciliation(orderNo);
    	System.out.println("result="+resposne);
    }

    /**
     * Status-Check
     * 10
     * */
//    @Test
    public void statusCheck(){
    	int resposne = kudosService.kudosStatusCheck(orderNo);
    	System.out.println("result="+resposne);
    }

    /**
     * NC-Check
     * 11
     * */
//    @Test
    public void nCCheck(){
    	int resposne = kudosService.kudosNCCheck(orderNo);
    	System.out.println("result="+resposne);
    }

    /**
     * Update-Document
     * 12
     * */
//    @Test
    public void updateDocument(){
    	int resposne = kudosService.kudosUpdateDocument(orderNo);
    	System.out.println("result="+resposne);
    }
    /***
     * Release 3: Partner loan settlement, transaction, request for loan settlement (14th July)
     *
     * */
    /**
     * Partner-LoanSettlement
     * 13
     * */
//    @Test
    public void partnerLoanSettlement(){
    	int resposne = kudosService.kudosLoanSettlement(orderNo);
    	System.out.println("result="+resposne);
    }

    /**
     * transaction
     * 14/15
     * com.cloud.order.controller.KudosController
     * */
//    @Test
      public void pGTxnNotify(){
    	int resposne = kudosService.kudosPGTxnNotify(orderNo);
    	System.out.println("result="+resposne);
      }
	  @Test
	  public void offlineTransactionNotify(){
	  	int resposne = kudosService.kudosOfflineTransactionNotify(orderNo);
	  	System.out.println("result="+resposne);
	  }
	  
     /**
      * Loan-StmtReq
      *
      * */
      @Test
	  public void loanStmtReq(){
    	  int resposne = kudosService.kudosLoanStateRequest(orderNo);
    	  System.out.println("result="+resposne);
	  }

    /***
     * Release 4: Partner to update statements periodically and request to increase FLDG APIs (15th July)
     *
     * */


    /**
     * Partner-PeriodicUpd
     *
     * */
//    @Test
    public void partnerPeriodicUpd(){

    }

    /**
     * Inc-FLDG
     *
     * */
//    @Test
    public void incFLDG(){

    }



//  @Test
  public void testTrancheAppend(){
  	String loanreq = "{\"status\": \"true\",\"result_code\": \"200\",\"message\": \"Success\",\"kudosloanid\": null,\"kudosborrowerid\": null,\"tranche-append\": \"VALIDATED\",\"num-of-loans\": \"34\",\"amount-total\": null,\"details\": \"Disbursment Request will be initiated\",\"TimestampofRequest\": \"2019-07-11 17:30:45\"}";
  	JSONObject jsonObject = JSONObject.fromObject(loanreq);
  	KDTrancheAppendReq loanRequestReq2 = JSON.parseObject(loanreq, KDTrancheAppendReq.class);
  	System.out.println("---------------------------->"+loanRequestReq2.getNumOfLoans());
  }
//    @Test
    public void kudosLoanDemandNoteTest() {
        LoanDemandNoteApiEntity ldnApiEntity = new LoanDemandNoteApiEntity();
        String kudosType = "Loan-DemandNote";

//        String response = kudosApiService.kudosLoanDemandNote(ldnApiEntity, kudosType, URL_VALUE);
//        System.out.println(response);
    }
}
