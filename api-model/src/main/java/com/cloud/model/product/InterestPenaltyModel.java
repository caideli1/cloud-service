package com.cloud.model.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
* @Author:         wza
* @CreateDate:     2019/1/9 14:05
* @Version:        1.0
*/
@Data
public class InterestPenaltyModel {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 罚息表名称
     */
    private String name;
    /**
     * 利率类型（1：日利率，2：周利率）
     */
    private Integer type;
    /**
     * 利率
     */
    private BigDecimal rate;
    /**
     * 是否删除
     */
    private Integer isDeleted;
    /**
     * 创建人
     */
    private String createUser;
    /**
     * 创建时间 例:"2019-01-08T08:35:01.000+0000"
     */
    private Date createTime;
    /**
     * 最后更新者
     */
    private String modifyUser;
    /**
     * 最后更新时间
     */
    private Date modifyTime;
    /**
     * 转换成年月日时分秒格式展示的时间 例:"2019-01-08 16:35:01"
     */
    private String createTimeStr;
    /**
     * 是否在产品表有关联 0:无关联
     */
    private Integer isRelevance;
    /**
     * 产品表逻辑删除字段
     */
    private Integer proIsDel;
    /**
     * 判断表的类型 0:否-利率，1:是-罚息
     */
    private Integer isPenalty;
}
