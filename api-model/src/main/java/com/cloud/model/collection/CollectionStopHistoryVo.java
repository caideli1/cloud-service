package com.cloud.model.collection;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/22 16:32
 * 描述：停催历史
 */
@Data
public class CollectionStopHistoryVo implements Serializable {
    /**
     * 主鍵id
     */
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