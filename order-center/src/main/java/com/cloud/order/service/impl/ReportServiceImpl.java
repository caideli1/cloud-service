package com.cloud.order.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.cloud.common.utils.ExcelUtil;
import com.cloud.model.collection.CollectionRecordVo;
import com.cloud.model.pay.FinancePayLogModel;
import com.cloud.model.product.FinanceLoanModel;
import com.cloud.order.constant.ReportTypeEnum;
import com.cloud.order.dao.*;
import com.cloud.order.model.*;
import com.cloud.order.model.req.ReportExportReq;
import com.cloud.order.service.FinanceRepayService;
import com.cloud.order.service.ReportService;
import com.cloud.service.feign.collection.CollectionClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 报表服务接口实现
 *
 * @author danquan.miao
 * @date 2019/5/6 0006
 * @since 1.0.0
 */
@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired(required = false)
    private ReportQueryParamsDao reportQueryParamsDao;

    @Autowired(required = false)
    private ReportExportParamsDao reportExportParamsDao;

    @Autowired(required = false)
    private FinanceLoanDao financeLoanDao;

    @Autowired(required = false)
    private FinanceReportDao reportDao;

    @Autowired(required = false)
    private FinanceRepayService repayService;

    @Autowired(required = false)
    private FinanceRepayDao repayDao;

    @Autowired(required = false)
    private CollectionClient collectionClient;

    private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public ParamModel queryParamsByType(Integer dataType) {
        ParamModel paramModel = new ParamModel();
        if (null == dataType || dataType.intValue() < 1) {
            log.info("invalid param error. dataType:{}", dataType);
            return paramModel;
        }

        List<QueryParamModel> queryParamList = reportQueryParamsDao.selectQueryParamsByType(dataType);
        List<ExportParamModel> exportParamList = reportExportParamsDao.selectExportParamsByType(dataType);

        paramModel.setQueryParamList(queryParamList);
        paramModel.setExportParamList(exportParamList);
        return paramModel;
    }

    @Override
    public void exportByType(ReportExportReq reportExportReq, HttpServletResponse response) {
        if (null == reportExportReq || null == reportExportReq.getDataType() || null == reportExportReq.getExportParamsMap()) {
            log.info("invalid param error. reportExportReq:{}", JSON.toJSONString(reportExportReq));
            return;
        }

        Integer dataType = reportExportReq.getDataType();
        String fileName =  ReportTypeEnum.getByCode(dataType) + DATE_TIME_FORMAT.format(new Date());
        Map<String, Object> queryParamsMap = reportExportReq.getQueryParamsMap();
        Map<String, String> exportParamsMap = reportExportReq.getExportParamsMap();

        try (BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream())) {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet = new Sheet(1, 0);
            sheet.setHead(ExcelUtil.createListStringHead(exportParamsMap.values()));

            List<List<Object>> rowsList = createSheetListObject(dataType, queryParamsMap, exportParamsMap.keySet());
            writer.write1(rowsList, sheet);
            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1") + ".xlsx");
            out.flush();
            writer.finish();
        } catch (Exception e) {
            log.error("导出报表异常，{}", e);
        }
    }

    private List<List<Object>> createSheetListObject(Integer dataType, Map<String, Object> queryParamsMap, Set<String> keySet) throws Exception {
        if (Objects.equals(dataType, ReportTypeEnum.LOAN_STATISTICS.getCode())) {
            List<FinanceLoanReportRecordModel> loanReportRecordModelList = financeLoanDao.getFinanceLoanCount(queryParamsMap);
            return ExcelUtil.createListObject(loanReportRecordModelList, keySet);
        } else if (Objects.equals(dataType, ReportTypeEnum.LOAN_RECORD.getCode())) {
            List<FinanceLoanModel> loanModelList = financeLoanDao.getFinanceLoan(queryParamsMap);
            return ExcelUtil.createListObject(loanModelList, keySet);
        } else if (Objects.equals(dataType, ReportTypeEnum.LOAN_FAIL.getCode())) {
            List<FinanceLoanModel> loanModelList = financeLoanDao.getFinanceLoanFailure(queryParamsMap);
            return ExcelUtil.createListObject(loanModelList, keySet);
        } else if (Objects.equals(dataType, ReportTypeEnum.UNREPAY_STATISTICS.getCode())) {
            List<FinanceReportRecordModel> financeReportRecordModelList = reportDao.queryReport(queryParamsMap);
            return ExcelUtil.createListObject(financeReportRecordModelList, keySet);
        } else if (Objects.equals(dataType, ReportTypeEnum.UNREPAY_RECORD.getCode())) {
            List<FinanceRepayModel> financeReportRecordModelList = repayDao.queryRepaymentUnpaidList(queryParamsMap);
            return ExcelUtil.createListObject(financeReportRecordModelList, keySet);
        } else if (Objects.equals(dataType, ReportTypeEnum.REPAYED_RECORD.getCode())) {
            List<FinanceRepayModel> financeRepayModelList = repayDao.queryRepaymentPaidList(queryParamsMap);
            return ExcelUtil.createListObject(financeRepayModelList, keySet);
        } else if (Objects.equals(dataType, ReportTypeEnum.REPAY_FAIL.getCode())) {
            List<FinanceRepayModel> financeRepayModelList = repayDao.queryRepaymentFailedList(queryParamsMap);
            return ExcelUtil.createListObject(financeRepayModelList, keySet);
        } else if (Objects.equals(dataType, ReportTypeEnum.TRANSACTION_RECORD.getCode())) {
            List<FinancePayLogModel> financePayLogModelList = financeLoanDao.getFinancePayLogModelList(queryParamsMap);
            return ExcelUtil.createListObject(financePayLogModelList, keySet);
        } else if (Objects.equals(dataType, ReportTypeEnum.OVER_DUE_ORDER.getCode())) {
            //添加条件，参照还款管理，逾期订单列表入参，获取最新逾期订单，包括展期的一起 2019/8/9 created by caideli
            queryParamsMap.put("isCollection", "1");
            //---------------分割线 ，以下方法sql注明：author by  zhujingtao
            List<FinanceDueOrderModel> financeDueOrderModelList = repayDao.queryLateOrderList(queryParamsMap);
            return ExcelUtil.createListObject(financeDueOrderModelList, keySet);
        } else if (Objects.equals(dataType, ReportTypeEnum.COLLECTION_DATA_ORDER.getCode())) {
            //查詢 催收報表 信息 進行 寫入 報表
           List<CollectionRecordVo> collectionRecordVos= collectionClient.queryCollectionReport(queryParamsMap);
           collectionRecordVos.stream().forEach(collectionRecordVo -> {
               if (collectionRecordVo.getPayStatus()==0){
                   collectionRecordVo.setPayStatusStr("未支付");
               }else if ( collectionRecordVo.getPayStatus()==1){
                   collectionRecordVo.setPayStatusStr("已支付");
               }else  if ( collectionRecordVo.getPayStatus()==2){
                   collectionRecordVo.setPayStatusStr("展期");
               }

           }

            );
            return ExcelUtil.createListObject(collectionRecordVos, keySet);
        }else if (Objects.equals(dataType, ReportTypeEnum.COLLECTION_MAN_ORDER.getCode())) {
            //查詢 催收報表 信息 進行 寫入 報表
            List<ReportCollectionManModel> collectionRecordVos= repayService.queryReportCollectionManModel(queryParamsMap);

            return ExcelUtil.createListObject(collectionRecordVos, keySet);
        }

        return new ArrayList<>();
    }
}
