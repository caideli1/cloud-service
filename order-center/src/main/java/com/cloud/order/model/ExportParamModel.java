package com.cloud.order.model;

import lombok.Data;

/**
 * 导出参数model
 *
 * @author danquan.miao
 * @date 2019/5/6 0006
 * @since 1.0.0
 */
@Data
public class ExportParamModel {
    /**
     * 参数英文名
     */
    private String engName;
    /**
     * 参数中文名
     */
    private String cnName;
    /**
     * 是否额外导出字段 0:否 1:是
     */
    private Integer isExtra;
}
