package com.cloud.order.constant;

public enum FinanceAccountStation {
    /**
     * 0:未启用
     */
    DOWN(0),
    /**
     * 1:启用
     */
    UP(1);
    public int num = 0;
    FinanceAccountStation(int num){
        this.num=num;
    }
}
