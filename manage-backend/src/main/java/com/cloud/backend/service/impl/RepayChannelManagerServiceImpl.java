package com.cloud.backend.service.impl;

import com.cloud.backend.dao.RepayChannelManagerDao;
import com.cloud.backend.model.RepayChannelManager;
import com.cloud.backend.service.RepayChannelManagerService;
import com.cloud.common.base.BaseServiceImpl;
import com.cloud.model.manage.vo.RepayChannelManagerVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/7/25 14:06
 * 描述：
 */
@Service
@Slf4j
public class RepayChannelManagerServiceImpl extends BaseServiceImpl<RepayChannelManagerVo, RepayChannelManager> implements RepayChannelManagerService {

    @Resource
    private RepayChannelManagerDao repayChannelManagerDao;

    @PostConstruct
    public void init() {
        super.commonMapper = repayChannelManagerDao;
    }
}
