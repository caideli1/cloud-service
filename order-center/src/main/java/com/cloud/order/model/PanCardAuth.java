package com.cloud.order.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PanCardAuth {


    /**
     * panCard 账号
     */
    private String  pancardNo;
    /**
     * 父亲姓名
     */
    private String fatherName;
    /**
     * panCard 拥有者
     */
    private   String pancardOwner;
    /**
     * pancard 背面照
     */
    private String panCardBack;

    /**
     * panCard 正面照
     */
    private String panCardFront;

}
