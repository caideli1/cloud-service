package com.cloud.service.fallback;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.risk.RiskDeviceInfoModel;
import com.cloud.service.feign.risk.RiskClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 风控中心服务降级类
 *
 * @author danquan.miao
 * @date 2019/7/24 0024
 * @since 1.0.0
 */
@Component
public class RiskFallBackFactory implements FallbackFactory<RiskClient> {
    @Override
    public RiskClient create(Throwable throwable) {
        return new RiskClient() {
            @Override
            public JsonResult getAddressBookByOrderNum(String params) {
                return null;
            }

            @Override
            public JsonResult getDeviceInfoByOrderNum(Map<String, Object> params) {
                return null;
            }

            @Override
            public JsonResult getCallRecordByOrderNum(Map<String, Object> params) {
                return null;
            }

            @Override
            public JsonResult getMessage(Map<String, Object> params) {
                return null;
            }

            @Override
            public JsonResult getCallCountByOrderNum(Map<String, Object> params) {
                return null;
            }

            @Override
            public JsonResult getHistoryMatchs(Map<String, Object> params) {
                return null;
            }

            @Override
            public JsonResult queryRiskStoreListByOrderNum(String orderNum, Integer approveStatus) {
                return null;
            }

            @Override
            public JsonResult selectMessageReadRate(String orderNum) {
                return null;
            }

            @Override
            public List<RiskDeviceInfoModel> queryImiNoAndOrderNobyOrderNo(String orderNo) {
                return null;
            }

            @Override
            public List<String> queryOrderNoByPhone(String phone) {
                return null;
            }
        };
    }
}
