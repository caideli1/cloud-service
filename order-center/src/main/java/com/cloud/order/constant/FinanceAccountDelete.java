package com.cloud.order.constant;

public enum FinanceAccountDelete {
    /**
     * 0:未删除
     */
    UNDELETE(0),
    /**
     * 1:删除
     */
    DELETE(1);

    public int num;

    FinanceAccountDelete(int num){
        this.num=num;
    }
}
