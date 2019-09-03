package com.cloud.service.feign.order;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.appUser.AppUserOrderInfo;
import com.cloud.model.cibil.OrderTableModel;
import com.cloud.model.order.OrderOntherRefuse;
import com.cloud.model.product.InterestPenaltyModel;
import com.cloud.model.product.LoanProductModel;
import com.cloud.model.user.UserLoan;
import com.cloud.service.fallback.OrderFallBackFactory;
import org.apache.ibatis.annotations.Select;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@FeignClient(name ="order-center", fallbackFactory = OrderFallBackFactory.class)
public interface OrderClient {

	@GetMapping("/orders-anon/internal/loanProduct")
    LoanProductModel getLoanProductById(@RequestParam("id") Integer id);

    @GetMapping("/orders-anon/internal/interestPenaltyModel")
    InterestPenaltyModel getInterestPenaltyById(@RequestParam("id") Integer id);

    @GetMapping("/orders-anon/internal/findFailFianceLoanByUserIdAndReason")
    List<Map<String,Object>> findFailFianceLoanByUserIdAndReason(@RequestParam("userId") Long userId);

    @GetMapping("/orders-anon/internal/renewBankInfoTail")
    JsonResult renewBankInfoTail(@RequestParam("userId") Long userId, @RequestParam("bankCode") String bankCode);

    @GetMapping("/orders-anon/internal/finalJudgeForLoan")
    boolean finalJudgeForLoan(@RequestParam("userId") Integer userId);

    @GetMapping("/finance-anon/getUserLoanByCondition")
    List<UserLoan> getUserLoanByCondition(@RequestParam Long userId, @RequestParam List<String> userContactRelations);

    @GetMapping("/finance-anon/getUserOverdueLoanByAadhaarAccount")
    List<UserLoan> getUserOverdueLoanByAadhaarAccount(@RequestParam String aadhaarAccount);


    @GetMapping("/orders-anon/internal/finalJudgeForLoanOrder")
    boolean finalJudgeForLoanOrder(@RequestParam("userId")Integer userId);

    @GetMapping("/orders-anon/getOrderListByUserId")
    List<AppUserOrderInfo> getOrderListByUserId(@RequestParam("userId")Long userId);

    /**
     * 同一公司名称放款人数
     */
    @GetMapping("/finance-anon/loanCountInSameCompanyName")
    int getLoanCountInSameCompanyName(@RequestParam long userId);

    /**
     * 同一公司名称正在逾期人数
     */
    @GetMapping("/finance-anon/dueUserCountInSameCompanyName")
    int getDueUserCountInSameCompanyName(@RequestParam long userId);


    /**
     * 同一单位电话放款人数
     */
    @GetMapping("/finance-anon/loanCountInSameCompanyPhone")
    int getLoanCountInSameCompanyPhone(@RequestParam long userId);


    /**
     * 同一单位电话的申请人正在逾期人数
     */
    @GetMapping("/finance-anon/dueUserCountInSameCompanyPhone")
    int getDueUserCountInSameCompanyPhone(@RequestParam String orderNum);

    @GetMapping("orders-anon/getUserOrderByOrderNum")
    AppUserOrderInfo getUserOrderByOrderNum(@RequestParam String orderNum);

    /**
     * 同一公司名称申请人数
     */
    @GetMapping("/orders-anon/getOrderCountInSameCompanyName")
    int getOrderCountInSameCompanyName(@RequestParam String orderNo);


    @GetMapping("/orders-anon/getOrderNoByUserId")
    List<String> getOrderNoByUserId(@RequestParam Long userId);


    @GetMapping("/orders-anon/getOrderByOrderNos")
    List<OrderTableModel>  getOrderByOrderNos(@RequestParam String orderNos);

    /**
     * 同一单位电话匹配申请人数
     */
    @GetMapping("/orders-anon/orderCountInSameCompanyPhone")
    int getOrderCountInSameCompanyPhone(@RequestParam String orderNum);

    @GetMapping("/orders-anon/isCreatedOrderByCurrentUser")
    boolean isCreatedOrderByCurrentUser(@RequestParam("userIds") List<Long> userIds, @RequestParam("currentUserId") Long currentUserId);

    @GetMapping("/orders-anon/saveCheckResult")
    boolean  saveCheckResult(@RequestParam  String orderNo, @RequestParam  Integer    node,@RequestParam String  rejectReason);

    /**
     *  处理 审核订单状态
     * @param orderNo
     * @param checkStatus
     * @return
     */
    @GetMapping("/orders-anon/updateMechineCheckToOrderByOrderNum")
    boolean updateMechineCheckToOrderByOrderNum(@RequestParam String orderNo, @RequestParam Integer  checkStatus);

    @GetMapping("/orders-anon/product/gst")
    BigDecimal getProductGst(@RequestParam Integer productId);


    @GetMapping("/orders-anon/queryAllOrderOntherRefuse")
    List<OrderOntherRefuse> queryAllOrderOntherRefuse(@RequestParam String orderNo);

    @GetMapping("/orders-anon/userOrderCount")
    int countGetUserOrderByUserId(@RequestParam Long userId);

    @GetMapping("/orders-anon/noRepayLoanCount")
    int countGetNoRepayLoanByUserId(@RequestParam Long userId);

    @GetMapping("/orders-anon/sameAadhaarAddress/loanStatus/list/")
    List<Integer> listGetLoanStatusBySameAadhaarAddress(@RequestParam String address);

    /**
     * 同AadhaarAddress逾期订单
     */
    @GetMapping("/orders-anon/sameAadhaarAddress/dueOrder/list")
    List<String> listGetDueOrderNumBySameAadhaarAddress(@RequestParam String address);

    /**
     * 返回订单的状态
     */
    @GetMapping("/orders-anon/loanStatus/list/")
    List<Integer> listGetLoanStatusByOrderNumList(@RequestParam List<String> orderNumList);

    /**
     * 从号码中找结清且有逾期记录的订单
     */
    @GetMapping("/orders-anon/dueOrderNum/list/")
    List<String> listGetRepayedDueOrderNumInOrderNumList(@RequestParam List<String> orderNumList);

    /**
     * 从号码中找出放正在逾期的订单
     */
    @GetMapping("/orders-anon/currentDueOrderNum/list/")
    List<String> listGetCurrentDueOrderNumByPhoneList(@RequestParam List<String> phoneList);

    /**
     * 从号码中找出放过款的号码
     */
    @GetMapping("/finance-anon/payedPhone/list")
    List<String> listGetPayedPhonesInPhones (@RequestParam List<String> phoneList);

    /**
     * 从号码中找出申请过的号码
     */
    @GetMapping("/finance-anon/applyedPhone/list")
    List<String> listGetApplyPhonesInPhones (@RequestParam List<String> phoneList);

    /**
     * 找出上一还完款的借据单号
     */
    @GetMapping("/orders-anon/{userId}/lastRePayedLoanOrderNum")
    String getUserLastRePayedLoanOrderNum(@PathVariable Long userId);

    @GetMapping("/orders-anon/dueDaysList")
    List<Integer> listGetDueDaysListByOrderNum(@RequestParam String orderNum);

}
