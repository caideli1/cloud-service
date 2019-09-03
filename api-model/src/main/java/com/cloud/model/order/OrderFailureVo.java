package com.cloud.model.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/26 14:29
 * 描述：
 */
@Data
public class OrderFailureVo implements Serializable {

    /**
     * 借据id
     */
    private Long userLoanId;
    /**
     * 催收主键id
     */
    private Long collectionId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 手机号
     */
    private String userPhone;
    /**
     * 应还时间
     */
    private Date shouldRepayTime;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 最近一次还款失败时间
     */
    private Date finalFailureTime;
    /**
     * 还款失败次数
     */
    private Integer countNum;
    /**
     * 应还金额
     */
    private BigDecimal shouldRepayAmount;
    /**
     * 逾期天数
     */
    private Integer dueDays;
    /**
     * 0 未根进 1 已跟进
     */
    private Integer isFollow;
}
