package com.cloud.order.dao;

import com.cloud.order.model.FinanceReportRecordModel;
import com.cloud.order.model.FinanceReportSumRecordModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


/**
 * FinanceReportDao interface
 *
 * @author zhujingtao
 * @date 2019/2/26
 */
@Mapper
public interface FinanceReportDao {

    /** 获取汇总报表信息
     * @author zhujingtao
     * @return FinanceReportSumRecordModel
     */
    FinanceReportSumRecordModel querySumRecord();

    /**
     * 获取日期报表信息
     * @author zhujingtao
     * @param params startDate endDate
     * @return 日期报表 列表
     */
    List<FinanceReportRecordModel> queryReport(Map<String,Object> params);
    /**
     * 修改匯總報表
     * @author zhujingtao
     *
     * @return 日期报表 列表
     */
    int  updateToSum();

}
