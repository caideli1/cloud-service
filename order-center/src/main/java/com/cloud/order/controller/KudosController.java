package com.cloud.order.controller;

import com.cloud.order.model.kudos.KudosOfflineRepayEntity;
import com.cloud.order.service.KudosApiService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * kudos控制类
 *
 * @author danquan.miao
 * @date 2019/5/27 0027
 * @since 1.0.0
 */
@Slf4j
@RestController
public class KudosController {

    @Autowired(required = false)
    private KudosApiService kudosApiService;

//    @ApiOperation(value = "kudos线下还款通知")
//    @PostMapping("/kudos/offlineTransactionNotify")
//    public Boolean offlineTransactionNotify(@RequestBody KudosOfflineRepayEntity offlineRepayEntity) {
//        return kudosApiService.offlineTransactionNotify(offlineRepayEntity);
//    }

//    @ApiOperation(value = "线下还款成功-关闭借据")
//    @PostMapping("/kudos/offlineTransCloseLoan")
//    public Boolean offlineTransCloseLoan(@RequestBody KudosOfflineRepayEntity offlineRepayEntity) {
//        return kudosApiService.offlineTransCloseLoan(offlineRepayEntity);
//    }
}
