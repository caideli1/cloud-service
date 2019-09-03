package com.cloud.order.service.impl;

import com.cloud.app.dao.AppLoanDao;
import com.cloud.common.component.utils.DateUtil;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.enums.OperatorTypeEnum;
import com.cloud.model.appUser.AppUserLoanInfo;
import com.cloud.model.common.OrderStatus;
import com.cloud.model.common.PayStatus;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.model.product.constant.ProductConstants;
import com.cloud.model.user.UserLoan;
import com.cloud.order.constant.LoanType;
import com.cloud.order.constant.OfflineRepayOperateTypeEnum;
import com.cloud.order.constant.VoucherIsConfirmEnum;
import com.cloud.order.dao.FinanceLoanDao;
import com.cloud.order.dao.OrderOfflineRepayVoucherDao;
import com.cloud.order.model.FinanceExtensionModel;
import com.cloud.order.model.OrderOfflineRepayVoucherModel;
import com.cloud.order.service.OfflineRepayService;
import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.cloud.service.feign.pay.PayClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hasee on 2019/5/22.
 */
@Slf4j
@Service
public class OfflineRepayServiceImpl implements OfflineRepayService {

    @Autowired
    private OrderOfflineRepayVoucherDao orderOfflineRepayVoucherDao;

    @Autowired
    private FinanceLoanDao financeLoanDao;

    @Autowired
    private AppLoanDao appLoanDao;

    @Autowired
    private PayClient payClient;

    @Autowired(required = false)
    private RedisUtil redisUtil;

    @Override
    public JsonResult clearIous(String orderNo, Integer voucherId) {
        //获得线下还款凭证
        OrderOfflineRepayVoucherModel orderOfflineRepayVoucher = orderOfflineRepayVoucherDao.findById(voucherId);
        if (orderOfflineRepayVoucher == null) {
            return JsonResult.errorMsg("请上传线下还款凭证");
        }

        if (!Objects.equals(orderNo, orderOfflineRepayVoucher.getOrderNo())) {
            return JsonResult.errorMsg("orderNo not match");
        }

        if (Objects.equals(orderOfflineRepayVoucher.getIsConfirm(), VoucherIsConfirmEnum.CONFIRMED.getStatus())) {
            return JsonResult.errorMsg("has confirm the voucher");
        }

        UserLoan userLoan = financeLoanDao.getUserLoanByOrderNo(orderNo);
        if (userLoan.getLoanStatus() == ProductConstants.FINISH || userLoan.getLoanStatus() == ProductConstants.NOT_ACTIVATED) {
            return JsonResult.errorMsg("This loan has been finished!");
        }
        //校验金额
        if (BigDecimal.ZERO.compareTo(orderOfflineRepayVoucher.getTransferAmount()) >= 0) {
            return JsonResult.errorMsg("transfer amount <=0");
        }
        //校验还款凭证
        if (!orderOfflineRepayVoucher.getIsValid()) {
            return JsonResult.errorMsg(orderOfflineRepayVoucher.getErrorInfo());
        }
        //借据结清
        return closingIous(userLoan, orderOfflineRepayVoucher);
    }

    @Override
    public PageInfo<UserLoan> pageOfflineRepayOrder(Map<String, Object> parameter) {
        Integer page = MapUtils.getInteger(parameter, "page");
        Integer pageSize = MapUtils.getInteger(parameter, "limit");

        PageHelper.startPage(page, pageSize);
        List<UserLoan> userLoanList = financeLoanDao.queryOfflineRepayOrderList(parameter);
        PageInfo<UserLoan> pageInfo = new PageInfo<>(userLoanList);

        return pageInfo;
    }

