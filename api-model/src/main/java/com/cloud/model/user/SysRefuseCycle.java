package com.cloud.model.user;

import lombok.Data;

import java.util.Date;

/**
 *系统周期表
 */
@Data
public class SysRefuseCycle {


     private Integer id;

    /**
     * 周期类型 名称
     */
    private  String cycleTypeName;
    /**
     * 周期 天数
     */
    private Integer cycleDayCount;

    /**
     * 是否 删除 0 未删除 1 已删除
     */
    private Integer isDelete;
    /**
     *1 审批拒绝类型
     * 2 放款失败类型
     * 3 其他类型
     */
    private  Integer  code ;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}