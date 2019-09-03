package com.cloud.order.constant;

public enum TxnType {
	/** 放款申请 **/
    LA(1),
    
    /** 放款重提 **/
    LR(2);
    
    public int num;

	TxnType(int num) {
		this.num = num;
	}
    
}
