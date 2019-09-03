package com.cloud.order.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 *  OrderAssignLogModel class
 *
 * @author zhujingtao
 * @date 2019/2/20
 */
@Data
public class OrderAssignLogModel implements Serializable {
    /**
     * 主键Id
     */
    private  Long  id;
    /**
     *	订单id
     */
    private  Long  orderId;
    /**
     *	分配者名字
     *	（自己领取填自己名字）
     */
    private  String  dispatcherName;
    /**
     *审核员名字
     */
    private  String auditorName;
    /**
     *	创建时间
     */
    private Date createTime;

}
