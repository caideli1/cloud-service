package com.cloud.order.service;

import com.cloud.order.model.ParamModel;
import com.cloud.order.model.req.ReportExportReq;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 报表服务接口
 *
 * @author danquan.miao
 * @date 2019/5/6 0006
 * @since 1.0.0
 */
public interface ReportService {
    /**
     * 根据类型查找查询、导出参数
     *
     * @param dataType
     * @return
     */
    ParamModel queryParamsByType(Integer dataType);

    /**
     * 根据类型导出excel
     *
     * @param reportExportReq
     * @param response
     * @return
     */
    void exportByType(ReportExportReq reportExportReq, HttpServletResponse response);
}