    private JsonResult closingIous(UserLoan userLoan, OrderOfflineRepayVoucherModel orderOfflineRepayVoucher) {
        if (redisUtil.isLock("offlineRepay:" + userLoan.getLoanNumber())) {
            return JsonResult.errorMsg("Please do not submit again!");
        }
        if (userLoan.getLoanStatus() == ProductConstants.FINISH) {
            return JsonResult.errorMsg("This ious has been finished!");
        }

        Integer updateNum = orderOfflineRepayVoucherDao.updateByIdAndOrderNo(orderOfflineRepayVoucher.getOrderNo(),
                orderOfflineRepayVoucher.getId(), VoucherIsConfirmEnum.CONFIRMED.getStatus());
        if (null != updateNum && updateNum.intValue() != 1) {
            return JsonResult.errorMsg("This voucher confirm update fail");
        }

        Integer operateType = orderOfflineRepayVoucher.getOperateType();
        if (Objects.equals(operateType, OfflineRepayOperateTypeEnum.SETTLE.getType())) {
            return handleSettleIous(userLoan, orderOfflineRepayVoucher);
        } else if (Objects.equals(operateType, OfflineRepayOperateTypeEnum.EXTENSION.getType())) {
            return handleExtensionIous(userLoan, orderOfflineRepayVoucher);
        }

        return JsonResult.errorMsg("unknown operate type");
    }

    //线下还款 渠道与放款一致
    private JsonResult handleSettleIous(UserLoan userLoan, OrderOfflineRepayVoucherModel orderOfflineRepayVoucher) {
        FinancePayLogModel financePayLogModel = null;
        try {
            AppUserLoanInfo userLoanInfo = appLoanDao.selectLoanInfoById(userLoan.getLoanNumber());
            //取当前时间时分秒 跟closingDate的年月日拼接
            Date closingDate = DateUtil.transDateByCurr(orderOfflineRepayVoucher.getClosingDate());
            if (closingDate == null) {
                return JsonResult.errorMsg("Time error!");
            }
            //创建一条线下还款的交易记录
            financePayLogModel = this.saveFinancePayLog(closingDate, userLoanInfo, orderOfflineRepayVoucher.getTransferAmount(), null, OperatorTypeEnum.OFFLINE.getCode());
            String transactionTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(orderOfflineRepayVoucher.getClosingDate());
            payClient.handPaymentSuccess(financePayLogModel, transactionTimeStr);
            return JsonResult.ok();
        } catch (Exception ex) {
            log.error("线下结清失败", ex);
            payClient.handPaymentFail(financePayLogModel, "线下结清失败！");
            redisUtil.remove("offlineRepay:" + userLoan.getLoanNumber());
            return JsonResult.errorMsg("System error!");
        }
    }


    private JsonResult handleExtensionIous(UserLoan userLoan, OrderOfflineRepayVoucherModel orderOfflineRepayVoucher) {
        FinancePayLogModel financePayLogModel = null;
        try {
            AppUserLoanInfo userLoanInfo = appLoanDao.selectLoanInfoById(userLoan.getLoanNumber());
            //创建一条线下还款的交易记录
            Date now = new Date();
            financePayLogModel = this.saveFinancePayLog(now, userLoanInfo, orderOfflineRepayVoucher.getTransferAmount(), LoanType.EXTENSION, OperatorTypeEnum.OFFLINE.getCode());
            payClient.handleOfflineExtensionSuccess(financePayLogModel, now, orderOfflineRepayVoucher.getExtensionStartDate());
            return JsonResult.ok();
        } catch (Exception ex) {
            log.error("线下展期失败", ex);
            payClient.handPaymentFail(financePayLogModel, "线下展期失败！");
            redisUtil.remove("offlineRepay:" + userLoan.getLoanNumber());
            return JsonResult.errorMsg("System error!");
        }
    }


