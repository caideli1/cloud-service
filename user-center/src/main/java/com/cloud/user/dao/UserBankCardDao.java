package com.cloud.user.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户银行卡dao
 *
 * @author danquan.miao
 * @date 2019/8/20 0020
 * @since 1.0.0
 */
@Mapper
public interface UserBankCardDao {
    /**
     * 更新银行卡信息
     * @param bankAccount
     * @param ifsc
     * @param verifyReturnName
     */
    void updateBankCardInfo(String bankAccount, String ifsc, String verifyReturnName);
}
