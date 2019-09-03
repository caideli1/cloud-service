package com.cloud.order.handle;

import com.cloud.common.enums.LoanChannelEnum;
import com.cloud.model.appUser.AppUserLoanInfo;
import com.cloud.model.common.CheckStatus;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.order.BeanFactory.OrderBeanFactory;
import com.cloud.order.constant.OrderNode;
import com.cloud.order.dao.FinanceAccountManagerDao;
import com.cloud.order.dao.FinanceLoanDao;
import com.cloud.order.dao.OrderDao;
import com.cloud.order.model.FinanceAccountManagerModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class HandleFinalJudgment {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private FinanceLoanDao loanDao;

    @Autowired
    private OrderBeanFactory orderBeanFactory;

    @Autowired(required = false)
    private FinanceAccountManagerDao accountManagerDao;

    // 校验订单审批状态 注意:orderId为申请订单的id
    public void isFinalJudgmentOrder(Map<String, Object> result, String orderId) throws Exception {
        log.info("step1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>查询订单【id:{}】是否可以进行终审操作", orderId);
        int checkStatus = orderDao.findCheckStatus(orderId);
        if (checkStatus != CheckStatus.WAITTINGFINALJUDGE.toNum()) {
            log.info("step1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单【id:{}】不是待终审状态,无法进行终审操作", orderId);
            result.put("judgment", "failure:非终审状态的订单");
            throw new Exception("failure:非终审状态的订单");
        }
        log.info("step1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单【id:{}】可以进行终审操作", orderId);
    }

    // 插入审批记录
    public void saveApprovalRecord(Map<String, Object> params, Map<String, Object> result) throws Exception {
        // 状态: 1.审批中 2.审批结束
        params.put("status", 2);
        // 审核结果: 0.未通过 1.通过
        params.put("result", 1);
        // 节点： 10.xx 11.xx (1开头代表机审) 20.xx 21.xx
        params.put("node", OrderNode.FINALJUDGE.toNum());
        // 是否预警: 0.否 1.是
        params.put("isWarning", params.getOrDefault("isWarning", 0));
        // 标签信息
        params.put("tagIds", params.getOrDefault("tagIds", ""));
        // 插入审批记录 如果出现异常 会被外部捕获
        log.info("step2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单【id:{}】创建审批记录开始", params.get("orderId"));
        try {
            //终审通过审批记录
            orderDao.insertOrderCheckLog(params);

            //开户放款审批记录
            params.put("node", OrderNode.OPENLOAN.toNum());
            params.put("reason", "");
            params.put("status", 2);
            params.put("auditorName", "系统审批");
            orderDao.insertOrderCheckLog(params);
        } catch (Exception e) {
            log.info("step2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单【id:{}】创建审批记录异常", params.get("orderId"));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.put("judgment", "failure:创建审批记录异常");
            e.printStackTrace();
            throw new Exception("failure:创建审批记录异常");
        }
        log.info("step2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单【id:{}】创建审批记录完成", params.get("orderId"));
    }

    // 更新订单审核的状态
    public void updateOrderStatus(Map<String, Object> params, Map<String, Object> result) throws Exception {
        // 审核状态： 1.机审 2.机审拒绝 3待初审 4.人工初审拒绝 5.待终审 6.人工终审拒绝 7.通过
        params.put("checkStatus", CheckStatus.PASSED.toNum());
        log.info("step3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单【id:{}】更新审核状态开始", params.get("orderId"));
        try {
            orderDao.updateOrderStatusById(params);
        } catch (Exception e) {
            log.info("step3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单【id:{}】更新审核状态失败", params.get("orderId"));
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.put("judgment", "failure:更新订单审核状态失败");
            e.printStackTrace();
            throw new Exception("failure:更新订单审核状态失败");
        }
        log.info("step3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单【id:{}】更新审核状态完成", params.get("orderId"));
    }

    public Integer getLoanChannel(Map<String, Object> result) throws Exception {
        log.info("step4>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>获取有效放款渠道开始");
        Integer loanChannel = null;

        try {
            FinanceAccountManagerModel accountManagerModel = accountManagerDao.selectHighestPriorityAccount();
            Optional<LoanChannelEnum> optional = LoanChannelEnum.getByValue(accountManagerModel.getLoanPassageway());
            loanChannel = optional.get().getCode();
        } catch (Exception e) {
            log.info("step4>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>获取有效放款渠道失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.put("judgment", "failure:获取有效放款渠道失败");
            e.printStackTrace();
            throw new Exception("failure:获取有效放款渠道失败");
        }

        log.info("step4>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>获取有效放款渠道结束, loanChannel:{}", loanChannel);
        return loanChannel;
    }

    // 插入借据
    public AppUserLoanInfo saveUserLoan(Integer loanChannel, String orderId, Map<String, Object> result) throws Exception {
        log.info("step5>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单【id:{}】插入建借据开始", orderId);
        AppUserLoanInfo userLoanInfo = null;
        try {
            userLoanInfo = orderBeanFactory.createLoanInfo(orderId, loanChannel);
            loanDao.saveLoanInfo(userLoanInfo);
        } catch (Exception e) {
            log.info("step5>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单【id:{}】插入借据失败", orderId);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.put("judgment", "failure:插入借据失败");
            e.printStackTrace();
            throw new Exception("failure:插入借据失败");
        }
        log.info("step5>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单【id:{}】插入借据完成", orderId);
        return userLoanInfo;
    }

    //插入放款记录与放款交易记录
    public void saveFinance(FinanceLoanModel loanModel, FinancePayLogModel payLogModel,
                            Map<String, Object> result) throws Exception {
        log.info("step6>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单-插入放款记录和放款交易记录开始");
        try {
            loanDao.savePayLog(payLogModel);
            loanDao.saveFinanceLoan(loanModel);
        } catch (Exception e) {
            log.info("step6>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单-插入放款记录和放款交易记录失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.put("judgment", "failure:插入放款记录和放款交易记录失败");
            e.printStackTrace();
            throw new Exception("failure:插入放款记录和放款交易记录失败");
        }
        log.info("step6>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单-插入放款记录和放款交易记录完成");
    }
}
