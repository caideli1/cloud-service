package com.cloud.app.controller;

import com.cloud.app.dao.AppLoanDao;
import com.cloud.app.model.PaymentIssue;
import com.cloud.app.service.AppLoanService;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.enums.FunctionStatusEnum;
import com.cloud.common.enums.LoanChannelEnum;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.appUser.AppLoanVo;
import com.cloud.model.appUser.AppUserLoanInfo;
import com.cloud.model.appUser.AppUserOrderInfo;
import com.cloud.model.common.PayStatus;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.model.user.UserAddress;
import com.cloud.model.user.UserInfo;
import com.cloud.model.user.UserLoan;
import com.cloud.order.BeanFactory.OrderBeanFactory;
import com.cloud.order.config.KudosConfig;
import com.cloud.order.dao.BasicDao;
import com.cloud.order.dao.FinanceLoanDao;
import com.cloud.order.dao.OrderDao;
import com.cloud.order.model.ContractExtensionAgreement;
import com.cloud.order.model.ContractLoan;
import com.cloud.order.model.FinanceExtensionModel;
import com.cloud.order.model.RepayMentRspVo;
import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.cloud.service.feign.pay.PayClient;
import com.cloud.service.feign.user.UserClient;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: java类作用描述
 * @Author: wza
 * @CreateDate: 2019/1/25 18:05
 * @Version: 1.0
 */

@Slf4j
@RestController
public class AppLoanController {
    @Autowired
    private AppLoanService appLoanService;
    @Autowired
    private FinanceLoanDao financeLoanDao;
    @Autowired
    private OrderBeanFactory orderBeanFactory;
    @Autowired
    private AppLoanDao appLoanDao;
    @Autowired
    private UserClient userClient;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private BasicDao basicDao;

    @Autowired(required = false)
    private RedisUtil redisUtil;

    @Autowired(required = false)
    private KudosConfig kudosConfig;

    @Autowired(required = false)
    private PayClient payClient;

    /**
     * 分页条件查询用户ID的借据
     *
     * @param params
     * @param request
     * @return
     * @Author:wza
     */
    @GetMapping("/orders-anon/getLoans")
    public JsonResult getLoansById(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        Integer userId = Integer.valueOf(request.getHeader("mid"));
        params.put("userId", userId);
        PageInfo<AppLoanVo> infoPageInfo = appLoanService.getLoansById(params);
        return JsonResult.ok(infoPageInfo.getList(), (int) infoPageInfo.getTotal());
    }

    /**
     * 借据编号查借据
     *
     * @param loanNumber
     * @return
     * @Author:wza
     */
    @GetMapping("/orders-anon/getLoanById")
    public JsonResult getLoanInfoById(@RequestParam String loanNumber) {
        AppLoanVo userLoanInfo = appLoanService.getLoanInfoById(loanNumber);
        return JsonResult.ok(userLoanInfo);
    }

    @GetMapping("/orders-anon/getExtensionInfoByOrderNo")
    public JsonResult getExtensionInfoByOrderNo(@RequestParam String orderNo) {
        FinanceExtensionModel financeExtensionModel = basicDao.getFinanceExtensionByOrderNo(orderNo);
        if (financeExtensionModel == null) {
            return JsonResult.errorMsg("extension not exist");
        }
        AppUserLoanInfo userLoanInfo = appLoanDao.selectLoanInfoById(financeExtensionModel.getOrderNo());
        ContractExtensionAgreement extensionAgreement = orderBeanFactory.createExtensionAgreement(financeExtensionModel, userLoanInfo);
        extensionAgreement.setExtensionSignature(kudosConfig.getOssUrl() + extensionAgreement.getExtensionSignature());
        return JsonResult.ok(extensionAgreement);
    }

