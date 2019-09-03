package com.cloud.order.model;

import lombok.Data;

import java.util.List;

/**
 * 参数model
 *
 * @author danquan.miao
 * @date 2019/5/6 0006
 * @since 1.0.0
 */
@Data
public class ParamModel {
    /**
     * 查询参数列表
     */
    private List<QueryParamModel> queryParamList;
    /**
     * 导出参数列表
     */
    private List<ExportParamModel> exportParamList;
}
