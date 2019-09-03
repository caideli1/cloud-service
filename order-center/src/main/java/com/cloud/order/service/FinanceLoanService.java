package com.cloud.order.service;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.user.UserLoan;
import com.cloud.order.model.FinanceExtensionModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 放款记录服务
 * @author bjy
 * @date 2019/2/26 0026  16:48
 */
public interface FinanceLoanService {
    /**
     * 获取放款记录信息
     * @param parameter 放款记录参数
     * @return
     */
    JsonResult getFinanceLoan(Map<String,Object> parameter);

    /**
     * 获取放款失败记录信息
     * @param parameter
     * @return
     */
    JsonResult getFinanceLoanFailure(Map<String,Object> parameter);

    /**
     * 手动放款
     * @param id
     * @return
     */
    Map<String, Object> manualLoan (String id);

    /**
     * 查询交易记录信息
     * @param parameter
     * @return
     */
    JsonResult getPayLog(Map<String,Object> parameter);

    /**
     * 查询放款统计记录
     * @param params
     * @return
     */
    JsonResult getFinanceLoanCount(Map<String, Object> params);

    List<UserLoan> getUserLoanByCondition(Long userId, List<String> userContactRelations);

    List<UserLoan> getUserOverdueLoanByAadhaarAccount(String aadhaarAccount);


    /**
     * 同一公司名称放款人数
     */
    int getLoanCountInSameCompanyName(long userId);

    /**
     * 同一公司名称正在逾期人数
     */
    int getDueUserCountInSameCompanyName(long userId);



    /**
     * 同一单位电话放款人数
     */
    int getLoanCountInSameCompanyPhone(long userId);



    /**
     * 同一单位电话的申请人正在逾期人数
     */
    int getDueUserCountInSameCompanyPhone(String orderNum);
    /**
     * 获取 放款失败 的列表
     * @param userId
     * @param reason
     * @param  reason2
     * @return
     */
    List<Map<String,Object>> findFailFianceLoanByUserIdAndReason(Long userId,String reason,String reason2);

    /**
     * 修改 放款失败的
     * @param userId
     * @param reason
     * @return
     */
    JsonResult  renewBankInfoTail(Long userId,String bankCode,String reason,String reason2);


    JsonResult getFinanceLoanCountMessage(Map<String, Object> params);

    List<String> listGetPayedPhonesInPhones (List<String> phoneList);

}
