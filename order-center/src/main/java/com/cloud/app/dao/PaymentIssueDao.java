package com.cloud.app.dao;

import com.cloud.app.model.PaymentIssue;
import com.cloud.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yoga
 * @Description: PaymentIssueDao
 * @date 2019-07-3016:56
 */
@Mapper
public interface PaymentIssueDao extends CommonMapper<PaymentIssue> {
}
