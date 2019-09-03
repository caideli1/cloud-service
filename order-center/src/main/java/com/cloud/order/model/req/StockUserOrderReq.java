package com.cloud.order.model.req;

import com.cloud.model.common.BasePageModel;
import lombok.Data;

/**
 * 存量用户请求参数类
 *
 * @author danquan.miao
 * @date 2019/8/2 0002
 * @since 1.0.0
 */
@Data
public class StockUserOrderReq extends BasePageModel {
    /**
     * 订单编号
     */
    private String orderNum;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户手机号
     */
    private String mobile;
    /**
     * 来源类型
     */
    private Integer sourceType;
}
