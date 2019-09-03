package com.cloud.collection.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 指派人员实体集
 *
 * @Author: zhujingtao
 * @CreateDate: 2019/3/8 14:05
 * 对应数据库表 `collection_assgin_record`
 */
@Data
@Builder
public class CollectionAssginRecordModel {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 催收表ID
     */
    private Long collectionId;

    /**
     * 催收人名称
     */
    private String collectionName;

    /**
     * 指派时间
     */
    private Date assginDate;
    /**
     * 委案类型 1：s1 2:s2 3:s3
     */
    private Integer appointCaseType;

    /**
     * 分配人ID
     */
    private Long managerId;

    /**
     * 指派人ID
     */
    private Long collectionUserId;
}
