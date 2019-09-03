package com.cloud.order.service;

import com.cloud.common.dto.JsonResult;
import com.cloud.order.model.FinanceReportRecordModel;
import com.cloud.order.model.FinanceReportSumRecordModel;
import com.github.pagehelper.PageInfo;


import java.util.Map;

/**
 * @Author:   zhujingtao
 *
 * @CreateDate:  2019/2/27
 * 报表管理 服务接口
 */
public interface FinanceReportService {
    /**
     * 查询 汇总报表信息
     * @Author:   zhujingtao
     * @return FinanceReportSumRecordModel 汇总报表信息
     */
    FinanceReportSumRecordModel querySumRecord();

    /**
     * 查询 日期报表信息
     * @Author:   zhujingtao
     *  @param params 前端传入参数
     * @return  List<FinanceReportRecordModel>  日期报表查询
     */
    PageInfo<FinanceReportRecordModel> queryReport(Map<String,Object> params);


    FinanceReportSumRecordModel queryTotalRecord();


    JsonResult queryReportMessage(Map<String, Object> params);
}
