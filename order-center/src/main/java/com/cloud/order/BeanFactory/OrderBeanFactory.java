package com.cloud.order.BeanFactory;

import com.cloud.common.component.utils.DateUtil;
import com.cloud.common.config.CommonConfig;
import com.cloud.common.enums.OperatorTypeEnum;
import com.cloud.common.utils.StringUtils;
import com.cloud.model.appUser.AppUserBasicInfo;
import com.cloud.model.appUser.AppUserLoanInfo;
import com.cloud.model.appUser.AppUserOrderInfo;
import com.cloud.model.common.OrderStatus;
import com.cloud.model.common.PayStatus;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.model.product.InterestPenaltyModel;
import com.cloud.model.product.LoanProductModel;
import com.cloud.model.product.RateType;
import com.cloud.model.product.constant.ProductConstants;
import com.cloud.model.user.UserBankCard;
import com.cloud.model.user.UserInfo;
import com.cloud.model.user.UserLoan;
import com.cloud.order.constant.LoanType;
import com.cloud.order.dao.BasicDao;
import com.cloud.order.dao.OrderDao;
import com.cloud.order.dao.ProductDao;
import com.cloud.order.model.*;
import com.cloud.service.feign.user.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
@Slf4j
public class OrderBeanFactory {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserClient userClient;
    @Autowired
    private BasicDao basicDao;

    // 创建初始化的放款交易记录
    public FinancePayLogModel createFinancePayLogModel(FinanceLoanModel loanModel, int loanType) {
        FinancePayLogModel payLogModel = new FinancePayLogModel();
        payLogModel.setOrderNo(loanModel.getOrderNo());
        payLogModel.setLoanChannel(loanModel.getLoanChannel());
        payLogModel.setLoanType(loanType);
        payLogModel.setMobile(loanModel.getMobile());
        payLogModel.setName(loanModel.getName());
        payLogModel.setCustomerNo(loanModel.getCustomerNo());
        payLogModel.setBankNo(loanModel.getBankNo());
        payLogModel.setPayStatus(PayStatus.INITIALIZE.num);
        payLogModel.setOrderStatus(OrderStatus.INITIALIZE.num);
        payLogModel.setRepayDate(DateUtil.getDate(new Date(), loanModel.getLoanPeriod() - 1));
        payLogModel.setCreateTime(new Date());
        // 流水号
        payLogModel.setSerialNumber(String.valueOf(Instant.now().toEpochMilli()));
        payLogModel.setAmount(loanModel.getPayAmount());
        //原始交易金额
        payLogModel.setOriginAmount(loanModel.getOriginAmount());
        payLogModel.setHandler(OperatorTypeEnum.PLATFORM.getCode());
        payLogModel.setMoneyType("Rs");
        payLogModel.setBankNo(loanModel.getBankNo());
        return payLogModel;
    }

    // 创建初始化的放款记录
    public FinanceLoanModel createFinanceLoan(AppUserLoanInfo userLoanInfo, Map<String, Object> result) throws Exception {
        FinanceLoanModel loanModel = new FinanceLoanModel();
        //注意：这里面的CreateTime为交易时间   放款成功后需修改
        loanModel.setCreateTime(new Date());
        //借款金额
        loanModel.setLoanAmount(userLoanInfo.getBorrowAmount());
        //实际放款金额  可以放小数金额
        BigDecimal payAmount = userLoanInfo.getLoanMoneyAmount();
        payAmount = payAmount.setScale(0, BigDecimal.ROUND_DOWN);
        loanModel.setPayAmount(payAmount);
        //原始应该放款
        loanModel.setOriginAmount(userLoanInfo.getLoanMoneyAmount());
        loanModel.setCustomerNo(userLoanInfo.getUserId());
        loanModel.setName(userLoanInfo.getUserName());
        loanModel.setMobile(userLoanInfo.getUserPhone());
        loanModel.setLoanPeriod(userLoanInfo.getTerm());
        loanModel.setPayStatus(PayStatus.INITIALIZE.num);
        loanModel.setOrderStatus(OrderStatus.INITIALIZE.num);
        // 关联借据编号
        loanModel.setLoanNumber(userLoanInfo.getLoanNumber());
        loanModel.setOrderNo(userLoanInfo.getLoanNumber());
        loanModel.setTxnDirection("D");
        loanModel.setModifyBankcardCount(0);
        //获取最近的银行卡
        UserBankCard userBankCard = userClient.getBankCardByUserId(userLoanInfo.getUserId());
        if (userBankCard != null && StringUtils.isNotBlank(userBankCard.getBankAccount())) {
            loanModel.setBankNo(userBankCard.getBankAccount());
            loanModel.setIfscCode(userBankCard.getIfscCode());
            result.put("userBankCard", userBankCard);
        } else {
            throw new Exception("未获取到银行卡");
        }
        //放款渠道
        loanModel.setLoanChannel(userLoanInfo.getLoanChannel());

        return loanModel;
    }

