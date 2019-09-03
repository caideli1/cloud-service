package com.cloud.model.collection;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/16 14:54
 * 描述：
 */
@Data
public class CollectionGroupUserVo implements Serializable {
    /**
     * 自增id
     */
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

    /**
     * 催收员的名称
     */
    private String userName;
}
