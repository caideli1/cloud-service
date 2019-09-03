package com.cloud.order.payment.queryPayment.kotak;

import com.cloud.model.common.PayStatus;
import com.cloud.model.product.FinanceLoanModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

/**
 * Created by hasee on 2019/7/24.
 */
@Component
@Slf4j
public class QueryKotakPayment {

    public static final String CLIENT_CODE = "TEMPTEST1";

    public static final String MSG_SOURCE = "MUTUALIND";

    public PayStatus query(FinanceLoanModel financeLoanModel){
        String postUrl = "https://apigwuat.kotak.com:8443/cms_generic_service?apikey=l7xx9e2c880d0b4549049ff62c5bf595c310";
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(postUrl);
        StringBuffer soapXml = buildSoap(financeLoanModel);
        try {
            String res = null;
            httpPost.setHeader("Content-Type", "application/soap+xml;charset=UTF-8;action=\"/BusinessServices/StarterProcesses/CMS_Generic_Service.serviceagent/Reversal\"");
            StringEntity data = new StringEntity(soapXml.toString(), Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                res = EntityUtils.toString(httpEntity, "UTF-8");
                log.info("订单号[{}]查询支付状态响应[{}]", financeLoanModel.getOrderNo(),res);
            }
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            financeLoanModel.setFailureReason("call kotak revesal error");
            e.printStackTrace();
            return PayStatus.FAILURE;
        }
        return  PayStatus.UNDERWAY;
    }

    public StringBuffer buildSoap(FinanceLoanModel financeLoanModel){
        StringBuffer sb = new StringBuffer("");
        sb.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:rev=\"http://www.kotak.com/schemas/CMS_Generic/Reversal_Request.xsd\">" +
                "   <soap:Body>" +
                "      <rev:Reversal>" +
                "         <rev:Header>" +
                "            <rev:Req_Id>"+financeLoanModel.getOrderNo()+"</rev:Req_Id>" +
                "            <rev:Msg_Src>"+MSG_SOURCE+"</rev:Msg_Src>" +
                "            <rev:Client_Code>"+CLIENT_CODE+"</rev:Client_Code>" +
                "            <rev:Date_Post>2019-07-24</rev:Date_Post>" +
                "         </rev:Header>" +
                "         <rev:Details>" +
                "            <!--Zero or more repetitions:-->" +
                "            <rev:Msg_Id>"+financeLoanModel.getOrderNo()+"</rev:Msg_Id>" +
                "         </rev:Details>" +
                "      </rev:Reversal>" +
                "   </soap:Body>" +
                "</soap:Envelope>");
        return sb;
    }

}