    // 创建借据
    public AppUserLoanInfo createLoanInfo(String orderId, Integer loanChannel) {
        OrderProcessModel processModel = orderDao.getOrderById(orderId);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", orderId);
        if (null != processModel) {
            AppUserLoanInfo loanInfo = new AppUserLoanInfo();
            LoanProductModel product = productDao.getLoanProductById((int) processModel.getProductId());
            // 利率表
            InterestPenaltyModel penaltyModel = productDao.findInterestById(product.getInterestId());
            // 额外总利率
            BigDecimal totalRate = BigDecimal.ZERO;
            // 利率表利率
            BigDecimal exceptRate = BigDecimal.ZERO;
            // 放款金额
            BigDecimal zero = BigDecimal.ZERO;

//            BigDecimal addedValue = CommonConfig.ADDEDVALUE;
            BigDecimal addedValue = productDao.getGstByProductId(product.getId());
            Date now = new Date();
            AppUserBasicInfo basicInfo = userClient.getBasicInfoById( processModel.getUserId());
            BigDecimal borrowMoney = BigDecimal.valueOf(processModel.getLoanAmount());
            if (null != product && null != product.getRateTypeList()) {
                for (RateType rate : product.getRateTypeList()) {
                    totalRate = totalRate.add(rate.getRate());
                }
                exceptRate = penaltyModel.getRate() == null ? exceptRate : penaltyModel.getRate();
            }
            // 利息费用
            BigDecimal decimal = borrowMoney.multiply(exceptRate).multiply(BigDecimal.valueOf(product.getTerm()));
            // 服务费 + 利息费 + 税务费
            BigDecimal totalFree = borrowMoney.multiply(totalRate).add(decimal)
                    .add(borrowMoney.multiply(totalRate).multiply(addedValue));

            //四舍五入取整
            //totalFree = totalFree.setScale(0, BigDecimal.ROUND_HALF_UP);

            loanInfo.setUserId(processModel.getUserId());
            loanInfo.setProductId(product.getId());
            loanInfo.setUserPhone(basicInfo.getCellPhone());
            loanInfo.setLoanNumber(processModel.getOrderNum());
            loanInfo.setUserName(basicInfo.getFirstName() + basicInfo.getLastName());
            loanInfo.setOverdueDay(0);
            loanInfo.setBorrowAmount(borrowMoney);
            loanInfo.setLoanMoneyAmount(borrowMoney.subtract(totalFree));
            loanInfo.setShouldRepayAmount(borrowMoney);
            loanInfo.setPaidTotalAmount(zero);
            loanInfo.setLateCharge(zero);
            loanInfo.setDeferDisbursement(zero);
            loanInfo.setOverdueStatus(ProductConstants.NOT_OVERDUE);
            loanInfo.setLoanStatus(ProductConstants.NOT_ACTIVATED);
            loanInfo.setBalanceDateStatus(ProductConstants.NOT_BALANCE_DATE);
            loanInfo.setLoanStartDate(new Date());
            loanInfo.setTerm(product.getTerm());
            Date date = DateUtil.getDate(now, product.getTerm() - 1);
            loanInfo.setLoanEndDate(date);
            loanInfo.setLoanChannel(loanChannel);
            return loanInfo;
        }
        return null;
    }

    // 创建一条待还款记录
    public FinanceRepayModel createFinanceRepayModel(FinanceLoanModel financeLoanModel) {
        FinanceRepayModel financeRepayModel = new FinanceRepayModel();
        Date date = DateUtil.getDate(new Date(), financeLoanModel.getLoanPeriod() - 1);
        financeRepayModel.setRepayDate(date);
        financeRepayModel.setCustomerNo(financeLoanModel.getCustomerNo());
        financeRepayModel.setName(financeLoanModel.getName());
        financeRepayModel.setMobile(financeLoanModel.getMobile());
        financeRepayModel.setLoanPeriod(financeLoanModel.getLoanPeriod());
        financeRepayModel.setLoanAmount(financeLoanModel.getLoanAmount());
        financeRepayModel.setPayAmount(financeLoanModel.getPayAmount());
        financeRepayModel.setBankNo(financeLoanModel.getBankNo());
        financeRepayModel.setOrderStatus(OrderStatus.WAITPROCESS.num);
        financeRepayModel.setOrderNo(financeLoanModel.getOrderNo());
        financeRepayModel.setLoanNumber(financeLoanModel.getLoanNumber());
        financeRepayModel.setActualAmount(new BigDecimal(0));
        financeRepayModel.setPayDate(null);
        financeRepayModel.setLoanType(LoanType.WAITREPAY.num);
        financeRepayModel.setPayStatus(PayStatus.WAIT.num);
        //初始化的还款记录没有还款渠道
        //financeRepayModel.setLoanChannel(financeLoanModel.getLoanChannel());
        financeRepayModel.setFailureReason("");
        return financeRepayModel;
    }

