package com.cloud.collection.controller;

import com.cloud.collection.constant.CollectionApplyStatus;
import com.cloud.collection.model.*;
import com.cloud.collection.model.req.CollectionDetailsAuditReq;
import com.cloud.collection.service.CollectionService;
import com.cloud.collection.service.FinanceRepayService;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.enums.NotificationTemplateTypeEnum;
import com.cloud.common.mq.sender.NotificationSender;
import com.cloud.common.utils.DataPageUtil;
import com.cloud.common.utils.StringUtils;
import com.cloud.collection.model.CollectionRecord;
import com.cloud.model.collection.CollectionRecordVo;
import com.cloud.model.notification.NotificationDto;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: zhujingtao
 * @CreateDate: 2019/3/11
 * 催收控制类
 */
@Slf4j
@RestController
public class CollectionController {
    @Autowired(required = false)
    private CollectionService collectionService;

    @Autowired(required = false)
    private FinanceRepayService financeRepayService;

    @Autowired(required = false)
    private NotificationSender notificationSender;

    /**
     * 获取催收列表
     *
     * @param parameter 前端传入参数
     * @return 催收列表信息
     * @Author: zhujingtao
     */
//    @PreAuthorize("hasAuthority('order:ergency:in') or hasAuthority('permission:all')")
    @GetMapping("/collection/queryCollectionList")
    public JsonResult queryCollectionList(@RequestParam Map<String, Object> parameter) {
        if (parameter.get("sortName") != null && !((String) parameter.get("sortName")).equals("")) {
            parameter.put((String) parameter.get("sortName"), parameter.get("sort"));
        }
        PageInfo<CollectionRecordVo> collectionRecordModelPageInfo = collectionService.queryCollectionList(parameter);
        return JsonResult.ok(collectionRecordModelPageInfo.getList(), (int) collectionRecordModelPageInfo.getTotal());
    }
    @GetMapping("/collection/queryCollectionReport")
    public  List<CollectionRecordVo>   queryCollectionReport(@RequestParam Map<String, Object> parameter) {
        List<CollectionRecordVo> collectionRecordModelList = collectionService.queryCollectionReport(parameter);
        return collectionRecordModelList;
    }

    /**
     * 获取我的催收列表
     *
     * @param parameter 前端传入参数
     * @return 催收列表信息
     * @Author: zhujingtao
     */
    @PreAuthorize("hasAuthority('order:ergency:my') or hasAuthority('permission:all')")
    @GetMapping("/collection/queryMyCollectionList")
    public JsonResult queryMyCollectionList(@RequestParam Map<String, Object> parameter) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (parameter.get("sortName") != null && !((String) parameter.get("sortName")).equals("")) {
            parameter.put((String) parameter.get("sortName"), parameter.get("sort"));
        }