    // 还款-交易记录
    private FinancePayLogModel saveFinancePayLog(Date createTime, AppUserLoanInfo userLoanInfo, BigDecimal originAmount,
                                                 LoanType loanType, int handler) {
        FinancePayLogModel logModel = new FinancePayLogModel();
        logModel.setCreateTime(createTime);
        logModel.setOrderNo(userLoanInfo.getLoanNumber());
        logModel.setCustomerNo(userLoanInfo.getUserId());
        logModel.setName(userLoanInfo.getUserName());
        logModel.setMobile(userLoanInfo.getUserPhone());
        //原始交易金额
        logModel.setOriginAmount(originAmount);
        BigDecimal amount = originAmount.setScale(0, BigDecimal.ROUND_HALF_UP);
        // 交易金额  还款需要四舍五入处理
        logModel.setAmount(amount);
        logModel.setBankNo(userLoanInfo.getBankAccount());
        // 流水号
        logModel.setSerialNumber(String.valueOf(createTime.getTime()));
        //最终应还日期 用于计算还款类型 正常/逾期/提前
        Date lastRepayDate = null;
        if (userLoanInfo.getExtensionDueDate() == null) {
            //到期时间为借据结束时间
            lastRepayDate = userLoanInfo.getLoanEndDate();
        } else {
            //到期时间为展期结束时间
            lastRepayDate = userLoanInfo.getExtensionDueDate();
        }
        //初始应还日期
        logModel.setRepayDate(userLoanInfo.getLoanEndDate());

        //2019.8.12修改为最近的展期的结束时间为应还时间，从展期记录里面取值 created by caideli
        Map<String, Object> paramsFoExtension = new HashMap<>(1);
        log.info("获取最新最近的一条展期记录的入参：orderNo:" + userLoanInfo.getLoanNumber());
        paramsFoExtension.put("orderNo", userLoanInfo.getLoanNumber());
        FinanceExtensionModel financeExtensionModel = financeLoanDao.getOneNewFinanceExtension(paramsFoExtension);
        if (financeExtensionModel != null && financeExtensionModel.getExtensionEnd() != null) {
            //最新的展期结束时间是应还日期
            logModel.setRepayDate(financeExtensionModel.getExtensionEnd());
        } else {
            logModel.setRepayDate(lastRepayDate);
        }

        // 用户
        logModel.setHandler(handler);
        // 待支付
        logModel.setPayStatus(PayStatus.WAIT.num);
        // 待处理
        logModel.setOrderStatus(OrderStatus.WAITPROCESS.num);
        logModel.setFailureReason("");
        logModel.setLoanChannel(userLoanInfo.getLoanChannel());
        logModel.setMoneyType("Rs");

        //设置交易记录类型为展期,如果为 展期类型 再细分 6：展期申请(正常展期) 9:提前展期 10:逾期展期
        if (loanType == LoanType.EXTENSION) {
            //如果展期时间大于还款日时间，则表示逾期展期
            if (DateUtils.truncatedCompareTo(logModel.getCreateTime(), logModel.getRepayDate(), Calendar.DATE) > 0) {
                logModel.setLoanType(LoanType.EXTENSIONOVERDUE.num);
            }//如果展期时间小于还款日时间，则表示提前展期
            else if (DateUtils.truncatedCompareTo(logModel.getCreateTime(), logModel.getRepayDate(), Calendar.DATE) < 0) {
                logModel.setLoanType(LoanType.EXTENSIONAHEAD.num);
            } else {//当天的，正常展期
                logModel.setLoanType(LoanType.EXTENSION.num);
            }
        } else {
            //交易时间与最终到期日比较   线下结清会有时间差 所以不能用逾期天数来判断逾期
            if (DateUtil.getDayCount(lastRepayDate, createTime) > 0) {
                logModel.setLoanType(LoanType.DUEREPAY.num);
            } else {
                // 最后还款日期与当前日期的时间差(单位:天)
                if (DateUtil.getDayCount(createTime, lastRepayDate) > 0) {
                    // 类型:提前结清
                    logModel.setLoanType(LoanType.ADVANCECLEAR.num);
                } else {
                    logModel.setLoanType(LoanType.NORMALREPAY.num);
                }
            }
        }

        financeLoanDao.savePayLog(logModel);
        return logModel;
    }
}
