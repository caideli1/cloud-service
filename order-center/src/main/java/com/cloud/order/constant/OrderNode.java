package com.cloud.order.constant;

/**
 * OrderNode enum
 *
 * @author bujieye
 * @date 2019/02/19
 */
public enum OrderNode {

    /**
     *  10.规则一
     */
    RULEONE(10),
    /**
     * 11.规则二
     */
    RULETWO(11),
    /**
     * 12.规则三
     */
    RULETHREE(12),
    /**
     * 13.规则四
     */
    RULEFOUR(13),
    /**
     *  20.人工初审
     */
    FIRSTJUDGE(20),
    /**
     *  21.人工终审
     */
    FINALJUDGE(21),
    /**
     * 30.开户放款
     */
    OPENLOAN(30),
    /**
     * 40.放款池
     */
    LENDINGPOOL(40),
    /**
     * 50.存量库
     */
    STOCKLIBRARY(50);


    private int num=0;

    OrderNode(int i) {
        this.num=i;
    }

    public int toNum(){
        return this.num;
    }
}
