package com.cloud.collection.model.req;

import com.cloud.model.common.BasePageModel;
import lombok.Data;

/**
 * 催收订单审核请求实体
 *
 * @author danquan.miao
 * @date 2019/6/4 0004
 * @since 1.0.0
 */
@Data
public class CollectionDetailsAuditReq extends BasePageModel {
    /**
     * 客户编号
     */
    private String customerNo;
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 催收人用户id
     */
    private Integer collectionUserId;
    /**
     * 跟进状态
     */
    private String followUpStatus;
    /**
     * 本金排序 1：升序 2：降序
     */
    private Integer loanAmountSort;
    /**
     * 罚息排序 1：升序 2：降序
     */
    private Integer dueAmountSort;
}
