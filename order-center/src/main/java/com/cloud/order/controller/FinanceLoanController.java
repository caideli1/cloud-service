package com.cloud.order.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.user.UserLoan;
import com.cloud.order.service.FinanceLoanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 放款记录控制器
 *
 * @author bjy
 * @date 2019/2/26 0026 16:45
 */
@Slf4j
@RestController
public class FinanceLoanController {

    @Autowired
    private FinanceLoanService loanService;

    /**
     * 查询放款记录信息
     *
     * @param parameter
     * @return
     */
    @GetMapping("/finance-loan/getFinanceLoan")
    public JsonResult getFinanceLoan(@RequestParam Map<String, Object> parameter) {
        return loanService.getFinanceLoan(parameter);
    }

    /**
     * 查询放款失败记录信息
     *
     * @param parameter
     * @return
     */
    @PreAuthorize("hasAuthority('loan:failure') or hasAuthority('permission:all')")
    @GetMapping("/finance-loan/getFinanceLoanFailure")
    public JsonResult getFinanceLoanFailure(@RequestParam Map<String, Object> parameter) {
        return loanService.getFinanceLoanFailure(parameter);
    }

    /**
     * 放款重提中手动放款功能
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('loan:person') or hasAuthority('permission:all')")
    @PostMapping("/finance-loan/personLoan")
    public JsonResult personLoan(@RequestParam String id) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>开始处理放款失败记录【id:{}】的放款重提", id);
        if (StringUtils.isBlank(id)) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>放款失败, 记录id为空");
            return JsonResult.errorException("放款失败, 记录id为空");
        }


        Map<String, Object> map = loanService.manualLoan(id);

        String manualLoan = map.get("manualLoan") == null ? "failure:null" : map.get("manualLoan").toString();

        String pay = map.get("pay") == null ? "failure:null" : map.get("pay").toString();

        if (manualLoan.contains("ok") && (pay.contains("ok"))) {
            return JsonResult.ok();
        } else {
            return JsonResult.errorException("放款重提操作:" + manualLoan + ",放款重提-放款操作:" + pay);
        }

    }

    /**
     * 查询交易记录信息
     *
     * @param parameter
     * @return
     */
    @PreAuthorize("hasAuthority('trade:record') or hasAuthority('permission:all')")
    @GetMapping("/finance-loan/getPayLog")
    public JsonResult getPayLog(@RequestParam Map<String, Object> parameter) {
        return loanService.getPayLog(parameter);
    }

    /**
     * 查询放款统计信息
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('loan:list') or hasAuthority('permission:all')")
    @GetMapping("/finance-loan/getFinanceLoanCountMessage")
    public JsonResult getFinanceLoanCount(@RequestParam Map<String, Object> params) {
        return loanService.getFinanceLoanCount(params);
    }

    @GetMapping("/finance-loan/getFinanceLoanCount")
    public JsonResult getFinanceLoanCountMessage(@RequestParam Map<String, Object> params) {
        return loanService.getFinanceLoanCountMessage(params);
    }

    /**
     * 查询客户亲属放款记录
     *
     * @param userId
     * @param userContactRelations
     * @return
     */
    @GetMapping("/finance-anon/getUserLoanByCondition")
    public List<UserLoan> getUserLoanByCondition(@RequestParam Long userId,
                                                 @RequestParam List<String> userContactRelations) {
        return loanService.getUserLoanByCondition(userId, userContactRelations);
    }

    @GetMapping("/finance-anon/getUserOverdueLoanByAadhaarAccount")
    List<UserLoan> getUserOverdueLoanByAadhaarAccount(@RequestParam String aadhaarAccount) {
        return loanService.getUserOverdueLoanByAadhaarAccount(aadhaarAccount);
    }


    /**
     * 同一公司名称放款人数
     */
    @GetMapping("/finance-anon/loanCountInSameCompanyName")
    public int getLoanCountInSameCompanyName(@RequestParam long userId) {
        return loanService.getLoanCountInSameCompanyName(userId);
    }

    /**
     * 同一公司名称正在逾期人数
     */
    @GetMapping("/finance-anon/dueUserCountInSameCompanyName")
    public int getDueUserCountInSameCompanyName(@RequestParam long userId) {
        return loanService.getDueUserCountInSameCompanyName(userId);
    }


    /**
     * 同一单位电话放款人数
     */
    @GetMapping("/finance-anon/loanCountInSameCompanyPhone")
    public int getLoanCountInSameCompanyPhone(@RequestParam long userId) {
        return loanService.getLoanCountInSameCompanyPhone(userId);
    }


    /**
     * 同一单位电话的申请人正在逾期人数
     */
    @GetMapping("/finance-anon/dueUserCountInSameCompanyPhone")
    public int getDueUserCountInSameCompanyPhone(@RequestParam String orderNum) {
        return loanService.getDueUserCountInSameCompanyPhone(orderNum);
    }


    /**
     * 从号码中找出放过款的号码
     */
    @GetMapping("/finance-anon/payedPhone/list")
    List<String> listGetPayedPhonesInPhones (@RequestParam List<String> phoneList) {
        return loanService.listGetPayedPhonesInPhones(phoneList);
    }

}
