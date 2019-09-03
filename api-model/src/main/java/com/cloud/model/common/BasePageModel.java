package com.cloud.model.common;

import lombok.Data;

/**
 * 基础分页model
 *
 * @author danquan.miao
 * @date 2019/6/4 0004
 * @since 1.0.0
 */
@Data
public class BasePageModel {
    /**
     * 页码
     */
    private Integer page;
    /**
     * 条数
     */
    private Integer limit;
}
