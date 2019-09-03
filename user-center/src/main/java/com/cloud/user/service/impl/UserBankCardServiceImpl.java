package com.cloud.user.service.impl;

import com.cloud.common.utils.StringUtils;
import com.cloud.user.dao.UserBankCardDao;
import com.cloud.user.service.UserBankCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户银行卡Service实现
 *
 * @author danquan.miao
 * @date 2019/8/20 0020
 * @since 1.0.0
 */
@Slf4j
@Service
public class UserBankCardServiceImpl implements UserBankCardService {
    @Autowired(required = false)
    private UserBankCardDao userBankCardDao;

    @Override
    public void updateBankCardInfo(String bankAccount, String ifsc, String verifyReturnName) {
        if (StringUtils.isBlank(bankAccount) && StringUtils.isBlank(ifsc)) {
            log.info("parameter error. bankAccount:{}, ifsc:{}", bankAccount, ifsc);
            return;
        }

        userBankCardDao.updateBankCardInfo(bankAccount, ifsc, verifyReturnName);
    }
}
