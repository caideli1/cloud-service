package com.cloud.model.check;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * OrderCheckLogModel class
 *
 * @author zhujingtao
 * @date 2019/2/20
 */
@Data
@Builder
public class CheckLogModel implements Serializable {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * order_id
     */
    private Long orderId;
    /**
     * 审核员名字
     */
    private String auditorName;
    /**
     * 审核状态：
     * 1.机审
     * 2.机审拒绝
     * 3待初审
     * 4.人工初审拒绝
     * 5.待终审
     * 6.人工终审拒绝
     * 7.通过
     */
    private int status;
    /**
     * 审核结果:
     * 0.未通过
     * 1.通过
     */
    private int result;
    /**
     * 原因
     */
    private String reason;
    /**
     * 备注
     */
    private String comment;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 节点：
     * 10.xx
     * 11.xx (1开头代表机审)
     * 20.xx
     * 21.xx
     */
    private int node;
    /**
     * 是否预警:
     * 0.否
     * 1.是
     */
    private int isWarning;
    /**
     * 标签ids
     */
    private String tagIds;

}
