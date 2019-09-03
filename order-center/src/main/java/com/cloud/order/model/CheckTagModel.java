package com.cloud.order.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 对应数据库表 check_tag
 *
 */

@Data
public class CheckTagModel implements Serializable {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 描述
     */
    private String descrption;

    /**
     * 类型:
     * 1.预警标签
     * 2.拒绝标签
     */
    private Short type;
}
