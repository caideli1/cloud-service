package com.cloud.model.product;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhujingtao
 * 审批报表实体集
 */
@Data
@Builder
public class OrderHistoryDto {
    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 电话号码
     */
    private String mobile;
    /**
     * 公司名称
     */
    private  String  companyName;
    private String companyMobile;
    /**
     * 用户名称
     */
    private  String  userName;

    /**
     * 獲取訂單 判定值進行判定
     */
    private Integer auditorOrderStatus;


    private    String  auditorStatus;

    /**
     * 设备号
     */
    private String  imiNo;

    /**
     * 关联关系名称
     */
    private  String relationName;


}
