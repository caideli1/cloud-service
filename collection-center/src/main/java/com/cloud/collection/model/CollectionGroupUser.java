package com.cloud.collection.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/16 14:17
 * 描述：
 */
@Data
@Table(name="collection_group_user")
public class CollectionGroupUser implements Serializable {
    /**
     * 自增id
     */
    @Id
    private Integer id;

    /**
     * 组id
     */
    private Integer groupId;

    /**
     * 催收员id
     */
    private Integer userId;

    /**
     * 创建时间
     */
    private Date createTime;
}
