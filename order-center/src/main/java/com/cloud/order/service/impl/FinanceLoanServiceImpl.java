package com.cloud.order.service.impl;

import com.cloud.common.component.utils.DateUtil;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.enums.LoanChannelEnum;
import com.cloud.common.enums.NoOffStatusEnum;
import com.cloud.model.common.PayStatus;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.model.user.UserLoan;
import com.cloud.order.dao.FinanceLoanDao;
import com.cloud.order.dao.OrderDao;
import com.cloud.order.handle.HandleManualLoan;
import com.cloud.order.handle.HandlePayment;
import com.cloud.order.model.FinanceLoanReportRecordFourteen;
import com.cloud.order.model.FinanceLoanReportRecordModel;
import com.cloud.order.model.FinanceLoanReportRecordSeven;
import com.cloud.order.model.ReportOrderDetailsModel;
import com.cloud.order.payment.facility.PaymentFacility;
import com.cloud.order.service.FinanceLoanService;
import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.cloud.service.feign.backend.BackendClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 放款记录查询服务实现
 *
 * @author bjy
 * @date 2019/2/26 0026 16:49
 */
@Slf4j
@Service
public class FinanceLoanServiceImpl implements FinanceLoanService {

    /**
     * 放款服务dao
     */
    @Autowired
    private FinanceLoanDao financeLoanDao;

    @Autowired
    private PaymentFacility paymentFacility;

    @Autowired
    private HandleManualLoan handleManualLoan;

    @Autowired
    private HandlePayment handlePayment;

    @Autowired
    private OrderDao orderDao;

    @Autowired(required = false)
    private RedisUtil redisUtil;

    @Autowired(required = false)
    private BackendClient backendClient;

    /**
     * 获取放款记录
     */
    @Override
    public JsonResult getFinanceLoan(Map<String, Object> parameter) {
        PageHelper.startPage(MapUtils.getIntValue(parameter, "page"), MapUtils.getIntValue(parameter, "limit"));
        List<FinanceLoanModel> list = financeLoanDao.getFinanceLoan(parameter);
        if (CollectionUtils.isNotEmpty(list)) {
            list.stream().forEach(financeLoanModel -> financeLoanModel.setOrderNo("MN" + financeLoanModel.getOrderNo() + "PD"));
        }
        PageInfo<FinanceLoanModel> page = new PageInfo<>(list);
        return JsonResult.ok(page.getList(), (int) page.getTotal());
    }

    /**
     * 查询放款失败记录
     *
     * @param parameter 放款失败查询条件
     * @return 放款失败记录信息
     */
    @Override
    public JsonResult getFinanceLoanFailure(Map<String, Object> parameter) {
        PageHelper.startPage(MapUtils.getIntValue(parameter, "page"), MapUtils.getIntValue(parameter, "limit"));
        List<FinanceLoanModel> list = financeLoanDao.getFinanceLoanFailure(parameter);

        for (FinanceLoanModel financeLoanModel : list) {
            financeLoanModel.setOrderNo("MN" + financeLoanModel.getOrderNo() + "PD");
            if (DateUtil.dayDate(DateUtil.getDate(financeLoanModel.getCreateTime(), 7)).after(DateUtil.dayDate(new Date()))) {
                financeLoanModel.setIsInvalid("2");
            } else {
                financeLoanModel.setIsInvalid("1");
            }
        }
        PageInfo<FinanceLoanModel> page = new PageInfo<>(list);

        return JsonResult.ok(page.getList(), (int) page.getTotal());
    }

