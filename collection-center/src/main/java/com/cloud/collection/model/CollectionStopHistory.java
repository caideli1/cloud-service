package com.cloud.collection.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/22 16:30
 * 描述：
 */
@Data
@Table(name="collection_stop_history")
public class CollectionStopHistory implements Serializable {
    /**
     * 主鍵id
     */
    @Id
    private Long id;

    /**
     * 催收id
     */
    private Long collectionId;

    /**
     * 停催时间
     */
    private Date stopCollectionTime;

    /**
     * 再分配时间
     */
    private Date assginTime;

    /**
     * 操作停催的主管id
     */
    private Long managerId;

}
