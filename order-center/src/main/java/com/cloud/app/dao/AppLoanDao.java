package com.cloud.app.dao;

import com.cloud.model.appUser.AppUserLoanInfo;
import com.cloud.order.model.FinanceExtensionModel;
import com.cloud.order.model.FinanceRepayModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
/**
* @Description:    app借据 还款
* @Author:         wza
* @CreateDate:     2019/3/1 16:02
* @Version:        1.0
*/
@Mapper
public interface AppLoanDao {

    //更新用户最近的一张银行卡状态
    void updateBankCardStatus(Map<String, Object> params);

    /**
     *  条件分页查询用户借据
     * @param params
     * @return
     */
    List<AppUserLoanInfo> selectAllLoansById(Map<String, Object> params);

    /**
     * 查询借据详情
     * @param loanId
     * @return
     */
    AppUserLoanInfo selectLoanInfoById(String loanId);

    /**
     * 更新借据状态
     * @param appUserLoanInfo
     * @return
     */
    void updateLoanInfo(AppUserLoanInfo appUserLoanInfo);

    /**
     * 查询还款计划
     * @param orderId 订单号
     * @return
     */
    FinanceRepayModel queryRepayByOrderNum(String orderId);

    /**
     * 保存展期记录
     * @param extensionModel
     */
    void saveFinanceExtension(FinanceExtensionModel extensionModel);

    void updateDueOrderByOrderId(String orderId);

    /**
     * 分页查询用户还款记录
     * @param params
     * @return
     */
    List<FinanceRepayModel> queryRepayById(Map<String, Object> params);
}
