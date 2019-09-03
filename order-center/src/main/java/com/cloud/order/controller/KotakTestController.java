package com.cloud.order.controller;

import com.cloud.common.enums.LoanChannelEnum;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.order.payment.IThirdParPay;
import com.cloud.order.payment.ThirdParPayFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hasee on 2019/7/8.
 */

@Slf4j
@RestController
public class KotakTestController {

    @Autowired
    private ThirdParPayFactory thirdParPayFactory;

    @GetMapping("orders-anon/kotak-payment")
    public void kotakPayment() {
        IThirdParPay thirdParty = thirdParPayFactory.getThirdParPayInstance(LoanChannelEnum.KOTAK.getCode());
        List<FinanceLoanModel> listFinanceLoanModel = new ArrayList<>();
        listFinanceLoanModel.add(randomFinanceLoanModel_1());
        listFinanceLoanModel.add(randomFinanceLoanModel_2());
        listFinanceLoanModel.add(randomFinanceLoanModel_3());
        listFinanceLoanModel.add(randomFinanceLoanModel_4());
        for (FinanceLoanModel financeLoanModel : listFinanceLoanModel) {
            try{
                thirdParty.payment(financeLoanModel);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
    //ifsc不匹配
    public FinanceLoanModel randomFinanceLoanModel_1(){
        FinanceLoanModel random = new FinanceLoanModel();
        random.setPayAmount(new BigDecimal(1));
        random.setIfscCode("SBIN0000621");
        random.setBankNo("1227001500003843");
        random.setName("Jagpreet singh");
        random.setOrderNo("5689555785885941");
        random.setLoanChannel(LoanChannelEnum.KOTAK.getCode());
        random.setCreateTime(new Date());
        return random;
    }
    //ifsc错误
    public FinanceLoanModel randomFinanceLoanModel_2(){
        FinanceLoanModel random = new FinanceLoanModel();
        random.setPayAmount(new BigDecimal(1));
        random.setIfscCode("PUNB");
        random.setBankNo("1227001500003843");
        random.setName("Jagpreet singh");
        random.setOrderNo("5690117046130442");
        random.setLoanChannel(LoanChannelEnum.KOTAK.getCode());
        random.setCreateTime(new Date());
        return random;
    }
    //卡号错误
    public FinanceLoanModel randomFinanceLoanModel_3(){
        FinanceLoanModel random = new FinanceLoanModel();
        random.setPayAmount(new BigDecimal(1));
        random.setIfscCode("PUNB0122700");
        random.setBankNo("122700150000");
        random.setName("Jagpreet singh");
        random.setOrderNo("5688486084854415");
        random.setLoanChannel(LoanChannelEnum.KOTAK.getCode());
        random.setCreateTime(new Date());
        return random;
    }
    //全部有效
    public FinanceLoanModel randomFinanceLoanModel_4(){
        FinanceLoanModel random = new FinanceLoanModel();
        random.setPayAmount(new BigDecimal(1));
        random.setIfscCode("PUNB0122700");
        random.setBankNo("1227001500003843");
        random.setName("Jagpreet singh");
        random.setOrderNo("5699274965610659");
        random.setLoanChannel(LoanChannelEnum.KOTAK.getCode());
        random.setCreateTime(new Date());
        return random;
    }
}