    /**
     * 手动放款
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public Map<String, Object> manualLoan(String id) {
        Map<String, Object> map = new HashMap<>();
        //选择放款渠道
        Integer channel = getValidLoanChannnel();
        if (null == channel) {
            map.put("manualLoan", "failure:no valid loan channel");
            map.put("pay", "fail:未放款");
            return map;
        }

        boolean loanTimely = paymentFacility.isLoanTimely(channel);
        FinanceLoanModel financeLoan = null;
        FinancePayLogModel financePayLogModel = null;

        try {
            if (redisUtil.isLock("RE-PAYOUT:" + id)) {
                map.put("manualLoan", "failure:请勿重复提交!");
                throw new Exception("重复提交处理");
            }
            // 1：校验放款失败记录是否存在
            financeLoan = handleManualLoan.existFailRecord(map, id);
            //更新放款渠道
            financeLoan.setLoanChannel(channel);
            financeLoan.setFailureReason(null);
            // 2：获取该用户最新的银行卡
            handleManualLoan.getLatestBankNo(map, financeLoan);
            // 3：插入放款交易记录
            financePayLogModel = handleManualLoan.save(financeLoan, map);

            // 校验银行卡
            handlePayment.checkUserBankCard(financeLoan, loanTimely, map);

        } catch (Exception e) {
            e.printStackTrace();
            if (map.get("manualLoan") == null) {
                String handPError = (String) map.get("handP-error");
                handPError = handPError == null ? "fail:系统错误" : handPError;
                map.put("manualLoan", handPError);
            }
            map.put("pay", "fail:未放款");
            redisUtil.remove("RE-PAYOUT:" + id);
            return map;
        }
        //userLoanInfo loanModel payLogModel带了id

        PayStatus payStatus = handlePayment.payStatus(loanTimely, map, financeLoan);

        //这里直接使用终审通过后的逻辑
        handlePayment.savePayStatus(payStatus, financeLoan, financePayLogModel, map);

        map.put("manualLoan", "ok");

        return map;

    }

    /**
     * 查询交易记录信息
     *
     * @param parameter 交易记录查询参数
     * @return
     */
    @Override
    public JsonResult getPayLog(Map<String, Object> parameter) {
        PageHelper.startPage(MapUtils.getIntValue(parameter, "page"), MapUtils.getIntValue(parameter, "limit"));
        List<Map<String, Object>> list = financeLoanDao.getPayLog(parameter);
        PageInfo<Map<String, Object>> page = new PageInfo<>(list);
        return JsonResult.ok(page.getList(), (int) page.getTotal());
    }

    /**
     * 查询放款统计记录
     *
     * @param params
     * @return
     */
    @Override
    public JsonResult getFinanceLoanCount(Map<String, Object> params) {
        Integer page = MapUtils.getInteger(params, "page");
        Integer limit = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, limit);
        List<FinanceLoanReportRecordModel> list = financeLoanDao.getFinanceLoanCount(params);
        PageInfo<FinanceLoanReportRecordModel> pageInfo = new PageInfo<FinanceLoanReportRecordModel>(list);
        return JsonResult.ok(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @Override
    public JsonResult getFinanceLoanCountMessage(Map<String, Object> params) {
        Integer page = MapUtils.getInteger(params, "page");
        Integer limit = MapUtils.getInteger(params, "limit");
        if (page == null || limit == null) {
            return JsonResult.ok();
        }
        List<FinanceLoanReportRecordModel> orderLoanReportRecordModels = new ArrayList<>();
        List<FinanceLoanReportRecordModel> returnList = new ArrayList<>();
        List<ReportOrderDetailsModel> reportOrderDetailsModels = orderDao.getReportOrderDetails(params);

        if ((page - 1) * limit >= reportOrderDetailsModels.size()) {
            return JsonResult.ok();
        }
        Map<Date, List<ReportOrderDetailsModel>> reportGoup = reportOrderDetailsModels.stream().collect(Collectors.groupingBy(ReportOrderDetailsModel::getReportDate));
        //获取 报表键
        Set<Date> reportDates = reportGoup.keySet();
        //**

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Date reportDate : reportDates) {

            //3000 5000  8000 10000 12000 13000
            Integer loanSeven3000Count = 0;
            Integer loanSeven5000Count = 0;
            Integer loanSeven8000Count = 0;
            Integer loanSeven10000Count = 0;
            Integer loanSeven12000Count = 0;
            Integer loanSeven13000Count = 0;
            Integer loanFourteen3000Count = 0;
            Integer loanFourteen5000Count = 0;
            Integer loanFourteen8000Count = 0;
            Integer loanFourteen10000Count = 0;
            Integer loanFourteen12000Count = 0;
            Integer loanFourteen13000Count = 0;
            BigDecimal loanAmount =
                    reportGoup.get(reportDate).stream().
                            map(ReportOrderDetailsModel::getLoanAmount).reduce(BigDecimal::add).get();

            Integer loanCount =
                    reportGoup.get(reportDate).stream().
                            map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();

            //获取 贷款
            List<ReportOrderDetailsModel> loanSeven = reportGoup.get(reportDate).stream().
                    filter(ReportOrderDetailsModel ->
                            ReportOrderDetailsModel.getPeriod() == 7).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(loanSeven)) {
                List<ReportOrderDetailsModel> loanSeven3000 = loanSeven.stream().
                        filter(ReportOrderDetailsModel ->
                                ReportOrderDetailsModel.getAmountType() == 3000).collect(Collectors.toList());
                List<ReportOrderDetailsModel> loanSeven5000 = loanSeven.stream().
                        filter(ReportOrderDetailsModel ->
                                ReportOrderDetailsModel.getAmountType() == 5000).collect(Collectors.toList());
                List<ReportOrderDetailsModel> loanSeven8000 = loanSeven.stream().
                        filter(ReportOrderDetailsModel ->
                                ReportOrderDetailsModel.getAmountType() == 8000).collect(Collectors.toList());
                List<ReportOrderDetailsModel> loanSeven10000 = loanSeven.stream().
                        filter(ReportOrderDetailsModel ->
                                ReportOrderDetailsModel.getAmountType() == 10000).collect(Collectors.toList());
                List<ReportOrderDetailsModel> loanSeven12000 = loanSeven.stream().
                        filter(ReportOrderDetailsModel ->
                                ReportOrderDetailsModel.getAmountType() == 12000).collect(Collectors.toList());
                List<ReportOrderDetailsModel> loanSeven13000 = loanSeven.stream().
                        filter(ReportOrderDetailsModel ->
                                ReportOrderDetailsModel.getAmountType() == 13000).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(loanSeven3000)) {
                    loanSeven3000Count = loanSeven3000.stream().map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
                }
                if (CollectionUtils.isNotEmpty(loanSeven5000)) {
                    loanSeven5000Count = loanSeven5000.stream().map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
                }
                if (CollectionUtils.isNotEmpty(loanSeven8000)) {
                    loanSeven8000Count = loanSeven8000.stream().map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
                }
                if (CollectionUtils.isNotEmpty(loanSeven10000)) {
                    loanSeven10000Count = loanSeven10000.stream().map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
                }
                if (CollectionUtils.isNotEmpty(loanSeven10000)) {
                    loanSeven10000Count = loanSeven10000.stream().map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
                }
                if (CollectionUtils.isNotEmpty(loanSeven12000)) {
                    loanSeven12000Count = loanSeven12000.stream().map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
                }
                if (CollectionUtils.isNotEmpty(loanSeven13000)) {
                    loanSeven13000Count = loanSeven13000.stream().map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
                }

            }

