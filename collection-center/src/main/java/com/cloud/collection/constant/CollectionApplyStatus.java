package com.cloud.collection.constant;
/**
 * CollectionApplyStatus  class
 *
 * @author zhujingtao
 * @date 2019/03/11
 */
public enum CollectionApplyStatus {
    /**
     * 1:待审批
     */
    APPROVALPENDING(1),
    /**
     * 2：减免通过
     */
    PASS(2),
    /**
     * 3：减免拒绝
     */
    REFUSE(3),
    /**
     * 4：弃用
     */
    ABANDON(4);


    public int num = 0;
    CollectionApplyStatus(int num){
        this.num=num;
    }

}
