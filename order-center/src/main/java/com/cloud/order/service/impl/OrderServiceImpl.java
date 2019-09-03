package com.cloud.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.component.utils.DateUtil;
import com.cloud.common.component.utils.MathUtil;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.enums.CashLoadSystemEnum;
import com.cloud.common.enums.LoanChannelEnum;
import com.cloud.common.enums.NoOffStatusEnum;
import com.cloud.common.exception.BusinessException;
import com.cloud.common.utils.DataPageUtil;
import com.cloud.model.appUser.AppUserLoanInfo;
import com.cloud.model.appUser.AppUserOrderInfo;
import com.cloud.model.check.CheckLogModel;
import com.cloud.model.collection.CollectionRecordVo;
import com.cloud.model.common.*;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.model.product.OrderHistoryDto;
import com.cloud.model.product.constant.ProductConstants;
import com.cloud.model.risk.RiskDeviceInfoModel;
import com.cloud.model.user.UserBankCard;
import com.cloud.model.user.UserContact;
import com.cloud.model.user.UserLoan;
import com.cloud.order.BeanFactory.OrderBeanFactory;
import com.cloud.order.constant.LoanType;
import com.cloud.order.constant.OrderNode;
import com.cloud.order.constant.StockOrderSourceTypeEnum;
import com.cloud.order.constant.StudentStatus;
import com.cloud.order.dao.*;
import com.cloud.order.handle.HandleFinalJudgment;
import com.cloud.order.handle.HandlePayment;
import com.cloud.order.model.*;
import com.cloud.order.payment.facility.PaymentFacility;
import com.cloud.order.service.OrderService;
import com.cloud.oss.autoconfig.utils.AliOssUtil;
import com.cloud.service.feign.backend.BackendClient;
import com.cloud.service.feign.collection.CollectionClient;
import com.cloud.service.feign.user.UserClient;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * OrderServiceImpl class
 *
 * @author zhujingtao
 * @date 2019/2/20
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserClient userClient;
    @Autowired
    private FinanceLoanDao loanDao;

    @Autowired
    private FinanceRepayDao financeRepayDao;

    @Autowired
    private OrderOntherRefuseDao orderOntherRefuseDao;

    @Autowired
    private PaymentFacility paymentFacility;

    @Autowired
    private BasicDao basicDao;

    @Autowired
    private FinanceLoanContractDao financeLoanContractDao;

    @Autowired
    private HandleFinalJudgment handleFinalJudgment;

    @Autowired
    private HandlePayment handlePayment;

    @Autowired
    private OrderBeanFactory orderBeanFactory;

    @Autowired
    private ReportOrderPassDao reportOrderPassDao;

    @Autowired
    private KudosDao kudosDao;

    @Autowired
    private FinanceRepayDao repayDao;

    @Autowired(required = false)
    private CollectionClient collectionClient;

    @Autowired(required = false)
    private AliOssUtil aliOssUtil;

    @Autowired(required = false)
    private StockUserOrderDao stockUserOrderDao;

    @Autowired(required = false)
    private BackendClient backendClient;

    @Autowired
    private ReportNewlyAddedDao reportNewlyAddedDao;
    @Autowired
    private ReportDueDao reportDueDao;

    /**
     * 查询订单
     *
     * @param params
     * @return
     * @author
     */
    @Override
    public Page<OrderModel> findOrder(Map<String, Object> params) {

        int total = orderDao.count(params);
        Integer length = MapUtils.getInteger(params, "length");
        Integer start = MapUtils.getInteger(params, "start");
        List<OrderModel> list = Collections.emptyList();
        if (total > 0) {
            list = orderDao.findAllOrder(start, length);
        }
        return new Page<>(total, list);
    }

    /**
     * 获取 待分配订单数量
     *
     * @return 待分配订单
     * @author zhujingtao
     */
    @Override
    public int getAllocatedOrderCount() {
        return orderDao.getAllocatedOrderCount();
    }

    @Override
    public IndexFirstParam queryIndexFirst() {
        BigDecimal loanAmount = orderDao.getReportOrderDetailsLoanAmount();
        Date now = new Date();
        Date yesterday = DateUtil.getDate(now, -1);
        String nowStr = DateUtil.getStringDate(now, "yyyy-MM-dd");
        String yesterdayStr = DateUtil.getStringDate(yesterday, "yyyy-MM-dd");
        Integer nowUserAdded = userClient.getUserCountByDate(nowStr);
        Integer yesterdayUserAdded = userClient.getUserCountByDate(yesterdayStr);
        Integer nowOrderAdded =
                orderDao.getOrderCountByDate(now);
        Integer yesterdayOrderAdded = orderDao.getOrderCountByDate(yesterday);

        return IndexFirstParam.builder().loanAmount(loanAmount)
                .totalDueAmount(repayDao.queryNotFinanceDueAmount()).yesterdayOrderAdded(yesterdayOrderAdded)
                .yesterdayUserAdded(yesterdayUserAdded)
                .nowUserAdded(nowUserAdded).nowOrderAdded(nowOrderAdded)
                .build();
    }

    /**
     * 获取待领取订单列表
     *
     * @param params 前端传值
     * @return 订单列表
     * @author zhujingtao
     */
    @Override
    public PageInfo<OrderTableModel> queryUnclaimedOrder(Map<String, Object> params) {

        // 获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        // 初始化列表
        List<OrderTableModel> orderTableModelList = orderDao.queryUnclaimedOrder(params);
        PageInfo<OrderTableModel> pageInfo = new PageInfo<>(orderTableModelList);
        // 返回对象信息
        return pageInfo;
    }

    /**
     * 查询所有订单
     *
     * @param params 前端传入参数
     * @return 分页信息
     * @author zhujingtao
     */
    @Override
    public PageInfo<OrderTableModel> queryAllOrderList(Map<String, Object> params) {
        // 获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        // 初始化列表
        List<OrderTableModel> orderTableModelList = orderDao.queryAllOrderList(params);
        PageInfo<OrderTableModel> pageInfo = new PageInfo<>(orderTableModelList);
        // 返回对象信息
        return pageInfo;
    }

    @Override
    public PageInfo<OrderTableModel> queryLendingPoolOrderList(String applyNum, String userPhone, Integer term, BigDecimal loanAmount, String startFinalAuditTime, String endFinalAuditTime, Integer page, Integer pageSize) {
        Map<String, Object> params = new HashMap<>(6);
        PageHelper.startPage(page, pageSize);
        params.put("applyNum", applyNum);
        params.put("userPhone", userPhone);
        params.put("term", term);
        params.put("loanAmount", loanAmount);
        params.put("startFinalAuditTime", startFinalAuditTime);
        params.put("endFinalAuditTime", endFinalAuditTime);
        List<OrderTableModel> orderTableModelList = orderDao.queryLendingPoolOrderList(params);
        PageInfo<OrderTableModel> pageInfo = new PageInfo<>(orderTableModelList);
        return pageInfo;
    }

    /**
     * 获取我的订单列表
     *
     * @param params 前端传值
     * @return 订单列表
     * @author zhujingtao
     */
    @Override
    public PageInfo<OrderTableModel> queryMyOrders(Map<String, Object> params) {
        // 获取当前用户名称
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        params.put("currentUser", name);

        // 获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        // 初始化列表
        List<OrderTableModel> orderTableModelList = orderDao.queryMyOrders(params);
        PageInfo<OrderTableModel> pageInfo = new PageInfo<>(orderTableModelList);

        return pageInfo;
    }

    /**
     * 获取待初审订单 列表
     *
     * @param params 前端传值
     * @return 订单列表
     * @author zhujingtao
     */
    @Override
    public PageInfo<OrderTableModel> queryFirstCheckOrders(Map<String, Object> params) {
        /// 获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        List<OrderTableModel> orderTableModelList = orderDao.queryFirstCheckOrders(params);

        // 返回对象信息
        PageInfo<OrderTableModel> pageInfo = new PageInfo<OrderTableModel>(orderTableModelList);
        return pageInfo;
    }

    /**
     * 获取待终审订单 列表
     *
     * @param params 前端传值
     * @return 订单列表
     * @author zhujingtao
     */
    @Override
    public PageInfo<OrderTableModel> queryEndCheckOrders(Map<String, Object> params) {
        // 获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        List<OrderTableModel> orderTableModelList = orderDao.queryEndCheckOrders(params);
        PageInfo<OrderTableModel> pageInfo = new PageInfo<>(orderTableModelList);
        return pageInfo;
    }

    @Override
    public IndexDueTypeModel queryIndexDueType(DateParam dateParam) {
        //處理 日期 傳入
        dateParam = processDateParam(dateParam);

        List<IndexDueTypeParam> indexDueTypeParamsDay = new ArrayList<>();

        List<IndexDueTypeParam> indexDueTypeParamsWeek = new ArrayList<>();

        List<IndexDueTypeParam> indexDueTypeParamsMonth = new ArrayList<>();
        List<ReportDueModel> reportDueModels = reportDueDao.selectAllByDateParam(dateParam);
        //判定日期
        if (CollectionUtils.isEmpty(reportDueModels)) {
            return IndexDueTypeModel.builder().build();
        }
        List<Date> dateList = new ArrayList<>();
        reportDueModels.stream().forEach(reportDueModel -> {
            if (!dateList.contains(reportDueModel.getReportDate())) {
                dateList.add(reportDueModel.getReportDate());
            }
        });
        reportDueModels = reportDueModels.stream().
                sorted(Comparator.comparing(ReportDueModel::getReportDate))
                .collect(Collectors.toList());
        //日報表的匯總
        for (Date date : dateList) {
            ReportDueModel d1 = null;
            ReportDueModel d3 = null;
            ReportDueModel d7 = null;
            ReportDueModel d15 = null;
            ReportDueModel d30 = null;
            List<ReportDueModel> d1List = reportDueModels.stream()
                    .filter(repreportDueModel ->
                            repreportDueModel.getReportDate().
                                    equals(date) && repreportDueModel.getDueType() == 1).collect(Collectors.toList());
            List<ReportDueModel> d3List = reportDueModels.stream()
                    .filter(repreportDueModel ->
                            repreportDueModel.getReportDate().
                                    equals(date) && repreportDueModel.getDueType() == 3).collect(Collectors.toList());
            List<ReportDueModel> d7List = reportDueModels.stream()
                    .filter(repreportDueModel ->
                            repreportDueModel.getReportDate().
                                    equals(date) && repreportDueModel.getDueType() == 7).collect(Collectors.toList());
            List<ReportDueModel> d15List = reportDueModels.stream()
                    .filter(repreportDueModel ->
                            repreportDueModel.getReportDate().
                                    equals(date) && repreportDueModel.getDueType() == 15).collect(Collectors.toList());
            List<ReportDueModel> d30List = reportDueModels.stream()
                    .filter(repreportDueModel ->
                            repreportDueModel.getReportDate().
                                    equals(date) && repreportDueModel.getDueType() == 30).collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(d1List)) {
                d1 = d1List.get(0);
            }
            if (CollectionUtils.isNotEmpty(d3List)) {
                d3 = d3List.get(0);
            }
            if (CollectionUtils.isNotEmpty(d7List)) {
                d7 = d7List.get(0);
            }
            if (CollectionUtils.isNotEmpty(d15List)) {
                d15 = d15List.get(0);
            }
            if (CollectionUtils.isNotEmpty(d30List)) {
                d30 = d30List.get(0);
            }

            indexDueTypeParamsDay.add(IndexDueTypeParam.builder()
                    .d1DuePre(d1 == null ? 0 : MathUtil.romand(d1.getRepayLoanAmount().doubleValue() * 100 / d1.getLoanAmount().doubleValue(), 2))
                    .d3DuePre(d3 == null ? 0 : MathUtil.romand(d3.getRepayLoanAmount().doubleValue() * 100 / d3.getLoanAmount().doubleValue(), 2))
                    .d7DuePre(d7 == null ? 0 : MathUtil.romand(d7.getRepayLoanAmount().doubleValue() * 100 / d7.getLoanAmount().doubleValue(), 2))
                    .d15DuePre(d15 == null ? 0 : MathUtil.romand(d15.getRepayLoanAmount().doubleValue() * 100 / d15.getLoanAmount().doubleValue(), 2))
                    .d30DuePre(d30 == null ? 0 : MathUtil.romand(d30.getRepayLoanAmount().doubleValue() * 100 / d30.getLoanAmount().doubleValue(), 2))
                    .day(date)
                    .dayStr(DateUtil.getStringDate(date, "yyyy-MM-dd"))
                    .build());
        }
        //統計周信息
        //周统计
        if (DateUtil.getDayCount(dateParam.getStartDate(), dateParam.getEndDate()) >= 7) {
            Date endDate = dateParam.getEndDate();
            Date startDate = dateParam.getStartDate();
            Date  temSunDay= DateUtil.getSunday(startDate,0);
            Date  temMonday = DateUtil.getMonday(startDate,0);
            int i =0;
            while (temSunDay.before(endDate)||temSunDay.equals(endDate)) {
                //獲取當時 時間
                temSunDay=i==0?temSunDay:DateUtil.getSunday(startDate,-i);
                temMonday=i==0?temMonday:DateUtil.getMonday(startDate,-i);
                i--;
                String dayStr = DateUtil.getStringDate(temMonday, "yyyy-MM-dd");
                String toDayStr = DateUtil.getStringDate(temSunDay, "yyyy-MM-dd");
                //无奈的java  8不能传
                Date[] temDate = new Date[]{temSunDay};
                if (temSunDay.after(new Date())) {
                    break;
                }
                List<ReportDueModel> reportDueModelW = reportDueModels.stream().filter(reportDueModel ->
                        reportDueModel.getReportDate().before(temDate[0])).collect(Collectors.toList());
                List<ReportDueModel> d1 = reportDueModelW.stream().filter(repreportDueModel -> repreportDueModel.getDueType() == 1).collect(Collectors.toList());
                List<ReportDueModel> d3 = reportDueModelW.stream().filter(repreportDueModel -> repreportDueModel.getDueType() == 3).collect(Collectors.toList());
                List<ReportDueModel> d7 = reportDueModelW.stream().filter(repreportDueModel -> repreportDueModel.getDueType() == 7).collect(Collectors.toList());
                List<ReportDueModel> d15 = reportDueModelW.stream().filter(repreportDueModel -> repreportDueModel.getDueType() == 15).collect(Collectors.toList());
                List<ReportDueModel> d30 = reportDueModelW.stream().filter(repreportDueModel -> repreportDueModel.getDueType() == 30).collect(Collectors.toList());

                indexDueTypeParamsWeek.add(IndexDueTypeParam.builder()
                        .d1DuePre(CollectionUtils.isEmpty(d1) ? 0 : MathUtil.romand(d1.stream().map(ReportDueModel::getRepayLoanAmount).reduce(BigDecimal::add).get().doubleValue() * 100 / d1.stream().map(ReportDueModel::getLoanAmount).reduce(BigDecimal::add).get().doubleValue(), 2))
                        .d3DuePre(CollectionUtils.isEmpty(d3) ? 0 : MathUtil.romand(d3.stream().map(ReportDueModel::getRepayLoanAmount).reduce(BigDecimal::add).get().doubleValue() * 100 / d3.stream().map(ReportDueModel::getLoanAmount).reduce(BigDecimal::add).get().doubleValue(), 2))
                        .d7DuePre(CollectionUtils.isEmpty(d7) ? 0 : MathUtil.romand(d7.stream().map(ReportDueModel::getRepayLoanAmount).reduce(BigDecimal::add).get().doubleValue() * 100 / d7.stream().map(ReportDueModel::getLoanAmount).reduce(BigDecimal::add).get().doubleValue(), 2))
                        .d15DuePre(CollectionUtils.isEmpty(d15) ? 0 : MathUtil.romand(d15.stream().map(ReportDueModel::getRepayLoanAmount).reduce(BigDecimal::add).get().doubleValue() * 100 / d15.stream().map(ReportDueModel::getLoanAmount).reduce(BigDecimal::add).get().doubleValue(), 2))
                        .d30DuePre(CollectionUtils.isEmpty(d30) ? 0 : MathUtil.romand(d30.stream().map(ReportDueModel::getRepayLoanAmount).reduce(BigDecimal::add).get().doubleValue() * 100 / d30.stream().map(ReportDueModel::getLoanAmount).reduce(BigDecimal::add).get().doubleValue(), 2))
                        .day(startDate)
                        .dayStr(dayStr + "-》" + toDayStr)
                        .build());
            }
        }

        //月统计
        Date endDate = dateParam.getEndDate();
        Date startDate = dateParam.getStartDate();
        while (startDate.before(endDate)) {
            String dayStr = DateUtil.getStringDate(startDate, "yyyy-MM-dd");
            //获取一周后数据
            startDate = DateUtil.getMonth(startDate, 1);
            if (DateUtil.getDate(startDate, -1).after(endDate)) {
                break;
            }
            //无奈的java  8不能传
            Date[] temDate = new Date[]{startDate};
            List<ReportDueModel> reportDueModelM = reportDueModels.stream().filter(reportOrderPassModel ->
                    reportOrderPassModel.getReportDate().before(temDate[0])).collect(Collectors.toList());
            List<ReportDueModel> d1 = reportDueModelM.stream().filter(repreportDueModel -> repreportDueModel.getDueType() == 1).collect(Collectors.toList());
            List<ReportDueModel> d3 = reportDueModelM.stream().filter(repreportDueModel -> repreportDueModel.getDueType() == 3).collect(Collectors.toList());
            List<ReportDueModel> d7 = reportDueModelM.stream().filter(repreportDueModel -> repreportDueModel.getDueType() == 7).collect(Collectors.toList());
            List<ReportDueModel> d15 = reportDueModelM.stream().filter(repreportDueModel -> repreportDueModel.getDueType() == 15).collect(Collectors.toList());
            List<ReportDueModel> d30 = reportDueModelM.stream().filter(repreportDueModel -> repreportDueModel.getDueType() == 30).collect(Collectors.toList());

            indexDueTypeParamsMonth.add(IndexDueTypeParam.builder()
                    .d1DuePre(CollectionUtils.isEmpty(d1) ? 0 : MathUtil.romand(d1.stream().map(ReportDueModel::getRepayLoanAmount).reduce(BigDecimal::add).get().doubleValue() * 100 / d1.stream().map(ReportDueModel::getLoanAmount).reduce(BigDecimal::add).get().doubleValue(), 2))
                    .d3DuePre(CollectionUtils.isEmpty(d3) ? 0 : MathUtil.romand(d3.stream().map(ReportDueModel::getRepayLoanAmount).reduce(BigDecimal::add).get().doubleValue() * 100 / d3.stream().map(ReportDueModel::getLoanAmount).reduce(BigDecimal::add).get().doubleValue(), 2))
                    .d7DuePre(CollectionUtils.isEmpty(d7) ? 0 : MathUtil.romand(d7.stream().map(ReportDueModel::getRepayLoanAmount).reduce(BigDecimal::add).get().doubleValue() * 100 / d7.stream().map(ReportDueModel::getLoanAmount).reduce(BigDecimal::add).get().doubleValue(), 2))
                    .d15DuePre(CollectionUtils.isEmpty(d15) ? 0 : MathUtil.romand(d15.stream().map(ReportDueModel::getRepayLoanAmount).reduce(BigDecimal::add).get().doubleValue() * 100 / d15.stream().map(ReportDueModel::getLoanAmount).reduce(BigDecimal::add).get().doubleValue(), 2))
                    .d30DuePre(CollectionUtils.isEmpty(d30) ? 0 : MathUtil.romand(d30.stream().map(ReportDueModel::getRepayLoanAmount).reduce(BigDecimal::add).get().doubleValue() * 100 / d30.stream().map(ReportDueModel::getLoanAmount).reduce(BigDecimal::add).get().doubleValue(), 2))
                    .day(startDate)
                    .dayStr(dayStr)
                    .build());
        }


        return IndexDueTypeModel.builder().indexDueTypeParamsDay(indexDueTypeParamsDay).indexDueTypeParamsWeek(indexDueTypeParamsWeek).indexDueTypeParamsMonth(indexDueTypeParamsMonth).build();
    }

    @Override
    public IndexDueIngModel queryIndexDueIng(DateParam dateParam) {
        //處理 日期 傳入
        dateParam = processDateParam(dateParam);
        List<IndexDueIngParam> dueIngParamsDay = new ArrayList<>();
        List<IndexDueIngParam> dueIngParamsWeek = new ArrayList<>();
        List<IndexDueIngParam> dueIngParamsMonth = new ArrayList<>();
        List<ReportDueModel> reportDueModels = reportDueDao.selectAllByDateParam(dateParam);
        //判定日期
        if (CollectionUtils.isEmpty(reportDueModels)) {
            return IndexDueIngModel.builder().build();
        }

        reportDueModels = reportDueModels.stream().
                sorted(Comparator.comparing(ReportDueModel::getReportDate))
                .collect(Collectors.toList());
        reportDueModels = reportDueModels.stream()
                .filter(reportDueModel -> reportDueModel.getDueType() == 0).collect(Collectors.toList());
        reportDueModels.stream().forEach(reportDueModel -> {
            dueIngParamsDay.add(IndexDueIngParam.builder()
                    .dueInPre(MathUtil.romand(reportDueModel.getRepayLoanAmount().doubleValue() * 100 / reportDueModel.getLoanAmount().doubleValue(), 2))
                    .day(reportDueModel.getReportDate())
                    .dayStr(DateUtil.getStringDate(reportDueModel.getReportDate(), "yyyy-MM-dd"))
                    .build());
        });
        //統計周信息
        //周统计
        if (DateUtil.getDayCount(dateParam.getStartDate(), dateParam.getEndDate()) >= 7) {
            Date endDate = dateParam.getEndDate();
            Date startDate = dateParam.getStartDate();
            Date  temSunDay= DateUtil.getSunday(startDate,0);
            Date  temMonday = DateUtil.getMonday(startDate,0);
            int i =0;
            while (temSunDay.before(endDate)||temSunDay.equals(endDate)) {
                //獲取當時 時間
                temSunDay=i==0?temSunDay:DateUtil.getSunday(startDate,-i);
                temMonday=i==0?temMonday:DateUtil.getMonday(startDate,-i);
                i--;
                String dayStr = DateUtil.getStringDate(temMonday, "yyyy-MM-dd");
                String toDayStr = DateUtil.getStringDate(temSunDay, "yyyy-MM-dd");
                //无奈的java  8不能传
                Date[] temDate = new Date[]{temSunDay};
                if (temSunDay.after(new Date())) {
                    break;
                }
                List<ReportDueModel> reportDueModelW = reportDueModels.stream().filter(reportDueModel ->
                        reportDueModel.getReportDate().before(temDate[0])).collect(Collectors.toList());
                dueIngParamsWeek.add(IndexDueIngParam.builder()
                        .dueInPre(MathUtil.romand(reportDueModelW.stream()
                                .map(ReportDueModel::getRepayLoanAmount).reduce(BigDecimal::add).get().doubleValue() * 100 /
                                reportDueModelW.stream()
                                        .map(ReportDueModel::getLoanAmount).reduce(BigDecimal::add).get().doubleValue(), 2))
                        .day(startDate)
                        .dayStr(dayStr + "-》" + toDayStr)
                        .build());
            }
        }
        //月统计

        Date endDate = dateParam.getEndDate();
        Date startDate = dateParam.getStartDate();
        while (startDate.before(endDate)) {
            String dayStr = DateUtil.getStringDate(startDate, "yyyy-MM-dd");
            //获取一周后数据
            startDate = DateUtil.getMonth(startDate, 1);
            if (DateUtil.getDate(startDate, -1).after(endDate)) {
                break;
            }
            //无奈的java  8不能传
            Date[] temDate = new Date[]{startDate};
            List<ReportDueModel> reportDueModelM = reportDueModels.stream().filter(reportOrderPassModel ->
                    reportOrderPassModel.getReportDate().before(temDate[0])).collect(Collectors.toList());
            dueIngParamsMonth.add(IndexDueIngParam.builder()
                    .dueInPre(MathUtil.romand(reportDueModelM.stream()
                            .map(ReportDueModel::getRepayLoanAmount).reduce(BigDecimal::add).get().doubleValue() * 100 /
                            reportDueModelM.stream()
                                    .map(ReportDueModel::getLoanAmount).reduce(BigDecimal::add).get().doubleValue(), 2))
                    .day(startDate)
                    .dayStr(dayStr)
                    .build());
        }


        return IndexDueIngModel.builder()
                .dueIngParamsDay(dueIngParamsDay)
                .dueIngParamsMonth(dueIngParamsMonth)
                .dueIngParamsWeek(dueIngParamsWeek).build();
    }

    @Override
    public IndexCustomerNewedModel queryIndexCustomerNewed(DateParam dateParam) {
        //處理 日期 傳入
        dateParam = processDateParam(dateParam);
        IndexCustomerMoth indexCustomerMoth = IndexCustomerMoth.builder().build();
        List<IndexCustomerDayParam> indexCustomerDayParamList = new ArrayList<>();
        List<IndexCustomerDayParam> indexCustomerWeekParamList = new ArrayList<>();
        List<IndexCustomerDayParam> indexCustomerMonthParamList = new ArrayList<>();

        List<ReportNewlyAddedModel> reportNewlyAddedModels = reportNewlyAddedDao.selectAllByDate(dateParam);
        List<ReportOrderPassModel> reportOrderPassModels = reportOrderPassDao.selectAllByDate(dateParam);
        if (CollectionUtils.isEmpty(reportNewlyAddedModels)) {
            return IndexCustomerNewedModel.builder().build();
        }
        //統計  日信息
        reportNewlyAddedModels = reportNewlyAddedModels.stream().
                sorted(Comparator.comparing(ReportNewlyAddedModel::getReportDate))
                .collect(Collectors.toList());
        reportNewlyAddedModels.stream().forEach(reportNewlyAddedModel -> {
            indexCustomerDayParamList.add(IndexCustomerDayParam.builder()
                    .newUserAdded(reportNewlyAddedModel.getCustomerNum())
                    .day(reportNewlyAddedModel.getReportDate())
                    .dayStr(DateUtil.getStringDate(reportNewlyAddedModel.getReportDate(), "yyyy-MM-dd"))
                    .build());
        });
        for (IndexCustomerDayParam indexCustomerDayParam : indexCustomerDayParamList) {
            List<ReportOrderPassModel> reportOrderPassModelsDay = reportOrderPassModels.stream().filter(reportOrderPassModel -> reportOrderPassModel.getReportDate().equals(indexCustomerDayParam.getDay())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(reportOrderPassModelsDay)) {
                indexCustomerDayParam.setOrderApplyNum(reportOrderPassModelsDay.get(0).getApplyNum());
                indexCustomerDayParam.setOrderPassNum(reportOrderPassModelsDay.get(0).getHumanPassNum());
            }
        }
        //統計周信息
        //周统计
        if (DateUtil.getDayCount(dateParam.getStartDate(), dateParam.getEndDate()) >= 7) {
            Date endDate = dateParam.getEndDate();
            Date startDate = dateParam.getStartDate();
            Date  temSunDay= DateUtil.getSunday(startDate,0);
            Date  temMonday = DateUtil.getMonday(startDate,0);
            int i =0;
            while (temSunDay.before(endDate)||temSunDay.equals(endDate)) {
                //獲取當時 時間
                temSunDay=i==0?temSunDay:DateUtil.getSunday(startDate,-i);
                temMonday=i==0?temMonday:DateUtil.getMonday(startDate,-i);
                i--;
                System.out.println(i);
                String dayStr = DateUtil.getStringDate(temMonday, "yyyy-MM-dd");
                String toDayStr = DateUtil.getStringDate(temSunDay, "yyyy-MM-dd");
                //无奈的java  8不能传
                Date[] temDate = new Date[]{temSunDay};
                if (temSunDay.after(new Date())) {
                    break;
                }
                List<ReportOrderPassModel> reportOrderPassModelsW = reportOrderPassModels.stream().filter(reportOrderPassModel ->
                        reportOrderPassModel.getReportDate().before(temDate[0])).collect(Collectors.toList());
                List<ReportNewlyAddedModel> reportNewlyAddedModelsW = reportNewlyAddedModels.stream().filter(reportOrderPassModel ->
                        reportOrderPassModel.getReportDate().before(temDate[0])).collect(Collectors.toList());
                Integer orderApplyNum = 0;
                Integer orderPassNum = 0;
                if (CollectionUtils.isNotEmpty(reportOrderPassModelsW)) {
                    orderApplyNum = reportOrderPassModelsW.stream().map(ReportOrderPassModel::getApplyNum).reduce(Integer::sum).get();
                    orderPassNum = reportOrderPassModelsW.stream().map(ReportOrderPassModel::getHumanPassNum).reduce(Integer::sum).get();
                }


                indexCustomerWeekParamList.add(IndexCustomerDayParam.builder()
                        .newUserAdded( CollectionUtils.isEmpty(reportNewlyAddedModelsW)?0:
                                reportNewlyAddedModelsW.stream().map(ReportNewlyAddedModel::getCustomerNum).reduce(Integer::sum).get())
                        .orderApplyNum(orderApplyNum)
                        .orderPassNum(orderPassNum)
                        .day(temMonday)
                        .dayStr(dayStr + "-》" + toDayStr)
                        .build());
            }
        }
        //月统计

        Date endDate = dateParam.getEndDate();
        Date startDate = dateParam.getStartDate();
        while (startDate.before(endDate)) {
            String dayStr = DateUtil.getStringDate(startDate, "yyyy-MM-dd");
            //获取一周后数据
            startDate = DateUtil.getMonth(startDate, 1);
            if (DateUtil.getDate(startDate, -1).after(endDate)) {
                break;
            }
            // 制定选中月
            if (DateUtil.getStringDate(dateParam.getStartDate(), "yyyyMM").equals(
                    DateUtil.getStringDate(dateParam.getEndDate(), "yyyyMM"))) {
                String fisrtDayOfMonth = DateUtil.getFisrtDayOfMonth(Integer.valueOf(DateUtil.getStringDate(dateParam.getStartDate(), "yyyy"))
                        , Integer.valueOf(DateUtil.getStringDate(dateParam.getStartDate(), "MM")));
                String lastDayOfMonth = DateUtil.getFisrtDayOfMonth(Integer.valueOf(DateUtil.getStringDate(dateParam.getStartDate(), "yyyy"))
                        , Integer.valueOf(DateUtil.getStringDate(dateParam.getStartDate(), "MM")));
                Integer customerNum = reportNewlyAddedModels.stream().map(ReportNewlyAddedModel::getCustomerNum).reduce(Integer::sum).get();
                Integer newlyCustomerApplyNum = reportNewlyAddedModels.stream().map(ReportNewlyAddedModel::getNewlyCustomerApplyNum).reduce(Integer::sum).get();
                Integer oldCustomerApplyNum = reportNewlyAddedModels.stream().map(ReportNewlyAddedModel::getOldCustomerApplyNum).reduce(Integer::sum).get();
                indexCustomerMoth.setNewUserAddedNum(customerNum);
                indexCustomerMoth.setNewOrderAddedNum(newlyCustomerApplyNum);
                indexCustomerMoth.setOldAddedNum(oldCustomerApplyNum);
                //第一天 和最後一天相等
                if (fisrtDayOfMonth.equals(DateUtil.getStringDate(dateParam.getStartDate(), "yyyy-MM-dd"))
                        && lastDayOfMonth.equals(DateUtil.getStringDate(dateParam.getEndDate(), "yyyy-MM-dd"))) {
                    List<ReportNewlyAddedModel> reportNewlyAddedLast = reportNewlyAddedDao.selectAllByDate(dateParam);
                    Integer customerNumLast = reportNewlyAddedLast.stream().map(ReportNewlyAddedModel::getCustomerNum).reduce(Integer::sum).get();
                    Integer newlyCustomerApplyNumLast = reportNewlyAddedLast.stream().map(ReportNewlyAddedModel::getNewlyCustomerApplyNum).reduce(Integer::sum).get();
                    Integer oldCustomerApplyNumLast = reportNewlyAddedLast.stream().map(ReportNewlyAddedModel::getOldCustomerApplyNum).reduce(Integer::sum).get();
                    indexCustomerMoth.setChainNewUserAddedPre((customerNumLast.doubleValue() - indexCustomerMoth.getNewUserAddedNum().doubleValue()) * 100 / indexCustomerMoth.getNewUserAddedNum().doubleValue());
                    indexCustomerMoth.setChainNewOrderAddedPre((newlyCustomerApplyNumLast.doubleValue() - indexCustomerMoth.getNewOrderAddedNum().doubleValue()) * 100 / indexCustomerMoth.getNewOrderAddedNum().doubleValue());
                    indexCustomerMoth.setChainOldUserAddedPre((oldCustomerApplyNumLast.doubleValue() - indexCustomerMoth.getOldAddedNum().doubleValue()) * 100 / indexCustomerMoth.getOldAddedNum().doubleValue());
                }
            }
            //无奈的java  8不能传
            Date[] temDate = new Date[]{startDate};
            List<ReportOrderPassModel> reportOrderPassModelsM = reportOrderPassModels.stream().filter(reportOrderPassModel ->
                    reportOrderPassModel.getReportDate().before(temDate[0])).collect(Collectors.toList());
            List<ReportNewlyAddedModel> reportNewlyAddedModelsM = reportNewlyAddedModels.stream().filter(reportOrderPassModel ->
                    reportOrderPassModel.getReportDate().before(temDate[0])).collect(Collectors.toList());
            indexCustomerMonthParamList.add(IndexCustomerDayParam.builder()
                    .newUserAdded(reportNewlyAddedModelsM.stream().map(ReportNewlyAddedModel::getCustomerNum).reduce(Integer::sum).get())
                    .orderApplyNum(reportOrderPassModelsM.stream().map(ReportOrderPassModel::getApplyNum).reduce(Integer::sum).get())
                    .orderPassNum(reportOrderPassModels.stream().map(ReportOrderPassModel::getHumanPassNum).reduce(Integer::sum).get())
                    .day(DateUtil.getMonth(startDate, -1))
                    .dayStr(dayStr)
                    .build());
        }


        return IndexCustomerNewedModel.builder().indexCustomerMoth(indexCustomerMoth).indexCustomerDayParamList(indexCustomerDayParamList).indexCustomerWeekParamList(indexCustomerWeekParamList).indexCustomerMonthParamList(indexCustomerMonthParamList).build();
    }

    @Override
    public IndexOrderPassModel queryIndexOrderPass(DateParam dateParam) {
        //處理 日期 傳入
        dateParam = processDateParam(dateParam);
        IndexOrderPassMoth indexOrderPassMoth = IndexOrderPassMoth.builder().build();
        List<IndexOrderPassDayParam> indexOrderPassDayParams = new ArrayList<>();
        List<IndexOrderPassDayParam> indexOrderPassWeekParams = new ArrayList<>();
        List<IndexOrderPassDayParam> indexOrderPassMonthParams = new ArrayList<>();
        List<ReportOrderPassModel> reportOrderPassModels = reportOrderPassDao.selectAllByDate(dateParam);
        if (CollectionUtils.isEmpty(reportOrderPassModels)) {
            return IndexOrderPassModel.builder().build();
        }
        //转换时间
        reportOrderPassModels = reportOrderPassModels.stream().sorted(Comparator.comparing(ReportOrderPassModel::getReportDate)).collect(Collectors.toList());

        //天数据 统计
        reportOrderPassModels.stream().forEach(reportOrderPassModel -> {
            indexOrderPassDayParams.add(IndexOrderPassDayParam.builder().totalPassPre(
                    MathUtil.romand(reportOrderPassModel.getApplyNum()!=null&& reportOrderPassModel.getApplyNum()!=0?reportOrderPassModel.getHumanPassNum().doubleValue() * 100 / reportOrderPassModel.getApplyNum().doubleValue():0, 2)
            )
                    .mechinePassPre(reportOrderPassModel.getApplyNum()!=null&&reportOrderPassModel.getApplyNum()!=0?
                            MathUtil.romand(reportOrderPassModel.getMechinePassNum().doubleValue() * 100 / reportOrderPassModel.getApplyNum().doubleValue(), 2):0)
                    .humanPassPre(reportOrderPassModel.getMechinePassNum()!=null&&reportOrderPassModel.getMechinePassNum()!=0?
                            MathUtil.romand(reportOrderPassModel.getHumanPassNum() * 100 / reportOrderPassModel.getMechinePassNum().doubleValue(), 2):0)
                    .platformPassPre(reportOrderPassModel.getPlatformApplyNum()!=null&&reportOrderPassModel.getPlatformApplyNum()!=0?MathUtil.romand(reportOrderPassModel.getPlatformPassNum().doubleValue() * 100 / reportOrderPassModel.getPlatformApplyNum().doubleValue(), 2):0)
                    .day(reportOrderPassModel.getReportDate())
                    .dayStr(DateUtil.getStringDate(reportOrderPassModel.getReportDate(), "yyyy-MM-dd")).build());
        });
        //周统计
        if (DateUtil.getDayCount(dateParam.getStartDate(), dateParam.getEndDate()) >= 7) {
            Date endDate = dateParam.getEndDate();
            Date startDate = dateParam.getStartDate();
            Date  temSunDay= DateUtil.getSunday(startDate,0);
            Date  temMonday = DateUtil.getMonday(startDate,0);
            int i =0;
            while (temSunDay.before(endDate)||temSunDay.equals(endDate)) {
                //獲取當時 時間
                temSunDay=i==0?temSunDay:DateUtil.getSunday(startDate,-i);
                temMonday=i==0?temMonday:DateUtil.getMonday(startDate,-i);
                i--;
                String dayStr = DateUtil.getStringDate(temMonday, "yyyy-MM-dd");
                String toDayStr = DateUtil.getStringDate(temSunDay, "yyyy-MM-dd");
                //无奈的java  8不能传
                Date[] temDate = new Date[]{temSunDay};
                if (temSunDay.after(new Date())) {
                    break;
                }

                List<ReportOrderPassModel> reportOrderPassModelsW = reportOrderPassModels.stream().filter(reportOrderPassModel ->
                        reportOrderPassModel.getReportDate().before(temDate[0])).collect(Collectors.toList());

                Double applyNum = CollectionUtils.isNotEmpty(reportOrderPassModelsW)? reportOrderPassModelsW.stream().map(ReportOrderPassModel::getApplyNum).reduce(Integer::sum).get().doubleValue():0;
                Double mechinePassNum =CollectionUtils.isNotEmpty(reportOrderPassModelsW)?  reportOrderPassModelsW.stream().map(ReportOrderPassModel::getMechinePassNum).reduce(Integer::sum).get().doubleValue():0;
                Double humanPassNum = CollectionUtils.isNotEmpty(reportOrderPassModelsW)? reportOrderPassModelsW.stream().map(ReportOrderPassModel::getHumanPassNum).reduce(Integer::sum).get().doubleValue():0;
                Double platformApplyNum = CollectionUtils.isNotEmpty(reportOrderPassModelsW)? reportOrderPassModelsW.stream().map(ReportOrderPassModel::getPlatformApplyNum).reduce(Integer::sum).get().doubleValue():0;
                Double platformPassNum =CollectionUtils.isNotEmpty(reportOrderPassModelsW)?  reportOrderPassModelsW.stream().map(ReportOrderPassModel::getPlatformPassNum).reduce(Integer::sum).get().doubleValue():0;
                indexOrderPassWeekParams.add(IndexOrderPassDayParam.builder().humanPassPre(mechinePassNum!=0?humanPassNum * 100 / mechinePassNum:0)
                        .mechinePassPre(applyNum!=0?MathUtil.romand(mechinePassNum * 100 / applyNum, 2):0)
                        .platformPassPre(platformApplyNum!=0?MathUtil.romand(platformPassNum * 100 / platformApplyNum, 2):0)
                        .totalPassPre(applyNum!=0?MathUtil.romand(humanPassNum * 100 / applyNum, 2):0).day(startDate)
                        .dayStr(dayStr + "-》" + toDayStr)
                        .build()
                );
            }
        }
        //月统计
        Date endDate = dateParam.getEndDate();
        Date startDate = dateParam.getStartDate();
        while (startDate.before(endDate)) {
            String dayStr = DateUtil.getStringDate(startDate, "yyyy-MM-dd");
            //获取一周后数据
            startDate = DateUtil.getMonth(startDate, 1);
            if (DateUtil.getDate(startDate, -1).after(endDate)) {
                break;
            }
            // 制定选中月
            if (DateUtil.getStringDate(dateParam.getStartDate(), "yyyyMM").equals(
                    DateUtil.getStringDate(dateParam.getEndDate(), "yyyyMM"))) {
                String fisrtDayOfMonth = DateUtil.getFisrtDayOfMonth(Integer.valueOf(DateUtil.getStringDate(dateParam.getStartDate(), "yyyy"))
                        , Integer.valueOf(DateUtil.getStringDate(dateParam.getStartDate(), "MM")));
                String lastDayOfMonth = DateUtil.getFisrtDayOfMonth(Integer.valueOf(DateUtil.getStringDate(dateParam.getStartDate(), "yyyy"))
                        , Integer.valueOf(DateUtil.getStringDate(dateParam.getStartDate(), "MM")));

                Double applyNum=.0;
                Double mechinePassNum=.0;
                Double humanPassNum=.0;

                if (CollectionUtils.isNotEmpty(reportOrderPassModels)){
                    applyNum = reportOrderPassModels.stream().map(ReportOrderPassModel::getApplyNum).reduce(Integer::sum).get().doubleValue();
                     mechinePassNum = reportOrderPassModels.stream().map(ReportOrderPassModel::getMechinePassNum).reduce(Integer::sum).get().doubleValue();
                      humanPassNum = reportOrderPassModels.stream().map(ReportOrderPassModel::getHumanPassNum).reduce(Integer::sum).get().doubleValue();
                }

                indexOrderPassMoth.setNowHumanPassPre(mechinePassNum!=0?MathUtil.romand(humanPassNum * 100 / mechinePassNum, 2):0);
                indexOrderPassMoth.setNowMachinePassPre(applyNum!=0?MathUtil.romand(mechinePassNum * 100 / applyNum, 2):0);
                indexOrderPassMoth.setNowTotalPassPre(applyNum!=0?MathUtil.romand(humanPassNum * 100 / applyNum, 2):0);
                //第一天 和最後一天相等
                if (fisrtDayOfMonth.equals(DateUtil.getStringDate(dateParam.getStartDate(), "yyyy-MM-dd"))
                        && lastDayOfMonth.equals(DateUtil.getStringDate(dateParam.getEndDate(), "yyyy-MM-dd"))) {
                    List<ReportOrderPassModel> lastOrderPassModels = reportOrderPassDao.selectAllByDate(dateParam);
                    Double applyNumLast = lastOrderPassModels.stream().map(ReportOrderPassModel::getApplyNum).reduce(Integer::sum).get().doubleValue();
                    Double mechinePassNumLast = lastOrderPassModels.stream().map(ReportOrderPassModel::getMechinePassNum).reduce(Integer::sum).get().doubleValue();
                    Double humanPassNumLast = lastOrderPassModels.stream().map(ReportOrderPassModel::getHumanPassNum).reduce(Integer::sum).get().doubleValue();
                    indexOrderPassMoth.setChainHumanPassPre(MathUtil.romand((humanPassNumLast * 100 / mechinePassNumLast) - indexOrderPassMoth.getNowHumanPassPre(), 2));
                    indexOrderPassMoth.setChainMachinePassPre(MathUtil.romand((mechinePassNumLast * 100 / applyNumLast) - indexOrderPassMoth.getNowMachinePassPre(), 2));
                    indexOrderPassMoth.setChainTotalPassPre(MathUtil.romand((humanPassNumLast * 100 / applyNumLast) - indexOrderPassMoth.getNowTotalPassPre(), 2));
                }
            }
            //无奈的java  8不能传
            Date[] temDate = new Date[]{startDate};
            List<ReportOrderPassModel> reportOrderPassModelsM = reportOrderPassModels.stream().filter(reportOrderPassModel ->
                    reportOrderPassModel.getReportDate().before(temDate[0])).collect(Collectors.toList());

            Double applyNum = reportOrderPassModelsM.stream().map(ReportOrderPassModel::getApplyNum).reduce(Integer::sum).get().doubleValue();
            Double mechinePassNum = reportOrderPassModelsM.stream().map(ReportOrderPassModel::getMechinePassNum).reduce(Integer::sum).get().doubleValue();
            Double humanPassNum = reportOrderPassModelsM.stream().map(ReportOrderPassModel::getHumanPassNum).reduce(Integer::sum).get().doubleValue();
            Double platformApplyNum = reportOrderPassModelsM.stream().map(ReportOrderPassModel::getPlatformApplyNum).reduce(Integer::sum).get().doubleValue();
            Double platformPassNum = reportOrderPassModelsM.stream().map(ReportOrderPassModel::getPlatformPassNum).reduce(Integer::sum).get().doubleValue();
            indexOrderPassMonthParams.add(IndexOrderPassDayParam.builder().humanPassPre(MathUtil.romand(humanPassNum * 100 / mechinePassNum, 2))
                    .mechinePassPre(MathUtil.romand(mechinePassNum * 100 / applyNum, 2))
                    .platformPassPre(MathUtil.romand(platformPassNum * 100 / platformApplyNum, 2))
                    .totalPassPre(MathUtil.romand(humanPassNum * 100 / applyNum, 2)).day(startDate)
                    .dayStr(dayStr)
                    .build()
            );
        }


        return IndexOrderPassModel.builder().IndexOrderPassDayParams(indexOrderPassDayParams).indexOrderPassMoth(indexOrderPassMoth)
                .indexOrderPassWeekParams(indexOrderPassWeekParams)
                .indexOrderPassMonthParams(indexOrderPassMonthParams)
                .build();
    }

    /**
     * 领取任务
     *
     * @param params 前端传入参数
     * @author baijianye
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult takeOrder(Map<String, Object> params) {
        if (params.get("orderIds") == null || ((String) params.get("orderIds")).equals("")) {
            return JsonResult.errorException("没有选中领取的订单，请领取订单");
        }
        String[] orderIds = ((String) params.get("orderIds")).split(",");
        String auditorName = (String) params.get("auditorName");
        String type = (String) params.get("type");
        int auditorId = orderDao.findIdByName(auditorName);
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < orderIds.length; i++) {
            Map<String, Object> map = new HashMap<>(16);

            //判定是否被领取
            if (orderDao.findAssignCountByOrderId(orderIds[i]) > 0) {
                continue;
            }
            map.put("orderId", orderIds[i]);
            map.put("auditorName", auditorName);
            map.put("dispatcherName", auditorName);
            map.put("auditorId", auditorId);
            map.put("managerId", auditorId);
            map.put("type", type);
            list.add(map);
        }
        if (list.size() > 0) {
            orderDao.insertOrderAssignLogUseList(list);
            orderDao.updateUserOrderUseList(list);
            return JsonResult.ok();
        } else {
            return JsonResult.errorMsg("选择订单已被领取!");
        }

    }

    /**
     * 分配任务
     *
     * @param params
     * @author baijieye
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonResult assignOrder(Map<String, Object> params) {
        if (params.get("orderIds") == null || ((String) params.get("orderIds")).equals("")) {
            return JsonResult.errorException("没有选中订单，请选择需要分配的订单");
        }
        String[] orderIds = ((String) params.get("orderIds")).split(",");
        if (params.get("auditorId") == null || ((String) params.get("auditorId")).equals("")) {
            return JsonResult.errorException("没有选择审核员，请选择审核员");
        }

        Integer auditorId = MapUtils.getInteger(params, "auditorId");
        String dispatcherName = (String) params.get("dispatcherName");
        int managerId = orderDao.findIdByName(dispatcherName);
        String type = (String) params.get("type");
        String auditorName = orderDao.findNameById(auditorId);
        List<Map<String, Object>> list = new ArrayList<>();

        for (int i = 0; i < orderIds.length; i++) {
            Map<String, Object> map = new HashMap<>(16);
            map.put("orderId", orderIds[i]);
            map.put("auditorName", auditorName);
            map.put("dispatcherName", dispatcherName);
            map.put("auditorId", auditorId);
            map.put("managerId", managerId);
            map.put("type", type);
            list.add(map);
        }
        if (list.size() > 0) {
            orderDao.insertOrderAssignLogUseList(list);
            orderDao.updateUserOrderUseList(list);
            return JsonResult.ok();
        } else {
            return JsonResult.errorMsg("数据列表为空!");
        }
    }


    /**
     * 获取审批人员
     *
     * @return
     * @author baijieye
     */
    @Override
    public JsonResult getAuditor(int type) {
        if (type != 1 && type != 2 && type != 3) {
            throw new IllegalArgumentException("参数错误");
        }

        List<Map<String, Object>> data = orderDao.getAuditor(type);
        return JsonResult.ok(data);
    }

    /**
     * 初审通过
     *
     * @param params
     * @author baijieye
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void firstJudgment(Map<String, Object> params) throws Exception {

        String orderId = String.valueOf(params.get("orderId"));

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单id:{} 查询是否可以进行初审通过操作", orderId);
        int checkStatus = orderDao.findCheckStatus(orderId);
        if (checkStatus != CheckStatus.WAITTINGFIRSTJUDGE.toNum()) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单id:{} 不是待初审状态无法进行初审通过操作", orderId);
            throw new Exception("不是待初审状态无法进行初审通过操作");
        }
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单id:{} 可以进行初审通过操作", orderId);


        // 状态: 1.审批中 2.审批结束
        params.put("status", 1);
        // 审核结果: 0.未通过 1.通过
        params.put("result", 1);
        // 节点： 10.xx 11.xx (1开头代表机审) 20.xx 21.xx
        params.put("node", OrderNode.FIRSTJUDGE.toNum());
        // 是否预警: 0.否 1.是
        params.put("isWarning", params.getOrDefault("isWarning", 0));
        // 标签信息
        params.put("tagIds", params.getOrDefault("tagIds", ""));
        int num = orderDao.insertOrderCheckLog(params);
        if (num == 1) {
            // 审核状态： 1.机审 2.机审拒绝 3待初审 4.人工初审拒绝 5.待终审 6.人工终审拒绝 7.通过
            params.put("checkStatus", CheckStatus.WAITTINGFINALJUDGE.toNum());
            orderDao.updateOrderStatusById(params);
            orderDao.updateOrderAuditTimeByOrderId(orderId);
        }
    }


    /**
     * 初审拒绝
     *
     * @param params
     * @author baijieye
     */
    @Override
    public void firstRefuse(Map<String, Object> params) throws Exception {

        String orderId = String.valueOf(params.get("orderId"));

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单id:{} 查询是否可以进行初审拒绝操作", orderId);
        int checkStatus = orderDao.findCheckStatus(orderId);
        if (checkStatus != CheckStatus.WAITTINGFIRSTJUDGE.toNum()) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单id:{} 不是待初审状态无法进行初审拒绝操作", orderId);
            throw new Exception("不是待初审状态无法进行初审拒绝操作");
        }
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单id:{} 可以进行初审拒绝操作", orderId);


        // 状态: 1.审批中 2.审批结束
        params.put("status", 2);
        // 审核结果: 0.未通过 1.通过
        params.put("result", 0);
        // 节点： 10.xx 11.xx (1开头代表机审) 20.xx 21.xx
        params.put("node", OrderNode.FIRSTJUDGE.toNum());
        // 是否预警: 0.否 1.是
        params.put("isWarning", params.getOrDefault("isWarning", 0));
        // 标签信息
        params.put("tagIds", params.getOrDefault("tagIds", ""));
        int num = orderDao.insertOrderCheckLog(params);
        if (num == 1) {
            // 审核状态： 1.机审 2.机审拒绝 3待初审 4.人工初审拒绝 5.待终审 6.人工终审拒绝 7.通过
            params.put("checkStatus", CheckStatus.FIRSTREFUSE.toNum());
            orderDao.updateOrderStatusById(params);
            orderDao.updateOrderAuditTimeByOrderId(orderId);
        }
    }

    /**
     * 终审通过
     * 事物默认是遇到未捕获的RuntimeException才会回滚
     * 如果希望发生RuntimeException之外的异常的时候也回滚
     * 那么需要指定回滚类型异常为Exception  或者发生异常时手动回滚
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> finalJudgment(Map<String, Object> params) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //获取有效放款渠道
        Integer loanChannel = getValidLoanChannnel();
        if (null == loanChannel) {
            log.error("no valid loan channel, orderId:{}", params.get("orderId"));
            map.put("judgment", "failure:no valid loan channel");
            map.put("pay", "fail:未放款");
            return map;
        }
        //如果银行卡已校验   该平台是否能否直接放款
        //如果是kudos_2 则手动批量放款
        boolean loanTimely = paymentFacility.isLoanTimely(loanChannel);
        //注意:orderId为申请订单的id
        String orderId = String.valueOf(params.get("orderId"));
        FinanceLoanModel loanModel = null;
        FinancePayLogModel payLogModel = null;

        orderDao.updateOrderAuditTimeByOrderId(orderId);
        //放款前的操作  平台无关
        try {
            //正式放款之前的操作   遇到错误全部回滚
            log.info("【orderId:{}】-----------------------------------放款前的操作开始", orderId);
            // 1：校验订单审批状态        【 每个步骤遇到异常,记录,手动回滚,并手动抛出异常被外部捕捉】
            handleFinalJudgment.isFinalJudgmentOrder(map, orderId);
            // 2：插入审批记录
            handleFinalJudgment.saveApprovalRecord(params, map);
            // 3：更新订单审核的状态
            handleFinalJudgment.updateOrderStatus(params, map);
            // 5：插入借据
            AppUserLoanInfo userLoanInfo = handleFinalJudgment.saveUserLoan(loanChannel, orderId, map);
            // 6：创建放款记录+交易记录：初始化状态
            loanModel = orderBeanFactory.createFinanceLoan(userLoanInfo, map);
            payLogModel = orderBeanFactory.createFinancePayLogModel(loanModel, LoanType.LOANAPPLY.num);
            // 7：插入放款记录与放款交易：记录初始化状态
            handleFinalJudgment.saveFinance(loanModel, payLogModel, map);
            // 8：校验银行卡
            handlePayment.checkUserBankCard(loanModel, loanTimely, map);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            if (map.get("judgment") == null) {
                String handPError = (String) map.get("handP-error");
                handPError = handPError == null ? "fail:系统错误" : handPError;
                map.put("judgment", handPError);
            }
            map.put("pay", "fail:未放款");
            return map;
        }
        //userLoanInfo loanModel payLogModel带了id

        PayStatus payStatus = handlePayment.payStatus(loanTimely, map, loanModel);

        /**
         * 特别注意：以下为放款后的逻辑，如果放款没有失败，以下逻辑遇到异常，不应该回滚放款前的操作
         */
        //根据支付状态 更新放款记录和交易记录
        handlePayment.savePayStatus(payStatus, loanModel, payLogModel, map);

        //终审通过短信 task 82 贷款审核通过及放款成功短信/邮件合并，短信/邮件发送时间为：用户金额到账后发送。
        // finalJudgeMsg(loanModel);

        map.put("judgment", "ok");

        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lendingPool(String orderId, String reason, Integer isWarning, String tagIds, String auditorName) throws Exception {
        Map<String, Object> params = new HashMap<>();
        //校验订单审批状态
        if (CheckStatus.WAITTINGFINALJUDGE.toNum() != orderDao.findCheckStatus(orderId)) {
            throw new BusinessException("failure:非终审状态的订单", CashLoadSystemEnum.BAD_REQUEST);
        }
        //插入审批记录
        params.put("orderId", orderId);
        params.put("status", 3);// 状态: 1.审批中 2.审批结束 3.加入放款池，待放款
        params.put("result", 1);// 审核结果: 0.未通过 1.通过
        params.put("node", OrderNode.LENDINGPOOL.toNum());// 节点
        params.put("isWarning", isWarning);// 是否预警: 0.否 1.是
        params.put("tagIds", tagIds);// 标签信息
        params.put("reason", reason);// 原因备注
        params.put("auditorName", auditorName);// 审批人
        orderDao.insertOrderCheckLog(params);
        // 审核状态： 1.机审 2.机审拒绝 3待初审 4.人工初审拒绝 5.待终审 6.人工终审拒绝 7.通过 8.放款池（审批通过） 9.加入存量
        params.clear();
        params.put("orderId", orderId);
        params.put("checkStatus", CheckStatus.LENDINGPOOL.toNum());
        // 更新订单审核的状态
        orderDao.updateOrderStatusById(params);
        //修改放入放款池的时间，定时任务需要使用这个时间进行判定7天节点
        orderDao.updateOrderAuditTimeByOrderId(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void lendingPoolLoan(String orderId, String auditorName) throws Exception {
        //获取有效放款渠道
        Integer loanChannel = getValidLoanChannnel();
        if (null == loanChannel) {
            log.error("no valid loan channel, orderId:{}", orderId);
            return ;
        }
        //如果银行卡已校验   该平台是否能否直接放款
        //如果是kudos_2 则手动批量放款
        boolean loanTimely = paymentFacility.isLoanTimely(loanChannel);
        FinanceLoanModel loanModel = null;
        FinancePayLogModel payLogModel = null;
        Map<String, Object> params = new HashMap<>();
        try {
            //校验订单审批状态
            if (CheckStatus.LENDINGPOOL.toNum() != orderDao.findCheckStatus(orderId)) {
                throw new BusinessException("failure:非放款池的订单", CashLoadSystemEnum.BAD_REQUEST);
            }
            //插入审批记录
            //开户放款审批记录
            params.put("orderId", orderId);
            params.put("reason", "");
            params.put("isWarning", 0);
            params.put("tagIds", "");
            params.put("status", 2);// 状态: 1.审批中 2.审批结束
            params.put("result", 1);// 审核结果: 0.未通过 1.通过
            params.put("node", OrderNode.OPENLOAN.toNum());// 节点
            params.put("auditorName", auditorName);// 审批人
            orderDao.insertOrderCheckLog(params);
            // 审核状态： 1.机审 2.机审拒绝 3待初审 4.人工初审拒绝 5.待终审 6.人工终审拒绝 7.通过 8.放款池（审批通过） 9.加入存量
            params.clear();
            params.put("orderId", orderId);
            params.put("checkStatus", CheckStatus.PASSED.toNum());
            // 更新订单审核的状态
            orderDao.updateOrderStatusById(params);
            //修改放入放款池的时间，定时任务需要使用这个时间进行判定7天节点
            orderDao.updateOrderAuditTimeByOrderId(orderId);
            // 5：插入借据
            params.clear();
            AppUserLoanInfo userLoanInfo = handleFinalJudgment.saveUserLoan(loanChannel, orderId, params);
            // 6：创建放款记录+交易记录：初始化状态
            loanModel = orderBeanFactory.createFinanceLoan(userLoanInfo, params);
            payLogModel = orderBeanFactory.createFinancePayLogModel(loanModel, LoanType.LOANAPPLY.num);
            // 7：插入放款记录与放款交易：记录初始化状态
            handleFinalJudgment.saveFinance(loanModel, payLogModel, params);
            // 8：校验银行卡
            handlePayment.checkUserBankCard(loanModel, loanTimely, params);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        //userLoanInfo loanModel payLogModel带了id
        PayStatus payStatus = handlePayment.payStatus(loanTimely, params, loanModel);
        /**
         * 特别注意：以下为放款后的逻辑，如果放款没有失败，以下逻辑遇到异常，不应该回滚放款前的操作
         */
        //根据支付状态 更新放款记录和交易记录
        handlePayment.savePayStatus(payStatus, loanModel, payLogModel, params);

        //终审通过短信 task 82 贷款审核通过及放款成功短信/邮件合并，短信/邮件发送时间为：用户金额到账后发送。
        // finalJudgeMsg(loanModel);
    }

    /**
     * 终审拒绝
     *
     * @param params
     * @author baijieye
     */
    @Override
    public void finalRefuse(Map<String, Object> params) {

        String orderId = String.valueOf(params.get("orderId"));
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单id:{} 查询是否可以进行终审拒绝操作", orderId);
        int checkStatus = orderDao.findCheckStatus(orderId);
        if (checkStatus != CheckStatus.WAITTINGFINALJUDGE.toNum()) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单id:{} 不是待终审状态无法进行终审拒绝操作", orderId);
            try {
                throw new Exception("不是待终审状态无法进行终审拒绝操作");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单id:{} 可以进行终审拒绝操作", orderId);


        // 状态: 1.审批中 2.审批结束
        params.put("status", 2);
        // 审核结果: 0.未通过 1.通过
        params.put("result", 0);
        // 是否预警: 0.否 1.是
        params.put("isWarning", params.getOrDefault("isWarning", 0));
        // 标签信息
        params.put("tagIds", params.getOrDefault("tagIds", ""));
        // 节点： 10.xx 11.xx (1开头代表机审) 20.xx 21.xx
        if (params.get("isStock") != null && 1 == Integer.parseInt(params.get("isStock").toString())) {
            // 插入存量库操作
            this.validAndSaveStockUserOrder(orderId);
        }
        params.put("checkStatus", CheckStatus.FINALREFUSE.toNum());
        params.put("node", OrderNode.FINALJUDGE.toNum());
        int num = orderDao.insertOrderCheckLog(params);
        if (num > 0) {
            orderDao.updateOrderStatusById(params);
            orderDao.updateOrderAuditTimeByOrderId(orderId);
        }
    }

    /**
     * 获取标签
     *
     * @return
     * @author baijieye
     */
    @Override
    public JsonResult getTags() {
        List<Map<String, Object>> data = orderDao.getTags();
        return JsonResult.ok(data);
    }

    @Override
    public List<OrderTableModel> getOrderByOrderNos(String orderNos) {
        return orderDao.getOrderByOrderNos(orderNos);
    }

    /**
     * 获取订单审批记录
     *
     * @param orderId
     * @return
     * @author baijieye
     */
    @Override
    public JsonResult getOrderCheckLog(String orderId, String name) {
        Map<String, Object> map = new HashMap<>();
        List<OrderCheckLogModel> data = orderDao.getOrderCheckLogByOrderId(orderId);
        map.put("orderCheckLog", data);
        int operation = orderDao.getOperation(orderId, name);
        map.put("operation", operation);
        return JsonResult.ok(map);
    }

    @Override
    public OrderTableModel queryUserOrderById(Integer id) {
        return orderDao.queryUserOrderById(id);
    }


    /**
     * 获取订单详情Service
     *
     * @param orderId
     * @return
     * @author zhujingtao
     */
    @Override
    public JsonResult getOrderDetail(String orderId) {
        // 根据订单 寻找对应的客户信息
        OrderProcessModel orderProcessModel = orderDao.getOrderById(orderId);
        if (orderProcessModel != null) {
            //是否续贷  判定
            if (orderProcessModel.getRenewalState() != null) {
                boolean isRenewLoans = orderProcessModel.getRenewalState() == 0 ? false : true;
                orderProcessModel.setIsRenewLoans(isRenewLoans);
            } else {
                orderProcessModel.setIsRenewLoans(false);
            }
        }
        //查询对应信息
        UserInfoParam userInfo = orderDao.getUserInfo(orderId);
        String orderNo = userInfo.getOrderNo();
        AadhaarAuth aadhaarAuth = orderDao.getAadhaarAuthByOrderNo(orderNo);
        PanCardAuth panCardAuth = orderDao.getPanCardAuthByOrderNo(orderNo);
        //设置 无值扫入 时替换
        aadhaarAuth.setAadhaarAddressDetails(aadhaarAuth.getAadhaarAddressDetails().replaceAll(",,,", "").replaceAll(",,", ","));
        aadhaarAuth.setAadhaarName(userInfo.getName());
        UserBankCard bankInfo = orderDao.getBankInfo(orderNo);
        ImgInfo imgInfo = orderDao.getImgInfoByOrderId(orderId);

        WorkAddressInfo addrInfo = WorkAddressInfo.builder().build();
        StudentInfo studentInfo = StudentInfo.builder().build();
        //判定是否是学生信息
        if (userInfo.getStudentStatus() == StudentStatus.NOTSTUDENT.num) {
            addrInfo = orderDao.getWorkInfoByOrderNo(orderNo);
        } else {
            studentInfo = orderDao.getStudentInfoByOrderNo(orderNo);
        }

        VoterAuthInfo authInfo = orderDao.getVoterAuthInfoByOrderNo(orderNo);
        List<UserContact> userContacts = new ArrayList<>();

        Optional.ofNullable(userClient.getContactById(String.valueOf(userInfo.getFirstContactId())))
                .ifPresent(firstContract -> userContacts.add(firstContract));
        Optional.ofNullable(userClient.getContactById(String.valueOf(userInfo.getSecondContactId())))
                .ifPresent(secondContact -> userContacts.add(secondContact));

        ConformFile conformInfo = orderDao.getConformFile(orderNo);
        //图片转换
        if (null != conformInfo) {
            String workerCardUrl = Stream.of(StringUtils.split(conformInfo.getWorkCard(), ","))
                    .map(workCard -> aliOssUtil.getDefaultUrl() + workCard)
                    .collect(Collectors.joining(","));

            String bankStatementUrl = Stream.of(StringUtils.split(conformInfo.getBankStatementUrl(), ","))
                    .map(bankStatement -> aliOssUtil.getDefaultUrl() + bankStatement)
                    .collect(Collectors.joining(","));

            conformInfo.setWorkCard(workerCardUrl);
            conformInfo.setBankStatementUrl(bankStatementUrl);
        }

        if (null != imgInfo) {
            if (StringUtils.isNotBlank(imgInfo.getFaceImg())) {
                imgInfo.setFaceImg(aliOssUtil.getDefaultUrl() + imgInfo.getFaceImg());
            }
            if (StringUtils.isNotBlank(imgInfo.getSignatureUrl())) {
                imgInfo.setSignatureUrl(aliOssUtil.getDefaultUrl() + imgInfo.getSignatureUrl());
            }
        }

        if (null != authInfo) {
            if (StringUtils.isNotBlank(authInfo.getVoterBackUrl())) {
                authInfo.setVoterBackUrl(aliOssUtil.getDefaultUrl() + authInfo.getVoterBackUrl());
            }
            if (StringUtils.isNotBlank(authInfo.getVoterFrontUrl())) {
                authInfo.setVoterFrontUrl(aliOssUtil.getDefaultUrl() + authInfo.getVoterFrontUrl());
            }
        }
        if (null != aadhaarAuth) {
            if (StringUtils.isNotBlank(aadhaarAuth.getAadhaarFront())) {
                aadhaarAuth.setAadhaarFront(aliOssUtil.getDefaultUrl() + aadhaarAuth.getAadhaarFront());
            }
            if (StringUtils.isNotBlank(aadhaarAuth.getAadhaarBack())) {
                aadhaarAuth.setAadhaarBack(aliOssUtil.getDefaultUrl() + aadhaarAuth.getAadhaarBack());
            }
        }
        if (null != panCardAuth) {
            if (StringUtils.isNotBlank(panCardAuth.getPanCardBack())) {
                panCardAuth.setPanCardBack(aliOssUtil.getDefaultUrl() + panCardAuth.getPanCardBack());
            }
            if (StringUtils.isNotBlank(panCardAuth.getPanCardFront())) {
                panCardAuth.setPanCardFront(aliOssUtil.getDefaultUrl() + panCardAuth.getPanCardFront());
            }
        }
        if (null != bankInfo) {
            if (StringUtils.equalsIgnoreCase(bankInfo.getAccountName(), bankInfo.getVerifyReturnName())) {
                bankInfo.setMatchResult(Boolean.TRUE);
            } else {
                bankInfo.setMatchResult(Boolean.FALSE);
            }
        }

        OrderDetailParam orderDetailParam = OrderDetailParam.builder()
                .userContacts(userContacts)
                .workAddressInfo(addrInfo)
                .studentInfo(studentInfo)
                .imgInfo(imgInfo)
                .voterAuthInfo(authInfo)
                .conformFile(conformInfo)
                .orderProcessModel(orderProcessModel)
                .panCardAuth(panCardAuth)
                .userInfoParam(userInfo)
                .aadhaarAuth(aadhaarAuth)
                .userBankCard(bankInfo)
                .build();

        return JsonResult.ok(orderDetailParam);
    }


    /**
     * 获取平台审批统计报表
     *
     * @param params
     * @return
     * @author baijieye
     */
    @Override
    public JsonResult getSystemCount(Map<String, Object> params) {
        // 获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        List<SystemCountModel> systemCountModelList = orderDao.getSystemCount(params);
        systemCountModelList.forEach(SystemCountModel::setFirstRate);
        Map<String, Object> data = new HashMap<String, Object>(16);
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (systemCountModelList.size() > 0 && today.equals(systemCountModelList.get(0).getCreateDate())) {
            data.put("headInfo", systemCountModelList.get(0));
        } else {
            params.clear();
            params.put("beginDate", today);
            List<SystemCountModel> todayList = orderDao.getSystemCount(params);
            SystemCountModel systemCountModel = new SystemCountModel();
            if (todayList != null && todayList.size() > 0) {
                systemCountModel = todayList.get(0);
                systemCountModel.setFirstRate();
            } else {
                systemCountModel.setCreateDate(today);
                systemCountModel.setFirstSum(0);
                systemCountModel.setFirstPassedSum(0);
                systemCountModel.setFinalSum(0);
                systemCountModel.setFinalPassedSum(0);
                systemCountModel.setLoanAmount(0);
                systemCountModel.setFinalAmount(0);
                systemCountModel.setFirstRate("0%");
                systemCountModel.setFinalRate("0%");
            }
            data.put("headInfo", systemCountModel);
        }
        PageInfo<SystemCountModel> pageInfo = new PageInfo<SystemCountModel>(systemCountModelList);
        data.put("info", pageInfo.getList());
        return JsonResult.ok(data, (int) pageInfo.getTotal());
    }

    /**
     * 获取个人审批统计报表
     *
     * @param params
     * @return
     * @author baijieye
     */
    @Override
    public JsonResult getPersonCount(Map<String, Object> params) {
        // 获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        if (MapUtils.getObject(params, "auditorName") != null && MapUtils.getString(params, "auditorName").equals("0")) {
            params.remove("auditorName");
        }
        PageHelper.startPage(page, pageSize);
        List<PersonCountModel> personCountModelList = orderDao.getPersonCount(params);
        personCountModelList.forEach(PersonCountModel::setFirstRate);
        PageInfo<PersonCountModel> pageInfo = new PageInfo<PersonCountModel>(personCountModelList);
        return JsonResult.ok(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    /**
     * 获取个人审批统计详情报表
     *
     * @param params
     * @return
     * @author baijieye
     */
    @Override
    public JsonResult getPersonCountDetail(Map<String, Object> params) {
        // 获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        if (params.get("userId") != null && params.get("userId") != "") {
            String auditorName = orderDao.findNameById(MapUtils.getInteger(params, "userId"));
            params.put("auditorName", auditorName);
        }
        List<PersonCountDetailModel> personCountDetailModelList = orderDao.getPersonCountDetail(params);
        personCountDetailModelList.forEach(PersonCountDetailModel::setFirstRate);
        Map<String, Object> data = new HashMap<String, Object>(16);
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        if (personCountDetailModelList.size() > 0 && today.equals(personCountDetailModelList.get(0).getCreateDate())) {
            data.put("headInfo", personCountDetailModelList.get(0));
        } else {
            String auditorName = MapUtils.getString(params, "auditorName");
            PersonCountDetailModel personCountDetailModel = new PersonCountDetailModel();
            params.clear();
            params.put("beginDate", today);
            params.put("auditorName", auditorName);
            List<PersonCountDetailModel> todayList = orderDao.getPersonCountDetail(params);
            if (todayList != null && todayList.size() > 0) {
                personCountDetailModel = todayList.get(0);
                personCountDetailModel.setFirstRate();
                personCountDetailModel.setAuditorName(auditorName);
            } else {
                personCountDetailModel.setFirstSum(0);
                personCountDetailModel.setFirstPassedSum(0);
                personCountDetailModel.setFinalSum(0);
                personCountDetailModel.setFinalPassedSum(0);
                personCountDetailModel.setFirstRate("0%");
                personCountDetailModel.setFinalRate("0%");
                personCountDetailModel.setAuditorName(auditorName);
            }
            data.put("headInfo", personCountDetailModel);
        }
        PageInfo<PersonCountDetailModel> pageInfo = new PageInfo<PersonCountDetailModel>(personCountDetailModelList);
        data.put("info", pageInfo.getList());
        return JsonResult.ok(data, (int) pageInfo.getTotal());
    }


    /**
     * 今日 平台统计数据
     *
     * @return
     */
    @Override
    public JsonResult getSystemNowReport() {
        //初始化 数据值
        BigDecimal applyAmount = new BigDecimal(0.0);
        DecimalFormat df = new DecimalFormat("#.00");
        Integer finalAuditCount = 0;
        Double finalPassRate = 0.0;
        Double firstPassCount = 0.0;
        Double finalPassCount = 0.0;
        String firstPassRateStr = "0.0";
        String finalPassRateStr = "0.0";
        Double firstPassRate = 0.0;
        Integer firstAuditCount = 0;
        BigDecimal passAmount = new BigDecimal(0.0);
        //获取 平台今日统计数据 无需传参
        List<ReportOrderAuditParam> reportOrderAuditParams = orderDao.queryNowOrderAudit(null);
        //处理 具体 逻辑
        if (CollectionUtils.isNotEmpty(reportOrderAuditParams)) {
            for (ReportOrderAuditParam reportOrderAuditParam : reportOrderAuditParams) {
                if (reportOrderAuditParam.getNode().equals("20")) {
                    applyAmount = applyAmount.add(reportOrderAuditParam.getAmount());
                    firstAuditCount = firstAuditCount + reportOrderAuditParam.getNum();
                    //通过逻辑处理
                    if (reportOrderAuditParam.getResult().equals("1")) {
                        firstPassCount = firstPassCount + reportOrderAuditParam.getNum();
                    }
                } else if (reportOrderAuditParam.getNode().equals("21")) {
                    finalAuditCount = finalAuditCount + reportOrderAuditParam.getNum();
                    if (reportOrderAuditParam.getResult().equals("1")) {
                        finalPassCount = finalPassCount + reportOrderAuditParam.getNum();
                        passAmount = passAmount.add(reportOrderAuditParam.getAmount());
                    }


                }
            }
            //初审通过率计算
            firstPassRate = firstAuditCount == null || firstAuditCount == 0 ? 0 : (firstPassCount * 100) / firstAuditCount;
            //终审通过率 计算
            finalPassRate = finalAuditCount == null || finalAuditCount == 0 ? 0 : (finalPassCount * 100) / finalAuditCount;
        }

        if (firstPassRate != 0) {
            firstPassRateStr = df.format(firstPassRate) + "%";
        }
        if (finalPassRate != 0) {
            finalPassRateStr = df.format(finalPassRate) + "%";
        }
        return JsonResult.ok(ReportOrderAuditModel.builder().
                applyAmount(applyAmount)
                .firstPassRateStr(firstPassRateStr)
                .finalPassRateStr(finalPassRateStr)
                .finalAuditCount(finalAuditCount).finalPassRate(finalPassRate)
                .firstPassRate(firstPassRate).firstAuditCount(firstAuditCount)
                .passAmount(passAmount)
                .build());
    }

    @Override
    public JsonResult getPersonNowReport(Map<String, Object> params) {
        Integer page = 1;
        Integer limit = 10;
        List<ReportOrderAuditModel> reportOrderAuditModels = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.00");
        List<ReportOrderAuditParam> reportOrderAuditParams = orderDao.queryNowOrderAudit(params);
        Map<String, List<ReportOrderAuditParam>> reportMap = reportOrderAuditParams.stream().collect(Collectors.groupingBy(ReportOrderAuditParam::getAuditorName));
        List<Map<String, Object>> auditorList = new ArrayList<>();
        //初始化 列表集合
        if (MapUtils.getInteger(params, "auditorId") == null) {
            auditorList = orderDao.getAuditor(3);
        } else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", MapUtils.getInteger(params, "auditorId"));
            map.put("username", orderDao.findNameById(MapUtils.getInteger(params, "auditorId")));
            auditorList.add(map);
        }
        for (Map<String, Object> auditor : auditorList) {
            BigDecimal applyAmount = new BigDecimal(0.0);
            Integer finalAuditCount = 0;
            Double finalPassRate = 0.0;
            Double firstPassRate = 0.0;
            Integer firstAuditCount = 0;
            BigDecimal passAmount = new BigDecimal(0.0);
            reportOrderAuditModels.add(
                    ReportOrderAuditModel.builder()
                            .auditorName(MapUtils.getString(auditor, "username"))
                            .applyAmount(applyAmount).
                            finalAuditCount(finalAuditCount).finalPassRate(finalPassRate)
                            .firstPassRate(firstPassRate).firstAuditCount(firstAuditCount)
                            .auditorId((MapUtils.getInteger(auditor, "id")))
                            .passAmount(passAmount)
                            .finalPassRateStr(finalPassRate + "%")
                            .firstPassRateStr(firstPassRate + "%")
                            .build());

        }
        for (Map.Entry<String, List<ReportOrderAuditParam>> entry : reportMap.entrySet()) {
            //初始化 数据值
            BigDecimal applyAmount = new BigDecimal(0.0);
            Integer finalAuditCount = 0;
            Double finalPassRate = 0.0;
            Double firstPassCount = 0.0;
            Double finalPassCount = 0.0;

            Double firstPassRate = 0.0;
            Integer firstAuditCount = 0;
            BigDecimal passAmount = new BigDecimal(0.0);
            //获取 平台今日统计数据 无需传参
            List<ReportOrderAuditParam> reportOrderAuditParams1 = entry.getValue();

            //处理 具体 逻辑
            if (CollectionUtils.isNotEmpty(reportOrderAuditParams1)) {
                for (ReportOrderAuditParam reportOrderAuditParam : reportOrderAuditParams1) {
                    if (reportOrderAuditParam.getNode().equals("20")) {
                        firstAuditCount = firstAuditCount + reportOrderAuditParam.getNum();
                        applyAmount = applyAmount.add(reportOrderAuditParam.getAmount());
                        //通过逻辑处理
                        if (reportOrderAuditParam.getResult().equals("1")) {
                            firstPassCount = firstPassCount + reportOrderAuditParam.getNum();
                        }
                    } else if (reportOrderAuditParam.getNode().equals("21") || reportOrderAuditParam.getNode().equals("40")) {
                        finalAuditCount = finalAuditCount + reportOrderAuditParam.getNum();
                        if (reportOrderAuditParam.getResult().equals("1")) {
                            finalPassCount = finalPassCount + reportOrderAuditParam.getNum();
                            passAmount = passAmount.add(reportOrderAuditParam.getAmount());
                        }


                    }
                }
                //初审通过率计算
                firstPassRate = firstAuditCount == null || firstAuditCount == 0 ? 0 : (firstPassCount * 100) / firstAuditCount;
                //终审通过率 计算
                finalPassRate = finalAuditCount == null || finalAuditCount == 0 ? 0 : (finalPassCount * 100) / finalAuditCount;
            }

            String finalPassRateStr = "0.00%";
            String firstPassRateStr = "0.00%";
            if (finalPassRate != 0) {
                finalPassRateStr = df.format(finalPassRate) + "%";
            }
            if (firstPassRate != 0) {
                firstPassRateStr = df.format(firstPassRate) + "%";
            }

            reportOrderAuditModels = reportOrderAuditModels.stream().filter(ReportOrderAuditModel -> !ReportOrderAuditModel.getAuditorName().equals(entry.getKey())).collect(Collectors.toList());

            Integer id = orderDao.findIdByName(entry.getKey());

            reportOrderAuditModels.add(
                    ReportOrderAuditModel.builder()
                            .auditorName(entry.getKey())
                            .auditorId(id)
                            .applyAmount(applyAmount).
                            finalAuditCount(finalAuditCount).finalPassRate(finalPassRate)
                            .firstPassRate(firstPassRate).firstAuditCount(firstAuditCount)
                            .finalPassRateStr(finalPassRateStr)
                            .firstPassRateStr(firstPassRateStr)
                            .passAmount(passAmount)
                            .build());

        }

        if (MapUtils.getInteger(params, "page") != null) {
            page = MapUtils.getInteger(params, "page");
            limit = MapUtils.getInteger(params, "limit");
        }

        return JsonResult.ok(DataPageUtil.pageLimit(reportOrderAuditModels, page, limit), reportOrderAuditModels.size());
    }

    @Override
    public JsonResult getSystemHistoryReport(Map<String, Object> params) {
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        List<ReportOrderAuditModel> reportOrderAuditModels = orderDao.getSystemHistoryReport(params);
        PageInfo<ReportOrderAuditModel> reportOrderAuditModelPageInfo = new PageInfo<ReportOrderAuditModel>(reportOrderAuditModels);

        return JsonResult.ok(reportOrderAuditModelPageInfo.getList(), (int) reportOrderAuditModelPageInfo.getTotal());
    }

    @Override
    public JsonResult getPersonHistoryReport(Map<String, Object> params) {
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        List<ReportOrderAuditModel> reportOrderAuditModels = orderDao.getReportOrderAudit(params);

        PageInfo<ReportOrderAuditModel> reportOrderAuditModelPageInfo = new PageInfo<ReportOrderAuditModel>(reportOrderAuditModels);
        return JsonResult.ok(reportOrderAuditModelPageInfo.getList(), (int) reportOrderAuditModelPageInfo.getTotal());
    }

    @Override
    public String getLoanPdf(String loanNo) {
        String url = financeLoanContractDao.getFinanceLoanContractFinanceLoan(loanNo);
        Assert.hasText(url, "找不到该份合同");
        return aliOssUtil.getUrl(url);
    }

    @Override
    public String getLoanOrderContractPdf(String loanNo) {
        String url = financeLoanContractDao.getFinanceLoanContractFinanceContract(loanNo);
        Assert.hasText(url, "找不到该份合同");
        return aliOssUtil.getUrl(url);
    }

    @Override
    public AppUserOrderInfo getUserOrderByOrderNum(String orderNum) {
        return orderDao.getUserOrderByOrderNum(orderNum);
    }

    @Override
    public String getExtensionContractPdf(long extensionId) {
        String url = financeLoanContractDao.getFinanceLoanContractExtension(extensionId);
        Assert.hasText(url, "找不到该份合同");
        return aliOssUtil.getUrl(url);
    }

    @Override
    public int getOrderCountInSameCompanyName(String orderNo) {
        return orderDao.getOrderCountInSameCompanyName(orderNo);
    }

    @Override
    public int getOrderCountInSameCompanyPhone(String orderNum) {
        return orderDao.getOrderCountInSameCompanyPhone(orderNum);
    }

    @Override
    public AppUserOrderInfo getUserOrderByUserId(String userId) {
        return orderDao.getUserOrderByUserId(userId);
    }

    /**
     * 通过orderID 寻找orderNUm
     *
     * @param orderId orderID
     * @return orderNum
     */
    @Override
    public String findOrderNumByOrderId(String orderId) {
        return orderDao.findOrderNumByOrderId(orderId);
    }

    @Override
    public OrderTableModel getFirstOrderCreatedByUserIds(List<Long> userIds) {
        return orderDao.getFirstOrderCreatedByUserIds(userIds);
    }

    @Override
    public int queryOrderNumToId(String orderNum) {
        return orderDao.queryOrderNumToId(orderNum);
    }

    @Override
    public boolean finalJudgeForLoanOrder(Integer userId) {
        JsonResult jsonResult = userClient.queryAllSysRefusalCycle();
        List<LinkedHashMap<String, Object>> sysRefuseCycles = (List<LinkedHashMap<String, Object>>) jsonResult.getData();
        //     *1 审批拒绝类型
        //     * 2 放款失败类型
        //     * 3 其他类型
        LinkedHashMap<String, Object> auditFuseSysRefuseCycle =
                sysRefuseCycles.stream().filter(stringObjectLinkedHashMap -> ((Integer) stringObjectLinkedHashMap.get("code")) == 1).collect(Collectors.toList()).get(0);
        LinkedHashMap<String, Object> loanFailSysRefuseCycle =
                sysRefuseCycles.stream().filter(stringObjectLinkedHashMap -> ((Integer) stringObjectLinkedHashMap.get("code")) == 2).collect(Collectors.toList()).get(0);
        LinkedHashMap<String, Object> ontherSysRefuseCycle =
                sysRefuseCycles.stream().filter(stringObjectLinkedHashMap -> ((Integer) stringObjectLinkedHashMap.get("code")) == 3).collect(Collectors.toList()).get(0);

        OrderTableModel orderTableModel = orderDao.queryLastOrderTableByUserId((long) userId);


        if (orderTableModel == null) {
            log.info("用户无贷款记录 可以正常贷款");
            return true;
        }
        List<OrderOntherRefuse> orderOntherRefuses = orderOntherRefuseDao.slectAllByOrderNo(orderTableModel.getOrderNum());
        Integer checkStatus = orderTableModel.getCheckStatus();
        Date finalAuditTime = orderTableModel.getFinalAuditTime();

        //判定  用户最后一笔提交订单 是否在其他列表里
        if (CollectionUtils.isNotEmpty(orderOntherRefuses)) {
            if (DateUtil.getDayCount(finalAuditTime, new Date()) >= (Integer) ontherSysRefuseCycle.get("cycleDayCount")) {
                log.info(" 用户归属于其他 但是已经超过" + ontherSysRefuseCycle.get("cycleDayCount") + "天");
                return true;

            }
        }
        //进入存量   可以贷款
        if (checkStatus==CheckStatus.LENDINGPOOL.toNum()&&(orderTableModel.getIsStock()==1||orderTableModel.getIsStock()==2)){
            return  true;
        }
        //倘若是通过类型  则判定是否  借据 完成
        if (checkStatus == CheckStatus.PASSED.toNum()) {
            UserLoan userLoan = basicDao.getLatestLoanByUserId(userId);
            //借据 完成 可借款
            if (userLoan.getLoanStatus().equals(ProductConstants.FINISH)) {

                log.info("借据 完成状态可借款");
                return true;
            }
            //借据未激活   即放款失败类型
            if (userLoan.getLoanStatus().equals(ProductConstants.NOT_ACTIVATED) || userLoan.getLoanStatus().equals(ProductConstants.IS_INVALID)) {
                if (DateUtil.getDayCount(userLoan.getCreateTime(), new Date()) >= (Integer) loanFailSysRefuseCycle.get("cycleDayCount")) {
                    log.info("  该用户最近一条借据为未激活 但是已经超过" + loanFailSysRefuseCycle.get("cycleDayCount") + "天");
                    return true;
                } else {
                    log.info("  该用户最近一条借据为未激活 但是未超过" + loanFailSysRefuseCycle.get("cycleDayCount") + "天");
                }

            }
        }
        //审核拒绝状态  判定 是否属于审批拒绝类
        else if (checkStatus == CheckStatus.MECHINEREFUSE.toNum()
                || checkStatus == CheckStatus.FIRSTREFUSE.toNum()
                || checkStatus == CheckStatus.FINALREFUSE.toNum()) {

            //判定是否是审批拒绝类型
            if (DateUtil.getDayCount(finalAuditTime, new Date()) >= (Integer) auditFuseSysRefuseCycle.get("cycleDayCount")) {
                log.info(" 用户审批拒绝 但是已经超过" + auditFuseSysRefuseCycle.get("cycleDayCount") + "天");
                return true;
            } else {
                log.info(" 用户审批拒绝 未超过" + auditFuseSysRefuseCycle.get("cycleDayCount") + "天");
            }
        }

        return false;
    }

    @Override
    public List<String> getOrderNoByUserId(Long userId) {
        return orderDao.getOrderNoByUserId(userId);
    }

    @Override
    public List<OrderHistoryDto> getSamePhoneCompanyByOrderNo(Map<String, Object> params) {
        String orderNo = MapUtils.getString(params, "orderNo");
        OrderTableModel orderTableModel = orderDao.queryOrderTableByOrderNo(orderNo);
        Map<String, Object> companyMap = orderDao.getSameCompanyNameAndMobileByOrderNo(orderNo);
        if (companyMap == null) {
            return new ArrayList<>();
        }
        companyMap.put("companyName", "");
        List<OrderHistoryDto> orderHistoryDtos =
                orderDao.getSameCompanyOrderInfoByCompanyMobileOrName(companyMap);
        orderHistoryDtos.stream().forEach(OrderHistoryDto -> OrderHistoryDto.setCompanyMobile((String) companyMap.get("companyMobile")));
        if (orderTableModel != null) {
            orderHistoryDtos = orderHistoryDtos.stream().filter(OrderHistoryDto -> !OrderHistoryDto.getMobile().equals(orderTableModel.getUserPhone())).collect(Collectors.toList());
        }

        orderHistoryDtos = processOrderHistoryDtoAuditorStatus(orderHistoryDtos);
        return orderHistoryDtos;
    }

    @Override
    public List<OrderHistoryDto> getSameCompanyNameByOrderNo(Map<String, Object> params) {
        String orderNo = MapUtils.getString(params, "orderNo");
        OrderTableModel orderTableModel = orderDao.queryOrderTableByOrderNo(orderNo);
        Map<String, Object> companyMap = orderDao.getSameCompanyNameAndMobileByOrderNo(orderNo);
        if (companyMap == null) {
            return new ArrayList<>();
        }
        companyMap.put("companyMobile", "");
        List<OrderHistoryDto> orderHistoryDtos =
                orderDao.getSameCompanyOrderInfoByCompanyMobileOrName(companyMap);
        if (orderTableModel != null) {
            orderHistoryDtos = orderHistoryDtos.stream().filter(OrderHistoryDto -> !OrderHistoryDto.getMobile().equals(orderTableModel.getUserPhone())).collect(Collectors.toList());
        }
        orderHistoryDtos = processOrderHistoryDtoAuditorStatus(orderHistoryDtos);
        orderHistoryDtos.stream().forEach(OrderHistoryDto -> OrderHistoryDto.setCompanyName((String) companyMap.get("companyName")));

        return orderHistoryDtos;
    }

    @Override
    public List<OrderHistoryDto> getSameDeviceInfoByOrderNo(Map<String, Object> params) {
        String orderNos = "";
        OrderTableModel orderTableModel = orderDao.queryOrderTableByOrderNo(MapUtils.getString(params, "orderNo"));
        List<OrderHistoryDto> orderHistoryDtoList = new ArrayList<>();
        List<RiskDeviceInfoModel> riskDeviceInfoModels = (List<RiskDeviceInfoModel>) params.get("RiskDeviceInfoModelList");

        for (int i = 0; i < riskDeviceInfoModels.size(); i++) {

            if (i != riskDeviceInfoModels.size() - 1) {
                orderNos = orderNos + riskDeviceInfoModels.get(i).getOrderNum() + ",";
            } else {
                orderNos = orderNos + riskDeviceInfoModels.get(i).getOrderNum();
            }

        }
        if (orderNos.equals("")) {
            return orderHistoryDtoList;
        }


        List<OrderHistoryDto> orderHistoryDtos =
                orderDao.getSameCompanyOrderInfoByOrderNo(orderNos);
        if (orderHistoryDtos != null) {
            for (OrderHistoryDto orderHistoryDto : orderHistoryDtos) {
                if (riskDeviceInfoModels.size() > 0) {
                    orderHistoryDto.setImiNo(riskDeviceInfoModels.get(0).getImiId().toString());
                }

            }
        }

        if (orderTableModel != null) {
            orderHistoryDtos = orderHistoryDtos.stream().filter(OrderHistoryDto -> !OrderHistoryDto.getMobile().equals(orderTableModel.getUserPhone())).collect(Collectors.toList());
        }
        orderHistoryDtos = processOrderHistoryDtoAuditorStatus(orderHistoryDtos);

        return orderHistoryDtos;
    }

    @Override
    public List<OrderHistoryDto> getSameDeviceInfoByRelationMan(Map<String, Object> params) {
        String orderNo = MapUtils.getString(params, "orderNo");

        List<OrderHistoryDto> orderHistoryDtoList = new ArrayList<>();
        List<OrderHistoryDto> orderHistoryDtos = new ArrayList<>();
        List<OrderHistoryDto> publicContactsorderHistoryDtos = new ArrayList<>();
        List<OrderHistoryDto> onthersorderHistoryDtos = new ArrayList<>();
        List<OrderHistoryDto> data = new ArrayList<>();
        OrderTableModel orderInfoModel = orderDao.getOrderInfoByOrderNo(orderNo);
        Map<String, Object> inMap = new HashMap<>();
        inMap.put("userId", orderInfoModel.getUserId());
        inMap.put("mobile", orderInfoModel.getUserPhone());

        Map<String, String> relationUserIdMap = userClient.getUserIdAndRelation(inMap);
        if (relationUserIdMap == null) {
            return orderHistoryDtoList;
        }
        String publicContactsUserIds = relationUserIdMap.get("publicContactsUserIds");
        String ontherUserIds = relationUserIdMap.get("ontherUserIds");
        if (publicContactsUserIds != null && !publicContactsUserIds.equals("")) {
            publicContactsorderHistoryDtos = orderDao.getSameCompanyOrderInfoByUserIds(publicContactsUserIds);
            publicContactsorderHistoryDtos.forEach(OrderHistoryDto -> OrderHistoryDto.setRelationName("shared contacts "));
        }
        if (ontherUserIds != null && !ontherUserIds.equals("")) {
            onthersorderHistoryDtos = orderDao.getSameCompanyOrderInfoByUserIds(ontherUserIds);
            onthersorderHistoryDtos.forEach(OrderHistoryDto -> OrderHistoryDto.setRelationName("other people's contacts"));
        }
        if (publicContactsorderHistoryDtos != null && !publicContactsorderHistoryDtos.isEmpty()) {
            orderHistoryDtos.addAll(publicContactsorderHistoryDtos);

        }

        if (onthersorderHistoryDtos != null && !onthersorderHistoryDtos.isEmpty()) {
            if (!orderHistoryDtos.isEmpty()) {

                for (OrderHistoryDto onthersorderHistoryDto : onthersorderHistoryDtos) {

                    List<OrderHistoryDto> orderHistoryDtos1 = orderHistoryDtos.stream().filter(OrderHistoryDto -> onthersorderHistoryDto.getOrderNo().equals(OrderHistoryDto.getOrderNo())).collect(Collectors.toList());
                    if (orderHistoryDtos1 != null && !orderHistoryDtos1.isEmpty()) {
                        orderHistoryDtos1.forEach(OrderHistoryDto -> OrderHistoryDto.setRelationName(OrderHistoryDto.getRelationName() + ",  " + onthersorderHistoryDto.getRelationName()));
                    } else {
                        data.add(onthersorderHistoryDto);
                    }

                }
            }

            if (data.isEmpty()) {
                orderHistoryDtos.addAll(onthersorderHistoryDtos);
            } else {
                orderHistoryDtos.addAll(data);
            }


        }
        if (orderInfoModel != null) {
            orderHistoryDtos = orderHistoryDtos.stream().filter(OrderHistoryDto -> !OrderHistoryDto.getMobile().equals(orderInfoModel.getUserPhone())).collect(Collectors.toList());
        }

        orderHistoryDtos = processOrderHistoryDtoAuditorStatus(orderHistoryDtos);

        return orderHistoryDtos;
    }

    @Override
    public JsonResult getSameUserInfoByOrderNo(String orderNo) {

        //获取同用户  相互匹配的 信息
        List<OrderTableModel> orderTableModels = orderDao.getSamUserIdOrderInfoByOrderNo(orderNo);


        orderTableModels = orderTableModels.stream().filter(orderTableModel
                -> !orderTableModel.getOrderNum()
                .equals(orderNo)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(orderTableModels)) {
            return JsonResult.ok();
        }
        List<SamUserInfoParam> samUserInfoParams = new ArrayList<>();

        for (OrderTableModel orderTableModel : orderTableModels) {
            if (orderTableModel.getCheckStatus() == 1) {
                continue;
            }
            //载入
            SamUserInfoParam samUserInfoParam = new SamUserInfoParam();
            samUserInfoParam.setOrderNo("MN" + orderTableModel.getOrderNum() + "PD");
            AppUserLoanInfo appUserLoanInfo = userClient.getUserLoanByOrderNo(orderTableModel.getOrderNum());
            if (appUserLoanInfo != null) {
                samUserInfoParam.setLoanStatus(appUserLoanInfo.getLoanStatus());
            }

            List<OrderCheckLogModel> orderCheckLogModels = new ArrayList<>();
            List<OrderCheckLogModel> checkLogModelsFirst = new ArrayList<>();
            samUserInfoParam.setAuditTime(orderTableModel.getFinalAuditTime());
            //处理 相关信息
            switch (orderTableModel.getCheckStatus()) {
                //机审拒绝
                case 2:
                    orderCheckLogModels = orderDao.getOrderCheckLogByOrderId(orderTableModel.getId() + "");
                    orderCheckLogModels = orderCheckLogModels.stream().filter(orderCheckLogModel -> StringUtils.isNotBlank(orderCheckLogModel.getReason())).collect(Collectors.toList());
                    samUserInfoParam.setMachineAuditStatus(0);
                    if (CollectionUtils.isNotEmpty(orderCheckLogModels)) {
                        samUserInfoParam.setMachineRefuseReason(orderCheckLogModels.get(orderCheckLogModels.size() - 1).getReason());
                    }

                    break;
                //机审通过 待初审
                case 3:
                    samUserInfoParam.setMachineAuditStatus(1);
                    break;
                //初审拒绝
                case 4:
                    samUserInfoParam.setMachineAuditStatus(1);
                    samUserInfoParam.setFirstAuditStatus(0);
                    //查找相应的标签  存入实体
                    orderCheckLogModels = orderDao.getOrderCheckLogByOrderId(orderTableModel.getId() + "");
                    orderCheckLogModels = orderCheckLogModels.stream().filter(orderCheckLogModel -> orderCheckLogModel.getNode() == 20).collect(Collectors.toList());

                    if (CollectionUtils.isNotEmpty(orderCheckLogModels)) {
                        samUserInfoParam.setFirstAuditReason(orderCheckLogModels.get(orderCheckLogModels.size() - 1).getReason());
                        String tagIds = orderCheckLogModels.get(orderCheckLogModels.size() - 1).getTagIds();
                        if (StringUtils.isNotBlank(tagIds)) {
                            samUserInfoParam.setFirstAuditTags(orderDao.getTagsByIds(tagIds));
                        }
                    }

                    break;
                case 5:
                    //初审通过   人工终审
                    samUserInfoParam.setMachineAuditStatus(1);
                    samUserInfoParam.setFirstAuditStatus(1);
                    //查询  人工初审  相应的标签
                    orderCheckLogModels = orderDao.getOrderCheckLogByOrderId(orderTableModel.getId() + "");
                    orderCheckLogModels = orderCheckLogModels.stream().filter(orderCheckLogModel -> orderCheckLogModel.getNode() == 20).collect(Collectors.toList());

                    if (CollectionUtils.isNotEmpty(orderCheckLogModels)) {
                        samUserInfoParam.setFirstAuditReason(orderCheckLogModels.get(orderCheckLogModels.size() - 1).getReason());
                        String tagIds = orderCheckLogModels.get(orderCheckLogModels.size() - 1).getTagIds();
                        if (StringUtils.isNotBlank(tagIds)) {
                            samUserInfoParam.setFirstAuditTags(orderDao.getTagsByIds(tagIds));
                        }
                    }

                    break;
                case 6:
                    //终审拒绝
                    samUserInfoParam.setMachineAuditStatus(1);
                    samUserInfoParam.setFirstAuditStatus(1);
                    samUserInfoParam.setFinalAuditStatus(0);
                    //查询   初审 终审 相应的标签
                    orderCheckLogModels = orderDao.getOrderCheckLogByOrderId(orderTableModel.getId() + "");

                    checkLogModelsFirst = orderCheckLogModels.
                            stream().
                            filter(orderCheckLogModel -> orderCheckLogModel.getNode() == 20).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(checkLogModelsFirst)) {
                        samUserInfoParam.setFirstAuditReason(checkLogModelsFirst.get(checkLogModelsFirst.size() - 1).getReason());
                        String tagIds = checkLogModelsFirst.get(checkLogModelsFirst.size() - 1).getTagIds();
                        if (StringUtils.isNotBlank(tagIds)) {
                            samUserInfoParam.setFirstAuditTags(orderDao.getTagsByIds(tagIds));
                        }
                    }
                    //分组 终审数据
                    orderCheckLogModels = orderCheckLogModels.
                            stream().
                            filter(orderCheckLogModel -> orderCheckLogModel.getNode() == 21).collect(Collectors.toList());

                    if (CollectionUtils.isNotEmpty(orderCheckLogModels)) {
                        samUserInfoParam.setFinalAuditReason(orderCheckLogModels.get(orderCheckLogModels.size() - 1).getReason());
                        String tagIds = orderCheckLogModels.get(orderCheckLogModels.size() - 1).getTagIds();
                        if (StringUtils.isNotBlank(tagIds)) {
                            samUserInfoParam.setFinalAuditTags(orderDao.getTagsByIds(tagIds));
                        }
                    }
                    break;
                case 7:
                    //处理放贷后逻辑
                    samUserInfoParam.setMachineAuditStatus(1);
                    samUserInfoParam.setFirstAuditStatus(1);
                    samUserInfoParam.setFinalAuditStatus(1);
                    //查询相应的标签
                    orderCheckLogModels = orderDao.getOrderCheckLogByOrderId(orderTableModel.getId() + "");
                    checkLogModelsFirst = orderCheckLogModels.
                            stream().
                            filter(orderCheckLogModel -> orderCheckLogModel.getNode() == 20).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(checkLogModelsFirst)) {
                        samUserInfoParam.setFirstAuditReason(checkLogModelsFirst.get(checkLogModelsFirst.size() - 1).getReason());
                        String tagIds = checkLogModelsFirst.get(checkLogModelsFirst.size() - 1).getTagIds();
                        if (StringUtils.isNotBlank(tagIds)) {
                            samUserInfoParam.setFirstAuditTags(orderDao.getTagsByIds(tagIds));
                        }
                    }
                    orderCheckLogModels = orderCheckLogModels.
                            stream().
                            filter(orderCheckLogModel -> orderCheckLogModel.getNode() == 21).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(orderCheckLogModels)) {
                        samUserInfoParam.setFinalAuditReason(orderCheckLogModels.get(orderCheckLogModels.size() - 1).getReason());
                        String tagIds = orderCheckLogModels.get(orderCheckLogModels.size() - 1).getTagIds();
                        if (StringUtils.isNotBlank(tagIds)) {
                            samUserInfoParam.setFinalAuditTags(orderDao.getTagsByIds(tagIds));
                        }
                    }
                    //查询 是否  放款    有放款 才生成还款 记录
                    FinanceRepayModel financeRepayModel = repayDao.queryRepaymentByOrderNo(orderTableModel.getOrderNum());
                    if (financeRepayModel != null) {

                        List<FinancePayLogModel> financePayLogModels = loanDao.getLoanPayLogByOrderNo(orderTableModel.getOrderNum());
                        if (CollectionUtils.isNotEmpty(financePayLogModels)) {
                            samUserInfoParam.setLoanTime(financePayLogModels.get(0).getCreateTime());
                        }
                        Date payDate = financeRepayModel.getPayDate();
                        if (payDate != null) {
                            samUserInfoParam.setActualPayDate(payDate);
                        }
                        FinanceExtensionModel financeExtensionModel = repayDao.queryFinanceExtensionByOrderNo(orderTableModel.getOrderNum());

                        if (financeExtensionModel == null) {
                            samUserInfoParam.setRepayDate(financeRepayModel.getRepayDate());
                        } else {
                            samUserInfoParam.setRepayDate(financeExtensionModel.getExtensionEnd());
                        }


                        List<FinanceDueOrderModel> orderDueModelList = repayDao.queryLateOrderByOrderNO(orderTableModel.getOrderNum());
                        if (CollectionUtils.isNotEmpty(orderDueModelList)) {

                            samUserInfoParam.setDueCount(orderDueModelList.size());
                            samUserInfoParam.setFinanceDueOrderModels(orderDueModelList);
                            samUserInfoParam.setDueDayCount(orderDueModelList.get(orderDueModelList.size() - 1).getDueDays());

                            String ids = "";
                            List<LinkedHashMap<String, Object>> collectionAccruedRecordModelList = new ArrayList<>();
                            JsonResult jsonResult = collectionClient.queryAccruedRecordByDueId(orderDueModelList.get(orderDueModelList.size() - 1).getId());
                            if (Objects.equals(jsonResult.getCode(), 200)) {
                                collectionAccruedRecordModelList = (List<LinkedHashMap<String, Object>>) jsonResult.getData();
                            }
                            JSONObject.toJSON(jsonResult.getData());

                            for (int i = 0; i < collectionAccruedRecordModelList.size(); i++) {
                                if (StringUtils.isBlank((String) collectionAccruedRecordModelList.get(i).get("tagIds"))) {
                                    continue;
                                }
                                if (StringUtils.isBlank(ids)) {
                                    ids = (String) collectionAccruedRecordModelList.get(i).get("tagIds");
                                } else {
                                    ids = ids + "," + (String) collectionAccruedRecordModelList.get(i).get("tagIds");
                                }
                            }
                            if (StringUtils.isNotBlank(ids)) {
                                JsonResult allTagsJsonResult = collectionClient.queryAllTagsByIds(ids);
                                if (Objects.equals(allTagsJsonResult.getCode(), 200)) {
                                    List<CollectionTag> collectionTagList = (List<CollectionTag>) allTagsJsonResult.getData();
                                    samUserInfoParam.setCollectionTags(collectionTagList);
                                }
                            }


                        } else {
                            samUserInfoParam.setDueCount(0);
                            samUserInfoParam.setDueDayCount(0);
                        }
                    }
                    break;
                case 8:
                    //处理放贷后逻辑
                    samUserInfoParam.setMachineAuditStatus(1);
                    samUserInfoParam.setFirstAuditStatus(1);
                    samUserInfoParam.setFinalAuditStatus(8);
                    //查询相应的标签
                    orderCheckLogModels = orderDao.getOrderCheckLogByOrderId(orderTableModel.getId() + "");
                    checkLogModelsFirst = orderCheckLogModels.
                            stream().
                            filter(orderCheckLogModel -> orderCheckLogModel.getNode() == 20).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(checkLogModelsFirst)) {
                        samUserInfoParam.setFirstAuditReason(checkLogModelsFirst.get(checkLogModelsFirst.size() - 1).getReason());
                        String tagIds = checkLogModelsFirst.get(checkLogModelsFirst.size() - 1).getTagIds();
                        if (StringUtils.isNotBlank(tagIds)) {
                            samUserInfoParam.setFirstAuditTags(orderDao.getTagsByIds(tagIds));
                        }
                    }
                    orderCheckLogModels = orderCheckLogModels.
                            stream().
                            filter(orderCheckLogModel -> orderCheckLogModel.getNode() == 40).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(orderCheckLogModels)) {
                        samUserInfoParam.setFinalAuditReason(orderCheckLogModels.get(orderCheckLogModels.size() - 1).getReason());
                        String tagIds = orderCheckLogModels.get(orderCheckLogModels.size() - 1).getTagIds();
                        if (StringUtils.isNotBlank(tagIds)) {
                            samUserInfoParam.setFinalAuditTags(orderDao.getTagsByIds(tagIds));
                        }
                    }
                    break;
                default:
                    break;


            }
            samUserInfoParams.add(samUserInfoParam);
        }

        return JsonResult.ok(samUserInfoParams.stream().sorted(Comparator.comparing(SamUserInfoParam::getAuditTime).reversed()).collect(Collectors.toList()));
    }

    /**
     * 處理狀態值信息
     *
     * @param orderHistoryDtos
     */
    private List<OrderHistoryDto> processOrderHistoryDtoAuditorStatus(List<OrderHistoryDto> orderHistoryDtos) {
        Map<String, Object> params = new HashMap<>();
        List<OrderHistoryDto> returnHistoryDto = new ArrayList<>();
        List<String> phoneTags = new ArrayList<>();
        Collections.reverse(orderHistoryDtos);
        for (OrderHistoryDto orderHistoryDto : orderHistoryDtos) {

            //去重 判定
            if (CollectionUtils.isEmpty(phoneTags)) {
                phoneTags.add(orderHistoryDto.getMobile());
            } else if (phoneTags.contains(orderHistoryDto.getMobile())) {
                continue;
            } else {
                phoneTags.add(orderHistoryDto.getMobile());
            }

            switch (orderHistoryDto.getAuditorOrderStatus()) {
                case 1:
                    orderHistoryDto.setAuditorStatus("Approving");
                    returnHistoryDto.add(orderHistoryDto);
                    break;
                case 2:
                    orderHistoryDto.setAuditorStatus("Denial");
                    returnHistoryDto.add(orderHistoryDto);
                    break;
                case 3:
                    orderHistoryDto.setAuditorStatus("Approving ");
                    returnHistoryDto.add(orderHistoryDto);
                    break;
                case 4:
                    orderHistoryDto.setAuditorStatus("Denial");
                    returnHistoryDto.add(orderHistoryDto);
                    break;
                case 5:
                    orderHistoryDto.setAuditorStatus("Approving ");
                    returnHistoryDto.add(orderHistoryDto);
                    break;
                case 6:
                    orderHistoryDto.setAuditorStatus("Denial");
                    returnHistoryDto.add(orderHistoryDto);
                    break;
                case 8:
                    orderHistoryDto.setAuditorStatus("LendingPool");
                    returnHistoryDto.add(orderHistoryDto);
                    break;
                case 7:
                    params.put("orderNo", orderHistoryDto.getOrderNo());
                    List<FinanceDueOrderModel> orderDueModelList = repayDao.queryLateOrderList(params);
                    //判定是否是逾期記錄
                    if (orderDueModelList.size() > 0) {
                        //判定是否為已還款
                        if (orderDueModelList.get(orderDueModelList.size() - 1).getDueEnd() == null && orderDueModelList.get(orderDueModelList.size() - 1).getDueDays() <= 30) {
                            orderHistoryDto.setAuditorStatus(orderDueModelList.get(orderDueModelList.size() - 1).getDueDays() + " days overdue ");
                            returnHistoryDto.add(orderHistoryDto);
                        } else if (orderDueModelList.get(orderDueModelList.size() - 1).getDueDays() > 30) {//It  is  property  processed judging
                            CollectionRecordVo collectionRecordModel = collectionClient.getCollectionByDueId(orderDueModelList.get(orderDueModelList.size() - 1).getId());
                            if (collectionRecordModel == null) {
                                orderHistoryDto.setAuditorStatus("not disposed ");
                                returnHistoryDto.add(orderHistoryDto);
                            } else if (collectionRecordModel.getHandleStatus() == 1) {
                                orderHistoryDto.setAuditorStatus("not disposed ");
                                returnHistoryDto.add(orderHistoryDto);
                            } else {
                                orderHistoryDto.setAuditorStatus("disposed");
                                returnHistoryDto.add(orderHistoryDto);
                            }
                        } else {
                            orderHistoryDto.setAuditorStatus(orderDueModelList.get(orderDueModelList.size() - 1).getDueDays() + "days overdue and Complete");
                            returnHistoryDto.add(orderHistoryDto);
                        }
                    } else {//未逾期邏記錄判定是否正常還款

                        FinanceRepayModel financeRepayModel = repayDao.queryRepaymentByOrderNo(orderHistoryDto.getOrderNo());
                        //判定是否為空
                        if (financeRepayModel == null) {
                            orderHistoryDto.setAuditorStatus("inactive");
                            returnHistoryDto.add(orderHistoryDto);
                        } else if (financeRepayModel.getPayStatus() == PayStatus.SUCCESS.num) {

                            orderHistoryDto.setAuditorStatus("complete");
                            returnHistoryDto.add(orderHistoryDto);
                        } else {
                            orderHistoryDto.setAuditorStatus("normal");
                            returnHistoryDto.add(orderHistoryDto);

                        }

                    }
                    break;
                default:
                    break;

            }
            orderHistoryDto.setOrderNo("MN" + orderHistoryDto.getOrderNo() + "PD");


        }

        Collections.reverse(returnHistoryDto);
        return returnHistoryDto;

    }

    @Override
    public List<OrderTableModel> getOrderListByUserId(Long userId) {
        return orderDao.getOrderListByUserId(userId);
    }


    @Override
    public boolean saveCheckResult(String orderNo, Integer node, String rejectReason) {

        //判定是否是  通过 倘若拒绝原因未传  则为未通过
        boolean isPass = StringUtils.isNotBlank(rejectReason) ? !rejectReason.equals("") ? false : true : true;
        //审批人员  写死为系统审批
        String auditorName = "System Approval";
        int machineBaseCount = MechineCheckStatus.BASE.num;
        OrderTableModel orderTableModel = orderDao.queryOrderTableByOrderNo(orderNo);
        Long orderId = orderTableModel.getId();
        List<CheckLogModel> checkLogModels = new ArrayList<>();
        if (isPass) {
            node = MechineCheckStatus.SIX.num;
        }
        //之前节点处理
        //为统一显示
        for (int categoryNum = machineBaseCount + 1; categoryNum < (node); categoryNum++) {
            checkLogModels.add(CheckLogModel.builder().auditorName(auditorName)
                    .node(categoryNum)
                    .reason("")
                    .comment("")
                    .orderId(orderId)
                    .tagIds("")
                    .status(CheckStatusOrder.PASS.num)
                    .result(CheckStatusOrder.PASS.num).build());
        }

        //处理最后一个节点
        //处理通过逻辑
        if (isPass) {
            checkLogModels.add(CheckLogModel.builder().auditorName(auditorName)
                    .node(node)
                    .reason("")
                    .orderId(orderId)
                    .comment("")
                    .tagIds("")
                    .status(CheckStatusOrder.PASS.num)
                    .result(CheckStatusOrder.PASS.num).build());
        }
        //处理拒绝逻辑
        else {
            checkLogModels.add(CheckLogModel.builder().auditorName(auditorName)
                    .node(node)
                    .reason(rejectReason)
                    .comment("")
                    .orderId(orderId)
                    .tagIds("")
                    .status(CheckStatusOrder.REFUSE.num)
                    .result(CheckStatusOrder.REFUSE.num).build());

        }
        //倘若节点不为空  则
        if (CollectionUtils.isNotEmpty(checkLogModels)) {
            int count = orderDao.insertMechineCheck(checkLogModels);
            if (count > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateMechineCheckToOrderByOrderNum(String orderNo, Integer checkStatus) {
        //通过订单编号 修改 订单状态
        Integer count = orderDao.updateMechineCheckToOrderByOrderNum(orderNo, checkStatus);
        if (count > 0) {
            return true;
        }
        return false;

    }

    @Override
    public List<OrderOntherRefuse> queryAllOrderOntherRefuse(String orderNo) {
        return orderOntherRefuseDao.slectAllByOrderNo(orderNo);
    }

    /**
     * 判断后新增存量用户订单表
     * @param orderId
     */
    private void validAndSaveStockUserOrder(String orderId) {
        AppUserOrderInfo appUserOrderInfo = orderDao.getUserOrderByOrderId(orderId);
        if (null == appUserOrderInfo) {
            log.info("user order null, orderId:{}", orderId);
            return;
        }

        Integer count = loanDao.countFinanceLoanByUserId(appUserOrderInfo.getUserId(), PayStatus.SUCCESS.num);
        if (null != count && count.intValue() > 0) {
            log.info("loan success before, no need to save stockUserOrder, orderId:{}", orderId);
            return;
        }

        //保存存量用户订单
        this.saveStockUserOrder(appUserOrderInfo);
        //更新用户订单isStock状态
        orderDao.updateIsStockById(orderId,1);
    }

    /**
     * 新增存量用户订单表记录
     *
     * @param appUserOrderInfo
     */
    private void saveStockUserOrder(AppUserOrderInfo appUserOrderInfo) {
        StockUserOrderModel stockUserOrderModel = StockUserOrderModel.builder().orderNum(appUserOrderInfo.getOrderNum())
                .applyNum(appUserOrderInfo.getApplyNum())
                .userName(appUserOrderInfo.getUserName())
                .userId(appUserOrderInfo.getUserId())
                .mobile(appUserOrderInfo.getUserPhone())
                .sourceType(StockOrderSourceTypeEnum.AUDIT.getSourceType())
                .auditTime(new Date()).build();
        stockUserOrderDao.saveStockUserOrder(stockUserOrderModel);
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

    private static DateParam processDateParam(DateParam dateParam) {

        //更具 日期 傳入判定 初始值
        if (dateParam == null) {
            Date now = new Date();
            String fisrtDayOfMonth = DateUtil.getFisrtDayOfMonth(Integer.valueOf(DateUtil.getStringDate(now, "yyyy"))
                    , Integer.valueOf(DateUtil.getStringDate(now, "MM")));
            String lastDayOfMonth = DateUtil.getLastDayOfMonth(Integer.valueOf(DateUtil.getStringDate(now, "yyyy"))
                    , Integer.valueOf(DateUtil.getStringDate(now, "MM")));
            dateParam = DateParam.builder().startDate(DateUtil.getDate(fisrtDayOfMonth, "yyyyMMdd")).endDate(DateUtil.getDate(lastDayOfMonth, "yyyyMMdd")).build();
        }
        if (dateParam.getStartDate() == null) {
            Date now = new Date();
            String fisrtDayOfMonth = DateUtil.getFisrtDayOfMonth(Integer.valueOf(DateUtil.getStringDate(now, "yyyy"))
                    , Integer.valueOf(DateUtil.getStringDate(now, "MM")));
            dateParam.setStartDate(DateUtil.getDate(fisrtDayOfMonth, "yyyyMMdd"));
        }
        if (dateParam.getEndDate() == null) {
            Date now = new Date();
            String lastDayOfMonth = DateUtil.getLastDayOfMonth(Integer.valueOf(DateUtil.getStringDate(now, "yyyy"))
                    , Integer.valueOf(DateUtil.getStringDate(now, "MM")));
            dateParam.setEndDate(DateUtil.getDate(lastDayOfMonth, "yyyyMMdd"));
        }
        return dateParam;
    }


    @Override
    public int countGetUserOrderByUserId(Long userId) {
        return orderDao.countQueryUserOrderByUserId(userId);
    }

    @Override
    public int countGetNoRepayLoanByUserId(Long userId) {
        return orderDao.countQueryNoRepayLoanByUserId(userId);
    }

    @Override
    public List<Integer> listGetLoanStatusBySameAadhaarAddress(String address) {
        return orderDao.listQueryLoanStatusBySameAadhaarAddress(address);
    }

    @Override
    public List<String> listGetDueOrderNumBySameAadhaarAddress(String address) {
        return orderDao.listQueryDueOrderNumBySameAadhaarAddress(address);
    }

    @Override
    public List<Integer> listGetLoanStatusByOrderNumList(List<String> orderNumList) {
        return orderDao.listQueryLoanStatusByOrderNumList(orderNumList);
    }

    @Override
    public List<String> listGetRepayedDueOrderNumInOrderNumList(List<String> orderNumList) {
        return orderDao.listQueryPaiedDueOrderNumInOrderNumList(orderNumList);
    }

    @Override
    public List<String> listGetCurrentDueOrderNumByPhoneList(List<String> phoneList) {
        return orderDao.listQueryCurrentDueOrderNumByPhoneList(phoneList);
    }

    @Override
    public String getUserLastRePayedLoanOrderNum(Long userId) {
        return orderDao.queryUserLastRePayedLoanOrderNum(userId);
    }

    @Override
    public List<Integer> listGetDueDaysListByOrderNum(String orderNum) {
        return orderDao.listQueryDueDaysListByOrderNum(orderNum);
    }

    @Override
    public List<String> listGetApplyPhonesInPhones(List<String> phoneList) {
        return orderDao.listQueryApplyPhonesInPhones(phoneList);
    }
}