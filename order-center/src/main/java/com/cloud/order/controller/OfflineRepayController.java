package com.cloud.order.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.product.constant.ProductConstants;
import com.cloud.model.user.UserLoan;
import com.cloud.order.constant.OfflineRepayOperateTypeEnum;
import com.cloud.order.constant.VoucherIsConfirmEnum;
import com.cloud.order.dao.FinanceLoanDao;
import com.cloud.order.dao.OrderOfflineRepayVoucherDao;
import com.cloud.order.model.FinanceDueOrderModel;
import com.cloud.order.model.OrderOfflineRepayVoucherModel;
import com.cloud.order.service.OfflineRepayService;
import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 线下还款controller
 * Created by hasee on 2019/5/22.
 */
@Slf4j
@RestController
public class OfflineRepayController {

    @Autowired
    private FinanceLoanDao financeLoanDao;

    @Autowired
    private OrderOfflineRepayVoucherDao orderOfflineRepayVoucherDao;

    @Autowired
    private OfflineRepayService offlineRepayService;

    @Autowired(required = false)
    private RedisUtil redisUtil;

    @ApiOperation("查询未结清借据信息")
    @GetMapping("orders-anon/getUncloseIous")
    public JsonResult getUncloseIous(@RequestParam String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return JsonResult.errorMsg("Empty mobile！");
        }

        Map map = new HashMap<String, Object>(2);
        map.put("mobile", mobile);
        //逾期或者还款中的借据
        int[] iousStatus = new int[]{ProductConstants.PAYMENT, ProductConstants.OVERDUE, ProductConstants.IS_DISPOSA};
        map.put("iousStatus", iousStatus);
        List<UserLoan> uncloseIous = financeLoanDao.getUncloseIous(map);

        if (CollectionUtils.isEmpty(uncloseIous)) {
            return JsonResult.errorMsg("手机号【" + mobile + "】没有查询到待还款的借据!");
        }

        uncloseIous.stream().forEach(userLoan -> {
            if (StringUtils.isBlank(userLoan.getVoucherId())) {
                userLoan.setVoucherId("-1");
            }
        });

        return JsonResult.ok(uncloseIous);
    }

    @ApiOperation("根据凭证Id查询凭证信息")
    @GetMapping("orders-anon/getOfflineRepayVoucherById")
    public JsonResult getOfflineRepayVoucherById(@RequestParam int id) {
        OrderOfflineRepayVoucherModel orderOfflineRepayVoucherModel = orderOfflineRepayVoucherDao.findById(id);
        if (null == orderOfflineRepayVoucherModel) {
            return JsonResult.errorMsg("没有查询到线下还款转账凭证!");
        }

        return JsonResult.ok(orderOfflineRepayVoucherModel);
    }

    @ApiOperation("提交或者修改凭证")
    @PostMapping("orders-anon/offlineRepayVoucher")
    public JsonResult offlineRepayVoucher(@RequestBody OrderOfflineRepayVoucherModel orderOfflineRepayVoucherModel) {
        if (redisUtil.isLock("OrderOfflineRepayVoucher:" + orderOfflineRepayVoucherModel.getOrderNo())) {
            return JsonResult.errorMsg("该凭证正在操作当中！");
        }

        try {
            if (Objects.equals(orderOfflineRepayVoucherModel.getOperateType(), OfflineRepayOperateTypeEnum.SETTLE.getType()) &&
                    (orderOfflineRepayVoucherModel.getClosingDate() == null || orderOfflineRepayVoucherModel.getClosingDate().getTime() > new Date().getTime())) {
                return JsonResult.errorMsg("Closing date error!");
            }
            if (Objects.equals(orderOfflineRepayVoucherModel.getOperateType(), OfflineRepayOperateTypeEnum.EXTENSION.getType()) &&
                    orderOfflineRepayVoucherModel.getExtensionStartDate() == null) {
                return JsonResult.errorMsg("extension start date null");
            }
            if (orderOfflineRepayVoucherModel.getAccountDate() == null || orderOfflineRepayVoucherModel.getAccountDate().getTime() > new Date().getTime()) {
                return JsonResult.errorMsg("Account date error!");
            }

            if (orderOfflineRepayVoucherModel.getId() == null) {
                if (CollectionUtils.isEmpty(orderOfflineRepayVoucherDao.findByOrderNoAndOperateType(orderOfflineRepayVoucherModel.getOrderNo(),
                        orderOfflineRepayVoucherModel.getOperateType()))
                        || Objects.equals(orderOfflineRepayVoucherModel.getOperateType(), OfflineRepayOperateTypeEnum.EXTENSION.getType())) {
                    orderOfflineRepayVoucherModel.setCreateTime(new Date());
                    orderOfflineRepayVoucherModel.setUpdateTime(new Date());
                    orderOfflineRepayVoucherModel.setIsValid(Boolean.TRUE);
                    orderOfflineRepayVoucherModel.setIsConfirm(VoucherIsConfirmEnum.UN_CONFIRM.getStatus());
                    orderOfflineRepayVoucherDao.insert(orderOfflineRepayVoucherModel);
                } else {
                    return JsonResult.errorMsg("voucher is exist!");
                }
            } else {
                orderOfflineRepayVoucherDao.updateById(orderOfflineRepayVoucherModel);
            }

            return JsonResult.ok();
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return JsonResult.errorMsg("System error!");
        } finally {
            redisUtil.remove("OrderOfflineRepayVoucher:" + orderOfflineRepayVoucherModel.getOrderNo());
        }
    }

    @ApiOperation("确认展期/结清操作")
    @GetMapping("orders-anon/clearIous")
    public JsonResult clearIous(@RequestParam String orderNo, @RequestParam Integer voucherId) {
        return offlineRepayService.clearIous(orderNo, voucherId);
    }

    @ApiOperation("线下还款订单列表")
    @GetMapping("orders-anon/pageOfflineRepayOrder")
    public JsonResult offlineRepayOrder(@RequestParam Map<String, Object> parameter) {
        PageInfo<UserLoan> userLoanPageInfo = offlineRepayService.pageOfflineRepayOrder(parameter);
        //循环查库  这种写法不推荐   应该优化
        for (UserLoan userLoan : userLoanPageInfo.getList()) {
            //设置借据号
            userLoan.setLoanNumber("MN" + userLoan.getLoanNumber() + "PD");
            //设置为转账金额
            userLoan.setPaidTotalAmount(userLoan.getTransferAmount());
            FinanceDueOrderModel financeDueOrderModel = financeLoanDao.getFinanceDueOrderByOrderNo(userLoan.getLoanNumber(), null);
            if (financeDueOrderModel != null) {
                userLoan.setOverdueDay(String.valueOf(financeDueOrderModel.getDueDays()));
                userLoan.setLateCharge(financeDueOrderModel.getDueAmount());
            }
        }

        return JsonResult.ok(userLoanPageInfo.getList(), (int) userLoanPageInfo.getTotal());
    }
}
