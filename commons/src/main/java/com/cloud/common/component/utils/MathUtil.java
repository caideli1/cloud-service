package com.cloud.common.component.utils;

import java.math.BigDecimal;

public class MathUtil {

    /**
     * 四捨五入
     * @param data  數值
     * @param digit 位數
     * @return  四捨五入后的數字
     */
    public static  Double  romand(Double data, Integer digit){
        BigDecimal b = new BigDecimal(data.toString());
       return b.setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