    //展期协议
    public ContractExtensionAgreement createExtensionAgreement(FinanceExtensionModel financeExtensionModel, AppUserLoanInfo userLoanInfo) {
        AppUserOrderInfo appUserOrderInfo = orderDao.getUserOrderByOrderNum(userLoanInfo.getLoanNumber());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ContractExtensionAgreement extensionAgreement = new ContractExtensionAgreement();
        extensionAgreement.setBorrower(userLoanInfo.getUserName());
        extensionAgreement.setLender("Kudos Finance & Investments Pvt.");
        extensionAgreement.setContractNo(appUserOrderInfo.getOrderNum());
        extensionAgreement.setBorrowAmount("Rs." + userLoanInfo.getBorrowAmount().toString());
        extensionAgreement.setLoanEndDate(sdf.format(userLoanInfo.getLoanEndDate()));
        extensionAgreement.setExtensionEndDate(sdf.format(financeExtensionModel.getExtensionEnd()));
        //展期利息
        //String extensionInterest = userLoanInfo.getBorrowAmount().multiply(new BigDecimal(0.015)).multiply(new BigDecimal(userLoanInfo.getTerm())).toString();
        BigDecimal deferRate = productDao.getInterestByType(CommonConfig.EXTENSION_INTEREST);
        extensionAgreement.setExtensionInterest(deferRate.multiply(new BigDecimal(100)).toString() + "% daily of the loan amount.");
        //展期之后要还的钱  相当于还款计划  也就是本金
        extensionAgreement.setExtensionAmount(userLoanInfo.getBorrowAmount().toString());
        extensionAgreement.setSignedDate(sdf.format(userLoanInfo.getLoanStartDate()));
        extensionAgreement.setLoanId(userLoanInfo.getId());
        extensionAgreement.setExtensionSignature(appUserOrderInfo.getSignatureUrl());
        extensionAgreement.setApplyNo(appUserOrderInfo.getApplyNum());
        return extensionAgreement;
    }

    public ContractLoan createContractLoan(AppUserOrderInfo appUserOrderInfo,
                                           FinanceLoanModel financeLoanModel, UserLoan userLoan, UserInfo custInfo) {
        ContractLoan contractLoan = new ContractLoan();
        contractLoan.setLoanId(userLoan.getId());
        contractLoan.setLoanDate(userLoan.getLoanStartDate());
        contractLoan.setAadhaar(custInfo.getAadhaarAccount());
        contractLoan.setApplyDate(appUserOrderInfo.getCreateTime());
        contractLoan.setBorrower(custInfo.getFirstName());
        contractLoan.setLoanEndDate(userLoan.getLoanEndDate());
        contractLoan.setApplyAmount(appUserOrderInfo.getLoanAmount());
        contractLoan.setPeriod(financeLoanModel.getLoanPeriod());
        //利息利率 0.1%
        BigDecimal interestRate = null;
        if (financeLoanModel.getLoanPeriod() == 7) {
            interestRate = productDao.getInterestByType(CommonConfig.INTEREST_7);
        }
        if (financeLoanModel.getLoanPeriod() == 14) {
            interestRate = productDao.getInterestByType(CommonConfig.INTEREST_14);
        }
        //利息金额
        BigDecimal interestAmount = null;
        interestAmount = appUserOrderInfo.getLoanAmount().multiply(interestRate).multiply(new BigDecimal(financeLoanModel.getLoanPeriod()));
        interestAmount = interestAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        contractLoan.setInterestAmount(interestAmount);

        interestRate = interestRate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        contractLoan.setInterestRate(interestRate.toString());

        //砍头费
        BigDecimal headAmount = userLoan.getBorrowAmount().subtract(userLoan.getLoanMoneyAmount());
        contractLoan.setHeadAmount(headAmount);
        contractLoan.setRepayAmount(appUserOrderInfo.getLoanAmount());
        //逾期利息利率 逾期费 2%
        BigDecimal overdueInterestRate = null;
        if (financeLoanModel.getLoanPeriod() == 7) {
            overdueInterestRate = productDao.getInterestByType(CommonConfig.INTEREST_DUE_7);
        }
        if (financeLoanModel.getLoanPeriod() == 14) {
            overdueInterestRate = productDao.getInterestByType(CommonConfig.INTEREST_DUE_14);
        }
        overdueInterestRate = overdueInterestRate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        contractLoan.setOverdueInterestRate(overdueInterestRate.toString());
        contractLoan.setContractNo(appUserOrderInfo.getOrderNum());
        contractLoan.setApplyNo(appUserOrderInfo.getApplyNum());
        contractLoan.setDeviceName(null);
        contractLoan.setDeviceId(null);
        contractLoan.setBorrowerSignature(appUserOrderInfo.getSignatureUrl());
        contractLoan.setKudosSignature(null);
        //contractLoan.setSignaturePlace("PUNE");
        contractLoan.setIp(basicDao.getIpByOrderNo(appUserOrderInfo.getOrderNum()));
        return contractLoan;
    }
}
