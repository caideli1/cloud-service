package com.cloud.model.common;

public enum CheckStatusOrder {

    //通过
    PASS(1),
    // 拒绝
    REFUSE(0);

    public int num;

    CheckStatusOrder( int num){
        this.num=num;
    }
}
