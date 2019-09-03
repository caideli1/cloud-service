package com.cloud.collection.model;

import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * 罚息减免报表
 *
 * @author danquan.miao
 * @date 2019/6/4 0004
 * @since 1.0.0
 */
@Data
@Table(name="report_collection_reduction")
public class ReportCollReductionModel {
    private Integer id;
    /**
     * 催收员ID
     */
    private Integer collectorId;
    /**
     * 催收员姓名
     */
    private String collectorName;
    /**
     * 委案日期
     */
    private Date appointCaseDate;
    /**
     * 通过率
     */
    private String passRate;
    /**
     * 通过个数
     */
    private Integer passCount;
    /**
     * 委案罚息申请个数
     */
    private Integer appointCaseApplyCount;
    /**
     * 委案总案件数
     */
    private Integer appointCaseTotalCount;
    /**
     * 状态：1:有效 2:失效
     */
    private Integer status;
}
