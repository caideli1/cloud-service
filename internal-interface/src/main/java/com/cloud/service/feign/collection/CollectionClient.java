package com.cloud.service.feign.collection;

import com.cloud.common.dto.JsonResult;
import com.cloud.model.collection.CollectionInterestReductionModelVo;
import com.cloud.model.collection.CollectionRecordVo;
import com.cloud.model.order.FinanceDueOrderVo;
import com.cloud.service.fallback.CollectionFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 催收feign 接口
 *
 * @author danquan.miao
 * @date 2019/8/9 0009
 * @since 1.0.0
 */
@FeignClient(name = "collection-center", fallbackFactory = CollectionFallBackFactory.class)
public interface CollectionClient {
    @GetMapping("/collection/getOrderNoById")
    String getOrderNoById(@RequestParam("collectionId") Integer collectionId);

    /**
     * 根据逾期id查询催收记录
     *
     * @param id
     * @return
     */
    @GetMapping("/collection/getCollectionByDueId")
    CollectionRecordVo getCollectionByDueId(@RequestParam Integer id);

    /**
     * 查詢催收報表 信息
     * @param parameter
     * @return
     */
     @GetMapping("/collection/queryCollectionReport")
     List<CollectionRecordVo> queryCollectionReport(@RequestParam Map<String, Object> parameter);

    /**
     * 查询催收标签
     *
     * @param id
     * @return
     */
    @GetMapping("/collection/queryAllTagsByIds")
    JsonResult queryAllTagsByIds(String ids);

    /**
     * 其中 sendType 0：正常結清 1：逾期還款 2：展期還款
     * 逾期還款時 請傳 逾期ID  其餘 傳orderNO
     * @param financeDueOrderVo
     * @param transactionTime
     * @return
     */
    @GetMapping("/collection-record/updateCollectionRecordByDue")
    boolean updateCollectionRecordByDue(@RequestParam FinanceDueOrderVo financeDueOrderVo, @RequestParam Date transactionTime);

    /**
     * 据逾期id查询催收反馈信息
     *
     * @param dueId
     * @return
     */
    @GetMapping("/collection/queryAccruedRecordByDueId")
    JsonResult queryAccruedRecordByDueId(@RequestParam Integer dueId);

    @GetMapping("/collection-anon/collDetailsAuditPermission")
    boolean hasCollDetailsAuditPermission(@RequestParam long sysUserId, @RequestParam String orderNo);

    /**
     * 获取减免项
     * @param orderNo
     * @return
     */
    @GetMapping("/collection/ReliefItems")
    JsonResult ReliefItems(@RequestParam String orderNo);

    /**
     * 获取最近一条已经通过的罚息减免
     * @param orderNo
     * @return
     */
    @GetMapping("/collection/getOneEnableCollectionInterestReduction")
    public CollectionInterestReductionModelVo getOneEnableCollectionInterestReduction(@RequestParam String orderNo);
}
