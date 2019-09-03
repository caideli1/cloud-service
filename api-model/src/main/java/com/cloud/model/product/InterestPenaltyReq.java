package com.cloud.model.product;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @Author:         wza
* @CreateDate:     2019/1/9 14:05
* @Version:        1.0
*/
@Data
public class InterestPenaltyReq implements Serializable {
    private static final long serialVersionUID = 1L;
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
     * 创建人
     */
    private String createUser;
    /**
     * 更新人
     */
    private String modifyUser;
    /**
     * 是否删除
     */
    private Integer isDeleted;
    /**
     * 判断表的类型 0:否-利率，1:是-罚息
     */
    private Integer isPenalty;
}