            List<ReportOrderDetailsModel> loanFourteen = reportGoup.get(reportDate).stream().
                    filter(ReportOrderDetailsModel ->
                            ReportOrderDetailsModel.getPeriod() == 14).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(loanFourteen)) {
                List<ReportOrderDetailsModel> loanFourteen3000 = loanFourteen.stream().
                        filter(ReportOrderDetailsModel ->
                                ReportOrderDetailsModel.getAmountType() == 3000).collect(Collectors.toList());

                List<ReportOrderDetailsModel> loanFourteen5000 = loanFourteen.stream().
                        filter(ReportOrderDetailsModel ->
                                ReportOrderDetailsModel.getAmountType() == 5000).collect(Collectors.toList());
                List<ReportOrderDetailsModel> loanFourteen8000 = loanFourteen.stream().
                        filter(ReportOrderDetailsModel ->
                                ReportOrderDetailsModel.getAmountType() == 8000).collect(Collectors.toList());
                List<ReportOrderDetailsModel> loanFourteen10000 = loanFourteen.stream().
                        filter(ReportOrderDetailsModel ->
                                ReportOrderDetailsModel.getAmountType() == 10000).collect(Collectors.toList());
                List<ReportOrderDetailsModel> loanFourteen12000 = loanFourteen.stream().
                        filter(ReportOrderDetailsModel ->
                                ReportOrderDetailsModel.getAmountType() == 12000).collect(Collectors.toList());
                List<ReportOrderDetailsModel> loanFourteen13000 = loanFourteen.stream().
                        filter(ReportOrderDetailsModel ->
                                ReportOrderDetailsModel.getAmountType() == 13000).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(loanFourteen3000)) {
                    loanFourteen3000Count = loanFourteen3000.stream().map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
                }
                if (CollectionUtils.isNotEmpty(loanFourteen5000)) {
                    loanFourteen5000Count = loanFourteen5000.stream().map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
                }
                if (CollectionUtils.isNotEmpty(loanFourteen8000)) {
                    loanFourteen8000Count = loanFourteen8000.stream().map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
                }
                if (CollectionUtils.isNotEmpty(loanFourteen10000)) {
                    loanFourteen10000Count = loanFourteen10000.stream().map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
                }
                if (CollectionUtils.isNotEmpty(loanFourteen12000)) {
                    loanFourteen12000Count = loanFourteen12000.stream().map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
                }
                if (CollectionUtils.isNotEmpty(loanFourteen13000)) {
                    loanFourteen13000Count = loanFourteen13000.stream().map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
                }

            }
            if (loanCount > 0) {

                orderLoanReportRecordModels.add(
                        FinanceLoanReportRecordModel.builder().loanCount(loanCount)
                                .loanAmount(loanAmount)
                                .financeLoanReportRecordFourteen(
                                        FinanceLoanReportRecordFourteen
                                                .builder()
                                                .loanFourteen3000Count(loanFourteen3000Count).loanFourteen5000Count(loanFourteen5000Count)
                                                .loanFourteen8000Count(loanFourteen8000Count)
                                                .loanFourteen10000Count(loanFourteen10000Count)
                                                .loanFourteen12000Count(loanFourteen12000Count)
                                                .loanFourteen13000Count(loanFourteen13000Count).build())
                                .financeLoanReportRecordSeven(FinanceLoanReportRecordSeven.builder().loanSeven3000Count(loanSeven3000Count)
                                        .loanSeven5000Count(loanSeven5000Count)
                                        .loanSeven8000Count(loanSeven8000Count)
                                        .loanSeven10000Count(loanSeven10000Count)
                                        .loanSeven12000Count(loanSeven12000Count)
                                        .loanSeven13000Count(loanSeven13000Count).build())
                                .reportDate(simpleDateFormat.format(reportDate)).build()
                );
            }

        }

        orderLoanReportRecordModels=  orderLoanReportRecordModels.stream().sorted(Comparator.comparing(FinanceLoanReportRecordModel::getReportDate).reversed()).collect(Collectors.toList());


        returnList = orderLoanReportRecordModels.subList((page - 1) * limit,
                (page * limit) < orderLoanReportRecordModels.size() ? (page * limit) : (orderLoanReportRecordModels.size()));
        return JsonResult.ok(returnList, orderLoanReportRecordModels.size());
    }

    @Override
    public List<UserLoan> getUserLoanByCondition(Long userId, List<String> userContactRelations) {
        return financeLoanDao.getUserLoadByCondition(userId, userContactRelations);
    }

    @Override
    public List<UserLoan> getUserOverdueLoanByAadhaarAccount(String aadhaarAccount) {
        return financeLoanDao.getUserOverdueLoanByAadhaarAccount(aadhaarAccount);
    }

    @Override
    public int getLoanCountInSameCompanyName(long userId) {
        return financeLoanDao.getLoanCountInSameCompanyName(userId);
    }

    @Override
    public int getDueUserCountInSameCompanyName(long userId) {
        return financeLoanDao.getDueUserCountInSameCompanyName(userId);
    }

    @Override
    public int getLoanCountInSameCompanyPhone(long userId) {
        return financeLoanDao.getLoanCountInSameCompanyPhone(userId);
    }

    @Override
    public int getDueUserCountInSameCompanyPhone(String orderNum) {
        return financeLoanDao.getDueUserCountInSameCompanyPhone(orderNum);
    }

    @Override
    public List<Map<String, Object>> findFailFianceLoanByUserIdAndReason(Long userId, String reason, String reason2) {

        return financeLoanDao.findFailFianceLoanByUserIdAndReason(userId, reason, reason2);
    }

    @Override
    public JsonResult renewBankInfoTail(Long userId, String bankCode, String reason, String reason2) {
        //获取需要修改的列表
        List<Map<String, Object>>
                failFianceLoanList =
                financeLoanDao.findFailFianceLoanByUserIdAndReason(userId, reason, reason2);

        String comment = "bank account was updated";

        if (failFianceLoanList == null || failFianceLoanList.isEmpty()) {
            return JsonResult.errorMsg("not have update data");
        }

        for (Map<String, Object> failFianceLoan : failFianceLoanList) {
            financeLoanDao.updateFailFianceLoanRemarkById(MapUtils.getInteger(failFianceLoan, "id"), bankCode, comment);
        }

        return JsonResult.ok();
    }

    private Integer getValidLoanChannnel() {
        Integer loanChannel = null;

        boolean razorpayFlag = backendClient.queryNoOffInfo(NoOffStatusEnum.PAYOUT_RAZORPAY.getMessage());
        if (razorpayFlag) {
            return LoanChannelEnum.RAZORPAY.getCode();
        }

        boolean kotakFlag = backendClient.queryNoOffInfo(NoOffStatusEnum.PAYOUT_KOTAK.getMessage());
        if (kotakFlag) {
            return LoanChannelEnum.KOTAK.getCode();
        }

        return loanChannel;
    }

    @Override
    public List<String> listGetPayedPhonesInPhones(List<String> phoneList) {
        return financeLoanDao.listQueryPayedPhonesInPhones(phoneList);
    }
}
