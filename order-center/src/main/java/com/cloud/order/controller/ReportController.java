package com.cloud.order.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.order.constant.ReportTypeEnum;
import com.cloud.order.model.ParamModel;
import com.cloud.order.model.req.ReportExportReq;
import com.cloud.order.service.ReportService;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

/**
 * 报表controller类
 *
 * @author danquan.miao
 * @date 2019/5/6 0006
 * @since 1.0.0
 */
@Slf4j
@RestController
public class ReportController {

    @Autowired(required = false)
    private ReportService reportService;

    @ApiOperation(value = "查询数据类型")
    @GetMapping(value = "report/queryDataType")
    public JsonResult queryDataType() {
        Map<Integer, String> dataTypeMap = Maps.newHashMap();
        Arrays.stream(ReportTypeEnum.values())
                //不包含催收 报表
                .filter(reportTypeEnum->!reportTypeEnum.getCode().equals(ReportTypeEnum.COLLECTION_DATA_ORDER.getCode())&&!reportTypeEnum.getCode().equals(ReportTypeEnum.COLLECTION_MAN_ORDER.getCode()))
                .forEach(reportTypeEnum -> dataTypeMap.put(reportTypeEnum.getCode(), reportTypeEnum.getMessage()));
        return JsonResult.ok(dataTypeMap);
    }

    @ApiOperation(value = "根据类型获取查询、导出参数")
    @GetMapping(value = "report/queryParamsByType")
    public JsonResult queryParamsByType(@RequestParam Integer dataType) {
        ParamModel paramModel = reportService.queryParamsByType(dataType);
        return JsonResult.ok(paramModel);
    }

    //    @PreAuthorize("hasAuthority('report:export') or hasAuthority('permission:all')")
    @ApiOperation(value = "根据类型导出excel")
    @PostMapping(value = "report/exportByType", consumes = {"application/x-www-form-urlencoded;charset=UTF-8"})
    public void exportByType(ReportExportReq reportExportReq,
                             HttpServletResponse response) {
        reportService.exportByType(reportExportReq, response);
    }

}