        String name = authentication.getName();
        parameter.put("name", name);
        PageInfo<CollectionRecordVo> collectionRecordModelPageInfo = collectionService.queryMyCollectionList(parameter);
        return JsonResult.ok(collectionRecordModelPageInfo.getList(), (int) collectionRecordModelPageInfo.getTotal());
    }

    /**
     * 显示我的催收的所有的总金额 催收订单的未还总额=所有本金+所有逾期；涉及减免通过的金额，不减。
     * 2019/7/30
     *
     * @return
     * @Author: caideli
     */
    @PreAuthorize("hasAuthority('order:ergency:my') or hasAuthority('permission:all')")
    @GetMapping("/collection/sumMyCollectionAllAmount")
    public JsonResult sumMyCollectionAllAmount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BigDecimal sumMyCollectionAllAmount = collectionService.sumMyCollectionAllAmount(authentication.getName());
        return JsonResult.ok(sumMyCollectionAllAmount == null ? 0 : sumMyCollectionAllAmount);
    }

    /**
     * 查找主管减免罚息列表
     *
     * @param parameter 前端传入参数
     * @return 催收列表信息
     * @Author: zhujingtao
     */

    @GetMapping("/collection/queryInterestReductionList")
    public JsonResult queryInterestReductionList(@RequestParam Map<String, Object> parameter) {
        if (parameter.get("sortName") != null && !(parameter.get("sortName")).equals("")) {
            parameter.put((String) parameter.get("sortName"), parameter.get("sort"));
        }
        PageInfo<CollectionRecordVo> collectionRecordModelPageInfo = collectionService.queryInterestReductionList(parameter);
        return JsonResult.ok(collectionRecordModelPageInfo.getList(), (int) collectionRecordModelPageInfo.getTotal());
    }

    /**
     * 催收状态订单查询
     *
     * @param mobile
     * @return
     */

    @GetMapping("/collection/queryOrderStatusList")
    public JsonResult queryOrderStatusList(@RequestParam(required = false) String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return JsonResult.ok();
        }

        return JsonResult.ok(collectionService.queryOrderStatusList(mobile));

    }


    /**
     * 查找主管指派列表
     *
     * @param parameter 前端传入参数
     * @return 催收列表信息
     * @Author: zhujingtao
     */
    @GetMapping("/collection/queryAssginList")
    public JsonResult queryAssginList(@RequestParam Map<String, Object> parameter) {
        if (parameter.get("sortName") != null && !((String) parameter.get("sortName")).equals("")) {
            parameter.put((String) parameter.get("sortName"), parameter.get("sort"));
        }
        PageInfo<CollectionRecordVo> collectionRecordModelPageInfo = collectionService.queryAssginList(parameter);
        return JsonResult.ok(collectionRecordModelPageInfo.getList(), (int) collectionRecordModelPageInfo.getTotal());
    }


    @ApiOperation(value = "逾期订单查询")
    @GetMapping("/collection/queryLaterList")
    public JsonResult queryLaterList(@RequestParam Map<String, Object> parameter) {
        //判定 是否催收
        //获取前端传入 分页信息
        Integer page = MapUtils.getInteger(parameter, "page");
        Integer pageSize = MapUtils.getInteger(parameter, "limit");

        parameter.put("isCollection", "1");
        if (parameter.get("sortName") != null && !((String) parameter.get("sortName")).equals("")) {
            parameter.put((String) parameter.get("sortName"), parameter.get("sort"));
        }
        List<FinanceDueOrderModel> financeDueOrderModelPageInfo = financeRepayService.queryDueOrderList(parameter);

        return JsonResult.ok(DataPageUtil.pageLimit(financeDueOrderModelPageInfo, page, pageSize), financeDueOrderModelPageInfo.size());
    }


    @ApiOperation(value = "逾期订单详情查询")
    @GetMapping("/collection/laterDetails")
    public JsonResult queryLaterDetails(@RequestParam String dueId) {
        List<LaterDetailsModel> laterDetailsModelPageInfo = financeRepayService.queryLaterDetails(dueId);

        return JsonResult.ok(laterDetailsModelPageInfo);

    }


    /**
     * 查找资产处置列表
     *
     * @param parameter 前端传入参数
     * @return 催收列表信息
     * @Author: zhujingtao
     */
    @PreAuthorize("hasAuthority('assets:disposal') or hasAuthority('permission:all')")
    @GetMapping("/collection/queryEndPropertyList")
    public JsonResult queryEndPropertyList(@RequestParam Map<String, Object> parameter) {
        if (parameter.get("sortName") != null && !((String) parameter.get("sortName")).equals("")) {
            parameter.put((String) parameter.get("sortName"), parameter.get("sort"));
        }
        PageInfo<CollectionRecordVo> collectionRecordModelPageInfo = collectionService.queryEndPropertyList(parameter);
        return JsonResult.ok(collectionRecordModelPageInfo.getList(), (int) collectionRecordModelPageInfo.getTotal());
    }

    /**
     * 查找反馈列表
     *
     * @param collectionId 前端传入催收表ID
     * @return 催收列表信息
     * @Author: zhujingtao
     */
    @GetMapping("/collection/queryAccruedListByCollectionId")
    public JsonResult queryAccruedListByCollectionId(@RequestParam String collectionId,@RequestParam(required = false) String tagId) {
        List<CollectionAccruedRecordModel> collectionAccruedRecordModelList = collectionService.queryAccruedListByCollectionId(collectionId,tagId);
        return JsonResult.ok(collectionAccruedRecordModelList);
    }


    /**
     * 新增主管减免罚息申请
     *
     * @param parameter 前端传入减息实体集
     * @return 返回成功失败信息
     * @Author: zhujingtao
     */
    @GetMapping("/collection/insertInterestReduction")
    public JsonResult insertInterestReduction(@RequestParam Map<String, Object> parameter) {
        int num = 0;
        Integer notRefuseNum;
        //判定前端传入  是否有错
        if (parameter == null) {
            //傳入參數為空
            return JsonResult.errorMsg("The afferent parameter is empty");
        }
        if (parameter.get("remarks") == null || parameter.get("remarks").equals("")) {
            //備註為空
            return JsonResult.errorMsg("Remarks are empty");
        }
        if (parameter.get("collectionId") == null || parameter.get("collectionId").equals("")) {
            //傳入 催收ID 為空
            return JsonResult.errorMsg("The incoming collection ID is empty");
        }
        if (parameter.get("reductionSumAmmount") == null || parameter.get("reductionSumAmmount").equals("")) {
            //罚息减免后应还费用为空
            return JsonResult.errorMsg("Fees payable after penalty reduction and exemption are empty");
        }
        if (parameter.get("reductionAmmount") == null || parameter.get("reductionAmmount").equals("")) {
            //减免金额为空
            return JsonResult.errorMsg("The amount of reduction and exemption is empty");
        }
        if (MapUtils.getDoubleValue(parameter, "reductionSumAmmount") < 0) {
            return JsonResult.errorMsg("PlS confirm your entered amount");
        }

        notRefuseNum = collectionService.queryNotRefuseInterestReductionCount((String) parameter.get("collectionId"));
        if (notRefuseNum > 0) {
            //已申请此催收单
            return JsonResult.errorMsg("This receipt has been applied for");
        }
        //罚息减免记录表添加订单编号字段  created by caideli 2019/8/6
        String orderNo = collectionService.getOrderNoById(Integer.valueOf(parameter.get("collectionId").toString()));

        parameter.put("orderNo", orderNo == null ? "" : orderNo);
        num = collectionService.insertInterestReduction(parameter);

        //判定是否有新增 记录
        if (num > 0) {
            return JsonResult.ok();
        } else {
            //新增记录少于一条
            return JsonResult.errorMsg("Less than one new record was added");
        }
    }


    /**
     * 催收反馈申请
     *
     * @param parameter 传入催收反馈 参数
     * @return
     * @Author: zhujingtao
     */
    @PostMapping("/collection/insertAccrued")
    public JsonResult insertAccrued(@RequestBody Map<String, Object> parameter) {
        int num = 0;
        //判定前端传入  是否有错
        if (parameter == null) {
            //傳入參數為空
            return JsonResult.errorMsg("The afferent parameter is empty");
        }
        if (parameter.get("collectionId") == null || parameter.get("collectionId").equals("")) {
            //傳入 催收ID 為空
            return JsonResult.errorMsg("The incoming collection ID is empty");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        parameter.put("accruedName", name);

        num = collectionService.insertAccrued(parameter);
        //判定是否有新增 记录
        if (num > 0) {
            return JsonResult.ok();
        } else {
            //新增记录少于一条
            return JsonResult.errorMsg("Less than one new record was added");
        }
    }


    /**
     * 申请审批
     *
     * @param parameter 传入审批记录  参数
     * @return
     * @Author: zhujingtao
     */
    @PreAuthorize("hasAuthority('order:reduction:audit') or hasAuthority('permission:all')")
    @GetMapping("/collection/updateInterestReduction")
    public JsonResult updateInterestReduction(@RequestParam Map<String, Object> parameter) {
        return collectionService.updateInterestReduction(parameter);
    }

    /**
     * 查找减免记录列表 信息
     *
     * @param collectionId 合同ID
     * @return
     * @author zhujingtao
     */
    @GetMapping("/collection/queryInterestReduction")
    public JsonResult queryInterestReduction(@RequestParam String collectionId) {
        List<CollectionInterestReductionModel> interestReductionModelList = collectionService.queryInterestReduction(collectionId);
        return JsonResult.ok(interestReductionModelList);
    }


    /**
     * 指派  催收订单
     *
     * @param parameter 前端传入参数
     * @return
     * @author zhujingtao
     */
    @GetMapping("/collection/insertAssginCollection")
    public JsonResult insertAssginCollection(@RequestParam Map<String, Object> parameter) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        parameter.put("managerName", name);

        return collectionService.insertAssginCollection(parameter);
    }

    /**
     * 催收订单详情
     *
     * @param collectionId 前端传入 催收ID
     * @return
     * @author zhujingtao
     */
    @PreAuthorize("hasAnyAuthority('order:ergency:detail', 'order:ergency:my', 'permission:all')")
    @GetMapping("/collection/getDetails")
    @ResponseBody
    public JsonResult getDetails(@RequestParam String collectionId) {
        return collectionService.getDetails(collectionId);
    }

    /**
     * 处置订单
     *
     * @param ids 前端传入 处置ID
     * @return
     * @author zhujingtao
     */
    @GetMapping("/collection/updateHandleStatus")
    public JsonResult updateHandleStatus(@RequestParam String ids) {
        return collectionService.updateHandleStatus(ids);
    }


    /**
     * 获取审批人员
     *
     * @return
     * @author zhujingtao
     */
    @GetMapping("/collection/queryCollectionMan")
    public JsonResult queryCollectionMan() {
        return collectionService.queryCollectionMan();
    }

    /**
     * 获取 所有标签
     *
     * @return
     * @author zhujingtao
     */
    @GetMapping("/collection/queryAllTags")
    public JsonResult queryAllTags() {

        List<CollectionTag> collectionTagList = collectionService.queryAllTags();
        return JsonResult.ok(collectionTagList);
    }

    @ApiOperation(value = "查询罚息减免统计报表")
    @GetMapping("/collection/queryCollReductionReport")
    public JsonResult queryCollReductionReport(@RequestParam Integer page, @RequestParam Integer limit,
                                               @RequestParam(required = false) Integer collectorId, @RequestParam(required = false) String appointCaseDate) {
        PageInfo<ReportCollReductionModel> pageInfo = collectionService
                .queryCollReductionReport(page, limit, collectorId, appointCaseDate);
        return new JsonResult(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @ApiOperation(value = "查询具体催收员的减免罚息统计")
    @GetMapping("/collection/queryReductionByCollectorId")
    public JsonResult queryReductionByCollectorId(@RequestParam Integer page, @RequestParam Integer limit,
                                                  @RequestParam Integer collectorId, @RequestParam(required = false) String appointCaseDate) {
        PageInfo<ReportCollReductionModel> pageInfo = collectionService
                .queryReductionByCollectorId(page, limit, collectorId, appointCaseDate);
        return new JsonResult(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @ApiOperation(value = "主管审核-申请订单查看列表")
    @GetMapping(value = "/collection/queryCollDetailsAuditList")
    public JsonResult queryCollDetailsAuditList(@RequestParam Map<String,Object>  collectionDetailsAuditReq) {
        PageInfo<CollectionRecordVo>  pageInfo = collectionService
                .queryCollDetailsAuditList(collectionDetailsAuditReq);
        return new JsonResult(pageInfo.getList(), (int) pageInfo.getTotal());
    }

    @ApiOperation(value = "主管审核-更新申请订单详情查看")
    @PutMapping("/collection/updateCollDetailsAudit")
    public JsonResult updateCollDetailsAudit(@RequestParam String detailAuditIds, @RequestParam Integer auditStatus,
                                             @RequestParam Integer auditorId) {
        Integer num = collectionService.updateCollDetailsAudit(detailAuditIds, auditStatus, auditorId);

        if (null != num && num > 0) {
            return JsonResult.ok();
        } else {
            return JsonResult.errorMsg("no record was updated");
        }
    }

    @ApiOperation(value = "申请订单详情查看")
    @PostMapping("/collection/insertCollDetailsAudit")
    public JsonResult insertCollDetailsAudit(@RequestBody Map<String, Long> paramsMap) {
        Integer num = collectionService.insertCollDetailsAudit(paramsMap);
        if (num > 0) {
            return JsonResult.ok();
        } else {
            return JsonResult.errorMsg("no record was added");
        }
    }

    /**
     * 用户是否有订单详情查看权限
     */
    @GetMapping("/collection-anon/collDetailsAuditPermission")
    public boolean hasCollDetailsAuditPermission(@RequestParam long sysUserId, @RequestParam String orderNo) {
        return collectionService.hasCollDetailsAuditPermission(sysUserId, orderNo);
    }

    @ApiOperation(value = "发送罚息减免还款短信")
    @GetMapping("/collection/sendInterestReductionSms")
    public JsonResult sendInterestReductionSms(@RequestParam String mobile) {
        NotificationDto notificationDto = NotificationDto.builder()
                .templateType(NotificationTemplateTypeEnum.INTEREST_REDUCTION.getCode()).mobile(mobile).build();
        notificationSender.send(notificationDto);

        return JsonResult.ok();
    }

    /**
     * 查找催收指派记录
     *
     * @param collectionId 催收记录
     * @return
     */
    @GetMapping("/collection/queryAssginRecordModelByCollectionId")
    public JsonResult queryAssginRecordModelByCollectionId(@Param("collectionId") String collectionId) {
        return JsonResult.ok(collectionService.queryAssginRecordModelByCollectionId(collectionId));
    }

    /**
     * 根据催收id查找订单号
     *
     * @param collectionId
     * @return
     */
    @GetMapping("/collection/getOrderNoById")
    public String getOrderNoById(@RequestParam("collectionId") Integer collectionId) {
        return collectionService.getOrderNoById(collectionId);
    }

    /**
     * 根据逾期id查询催收记录
     *
     * @param id
     * @return
     */
    @GetMapping("/collection/getCollectionByDueId")
    public CollectionRecordVo getCollectionByDueId(@RequestParam Integer id) {
        CollectionRecord collectionRecord = collectionService.getCollectionByDueId(id);
        CollectionRecordVo collectionRecordVo = CollectionRecordVo.builder().build();
        if (collectionRecord !=null){
            BeanUtils.copyProperties(collectionRecord, collectionRecordVo);
        }
        return collectionRecordVo;
    }

    /**
     * 查询催收标签
     *
     * @param ids
     * @return
     */
    @GetMapping("/collection/queryAllTagsByIds")
    public JsonResult queryAllTagsByIds(@RequestParam String ids) {
        List<CollectionTag> collectionTagList = collectionService.queryAllTagsByIds(ids);
        return JsonResult.ok(collectionTagList, collectionTagList.size());
    }

    /**
     * 据逾期id查询催收反馈信息
     *
     * @param dueId
     * @return
     */
    @GetMapping("/collection/queryAccruedRecordByDueId")
    public JsonResult queryAccruedRecordByDueId(@RequestParam Integer dueId) {
        List<CollectionAccruedRecordModel> accruedRecordModelList = collectionService.queryAccruedRecordByDueId(dueId);
        return JsonResult.ok(accruedRecordModelList, accruedRecordModelList.size());
    }

    /**
     * @Author : caideli
     * @Email : 1595252552@qq.com
     * @Date : 19:17  2019/8/8
     * Description :获取减免项
     */
    //@PreAuthorize("hasAuthority('loan:info:detail') or hasAuthority('permission:all')")
    @GetMapping("/collection/ReliefItems")
    public JsonResult ReliefItems(@RequestParam String orderNo) {
        //获取审核通过的，最近的一条罚息减免记录
        Map<String, Object> params = new HashMap<>(8);
        params.put("applyStatus", CollectionApplyStatus.PASS.num);//审核通过的
        params.put("orderNoAndMNPD", orderNo);
        CollectionInterestReductionModel collectionInterestReductionModel = collectionService.getOneCollectionInterestReductionByParams(params);
        //还款成功的还款激励
        params.put("orderStatus", 1);
        FinanceRepayModel financeRepayModel = financeRepayService.getOneFinanceRepayByParams(params);
        params.clear();
        // 减免金额，已使用/未使用，通过时间
        //减免后应还，生效中/已失效，失效时间
        if (collectionInterestReductionModel != null) {
            params.put("reductionAmount", collectionInterestReductionModel.getReductionAmmount());
            params.put("AuditTime", collectionInterestReductionModel.getAuditTime());
            //成功的还款记录里面，实际还款金额等于罚息减免后的金额
            //或者是交易时间在减免时间范围里面 financeRepayModel.getPayDate().compareTo(collectionInterestReductionModel.getExpireTime())==-1
            if (financeRepayModel != null && collectionInterestReductionModel.getReductionSumAmmount().compareTo(financeRepayModel.getActualAmount()) == 0) {
                params.put("isHaveUsed", true);
            } else {
                params.put("isHaveUsed", false);
            }
            //有效中
            if (collectionInterestReductionModel.getExpireTime() != null && collectionInterestReductionModel.getExpireTime().compareTo(new Date()) == 1) {
                params.put("isEffective", true);
                params.put("reductionSumAmount", collectionInterestReductionModel.getReductionSumAmmount());
            } else {
                params.put("isEffective", false);
                params.put("reductionSumAmount", 0.00);
            }
            params.put("expireTime", collectionInterestReductionModel.getExpireTime());
        } else {
            params.put("reductionAmount", 0.00);
            params.put("AuditTime", "-");
            params.put("isHaveUsed", false);
            params.put("reductionSumAmount", 0.00);
            params.put("isEffective", false);
            params.put("expireTime", "-");
        }
        return JsonResult.ok(params);
    }
}
