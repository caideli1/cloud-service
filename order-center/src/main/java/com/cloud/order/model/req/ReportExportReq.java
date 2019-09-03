package com.cloud.order.model.req;

import lombok.Data;

import java.util.Map;

/**
 * 报表导出请求类
 *
 * @author danquan.miao
 * @date 2019/5/15 0015
 * @since 1.0.0
 */
@Data
public class ReportExportReq {
    /**
     * 数据类型
     */
    private Integer dataType;
    /**
     * 请求参数map
     */
    private Map<String, Object> queryParamsMap;
    /**
     * 导出字段map
     */
    private Map<String, String> exportParamsMap;
}
