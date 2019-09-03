package com.cloud.model.appUser;

import lombok.Data;

@Data
public class AppUserLoanInfoReq {
    //用户ID
    private Long userId;
    //产品ID
    private Integer productId;
    //用户借款金额
    private Integer amount;

}
