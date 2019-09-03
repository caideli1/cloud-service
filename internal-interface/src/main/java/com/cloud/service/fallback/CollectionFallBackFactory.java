package com.cloud.service.fallback;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.collection.CollectionInterestReductionModelVo;
import com.cloud.model.collection.CollectionRecordVo;
import com.cloud.model.order.FinanceDueOrderVo;
import com.cloud.service.feign.collection.CollectionClient;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author danquan.miao
 * @date 2019/8/9 0009
 * @since 1.0.0
 */
@Component
public class CollectionFallBackFactory implements FallbackFactory<CollectionClient> {
    @Override
    public CollectionClient create(Throwable throwable) {
        return new CollectionClient() {
            @Override
            public List<CollectionRecordVo> queryCollectionReport(Map<String, Object> parameter) {
                return null;
            }

            @Override
            public String getOrderNoById(Integer collectionId) {
                return null;
            }

            @Override
            public CollectionRecordVo getCollectionByDueId(Integer id) {
                return null;
            }

            @Override
            public JsonResult queryAllTagsByIds(String ids) {
                return null;
            }

            @Override
            public boolean updateCollectionRecordByDue(FinanceDueOrderVo financeDueOrderVo, Date transactionTime) {
                return false;
            }

            @Override
            public JsonResult queryAccruedRecordByDueId(Integer dueId) {
                return null;
            }

            @Override
            public boolean hasCollDetailsAuditPermission(long sysUserId, String orderNo) {
                return false;
            }

            @Override
            public JsonResult ReliefItems(String orderNo) {
                return null;
            }

            @Override
            public CollectionInterestReductionModelVo getOneEnableCollectionInterestReduction(String orderNo) {
                return null;
            }
        };
    }
}
