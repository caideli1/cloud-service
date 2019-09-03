package com.cloud.order.service;

import com.cloud.common.dto.JsonResult;
import com.cloud.order.model.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: zhujingtao
 * @CreateDate: 2019/2/26 14:05
 * 还款管理服务接口
 */
public interface FinanceRepayService {
    /**
     * 查找已还款列表
     *
     * @param params 根据前端传值 查出已还款对应值
     * @return 已还款 集合
     * @Author: zhujingtao
     */
    PageInfo<FinanceRepayModel> queryRepaymentPaidList(Map<String, Object> params);

    /**
     * 查找未还款列表
     *
     * @param params 根据前端传值 查出未还款对应值
     * @return 未还款 集合
     * @Author: zhujingtao
     */
    PageInfo<FinanceRepayModel> queryRepaymentUnpaidList(Map<String, Object> params);

    /**
     * 查找还款失败列表
     *
     * @param params 根据前端传值 查出未还款对应值
     * @return 未还款 集合
     * @Author: zhujingtao
     */
    PageInfo<FinanceRepayModel> queryRepaymentFailedList(Map<String, Object> params);

    /**
     * 查找逾期列表
     *
     * @param params 根据前端传值 查出未还款对应值
     * @return 查找逾期列表 集合
     * @Author: zhujingtao
     */
    PageInfo<FinanceDueOrderModel> queryLateOrderList(Map<String, Object> params);


    /**
     * 删除制定ID 的账户表
     *
     * @return 返回前端 信息
     * @Author: zhujingtao
     */
    JsonResult deleteAccountManager(String id);

    /**
     * 新增  的账户表
     *
     * @param params 前端传值
     * @return 返回前端 信息
     * @Author: zhujingtao
     */
    JsonResult insertAccountManager(Map<String, Object> params);

    /**
     * 修改账户表
     *
     * @param params 前端传值
     * @return 返回前端 信息
     * @Author: zhujingtao
     */
    JsonResult updateAccountManager(Map<String, Object> params);


    /**
     * 启用或停用
     *
     * @param params 前端传值
     * @return 返回前端 信息
     * @Author: zhujingtao
     */
    JsonResult updateAccountStation(Map<String, Object> params);

    /**
     * 查找 催收员 报表
     * @param params
     * @return
     */
    List<ReportCollectionManModel> queryReportCollectionManModel(Map<String, Object> params);

    /**
     * 查找账户列表
     *
     * @param params 传入名称
     * @return 账户列表查询
     * @Author: zhujingtao
     */
    PageInfo<FinanceAccountManagerModel> selectAccountList(Map<String, Object> params);

    /**
     * 查询所有的账户名列表
     *
     * @return 账户名列表
     * @Author: zhujingtao
     */
    List<String> selectAllAccountName();


    List<FinanceRepayModel> queryFinanceExtensionList(Map<String, Object> params);

}
