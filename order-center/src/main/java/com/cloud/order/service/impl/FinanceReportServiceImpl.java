package com.cloud.order.service.impl;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.common.Page;
import com.cloud.order.dao.FinanceReportDao;
import com.cloud.order.dao.OrderDao;
import com.cloud.order.model.*;
import com.cloud.order.service.FinanceReportService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: zhujingtao
 * @CreateDate: 2019/2/27
 * 报表管理 服务实现类
 */
@Slf4j
@Service
public class FinanceReportServiceImpl implements FinanceReportService {

    @Autowired
    private FinanceReportDao reportDao;
    @Autowired
    private OrderDao orderDao;

    /**
     * 汇总报表导出
     *
     * @return FinanceReportSumRecordModel 汇总报表字段
     * @Author: zhujingtao
     */
    @Override
    public FinanceReportSumRecordModel querySumRecord() {
        return reportDao.querySumRecord();
    }

    @Override
    public FinanceReportSumRecordModel queryTotalRecord() {


        List<ReportOrderDetailsModel> reportOrderDetailsModels = orderDao.getReportOrderDetails(null);

        //获取分类list集合
        //3000 5000 8000 10000 12000 13000
        List<ReportOrderDetailsModel> seven3000 = reportOrderDetailsModels.stream().
                filter(ReportOrderDetailsModel ->
                        ReportOrderDetailsModel.getAmountType() == 3000 &&
                                ReportOrderDetailsModel.getPeriod() == 7).collect(Collectors.toList());
        List<ReportOrderDetailsModel> seven5000 = reportOrderDetailsModels.stream().
                filter(ReportOrderDetailsModel ->
                        ReportOrderDetailsModel.getAmountType() == 5000 &&
                                ReportOrderDetailsModel.getPeriod() == 7).collect(Collectors.toList());
        List<ReportOrderDetailsModel> seven8000 = reportOrderDetailsModels.stream().
                filter(ReportOrderDetailsModel ->
                        ReportOrderDetailsModel.getAmountType() == 8000 &&
                                ReportOrderDetailsModel.getPeriod() == 7).collect(Collectors.toList());
        List<ReportOrderDetailsModel> seven10000 = reportOrderDetailsModels.stream().
                filter(ReportOrderDetailsModel ->
                        ReportOrderDetailsModel.getAmountType() == 10000 &&
                                ReportOrderDetailsModel.getPeriod() == 7).collect(Collectors.toList());

        List<ReportOrderDetailsModel> seven12000 = reportOrderDetailsModels.stream().
                filter(ReportOrderDetailsModel ->
                        ReportOrderDetailsModel.getAmountType() == 12000 &&
                                ReportOrderDetailsModel.getPeriod() == 7).collect(Collectors.toList());
        List<ReportOrderDetailsModel> seven13000 = reportOrderDetailsModels.stream().
                filter(ReportOrderDetailsModel ->
                        ReportOrderDetailsModel.getAmountType() == 13000 &&
                                ReportOrderDetailsModel.getPeriod() == 7).collect(Collectors.toList());


        List<ReportOrderDetailsModel> fourteen3000 = reportOrderDetailsModels.stream().
                filter(ReportOrderDetailsModel ->
                        ReportOrderDetailsModel.getAmountType() == 3000 &&
                                ReportOrderDetailsModel.getPeriod() == 14).collect(Collectors.toList());
        List<ReportOrderDetailsModel> fourteen5000 = reportOrderDetailsModels.stream().
                filter(ReportOrderDetailsModel ->
                        ReportOrderDetailsModel.getAmountType() == 5000 &&
                                ReportOrderDetailsModel.getPeriod() == 14).collect(Collectors.toList());
        List<ReportOrderDetailsModel> fourteen8000 = reportOrderDetailsModels.stream().
                filter(ReportOrderDetailsModel ->
                        ReportOrderDetailsModel.getAmountType() == 8000 &&
                                ReportOrderDetailsModel.getPeriod() == 14).collect(Collectors.toList());
        List<ReportOrderDetailsModel> fourteen10000 = reportOrderDetailsModels.stream().
                filter(ReportOrderDetailsModel ->
                        ReportOrderDetailsModel.getAmountType() == 10000 &&
                                ReportOrderDetailsModel.getPeriod() == 14).collect(Collectors.toList());

        List<ReportOrderDetailsModel> fourteen12000 = reportOrderDetailsModels.stream().
                filter(ReportOrderDetailsModel ->
                        ReportOrderDetailsModel.getAmountType() == 12000 &&
                                ReportOrderDetailsModel.getPeriod() == 14).collect(Collectors.toList());
        List<ReportOrderDetailsModel> fourteen13000 = reportOrderDetailsModels.stream().
                filter(ReportOrderDetailsModel ->
                        ReportOrderDetailsModel.getAmountType() == 13000 &&
                                ReportOrderDetailsModel.getPeriod() == 14).collect(Collectors.toList());

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
        Integer repaymentSeven3000Count = 0;
        Integer repaymentSeven5000Count =0;
        Integer repaymentSeven8000Count = 0;
        Integer repaymentSeven10000Count = 0;
        Integer repaymentSeven12000Count = 0;
        Integer repaymentSeven13000Count = 0;
        Integer repaymentFourteen3000Count = 0;
        Integer repaymentFourteen5000Count = 0;
        Integer repaymentFourteen8000Count = 0;
        Integer repaymentFourteen10000Count = 0;
        Integer repaymentFourteen12000Count = 0;
        Integer repaymentFourteen13000Count = 0;
        Integer pendingSeven3000Count = 0;
        Integer pendingSeven5000Count = 0;
        Integer pendingSeven8000Count = 0;
        Integer pendingSeven10000Count =0;
        Integer pendingSeven12000Count =0;
        Integer pendingSeven13000Count =0;
        Integer pendingFourteen3000Count = 0;
        Integer pendingFourteen5000Count =0;
        Integer pendingFourteen8000Count = 0;
        Integer pendingFourteen10000Count = 0;
        Integer pendingFourteen12000Count =0;
        Integer pendingFourteen13000Count = 0;

        //获取汇总list信息处理
        BigDecimal loanAmount =
                reportOrderDetailsModels.stream().
                        map(ReportOrderDetailsModel::getLoanAmount).reduce(BigDecimal::add).get();
        Integer loanCount =
                reportOrderDetailsModels.stream().
                        map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
        BigDecimal pendingAmount = reportOrderDetailsModels.stream().
                map(ReportOrderDetailsModel::getPendingAmount).reduce(BigDecimal::add).get();
        Integer pendingCount = reportOrderDetailsModels.stream().
                map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
        BigDecimal repaymentAmount = reportOrderDetailsModels.stream().
                map(ReportOrderDetailsModel::getRepaymentAmount).reduce(BigDecimal::add).get();
        Integer repaymentCount = reportOrderDetailsModels.stream().
                map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();

        if (CollectionUtils.isNotEmpty(seven3000)){
            loanSeven3000Count = seven3000.stream().
                    map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
           repaymentSeven3000Count = seven3000.stream().
                    map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();
             pendingSeven3000Count = seven3000.stream().
                    map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
        }

        if (CollectionUtils.isNotEmpty(seven5000)){
            loanSeven5000Count = seven5000.stream().
                    map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
            repaymentSeven5000Count = seven5000.stream().
                    map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();
            pendingSeven5000Count = seven5000.stream().
                    map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
        }
        if (CollectionUtils.isNotEmpty(seven8000)){
            loanSeven8000Count = seven8000.stream().
                    map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
            repaymentSeven8000Count = seven8000.stream().
                    map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();
            pendingSeven8000Count = seven8000.stream().
                    map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
        }
        if (CollectionUtils.isNotEmpty(seven10000)){
            loanSeven10000Count = seven10000.stream().
                    map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
            repaymentSeven10000Count = seven10000.stream().
                    map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();
            pendingSeven10000Count = seven10000.stream().
                    map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
        }
        if (CollectionUtils.isNotEmpty(seven12000)){
            loanSeven12000Count = seven12000.stream().
                    map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
            repaymentSeven12000Count = seven12000.stream().
                    map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();
            pendingSeven12000Count = seven12000.stream().
                    map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
        }

        if (CollectionUtils.isNotEmpty(seven13000)){
            loanSeven13000Count = seven13000.stream().
                    map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
            repaymentSeven13000Count = seven13000.stream().
                    map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();
            pendingSeven13000Count = seven13000.stream().
                    map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
        }
        if (CollectionUtils.isNotEmpty(fourteen3000)){
            loanFourteen3000Count = fourteen3000.stream().
                    map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
            repaymentFourteen3000Count = fourteen3000.stream().
                    map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();
            pendingFourteen3000Count = fourteen3000.stream().
                    map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
        }
        if (CollectionUtils.isNotEmpty(fourteen5000)){
            loanFourteen5000Count = fourteen5000.stream().
                    map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
            repaymentFourteen5000Count = fourteen5000.stream().
                    map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();
            pendingFourteen5000Count = fourteen5000.stream().
                    map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
        }
        if (CollectionUtils.isNotEmpty(fourteen8000)){
            loanFourteen8000Count = fourteen8000.stream().
                    map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
            repaymentFourteen8000Count = fourteen8000.stream().
                    map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();
            pendingFourteen8000Count = fourteen8000.stream().
                    map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
        }
        if (CollectionUtils.isNotEmpty(fourteen10000)){
            loanFourteen10000Count = fourteen10000.stream().
                    map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
            repaymentFourteen10000Count = fourteen10000.stream().
                    map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();
            pendingFourteen10000Count = fourteen10000.stream().
                    map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
        }
        if (CollectionUtils.isNotEmpty(fourteen12000)){
            loanFourteen12000Count = fourteen12000.stream().
                    map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
            repaymentFourteen12000Count = fourteen12000.stream().
                    map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();
            pendingFourteen12000Count = fourteen12000.stream().
                    map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
        }
        if (CollectionUtils.isNotEmpty(fourteen13000)){
            loanFourteen13000Count = fourteen13000.stream().
                    map(ReportOrderDetailsModel::getLoanCount).reduce(Integer::sum).get();
            repaymentFourteen13000Count = fourteen13000.stream().
                    map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();
            pendingFourteen13000Count = fourteen13000.stream().
                    map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
        }

        FinanceReportSumRecordModel  financeReportSumRecordModel =  reportDao.querySumRecord();

        return FinanceReportSumRecordModel.builder()
                //汇总信息载入
                .loanAmount(loanAmount).loanCount(loanCount)
                .dueInAmount(financeReportSumRecordModel.getDueInAmount())
                .dueInCount(pendingCount-repaymentCount)
                .payableAmount(repaymentAmount)
                .payableCount(repaymentCount)
                //放款信息载入
                .loanFourteen3000Count(loanFourteen3000Count)
                .loanFourteen5000Count(loanFourteen5000Count)
                .loanFourteen8000Count(loanFourteen8000Count)
                .loanFourteen10000Count(loanFourteen10000Count)
                .loanFourteen12000Count(loanFourteen12000Count)
                .loanFourteen13000Count(loanFourteen13000Count)
                .loanSeven3000Count(loanSeven3000Count)
                .loanSeven5000Count(loanSeven5000Count)
                .loanSeven8000Count(loanSeven8000Count)
                .loanSeven10000Count(loanSeven10000Count)
                .loanSeven12000Count(loanSeven12000Count)
                .loanSeven13000Count(loanSeven13000Count)
                //待还信息载入
                .dueFourteen3000Count(pendingFourteen3000Count-repaymentFourteen3000Count)
                .dueFourteen5000Count(pendingFourteen5000Count-repaymentFourteen5000Count)
                .dueFourteen8000Count(pendingFourteen8000Count-repaymentFourteen8000Count)
                .dueFourteen10000Count(pendingFourteen10000Count-repaymentFourteen10000Count)
                .dueFourteen12000Count(pendingFourteen12000Count-repaymentFourteen12000Count)
                .dueFourteen13000Count(pendingFourteen13000Count-repaymentFourteen13000Count)
                .dueSeven3000Count(pendingSeven3000Count-repaymentSeven3000Count)
                .dueSeven5000Count(pendingSeven5000Count-repaymentSeven5000Count)
                .dueSeven8000Count(pendingSeven8000Count-repaymentSeven8000Count)
                .dueSeven10000Count(pendingSeven10000Count-repaymentSeven10000Count)
                .dueSeven12000Count(pendingSeven12000Count-repaymentSeven12000Count)
                .dueSeven13000Count(pendingSeven13000Count-repaymentSeven13000Count)
                //已还信息载入
                .payableFourteen3000Count(repaymentFourteen3000Count)
                .payableFourteen5000Count(repaymentFourteen5000Count)
                .payableFourteen8000Count(repaymentFourteen8000Count)
                .payableFourteen10000Count(repaymentFourteen10000Count)
                .payableFourteen12000Count(repaymentFourteen12000Count)
                .payableFourteen13000Count(repaymentFourteen13000Count)
                .payableSeven3000Count(repaymentSeven3000Count)
                .payableSeven5000Count(repaymentSeven5000Count)
                .payableSeven8000Count(repaymentSeven8000Count)
                .payableSeven10000Count(repaymentSeven10000Count)
                .payableSeven12000Count(repaymentSeven12000Count)
                .payableSeven13000Count(repaymentSeven13000Count)
                .build();

    }