    //h5借据信息
    @GetMapping("/orders-anon/getIousInfoByOrderNo")
    public JsonResult getIousInfoByOrderNo(@RequestParam String orderNo) {
        UserLoan userLoan = financeLoanDao.getUserLoanByOrderNo(orderNo);
        if (userLoan == null) {
            log.info("订单orderNo:{} 借据不存在", orderNo);
            return JsonResult.errorMsg("order ious is not exist");
        }

        FinanceLoanModel financeLoanModel = financeLoanDao.getFinanceLoanModelByOrderNo(orderNo,
                PayStatus.SUCCESS.num);

        UserInfo custInfo = userClient.getUserInfo(userLoan.getUserId());

        AppUserOrderInfo appUserOrderInfo = orderDao.getUserOrderByOrderNum(orderNo);
        ContractLoan contractLoan = orderBeanFactory.createContractLoan(appUserOrderInfo, financeLoanModel, userLoan, custInfo);

        UserAddress homeAddress = getUserAddress(userLoan.getUserId(), 0);
        UserAddress companyAddress = getUserAddress(userLoan.getUserId(), 1);
        if (homeAddress != null) {
            contractLoan.setHomeAddress(homeAddress.getState() + " " + homeAddress.getDistrict() + "/" + homeAddress.getCounty() + " " + homeAddress.getTown());
        }
        if (companyAddress != null) {
            contractLoan.setCompanyAddress(companyAddress.getState() + " " + companyAddress.getDistrict() + "/" + companyAddress.getCounty() + " " + companyAddress.getTown());
        }
        String signatureUrl = appUserOrderInfo.getSignatureUrl();
        contractLoan.setBorrowerSignature(kudosConfig.getOssUrl() + signatureUrl);
        contractLoan.setKudosSignature(kudosConfig.getOssUrl() + "img/common/kudos.png");
        return JsonResult.ok(contractLoan);
    }

    private UserAddress getUserAddress(long userId, int type) {
        //0：家庭地址  1:公司地址   status:1 启用
        List<UserAddress> list = basicDao.getUserLocalAddress(userId, type, FunctionStatusEnum.ENABLED.code);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * 还款
     * 已弃用，老版本使用
     */
    @Deprecated
    @GetMapping("/orders-anon/app/payment")
    public JsonResult payment(@RequestParam String loanNumber) {
        Integer loanChannel = LoanChannelEnum.RAZORPAY.getCode();
        return payClient.repay(loanNumber, loanChannel);
    }

    /**
     * 预展期申请
     * 已弃用，老版本使用
     */
    @Deprecated
    @GetMapping("/orders-anon/app/preApplyExtension")
    public JsonResult preApplyExtension(@RequestParam String loanNumber) {
        Integer loanChannel = LoanChannelEnum.RAZORPAY.getCode();
        return payClient.applyExtension(loanNumber, loanChannel);
    }

    @PostMapping(value = "/orders-anon/razorpayCaptureHandler")
    public JsonResult razorpayCaptureHandler(@RequestParam String razorpay_payment_id, @RequestParam String orderNo,
                                             @RequestParam BigDecimal amount) {
        log.debug("支付授权成功,自动进行capture回调,razorpayPaymentId={},orderNo={},amount={}", razorpay_payment_id, orderNo, amount);
        // orderNo为流水号
        return appLoanService.capture(new Date(), orderNo, amount, razorpay_payment_id);
    }

    /**
     * 还款记录查询
     *
     * @Author:wza
     */
    @GetMapping("/orders-anon/app/repaymentList")
    public JsonResult repaymentList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        Integer userId = Integer.valueOf(request.getHeader("mid"));
        params.put("userId", userId);
        PageInfo<RepayMentRspVo> repays = appLoanService.queryRepayById(params);
        if (repays.getList().size() == 1 && null == repays.getList().get(0) || repays.getList().size() == 0) {
            return JsonResult.ok();
        }
        return JsonResult.ok(repays.getList(), (int) repays.getTotal());
    }

    @GetMapping("/orders-anon/app/deferredCalc")
    public JsonResult deferredCalc(@RequestParam String loanNumber) {
        return appLoanService.deferredCalc(loanNumber);
    }

    /**
     * app - 我的当前loan信息
     */
    @GetMapping("/app/my/currentOrder/info")
    public JsonResult appMyCurrentOrder() {
        Long userId = AppUserUtil.getLoginAppUser().getId();
        return JsonResult.ok(appLoanService.appMyCurrentOrderInfo(userId));
    }


    /**
     * app - 展期申请费用明细
     */
    @GetMapping("/orders-anon/app/loan/extensionApply/info")
    public JsonResult appExtensionApplyInfo(@RequestParam String loanNumber) {
        return JsonResult.ok(
                appLoanService.appExtensionApplyInfo(loanNumber).toAppToastString()
        );
    }


    @PostMapping("/orders-anon/app/paymentIssue")
    public JsonResult appAddPaymentIssue(@RequestBody PaymentIssue req) {
        appLoanService.appAddPaymentIssue(req);
        return JsonResult.ok();
    }
}
