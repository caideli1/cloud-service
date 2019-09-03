package com.cloud.order.service.impl;

import com.cloud.common.component.utils.DateUtil;
import com.cloud.common.component.utils.MathUtil;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.utils.StringUtils;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.order.constant.FinanceAccountDelete;
import com.cloud.order.constant.FinanceAccountStation;
import com.cloud.order.dao.FinanceRepayDao;
import com.cloud.order.model.*;
import com.cloud.order.service.FinanceRepayService;
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
 * @Author: zhujingtao
 * @CreateDate: 2019/2/27
 * 放款管理 服务实现类
 */
@Slf4j
@Service
public class FinanceRepayServiceImpl implements FinanceRepayService {

    @Autowired(required = false)
    private FinanceRepayDao repayDao;

    @Override
    public List<ReportCollectionManModel> queryReportCollectionManModel(Map<String, Object> params) {
        String startDate = MapUtils.getString(params, "startDate");
        String endDate = MapUtils.getString(params, "endDate");
        DateParam dateParam = DateParam.builder().build();
        List<ReportCollectionManModel> reportCollectionManList = repayDao.queryReportCollectionManModel(params);
        if (StringUtils.isNotBlank(startDate)) {
            dateParam.setStartDate(DateUtil.getDate(startDate, "yyyy-MM-dd"));
        }
        if (StringUtils.isNotBlank(endDate)) {
            dateParam.setEndDate(DateUtil.getDate(endDate, "yyyy-MM-dd"));
        }
        //判定是否是月 報表
        if (MapUtils.getInteger(params, "isMonth") == 1) {
            //月報表处理
            List<ReportCollectionManModel> returnMonthList = new ArrayList<>();
            Map<String, List<ReportCollectionManModel>>
                    groupMonthList = reportCollectionManList.stream()
                    .collect(Collectors.groupingBy(reportCollectionManModel ->
                            DateUtil.getStringDate(reportCollectionManModel.getReportDate(), "yyyyMM")));
            //對月分組  若是 月 則 直接返回
            for (Map.Entry<String, List<ReportCollectionManModel>> monthMap : groupMonthList.entrySet()) {
                List<ReportCollectionManModel> monthList = monthMap.getValue();
                //獲取 日期
                Date reportDate = DateUtil.getDate(monthMap.getKey(), "yyyyMM");
                Map<String, List<ReportCollectionManModel>> collectorMapList = monthList.stream().collect(
                        Collectors.groupingBy(ReportCollectionManModel::getCollectorName));
                for (Map.Entry<String, List<ReportCollectionManModel>> collectorMap : collectorMapList.entrySet()) {
                    String collector = collectorMap.getKey();
                    List<ReportCollectionManModel> collectorList = collectorMap.getValue();
                    BigDecimal payAmount = collectorList.stream().map(ReportCollectionManModel::getPayAmount)
                            .reduce(BigDecimal::add).get();
                    BigDecimal repayAmount = collectorList.stream().map(ReportCollectionManModel::getRepayAmount)
                            .reduce(BigDecimal::add).get();
                    Integer followUpNum = collectorList.stream().map(ReportCollectionManModel::getFollowUpNum)
                            .reduce(Integer::sum).get();
                    returnMonthList.add(ReportCollectionManModel.builder().reportDate(reportDate)
                            .collectorName(collector)
                            .payAmount(payAmount)
                            .repayAmount(repayAmount)
                            .followUpNum(followUpNum)
                            .collectionPre(repayAmount.doubleValue() == 0 ? 0 :
                                    MathUtil.romand(payAmount.doubleValue() * 100
                                            / repayAmount.doubleValue(), 2))
                            .build());

                }
            }
            return returnMonthList;
        }

        if (CollectionUtils.isNotEmpty(reportCollectionManList)) {
            reportCollectionManList.stream().forEach(reportCollectionManModel -> {
                reportCollectionManModel.setCollectionPre(reportCollectionManModel
                        .getRepayAmount().doubleValue() == 0 ? 0 : MathUtil.romand(reportCollectionManModel
                        .getPayAmount().doubleValue() * 100 / reportCollectionManModel
                        .getRepayAmount().doubleValue(), 2));
            });
        }


        return repayDao.queryReportCollectionManModel(params);
    }


    /**
     * 查找已还款列表
     *
     * @param params 根据前端传值 查出已还款对应值
     * @return 已还款 集合
     * @Author: zhujingtao
     */
    @Override
    public PageInfo<FinanceRepayModel> queryRepaymentPaidList(Map<String, Object> params) {
        //获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        //查询对应的列表集合
        List<FinanceRepayModel> financeReportRecordModelList = repayDao.queryRepaymentPaidList(params);
        PageInfo<FinanceRepayModel> pageInfo = new PageInfo<>(financeReportRecordModelList);
        return pageInfo;
    }

    /**
     * 查找未还款列表
     *
     * @param params 根据前端传值 查出未还款对应值
     * @return 未还款 集合
     * @Author: zhujingtao
     */
    @Override
    public PageInfo<FinanceRepayModel> queryRepaymentUnpaidList(Map<String, Object> params) {
        //获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        //查询对应的列表集合
        List<FinanceRepayModel> financeReportRecordModelList = repayDao.queryRepaymentUnpaidList(params);
        PageInfo<FinanceRepayModel> pageInfo = new PageInfo<>(financeReportRecordModelList);
        return pageInfo;
    }