    /**
     * 查询 日期报表信息
     *
     * @return List<FinanceReportRecordModel>  日期报表查询
     * @Author: zhujingtao
     */
    @Override
    public PageInfo<FinanceReportRecordModel> queryReport(Map<String, Object> params) {
        //获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        List<FinanceReportRecordModel> financeReportRecordModelList = reportDao.queryReport(params);
        PageInfo<FinanceReportRecordModel> pageInfo = new PageInfo<>(financeReportRecordModelList);
        return pageInfo;
    }

    @Override
    public JsonResult queryReportMessage(Map<String, Object> params) {
        Integer page = MapUtils.getInteger(params, "page");
        Integer limit = MapUtils.getInteger(params, "limit");
        if (page == null || limit == null) {
            return JsonResult.ok();
        }
        PageHelper.startPage(page, limit);
        List<Map<String, Object>>   pageDate=   orderDao.getReportOrderDate(params);
        PageInfo<Map<String, Object>> pageDatePage = new PageInfo<>(pageDate);
        List<FinanceReportRecordModel> orderRepayReportRecordModels = new ArrayList<>();
        List<FinanceReportRecordModel> returnList = new ArrayList<>();
        if (CollectionUtils.isEmpty(pageDatePage.getList())){
            return   JsonResult.ok(returnList,returnList.size());
        }
        List<ReportOrderDetailsModel> reportOrderDetailsModels = orderDao.getReportOrderDetailsByDates(pageDatePage.getList());
        Map<Date, List<ReportOrderDetailsModel>> reportGoup = reportOrderDetailsModels.stream().collect(Collectors.groupingBy(ReportOrderDetailsModel::getReportDate));
        //获取 报表键
        Set<Date> reportDates = reportGoup.keySet();
        List<FinanceExtensionReportParam> financeExtensionReportParams = orderDao.getExtensionCount();

        for (Date reportDate : reportDates) {
            //定義傳參變量
            BigDecimal repaymentAmount;
            Integer repaymentCount;
            BigDecimal pendingAmount;
            BigDecimal pendingFourteenAmount;
            BigDecimal pendingSevenAmount;
            BigDecimal repaymentFourteenAmount;
            BigDecimal repaymentSevenAmount;
            Integer pendingCount;
            Integer pendingSevenCount;
            Integer pendingFourteenCount;
            Integer repaymentSevenCount;
            Integer repaymentFourteenCount;
            List<Double> amountTypeList = Arrays.asList(3000.0,5000.0,8000.0,10000.0,12000.0,13000.0);
            //匯總信息無需 判定篩選是否成功  直接處理
            List<FinanceReportRecordTypeParms> financeReportRecordType=new ArrayList<>();
            repaymentAmount =
                    reportGoup.get(reportDate).stream().
                            map(ReportOrderDetailsModel::getRepaymentAmount).reduce(BigDecimal::add).get();

            repaymentCount =
                    reportGoup.get(reportDate).stream().
                            map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();
            pendingAmount =
                    reportGoup.get(reportDate).stream().
                            map(ReportOrderDetailsModel::getPendingAmount).reduce(BigDecimal::add).get();
            pendingCount =
                    reportGoup.get(reportDate).stream().
                            map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();

            //獲取 初步處理 參數
            List<ReportOrderDetailsModel> fourteenList =
                    reportGoup.get(reportDate).stream().
                            filter(ReportOrderDetailsModel -> ReportOrderDetailsModel.getPeriod() == 14).collect(Collectors.toList());
            List<ReportOrderDetailsModel> sevenList =
                    reportGoup.get(reportDate).stream().
                            filter(ReportOrderDetailsModel -> ReportOrderDetailsModel.getPeriod() == 7).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(fourteenList)) {

                pendingFourteenAmount =
                        fourteenList.stream()
                                .map(ReportOrderDetailsModel::getPendingAmount).reduce(BigDecimal::add).get();
                pendingFourteenCount =
                        fourteenList.stream().
                                map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
                repaymentFourteenAmount =
                        fourteenList.stream()
                                .map(ReportOrderDetailsModel::getPendingAmount).reduce(BigDecimal::add).get();
                repaymentFourteenCount =
                        fourteenList.stream().
                                map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();





            } else {
                pendingFourteenAmount = new BigDecimal(0);
                pendingFourteenCount = 0;
                repaymentFourteenAmount = new BigDecimal(0);
                repaymentFourteenCount = 0;
            }
            if (CollectionUtils.isNotEmpty(sevenList)) {
                pendingSevenAmount =
                        sevenList.stream()
                                .map(ReportOrderDetailsModel::getPendingAmount).reduce(BigDecimal::add).get();
                repaymentSevenAmount =
                        sevenList.stream()
                                .map(ReportOrderDetailsModel::getPendingAmount).reduce(BigDecimal::add).get();

                pendingSevenCount =
                        sevenList.stream().
                                map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();

                repaymentSevenCount =
                        sevenList.stream().
                                map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();
            } else {
                pendingSevenAmount = new BigDecimal(0);
                repaymentSevenAmount = new BigDecimal(0);

                pendingSevenCount = 0;
                repaymentSevenCount = 0;
            }

            Integer exporeCount =0;
           List<FinanceExtensionReportParam> financeExtensionReportParams1=  financeExtensionReportParams.stream().filter(financeExtensionReportParam ->
                    financeExtensionReportParam.getReportDate().equals(reportDate)).collect(Collectors.toList());
           if (CollectionUtils.isNotEmpty(financeExtensionReportParams1)){
               //分组后 只可能显示一条
               exporeCount=financeExtensionReportParams1.get(0).getExporeCount();
           }
            //载入 列表逻辑
            for (Double amountType:amountTypeList){
                BigDecimal pendingFourteenAmountType = new BigDecimal(0.0);
                Integer  pendingFourteenCountType =  0;
                BigDecimal  repaymentFourteenAmountType= new BigDecimal(0.0);
                Integer  repaymentFourteenCountType= 0;
                BigDecimal pendingSevenAmountType = new BigDecimal(0.0);
                Integer  pendingSevenCountType =  0;
                BigDecimal  repaymentSevenAmountType= new BigDecimal(0.0);
                Integer  repaymentSevenCountType= 0;
                List<ReportOrderDetailsModel> fourteenListType= new ArrayList<>();
                List<ReportOrderDetailsModel> sevenListType= new ArrayList<>();
                if (CollectionUtils.isNotEmpty(fourteenList)){
                    fourteenListType=   fourteenList
                            .stream().filter(reportOrderDetailsModel ->
                                    reportOrderDetailsModel
                                            .getAmountType()
                                            .equals(amountType))
                            .collect(Collectors.toList());
                }

                if (CollectionUtils.isNotEmpty(sevenList)){
                    sevenListType=   sevenList
                            .stream().filter(reportOrderDetailsModel ->
                                    reportOrderDetailsModel
                                            .getAmountType()
                                            .equals(amountType))
                            .collect(Collectors.toList());
                }


                if (CollectionUtils.isNotEmpty(fourteenListType)){

                    pendingFourteenAmountType =
                            fourteenListType.stream()
                                    .map(ReportOrderDetailsModel::getPendingAmount).reduce(BigDecimal::add).get();
                    pendingFourteenCountType =
                            fourteenListType.stream().
                                    map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
                    repaymentFourteenAmountType =
                            fourteenListType.stream()
                                    .map(ReportOrderDetailsModel::getRepaymentAmount).reduce(BigDecimal::add).get();
                    repaymentFourteenCountType =
                            fourteenListType.stream().
                                    map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();

                }
                if (CollectionUtils.isNotEmpty(sevenListType)){

                    pendingSevenAmountType =
                            sevenListType.stream()
                                    .map(ReportOrderDetailsModel::getPendingAmount).reduce(BigDecimal::add).get();
                    pendingSevenCountType =
                            sevenListType.stream().
                                    map(ReportOrderDetailsModel::getPendingCount).reduce(Integer::sum).get();
                    repaymentSevenAmountType =
                            sevenListType.stream()
                                    .map(ReportOrderDetailsModel::getRepaymentAmount).reduce(BigDecimal::add).get();
                    repaymentSevenCountType =
                            sevenListType.stream().
                                    map(ReportOrderDetailsModel::getRepaymentCount).reduce(Integer::sum).get();

                }

                financeReportRecordType.add(
                        FinanceReportRecordTypeParms.builder()
                                .amountType(amountType)
                                .principalFourteenAmount(pendingFourteenAmountType)
                                .principalSevenAmount(pendingSevenAmountType)
                                .dueFourteenCount(pendingFourteenCountType)
                                .dueSevenCount(pendingSevenCountType)
                                .paymentsFourteenCount(repaymentFourteenCountType)
                                .paymentsSevenCount(repaymentSevenCountType)
                                .repaymentFourteenAmount(repaymentFourteenAmountType)
                                .repaymentSevenAmount(repaymentSevenAmountType)
                                .build()
                );
            }

            if (pendingCount > 0 || exporeCount > 0 || repaymentCount > 0) {

                orderRepayReportRecordModels.add(
                        FinanceReportRecordModel.builder().reportDate(reportDate).exporeCount(exporeCount)
                                .repaymentAmount(repaymentAmount)
                                .paymentsCount(repaymentCount)
                                .dueCount(pendingCount)
                                .principalAmount(pendingAmount)
                                .principalFourteenAmount(pendingFourteenAmount)
                                .principalSevenAmount(pendingSevenAmount)
                                .dueSevenCount(pendingSevenCount)
                                .dueFourteenCount(pendingFourteenCount)
                                .paymentsSevenCount(repaymentSevenCount)
                                .paymentsFourteenCount(repaymentFourteenCount)
                                .repaymentFourteenAmount(repaymentFourteenAmount)
                                .repaymentSevenAmount(repaymentSevenAmount)
                                .financeReportRecordType(financeReportRecordType)
                                .build()

                );
            }


        }
        Integer sortStatus= MapUtils.getInteger(params,"sortStatus");
        if (sortStatus != null && sortStatus == 1) {
            orderRepayReportRecordModels = orderRepayReportRecordModels
                    .stream()
                    .sorted(Comparator.comparing(FinanceReportRecordModel::getReportDate).reversed()).collect(Collectors.toList());
        } else if (sortStatus != null && sortStatus == 2) {
            orderRepayReportRecordModels = orderRepayReportRecordModels
                    .stream()
                    .sorted(Comparator.comparing(FinanceReportRecordModel::getReportDate)).collect(Collectors.toList());
        }

        if (sortStatus==null|| (sortStatus!=1 && sortStatus!=2 )){
            orderRepayReportRecordModels=orderRepayReportRecordModels.stream()
                    .sorted(Comparator.comparing(FinanceReportRecordModel::getReportDate).reversed()).collect(Collectors.toList());
        }



        return JsonResult.ok(orderRepayReportRecordModels,(int) pageDatePage.getTotal()  );
    }
}
