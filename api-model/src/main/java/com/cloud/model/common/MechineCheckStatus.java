package com.cloud.model.common;
/**
 * @author zhujingtao
 * @date 2019/3/29 0028 19:03
 */
public enum MechineCheckStatus {

    //机审转换参数+1
    BASE(10),
    // 机审一
    ONE(11),
    // 机审二
    TWO(12),
    // 机审三
    THREE(13),
    // 机审四
    FOUR(14),
    // 机审五
    FIVE(15),
    // 机审6
    SIX(16),
    ;
    public int num;

    MechineCheckStatus( int num){
        this.num=num;
    }
}
