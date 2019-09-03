package com.cloud.order.test.service.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.common.mq.sender.NotificationSender;
import com.cloud.model.notification.NotificationDto;
import com.cloud.order.model.ParamModel;
import com.cloud.order.service.ReportService;
import com.cloud.order.test.OrderCenterApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 报表服务接口测试类
 *
 * @author danquan.miao
 * @date 2019/5/7 0007
 * @since 1.0.0
 */
public class ReportServiceImplTest extends OrderCenterApplicationTest {
    @Autowired(required = false)
    private ReportService reportService;

    @Test
    public void queryParamsByType() {
        Integer dataType = 2;
        ParamModel paramModel = reportService.queryParamsByType(dataType);
        System.out.println(JSON.toJSONString(paramModel));
    }


    @Autowired
    private NotificationSender notificationSender;

    @Test
    public void test(){
        NotificationDto notificationDto = NotificationDto.builder()
                .templateType(3)
                .mobile("123456")
                .email("wallle@moneed.net")
                .userId(11442L)
                .build();
        notificationSender.send(notificationDto);
    }
}
