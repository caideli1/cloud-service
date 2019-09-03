package com.cloud.service.feign.risk;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.risk.RiskDeviceInfoModel;
import com.cloud.service.fallback.RiskFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author bjy
 * @date 2019/4/1 0001 10:15
 */
@FeignClient(name = "risk-center", fallbackFactory = RiskFallBackFactory.class)
public interface RiskClient {

    @PostMapping("/risk-anon/getAddressBookByOrderNum")
    JsonResult getAddressBookByOrderNum(String params);

    @PostMapping("/risk-anon/getDeviceInfoByOrderNum")
    JsonResult getDeviceInfoByOrderNum(Map<String, Object> params);

    @PostMapping("/risk-anon/getCallRecordByOrderNum")
    JsonResult getCallRecordByOrderNum(Map<String, Object> params);


    @PostMapping("/risk-anon/getMessage")
    JsonResult getMessage(Map<String, Object> params);

    @PostMapping("/risk-anon/getCallCountByOrderNum")
    JsonResult getCallCountByOrderNum(Map<String, Object> params);

    @PostMapping("/risk-anon/getHistoryMatchs")
    JsonResult getHistoryMatchs(Map<String, Object> params);

    @GetMapping("/risk-anon/getAlterList")
    JsonResult queryRiskStoreListByOrderNum(@RequestParam(value = "orderNum") String orderNum,
                                            @RequestParam(value = "approveStatus") Integer approveStatus);

    @PostMapping("/risk-anon/selectMessageReadRate")
    JsonResult selectMessageReadRate(String orderNum);


    @PostMapping("/risk-anon/queryImiNoAndOrderNobyOrderNo")
    List<RiskDeviceInfoModel> queryImiNoAndOrderNobyOrderNo(String orderNo);



    @PostMapping("/risk-anon/queryOrderNoByPhone")
     List<String> queryOrderNoByPhone(java.lang.String phone);
}
