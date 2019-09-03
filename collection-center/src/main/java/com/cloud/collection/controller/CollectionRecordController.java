package com.cloud.collection.controller;

import com.cloud.collection.model.CollectionRecord;
import com.cloud.collection.model.CollectionStopHistory;
import com.cloud.collection.service.CollectionStopHistoryService;
import com.cloud.collection.service.CollectionRecordService;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.collection.CollectionStopHistoryVo;
import com.cloud.model.order.FinanceDueOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/14 15:08
 * 描述：
 */
@Slf4j
@RestController
public class CollectionRecordController {

    @Autowired
    private CollectionRecordService collectionRecordService;

    @Autowired
    private CollectionStopHistoryService collectionStopHistoryService;

    /**
     * 催收记录批量标记高亮
     * @param collectionIds
     * @param isMark
     * @return
     */
    //@PreAuthorize("hasAuthority('lendingPool:batchLendingLoan') or hasAuthority('permission:all')")
    @PostMapping("/collection-record/batchMark")
    public JsonResult batchMark(@RequestParam String collectionIds,@RequestParam Boolean isMark) {
        return JsonResult.ok(collectionRecordService.batchMarkCollectionRecords(collectionIds,isMark));
    }

    /**
     * 批量退案停催
     * @param collectionIds
     * @return
     */
    //@PreAuthorize("hasAuthority('lendingPool:batchLendingLoan') or hasAuthority('permission:all')")
    @PostMapping("/collection-record/batchStop")
    public JsonResult batchStop(@RequestParam String collectionIds) {
        collectionRecordService.batchStop(collectionIds,AppUserUtil.getLoginSysUser().getId());
        return JsonResult.ok();
    }

    /**
     * 批量分配催收订单
     * @param collectionIds
     * @param userIds 分配的催收员id
     * @param isUpdate 是否是修分配， 是 true  首次分配 false
     * @return
     */
    //@PreAuthorize("hasAuthority('lendingPool:batchLendingLoan') or hasAuthority('permission:all')")
    @PostMapping("/collection-record/batchAssginColletion")
    public JsonResult batchAssginColletion(@RequestParam Long[] collectionIds,@RequestParam Long[] userIds,@RequestParam Boolean isUpdate) {
        return JsonResult.ok(collectionRecordService.batchAssginColletion(collectionIds,userIds,AppUserUtil.getLoginSysUser().getId(),isUpdate));
    }

    /**
     * 获取停催历史
     * @param collectionId
     * @return
     */
    @GetMapping("/collection-record/getCollectionStopHistoryList")
    public JsonResult getCollectionStopHistoryList(@RequestParam Long collectionId) {
        Example example = new Example(CollectionStopHistory.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("collectionId", collectionId);
        return JsonResult.ok(collectionStopHistoryService.getList(example));
    }

    /**
     * 其中 sendType 0：正常結清 1：逾期還款 2：展期還款
     * 逾期還款時 請傳 逾期ID  其餘 傳orderNO
     * @param financeDueOrderVo
     * @param transactionTime
     * @return
     */
    @GetMapping("/collection-record/updateCollectionRecordByDue")
    public boolean updateCollectionRecordByDue(@RequestParam FinanceDueOrderVo financeDueOrderVo,@RequestParam Date transactionTime) {
        return collectionRecordService.updateCollectionRecordByDue(financeDueOrderVo, transactionTime);
    }
}
