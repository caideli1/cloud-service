package com.cloud.collection.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/16 14:15
 * 描述：
 */
@Data
@Table(name="collection_group")
public class CollectionGroup implements Serializable {

    /**
     * 自增id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 组名
     */
    private String name;

    /**
     * 创建时间
     */
    private Date createTime;
}
