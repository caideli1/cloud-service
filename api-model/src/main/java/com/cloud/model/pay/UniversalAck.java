package com.cloud.model.pay;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hasee on 2019/7/25.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversalAck {

    //查询或回调的订单处理状态
    private String status;

    private String failureReason;

    private String orderNo;

    private int channel;
}
