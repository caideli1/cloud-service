package com.cloud.model.collection;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/16 14:19
 * 描述：
 */
@Data
public class CollectionGroupVo implements Serializable {

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 组名
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 催收员列表
     */
    private List<CollectionGroupUserVo> collectionGroupUserVos;

    /**
     * 组员数量
     */
    private Integer userCount;
}
