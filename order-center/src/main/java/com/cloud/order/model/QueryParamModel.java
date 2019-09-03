package com.cloud.order.model;

import lombok.Data;

/**
 * 查询参数model
 *
 * @author danquan.miao
 * @date 2019/5/6 0006
 * @since 1.0.0
 */
@Data
public class QueryParamModel {
    /**
     * 参数英文名
     */
    private String engName;
    /**
     * 参数中文名
     */
    private String cnName;
    /**
     * 参数值
     */
    private String paramValue;
    /**
     * 参数类型 1：input输入框 2:select选择框 3：时间选择器
     */
    private Integer paramType;
}
