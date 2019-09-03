package com.cloud.order.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndexCustomerMoth {


    /**
     * 新增用戶環比
     */
    private Double  chainNewUserAddedPre;

    /**
     *新增用戶數量
     */
    private Integer newUserAddedNum;

    /**
     * 新增用戶環比
     */
    private Double  chainOldUserAddedPre;

    /**
     *老客新增訂單數量
     */
    private Integer oldAddedNum;

    /**
     * 新增訂單數量環比
     */
    private Double  chainNewOrderAddedPre;

    /**
     *新增訂單數量
     */
    private Integer newOrderAddedNum;
}
