package com.cloud.user.service;

/**
 * 用户银行卡service
 *
 * @author danquan.miao
 * @date 2019/8/20 0020
 * @since 1.0.0
 */
public interface UserBankCardService {
    /**
     * 更新用户银行卡信息
     * @param bankAccount
     * @param ifsc
     * @param verifyReturnName
     */
    void updateBankCardInfo( String bankAccount, String ifsc, String verifyReturnName);
}
