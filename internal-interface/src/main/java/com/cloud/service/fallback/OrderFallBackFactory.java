package com.cloud.service.fallback;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.appUser.AppUserOrderInfo;
import com.cloud.model.cibil.OrderTableModel;
import com.cloud.model.order.OrderOntherRefuse;
import com.cloud.model.product.InterestPenaltyModel;
import com.cloud.model.product.LoanProductModel;
import com.cloud.model.user.UserLoan;
import com.cloud.service.feign.order.OrderClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单服务降级类
 *
 * @author danquan.miao
 * @date 2019/7/23 0023
 * @since 1.0.0
 */
@Component
public class OrderFallBackFactory implements FallbackFactory<OrderClient> {
    @Override
    public OrderClient create(Throwable throwable) {
        return new OrderClient() {

            @Override
            public List<OrderOntherRefuse> queryAllOrderOntherRefuse(String orderNo) {
                return null;
            }

            @Override
            public LoanProductModel getLoanProductById(Integer id) {
                return null;
            }

            @Override
            public InterestPenaltyModel getInterestPenaltyById(Integer id) {
                return null;
            }

            @Override
            public List<Map<String, Object>> findFailFianceLoanByUserIdAndReason(Long userId) {
                return null;
            }

            @Override
            public JsonResult renewBankInfoTail(Long userId, String bankCode) {
                return null;
            }

            @Override
            public boolean finalJudgeForLoan(Integer userId) {
                return false;
            }

            @Override
            public List<UserLoan> getUserLoanByCondition(Long userId, List<String> userContactRelations) {
                return null;
            }

            @Override
            public List<UserLoan> getUserOverdueLoanByAadhaarAccount(String aadhaarAccount) {
                return null;
            }

            @Override
            public boolean finalJudgeForLoanOrder(Integer userId) {
                return false;
            }

            @Override
            public List<AppUserOrderInfo> getOrderListByUserId(Long userId) {
                return null;
            }

            @Override
            public int getLoanCountInSameCompanyName(long userId) {
                return 0;
            }

            @Override
            public int getDueUserCountInSameCompanyName(long userId) {
                return 0;
            }

            @Override
            public int getLoanCountInSameCompanyPhone(long userId) {
                return 0;
            }

            @Override
            public int getDueUserCountInSameCompanyPhone(String orderNum) {
                return 0;
            }

            @Override
            public AppUserOrderInfo getUserOrderByOrderNum(String orderNum) {
                return null;
            }

            @Override
            public int getOrderCountInSameCompanyName(String orderNo) {
                return 0;
            }

            @Override
            public List<String> getOrderNoByUserId(Long userId) {
                return null;
            }

            @Override
            public List<OrderTableModel> getOrderByOrderNos(String orderNos) {
                return null;
            }

            @Override
            public int getOrderCountInSameCompanyPhone(String orderNum) {
                return 0;
            }

            // TODO: - 会导致拒绝
            @Override
            public boolean isCreatedOrderByCurrentUser(List<Long> userIds, Long currentUserId) {
                return false;
            }

            @Override
            public boolean saveCheckResult(String orderNo, Integer node, String rejectReason) {
                return false;
            }

            @Override
            public boolean updateMechineCheckToOrderByOrderNum(String orderNo, Integer checkStatus) {
                return false;
            }

            @Override
            public BigDecimal getProductGst(Integer productId)  {
                return null;
            }

            @Override
            public int countGetUserOrderByUserId(Long userId) {
                return 0;
            }

            @Override
            public int countGetNoRepayLoanByUserId(Long userId) {
                return 0;
            }

            @Override
            public List<Integer> listGetLoanStatusBySameAadhaarAddress(String address) {
                return null;
            }

            @Override
            public List<String> listGetDueOrderNumBySameAadhaarAddress(String address) {
                return null;
            }

            @Override
            public List<Integer> listGetLoanStatusByOrderNumList(List<String> orderNumList) {
                return null;
            }

            @Override
            public List<String> listGetRepayedDueOrderNumInOrderNumList(List<String> orderNumList) {
                return null;
            }

            @Override
            public List<String> listGetCurrentDueOrderNumByPhoneList(List<String> phoneList) {
                return null;
            }

            @Override
            public List<String> listGetPayedPhonesInPhones(List<String> phoneList) {
                return null;
            }

            @Override
            public String getUserLastRePayedLoanOrderNum(Long userId) {
                return null;
            }

            @Override
            public List<Integer> listGetDueDaysListByOrderNum( String orderNum) {
                return null;
            }

            @Override
            public List<String> listGetApplyPhonesInPhones(List<String> phoneList) {
                return null;
            }

        };
    }
}
