package com.cloud.collection.service.impl;

import com.cloud.collection.dao.FinanceRepayDao;
import com.cloud.collection.model.FinanceDueOrderModel;
import com.cloud.collection.model.FinancePayLogModel;
import com.cloud.collection.model.FinanceRepayModel;
import com.cloud.collection.model.LaterDetailsModel;
import com.cloud.collection.service.FinanceRepayService;
import com.cloud.common.utils.StringUtils;
import com.cloud.model.common.LoanTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    /**
     * 逾期订单  催收调用  修改
     *
     * @param params
     * @return
     */
    @Override
    public List<FinanceDueOrderModel> queryDueOrderList(Map<String, Object> params) {

        //查询对应的列表集合
        List<FinanceDueOrderModel> financeReportRecordModelList = repayDao.queryLateOrderList(params);
        String loanStatus = MapUtils.getString(params, "loanStatus");
        financeReportRecordModelList = this.processLoanStatus(financeReportRecordModelList, loanStatus);
        this.outFinanceDueOrderModel(financeReportRecordModelList);
        return financeReportRecordModelList;
    }

    @Override
    public FinanceRepayModel getOneFinanceRepayByParams(Map<String, Object> params) {
        return repayDao.getOneFinanceRepayByParams(params);
    }

    /**
     * 逾期订单 详情列表查询
     *
     * @param dueId
     * @return
     */

    @Override
    public List<LaterDetailsModel> queryLaterDetails(String dueId) {
        //通过 逾期ID 查出对应的订单号
        FinanceDueOrderModel financeDueOrderModel = repayDao.queryLateOrderByDueId(dueId);
        LaterDetailsModel laterDetailsModel = repayDao.queryLaterDetailsByOrderNo(financeDueOrderModel.getOrderNo());
        List<LaterDetailsModel> laterDetailsModels = new ArrayList<>();
        //获取是否展期  字段的值
        if (laterDetailsModel.getIsExtension() != null && laterDetailsModel.getIsExtension() == 1) {
            //设置状态为展期状态
            laterDetailsModel.setLoanStatus("Extension");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //获取展期金额
            List<FinancePayLogModel> financePayLogModels = repayDao.queryPayLogByOrderNoAndPayDateAndLoanType(laterDetailsModel.getOrderNo(), simpleDateFormat.format(laterDetailsModel.getDueEnd()), LoanTypeEnum.EXTENSION.getCode());
            if (financePayLogModels.size() > 0) {
                laterDetailsModel.setExtensionAmount(financePayLogModels.stream().map(FinancePayLogModel::getAmount).reduce(BigDecimal::add).get());
            }
            FinanceRepayModel financeRepayModel = repayDao.queryRepaymentByOrderNo(financeDueOrderModel.getOrderNo());
            if (financeRepayModel!=null
                    && financeRepayModel.getActualAmount() != null
                    && financeRepayModel.getActualAmount().doubleValue() > 0) {
                laterDetailsModel.setActualAmount(financeRepayModel.getActualAmount());
                laterDetailsModel.setLoanStatus("Paid");

                if (laterDetailsModel.getDueEnd() == null) {
                    laterDetailsModel.setDueEnd(financeRepayModel.getPayDate());
                }
            }
        } else if (laterDetailsModel.getDueEnd() == null) {
            laterDetailsModel.setLoanStatus("Unpaid");
        } else if (laterDetailsModel.getDueEnd() != null) {
            //已还款列表的查询
            laterDetailsModel.setLoanStatus("Paid");
             FinanceRepayModel  financeRepayModel = repayDao.queryRepaymentByOrderNo(financeDueOrderModel.getOrderNo());
            if (financeRepayModel!=null) {
                laterDetailsModel.setActualAmount(financeRepayModel.getActualAmount());
            }

        }
        laterDetailsModels.add(laterDetailsModel);


        return laterDetailsModels;
    }

    /**
     * 状态值判定
     *
     * @param financeDueOrderModels 逾期订单列表
     * @param loanStatus            状态值
     * @return
     */

    private List<FinanceDueOrderModel> processLoanStatus(List<FinanceDueOrderModel> financeDueOrderModels, String loanStatus) {
        //控制参数 记录
        if (financeDueOrderModels == null || financeDueOrderModels.size() < 0
                || !StringUtils.isNotBlank(loanStatus)) {
            return financeDueOrderModels;
        }

        List<FinanceDueOrderModel>
                financeDueOrderModelList = new ArrayList<>();
        switch (loanStatus) {
            //未
            case "1":
                return financeDueOrderModels.stream().filter(financeDueOrderModel -> financeDueOrderModel.getDueEnd() == null).collect(Collectors.toList());
            case "2":

                for (FinanceDueOrderModel financeDueOrderModel : financeDueOrderModels) {
                    if (financeDueOrderModel.getDueEnd() != null && financeDueOrderModel.getIsExtension() == 0) {
                        //正常结清
                        financeDueOrderModelList.add(financeDueOrderModel);
                    } else if (financeDueOrderModel.getDueEnd() != null && financeDueOrderModel.getIsExtension() == 1) {
                        //展期后还款

                        FinanceRepayModel financeRepayModel = repayDao.queryRepaymentByOrderNo(    financeDueOrderModel.getOrderNo().replaceAll("MN","").replaceAll("PD",""));
                        if (financeRepayModel!=null&& financeRepayModel.getActualAmount() != null) {
                            if (financeRepayModel.getActualAmount().doubleValue() > 0.0) {
                                financeDueOrderModelList.add(financeDueOrderModel);
                            }
                        }
                    }


                }
                return financeDueOrderModelList;
            //逾期并展期
            case "3":

                financeDueOrderModels =
                        financeDueOrderModels.stream().
                                filter(financeDueOrderModel ->
                                        financeDueOrderModel.getDueEnd()
                                                != null && financeDueOrderModel.getIsExtension() == 1).collect(Collectors.toList());
                for (FinanceDueOrderModel financeDueOrderModel : financeDueOrderModels) {
                    FinanceRepayModel financeRepayModel = repayDao.queryRepaymentByOrderNo(    financeDueOrderModel.getOrderNo().replaceAll("MN","").replaceAll("PD",""));
                    if (financeRepayModel!=null) {
                        if (financeRepayModel.getActualAmount() == null ||
                                financeRepayModel.getActualAmount().doubleValue() == 0.0) {
                            financeDueOrderModelList.add(financeDueOrderModel);
                        }
                    }

                }
                return financeDueOrderModelList;
        }

        return null;

    }

    /**
     * 输出 状态值增加
     *
     * @param financeDueOrderModels
     */
    private void outFinanceDueOrderModel(List<FinanceDueOrderModel> financeDueOrderModels) {

        if (CollectionUtils.isEmpty(financeDueOrderModels)) {
            return;
        }
        for (FinanceDueOrderModel financeDueOrderModel : financeDueOrderModels) {
            //判定是否正在逾期中
            if (financeDueOrderModel.getDueEnd() == null) {
                financeDueOrderModel.setLoanStatus("Unpaid");
            } else if (financeDueOrderModel.getIsExtension() == 1) {
                FinanceRepayModel  financeRepayModel = repayDao.queryRepaymentByOrderNo(    financeDueOrderModel.getOrderNo().replaceAll("MN","").replaceAll("PD",""));
                if (financeRepayModel!=null) {
                    if (financeRepayModel.getActualAmount() == null
                            || financeRepayModel.getActualAmount().doubleValue() == 0.0) {
                        financeDueOrderModel.setLoanStatus("Extension");
                    }
                }
            }
        }
        financeDueOrderModels.stream().filter(financeDueOrderModel -> !financeDueOrderModel.getLoanStatus().equals("Unpaid")
                && !financeDueOrderModel.getLoanStatus().equals("Extension")).forEach(financeDueOrderModel -> financeDueOrderModel.setLoanStatus("Paid"));
    }
}

