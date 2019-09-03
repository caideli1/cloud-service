package com.cloud.order.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 免息记录表
 * @Author:         zhujingtao
 * @CreateDate:     2019/3/8 14:05
 * 对应数据库表 ``collection_accrued_record` `
 */
@Data
public class CollectionAccruedRecordModel {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 催收表ID
     */
    private Long collectionId;

    /**
     *回访记录人名称
     */
    private String  accruedName;

    private List<CollectionTag> tagList;
    /**
     * 标签名称Id
     */
    private String tagIds;

    private List<String> tagName;



    /**
     * 回访记录
     */
    private String remarks;

    /**
     * 反馈时间
     */
    private Date accruedDate;

}
