package com.cloud.model.kotak;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hasee on 2019/4/24.
 */
@Data
public class KotakPaymentLog {

    public int  id;

    public String messageId;

    public String clientCode;

    public String msgSource;

    public String instRefNo;

    public BigDecimal txnAmnt;

    public String recBrCd;

    public String beneAcctNo;

    public String beneName;

    public String myProdCode;

    public String accountNo;

    public String paymentDt;

    public String instDt;

    public Date createTime;

    public Date updateTime;

}
