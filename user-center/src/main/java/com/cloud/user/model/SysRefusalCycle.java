package com.cloud.user.model;


import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 *系统周期表
 */
@Data
@Table(name="sys_refusal_cycle")
public class SysRefusalCycle {
    @Id
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 编码
     */
    private Integer code;

    /**
     * 更新时间
     */
    private  Date updateTime;

}