    /**
     * 查找还款失败列表
     *
     * @param params 根据前端传值 查出未还款对应值
     * @return 还款失败 集合
     * @Author: zhujingtao
     */
    @Override
    public PageInfo<FinanceRepayModel> queryRepaymentFailedList(Map<String, Object> params) {
        //获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        //查询对应的列表集合
        List<FinanceRepayModel> financeReportRecordModelList = repayDao.queryRepaymentFailedList(params);
        return new PageInfo<>(financeReportRecordModelList);
    }

    /**
     * 查找逾期记录
     *
     * @param params 根据前端传值 查找逾期记录
     * @return 查找逾期记录 集合
     * @Author: zhujingtao
     */
    @Override
    public PageInfo<FinanceDueOrderModel> queryLateOrderList(Map<String, Object> params) {
        //获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        //查询对应的列表集合
        List<FinanceDueOrderModel> financeReportRecordModelList = repayDao.queryLateOrderList(params);

        PageInfo<FinanceDueOrderModel> pageInfo = new PageInfo<>(financeReportRecordModelList);
        return pageInfo;
    }

    /**
     * 删除制定ID 的账户表
     *
     * @return 返回前端 信息
     */
    @Override
    public JsonResult deleteAccountManager(String id) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("id", id);
        params.put("isDelete", FinanceAccountDelete.DELETE.num);

        int num = repayDao.updateAccountManager(params);
        if (num > 0) {
            return JsonResult.ok();
        }

        return JsonResult.errorMsg("删除少于一条记录");
    }

    /**
     * 新增账户表
     *
     * @param params 根据前端传值 查出未还款对应值
     * @return 返回前端 信息
     * @Author: zhujingtao
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public JsonResult insertAccountManager(Map<String, Object> params) {
        //查找是否拥有支付顺序
        if (params.get("payOrder") == null || params.get("payOrder").equals("")) {
            //默认支付排序
            params.put("payOrder", "0");
        }

        int payNum = 0;
        //排除默认值
        if (!params.get("payOrder").equals("0")) {
            //查询支付顺序是否存在
            payNum = repayDao.selectAccountByPayOrder(
                    Integer.parseInt((String) params.get("payOrder")));
        }
        if (payNum > 0) {
            return JsonResult.errorMsg("当前排序重复, 请重新设置");
        }

        int insertNum = repayDao.insertAccountManager(params);
        if (insertNum > 0) {
            return JsonResult.ok();
        }

        return JsonResult.errorMsg("保存记录低于一条");
    }

    /**
     * 修改账户表
     *
     * @param params 前端传值
     * @return 返回前端 信息
     * @Author: zhujingtao
     */
    @Override
    public JsonResult updateAccountManager(Map<String, Object> params) {
        if (params.get("id") == null || params.get("id").equals("")) {
            return JsonResult.errorMsg("ID未传入");
        }

        int num = repayDao.updateAccountManager(params);
        if (num > 0) {
            return JsonResult.ok();
        }

        return JsonResult.errorMsg("修改成功记录低于一条");
    }

    /**
     * 启用或停用
     *
     * @param params 前端传值
     * @return 返回前端 信息
     * @Author: zhujingtao
     */
    @Override
    public JsonResult updateAccountStation(Map<String, Object> params) {
        //前端传值判定
        if (params.get("id") == null) {
            return JsonResult.errorMsg("ID未传入");
        }
        if (params.get("station") == null) {
            return JsonResult.errorMsg("标识未传入");
        }
        if ((Integer) params.get("station") != FinanceAccountStation.DOWN.num &&
                (Integer) params.get("station") != FinanceAccountStation.UP.num) {
            return JsonResult.errorMsg("标识传入参数错误");
        }

        int num = repayDao.updateAccountManager(params);
        if (num > 0) {
            return JsonResult.ok();
        }

        if ((Integer) params.get("station") == FinanceAccountStation.DOWN.num) {
            return JsonResult.errorMsg("停用失败");
        }

        if ((Integer) params.get("station") == FinanceAccountStation.UP.num) {
            return JsonResult.errorMsg("启动失败");
        }

        return JsonResult.errorMsg("请检查传参");
    }

    /**
     * 查找 账户列表
     *
     * @param params 传入名称
     * @return 账户列表查询
     * @Author: zhujingtao
     */
    @Override
    public PageInfo<FinanceAccountManagerModel> selectAccountList(Map<String, Object> params) {
        //获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        //查询对应的列表集合
        List<FinanceAccountManagerModel> financeAccountManagerModels = repayDao.selectAccountList(params);
        PageInfo<FinanceAccountManagerModel> pageInfo = new PageInfo<>(financeAccountManagerModels);
        return pageInfo;
    }


    /**
     * 查询所有的账户名列表
     *
     * @return 账户名列表
     * @Author: zhujingtao
     */
    @Override
    public List<String> selectAllAccountName() {
        return repayDao.selectAllAccountName();
    }

    @Override
    public List<FinanceRepayModel> queryFinanceExtensionList(Map<String, Object> params) {
      List<FinanceRepayModel>  queryRepaymentFailedList= repayDao.queryFinanceExtensionList(params);
      return queryRepaymentFailedList;
    }

}

