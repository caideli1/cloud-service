package com.cloud.model.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yoga
 * @Description: 用户银行卡
 */
@Data
public class UserBankCard implements Serializable {
    private static final long serialVersionUID = 7723666979577500630L;
    private Integer id;
    private Long userId;
    private String accountName;
    private String bankName;
    private String bankAccount;
    /**
     * IFSC Code 卡号国际code
     */
    private String ifscCode;
    /**
     * 使用状态:0>未使用；1>已使用 该卡有放款
     */
    private Integer status;
    private Date bindingTime;
    /**
     * 银行卡校验状态 unverified-未校验 created-校验中 completed-校验成功 其他-失败
     */
    private String vStatus;
    /**
     * 校验返回持卡人姓名
     */
    private String verifyReturnName;
    /**
     * 匹配结果，true:一致 false:不一致
     */
    private Boolean matchResult;


}
