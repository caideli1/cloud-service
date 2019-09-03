package com.cloud.model.common;

/**
 * CheckStatus class
 *
 * @author baijieye
 * @date 2019/02/19
 */
public enum CheckStatus {
    /**
     * 1.机审
     */
    MECHINEJUDGE(1),
    /**
     * 2.机审拒绝
     */
    MECHINEREFUSE(2),
    /**
     * 3.待初审
     */
    WAITTINGFIRSTJUDGE(3),
    /**
     * 4.人工初审拒绝
     */
    FIRSTREFUSE(4),
    /**
     * 5.待终审
     */
    WAITTINGFINALJUDGE(5),
    /**
     * 6.人工终审拒绝
     */
    FINALREFUSE(6),
    /**
     * 7.通过
     */
    PASSED(7),

    /**
     * 8.放款池
     */
    LENDINGPOOL(8);
    /**
     * 9.存量库
     */
    /*STOCKLIBRARY(9);*/


    int num=0;

    CheckStatus(int i) {
        this.num=i;
    }

    public int toNum(){
        return this.num;
    }
}
