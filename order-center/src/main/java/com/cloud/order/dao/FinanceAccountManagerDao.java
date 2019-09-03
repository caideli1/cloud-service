package com.cloud.order.dao;

import com.cloud.order.model.FinanceAccountManagerModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 账户管理dao
 *
 * @author danquan.miao
 * @create 2019/4/29 0029
 * @since 1.0.0
 */
@Mapper
public interface FinanceAccountManagerDao {
    /**
     * 根据channel查询账户信息
     *
     * @param loanchannel
     * @return
     */
    FinanceAccountManagerModel getLoanAccountInfo(@Param("loanchannel") String loanchannel);

    /**
     * 查询最高优先级的有效账户
     *
     * @return
     */
    FinanceAccountManagerModel selectHighestPriorityAccount();
}
