package com.cloud.collection.constant;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/23 16:31
 * 描述：
 */
public enum CollectionStationEnum {
    /**
     * 1.待派单
     */
    WAIT(1),
    /**
     * 2.催收中（已派单到制定催收员名下）
     */
    ASSGINING(2),
    /**
     * 3.完成（该条借据还清款项）
     */
    FINISH(3),
    /**
     *  4.停催
     */
    STOP(4);


    public int num = 0;
    CollectionStationEnum(int num){
        this.num=num;
    }
}
