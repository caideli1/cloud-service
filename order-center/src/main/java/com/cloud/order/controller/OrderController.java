package com.cloud.order.controller;

import com.alibaba.fastjson.JSON;
import com.cloud.common.component.utils.DateUtil;
import com.cloud.common.config.CommonConfig;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.enums.NotificationTemplateTypeEnum;
import com.cloud.common.mq.sender.NotificationSender;
import com.cloud.common.utils.DataPageUtil;
import com.cloud.common.utils.StringUtils;
import com.cloud.model.appUser.AppUserOrderInfo;
import com.cloud.model.common.CheckStatus;
import com.cloud.model.common.Page;
import com.cloud.model.notification.NotificationDto;
import com.cloud.model.product.OrderHistoryDto;
import com.cloud.model.product.constant.ProductConstants;
import com.cloud.model.risk.RiskDeviceInfoModel;
import com.cloud.model.user.UserLoan;
import com.cloud.order.dao.BasicDao;
import com.cloud.order.model.DateParam;
import com.cloud.order.model.OrderModel;
import com.cloud.order.model.OrderOntherRefuse;
import com.cloud.order.model.OrderTableModel;
import com.cloud.order.service.OrderService;
import com.cloud.order.service.RazorpayService;
import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.cloud.service.feign.collection.CollectionClient;
import com.cloud.service.feign.risk.RiskClient;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * OrderController class
 *
 * @author zhujingtao
 * @date 2019/2/20
 */
@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RazorpayService razorpayService;

    @Autowired
    private RiskClient riskClient;

    @Autowired
    private BasicDao basicDao;

    @Autowired
    private CollectionClient collectionClient;

    @Autowired
    private NotificationSender notificationSender;

    @Autowired(required = false)
    private RedisUtil redisUtil;

    /**
     * 璁㈠崟鏌ヨ
     *
     * @param params
     */
    @GetMapping("/orders-anon/orders")
    public Page<OrderModel> findOrder(@RequestParam Map<String, Object> params) {
        return orderService.findOrder(params);
    }


    @GetMapping("/orders-anon/saveCheckResult")
    public boolean saveCheckResult(@RequestParam String orderNo, @RequestParam Integer node, @RequestParam String rejectReason) {
        return orderService.saveCheckResult(orderNo, node, rejectReason);
    }

    /**
     * 我的訂單查詢
     *
     * @param params 前端传入参数
     * @return
     */
    @PreAuthorize("hasAuthority('audit:order:my') or hasAuthority('permission:all')")
    @GetMapping("/orders-anon/queryMyOrders")
    @ResponseBody
    public JsonResult queryMyOrders(@RequestParam Map<String, Object> params) {
        PageInfo<OrderTableModel> orderTableModelPage = orderService.queryMyOrders(params);
        return JsonResult.ok(orderTableModelPage.getList(), (int) orderTableModelPage.getTotal());
    }

    /**
     * 待领取订单查询
     *
     * @param params 前端传入参数
     * @return
     */
    @GetMapping("/orders-anon/queryUnclaimed")
    @ResponseBody
    public JsonResult queryUnclaimed(@RequestParam Map<String, Object> params) {
        PageInfo<OrderTableModel> orderTableModelPage = orderService.queryUnclaimedOrder(params);
        return JsonResult.ok(orderTableModelPage.getList(), (int) orderTableModelPage.getTotal());
    }

    /**
     * 订单页面查询
     *
     * @param params 前端传入参数
     * @return
     */
    @PreAuthorize("hasAuthority('order:query') or hasAuthority('permission:all')")
    @GetMapping("/orders-anon/queryOrders")
    @ResponseBody
    public JsonResult queryOrders(@RequestParam Map<String, Object> params) {
        PageInfo<OrderTableModel> orderTableModelPage = orderService.queryAllOrderList(params);
        List<OrderTableModel> orderTableModels = orderTableModelPage.getList();
//          获取订单效率
        for (OrderTableModel orderTableModel : orderTableModels) {
            if (orderTableModel.getType() != null) {
                if (orderTableModel.getType() == 1 && orderTableModel.getStatus() == 3) {
                    orderTableModel.setStatus(1);
                }
                if (orderTableModel.getType() == 2 && orderTableModel.getStatus() == 5) {
                    orderTableModel.setStatus(1);
                }
            }
        }
        return JsonResult.ok(orderTableModelPage.getList(), (int) orderTableModelPage.getTotal());
    }

    /**
     * 终审订单列表查询
     *
     * @param params 前端传入参数
     * @return
     */
    @GetMapping("/orders-anon/queryEndCheckOrders")
    @ResponseBody
    public JsonResult queryEndCheckOrders(@RequestParam Map<String, Object> params) {
        PageInfo<OrderTableModel> orderTableModelPage = orderService.queryEndCheckOrders(params);
        return JsonResult.ok(orderTableModelPage.getList(), (int) orderTableModelPage.getTotal());
    }

    /**
     * 初审列表查询
     *
     * @param params 前端传入参数
     * @return
     */
    @GetMapping("/orders-anon/queryFirstCheckOrders")
    @ResponseBody
    public JsonResult queryFirstCheckOrders(@RequestParam Map<String, Object> params) {
        PageInfo<OrderTableModel> orderTableModelPage = orderService.queryFirstCheckOrders(params);
        return JsonResult.ok(orderTableModelPage.getList(), (int) orderTableModelPage.getTotal());

    }

    /**
     * 领取订单
     *
     * @param params
     * @return
     */
    @PostMapping("orders-anon/takeOrder")
    public JsonResult takeOrder(@RequestBody Map<String, Object> params) {
        //获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        params.put("auditorName", name);

        return orderService.takeOrder(params);
    }

    /**
     * 分配订单
     *
     * @param params 前端传入参数
     * @return
     */
    @PreAuthorize("hasAuthority('audit:dispatch:update') or hasAuthority('permission:all')")
    @PostMapping("orders-anon/assignOrder")
    public JsonResult assignOrder(@RequestBody Map<String, Object> params) {
        //获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        params.put("dispatcherName", name);

        return orderService.assignOrder(params);

    }


    /**
     * 获取审核人列表
     *
     * @return 审核人列表
     * 1: - 初审
     * 2: - 终审
     * 3: - 所有
     */
    @GetMapping("orders-anon/getAuditor")
    public JsonResult getAuditor(@RequestParam int type) {
        return orderService.getAuditor(type);
    }

    /**
     * 初审通过
     *
     * @param params
     * @return
     */
    @PostMapping("orders-anon/firstJudgment")
    public JsonResult firstJudgment(@RequestBody Map<String, Object> params) {
        //获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        params.put("auditorName", name);
        try {
            if (redisUtil.isLock("first_pass:" + params.get("orderId"))) {
                return JsonResult.errorMsg("请勿重复提交");
            }
            orderService.firstJudgment(params);
            return JsonResult.ok();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            redisUtil.remove("first_pass:" + params.get("orderId"));
            return JsonResult.errorException("failure:" + e.getLocalizedMessage());
        }
    }

    /**
     * 初审拒绝
     *
     * @param params
     * @return
     */
    @PostMapping("orders-anon/firstRefuse")
    public JsonResult firstRefuse(@RequestBody Map<String, Object> params) {
        //获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        params.put("auditorName", name);
        try {
            if (redisUtil.isLock("first_reject:" + params.get("orderId"))) {
                return JsonResult.errorMsg("请勿重复提交");
            }
            orderService.firstRefuse(params);

            //初审通过短信
            sendAuditRejectMessage(params);

            return JsonResult.ok();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            redisUtil.remove("first_reject:" + params.get("orderId"));
            return JsonResult.errorException("failure:" + e.getLocalizedMessage());
        }
    }

    /**
     * razorpay回调
     *
     * @param razorpayEventResponse
     * @param request
     */
    @PostMapping("/orders-anon/loanCallback")
    public void loanCallback(@RequestBody String razorpayEventResponse, HttpServletRequest request) {
        String receivedSignature = request.getHeader("X-Razorpay-Signature");
        razorpayService.handleWebhook(razorpayEventResponse, receivedSignature);
    }

    /**
     * 终审通过
     *
     * @param params
     * @return
     */
    @PostMapping("orders-anon/finalJudgment")
    public JsonResult finalJudgment(@RequestBody Map<String, Object> params) {
        String orderId = (String) params.get("orderId");
        Assert.hasText(orderId, "终审订单id为空");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        params.put("auditorName", name);

        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>开始处理订单【id:{}】的终审通过以及放款", orderId);
        try {
            //重复请求锁定
            if (redisUtil.isLock("final_pass:" + orderId)) {
                return JsonResult.errorMsg("请勿重复提交");
            }
            Map<String, Object> map = orderService.finalJudgment(params);

            String judgment = map.get("judgment") == null ? "failure:null" : map.get("judgment").toString();

            String pay = map.get("pay") == null ? "failure:null" : map.get("pay").toString();

            if (judgment.contains("ok") && (pay.contains("ok"))) {
                return JsonResult.ok();
            } else {
                return JsonResult.errorException("终审结果:" + judgment + ",放款结果:" + pay);
            }
        } catch (Exception e) {
            log.error("终审异常", e);
            redisUtil.remove("final_pass:" + orderId);
            return JsonResult.errorException("failure:系统错误!");
        }
    }

    /**
     * 终审拒绝
     *
     * @param params
     * @return
     */
    @PostMapping("orders-anon/finalRefuse")
    public JsonResult finalRefuse(@RequestBody Map<String, Object> params) {
        //获取当前用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        params.put("auditorName", name);
        try {
            if (redisUtil.isLock("final_reject:" + params.get("orderId"))) {
                return JsonResult.errorMsg("请勿重复提交");
            }
            orderService.finalRefuse(params);

            sendAuditRejectMessage(params);
            return JsonResult.ok();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            redisUtil.remove("final_reject:" + params.get("orderId"));
            return JsonResult.errorException("failure:" + e.getLocalizedMessage());
        }
    }

    private void sendAuditRejectMessage(@RequestBody Map<String, Object> params) {
        String orderId = (String) params.get("orderId");
        log.info("终审订单id：{}", orderId);
        OrderTableModel userOrder = orderService.queryUserOrderById(Integer.valueOf(orderId));
        NotificationDto notificationDto = NotificationDto.builder()
                .templateType(NotificationTemplateTypeEnum.AUDIT_REJECT.getCode())
                .userId(userOrder.getUserId())
                .build();
        notificationSender.send(notificationDto);
    }

    /**
     * 获取标签列表
     *
     * @return
     */
    @GetMapping("orders-anon/getTags")
    public JsonResult getTags() {
        return orderService.getTags();
    }

    /**
     * 获取订单审批记录
     *
     * @param orderId
     * @return
     */
    @PreAuthorize("hasAnyAuthority('audit:order:detail:auditLog', 'permission:all') or hasPermission(#params[\"orderNum\"], 'r')")
    @GetMapping("orders-anon/getOrderCheckLog")
    public JsonResult getOrderCheckLog(@RequestParam String orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        return orderService.getOrderCheckLog(orderId, name);
    }


    /**
     * 获取订单详情
     *
     * @param orderId 订单ID
     * @return
     */
    @GetMapping("orders-anon/getOrderDetail")
    @ResponseBody
    public JsonResult getOrderDetail(@RequestParam String orderId) {
        return orderService.getOrderDetail(orderId);
    }


    /**
     * getOrderDetails
     * 平台审批统计报表
     *
     * @return
     */
    @PreAuthorize("hasAuthority('audit:report:platform') or hasAuthority('permission:all')")
    @GetMapping("orders-anon/getSystemCount")
    public JsonResult getSystemCount(@RequestParam Map<String, Object> params) {
        return orderService.getSystemCount(params);
    }


    /**
     * 个人审批统计报表
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('audit:report:person') or hasAuthority('permission:all')")
    @GetMapping("orders-anon/getPersonCount")
    public JsonResult getPersonCount(@RequestParam Map<String, Object> params) {
        return orderService.getPersonCount(params);
    }

    /**
     * 个人审批统计报表详情
     *
     * @return
     */
    @PreAuthorize("hasAuthority('audit:report:person') or hasAuthority('permission:all')")
    @GetMapping("orders-anon/getPersonCountDetail")
    public JsonResult getPersonCountDetail(@RequestParam Map<String, Object> params) {
        return orderService.getPersonCountDetail(params);
    }

    /**
     * 获取未领取订单数
     *
     * @return 未领取订单数
     */
    @PreAuthorize("hasAuthority('audit:order:unclaimed') or hasAuthority('permission:all')")
    @GetMapping("orders-anon/getAllocatedOrderCount")
    @ResponseBody
    public JsonResult getAllocatedOrderCount() {
        int orderCount = orderService.getAllocatedOrderCount();
        Map<String, Object> dataMap = new HashedMap<>(16);
        dataMap.put("orderCount", orderCount);
        return JsonResult.ok(dataMap);
    }

    @GetMapping("orders-anon/getUserOrderByOrderNum")
    public AppUserOrderInfo getUserOrderByOrderNum(@RequestParam String orderNum) {
        return orderService.getUserOrderByOrderNum(orderNum);
    }

    @PostMapping("orders-anon/getUserOrderByUserId")
    public AppUserOrderInfo getUserOrderByUserId(@RequestBody String userId) {
        return orderService.getUserOrderByUserId(userId);
    }


    /**
     * 同一公司名称申请人数
     */
    @GetMapping("/orders-anon/getOrderCountInSameCompanyName")
    public int getOrderCountInSameCompanyName(@RequestParam String orderNo) {
        return orderService.getOrderCountInSameCompanyName(orderNo);
    }

    /**
     * 借据pdf
     */
    @GetMapping("orders-anon/loan/pdf")
    public JsonResult getLoanPdf(@RequestParam String loanNo) {
        log.info("start api: -- orders-anon/loan/pdf");
        try {
            String url = orderService.getLoanPdf(loanNo);
            return JsonResult.ok(url);
        } catch (Exception e) {
            return JsonResult.ok("");
        }
    }

    /**
     * 同一单位电话匹配申请人数
     */
    @GetMapping("/orders-anon/orderCountInSameCompanyPhone")
    public int getOrderCountInSameCompanyPhone(@RequestParam String orderNum) {
        return orderService.getOrderCountInSameCompanyPhone(orderNum);
    }

    /**
     * 合同pdf
     */
    @GetMapping("orders-anon/loan/orderContract/pdf")
    public JsonResult getLoanOrderContractPdf(@RequestParam String loanNo) {
        log.info("start api: -- orders-anon/loan/orderContract/pdf");
        try {
            String url = orderService.getLoanOrderContractPdf(loanNo);
            return JsonResult.ok(url);
        } catch (Exception e) {
            return JsonResult.ok("");
        }
    }

    /**
     * 展期pdf
     */
    @GetMapping("orders-anon/loan/extensionContract/pdf")
    public JsonResult getExtensionContractPdf(@RequestParam long extensionId) {
        log.info("start api: -- orders-anon/loan/extensionContract/pdf");
        try {
            String url = orderService.getExtensionContractPdf(extensionId);
            return JsonResult.ok(url);
        } catch (Exception e) {
            return JsonResult.ok("");
        }
    }

    /**
     * 查询通讯录信息
     */
//    @PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
//            "or (hasAuthority('audit:order:detail:addressBook') and hasPermission(#params[\"orderNum\"], 'r'))")
    @GetMapping("/orders-anon/getAddressBookByOrderNum")
    public JsonResult getAddressBookByOrderNum(@RequestParam Map<String, Object> params) {
        String orderNum = (String) params.get("orderNum");
        if (params.get("isCollection") == null) {
            orderNum = orderService.findOrderNumByOrderId((String) params.get("orderNum"));
        } else if (MapUtils.getInteger(params, "isCollection") == 1) {
            orderNum = collectionClient.getOrderNoById(MapUtils.getInteger(params, "orderNum"));
        }

        params.put("orderNum", orderNum);
        String param = JSON.toJSONString(params);
        return riskClient.getAddressBookByOrderNum(param);
    }

    /**
     * 查询设备信息
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
            "or (hasAuthority('audit:order:detail:device') and hasPermission(#params[\"orderNum\"], 'r'))")
    @GetMapping("/orders-anon/getDeviceInfoByOrderNum")
    public JsonResult getDeviceInfoByOrderNum(@RequestParam Map<String, Object> params) {
        String orderNum = orderService.findOrderNumByOrderId((String) params.get("orderNum"));
        params.put("orderNum", orderNum);
        return riskClient.getDeviceInfoByOrderNum(params);
    }

    /**
     * 查询通话记录
     *
     * @param params
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
//            "or (hasAuthority('audit:order:detail:callRecord') and hasPermission(#params[\"orderNum\"], 'r'))")
    @GetMapping("/orders-anon/getCallRecordByOrderNum")
    public JsonResult getCallRecordByOrderNum(@RequestParam Map<String, Object> params) {
        String orderNum = (String) params.get("orderNum");
        if (params.get("isCollection") == null) {
            orderNum = orderService.findOrderNumByOrderId((String) params.get("orderNum"));
        } else if (MapUtils.getInteger(params, "isCollection") == 1) {

            orderNum = collectionClient.getOrderNoById(MapUtils.getInteger(params, "orderNum"));
        }
        params.put("orderNum", orderNum);
        return riskClient.getCallRecordByOrderNum(params);
    }

    /**
     * 查询短信
     *
     * @return 短信记录
     * @author zhujingtao
     */
    @PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
            "or (hasAuthority('audit:order:detail:msgRecord') and hasPermission(#params[\"orderNum\"], 'r'))")
    @GetMapping("/orders-anon/getMessage")
    public JsonResult getMessage(@RequestParam Map<String, Object> params) {
        String orderNum = orderService.findOrderNumByOrderId((String) params.get("orderNum"));
        params.put("orderNum", orderNum);
        return riskClient.getMessage(params);
    }


    /**
     * 查询呼叫次数
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
            "or (hasAuthority('audit:order:detail:callData') and hasPermission(#params[\"orderNum\"], 'r'))")
    @GetMapping("/orders-anon/getCallCountByOrderNum")
    public JsonResult getCallCountByOrderNum(@RequestParam Map<String, Object> params) {
        String orderNum = orderService.findOrderNumByOrderId((String) params.get("orderNum"));
        params.put("orderNum", orderNum);
        return riskClient.getCallCountByOrderNum(params);
    }

    /**
     * 查询历史匹配信息
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
            "or (hasAuthority('audit:order:detail:history') and hasPermission(#params[\"orderNum\"], 'r'))")
    @GetMapping("/orders-anon/getHistoryMatchs")
    public JsonResult getHistoryMatchs(@RequestParam Map<String, Object> params) {
        String orderNum = orderService.findOrderNumByOrderId((String) params.get("orderNum"));
        params.put("orderNum", orderNum);
        return riskClient.getHistoryMatchs(params);
    }

    /**
     * 查询预警信息
     *
     * @return
     */
    @PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
            "or (hasAuthority('audit:order:detail:warning') and hasPermission(#orderNum, 'r'))")
    @GetMapping("/orders-anon/getAlterList")
    public JsonResult queryRiskStoreListByOrderNum(@RequestParam String orderNum,
                                                   @RequestParam Integer approveStatus) {
        return riskClient.queryRiskStoreListByOrderNum(orderNum, approveStatus);
    }

    /**
     * 查询短信占比率
     *
     * @param orderNum
     * @return
     */
    @PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
            "or (hasAuthority('audit:order:detail:msgRecord') and hasPermission(#orderNum, 'r'))")
    @GetMapping("/orders-anon/selectMessageReadRate")
    public JsonResult selectMessageReadRate(@RequestParam String orderNum) {
        orderNum = orderService.findOrderNumByOrderId(orderNum);
        return riskClient.selectMessageReadRate(orderNum);
    }

    @GetMapping("/orders-anon/isCreatedOrderByCurrentUser")
    public boolean isCreatedOrderByCurrentUser(@RequestParam List<Long> userIds, @RequestParam Long currentUserId) {
        AtomicBoolean isCreatedByCurrentUser = new AtomicBoolean(false);
        Optional.ofNullable(orderService.getFirstOrderCreatedByUserIds(userIds))
                .ifPresent(order -> isCreatedByCurrentUser.set(order.getUserId().equals(currentUserId)));
        return isCreatedByCurrentUser.get();
    }

    /**
     * ssp
     * ordernum to id
     */
    @GetMapping("/orders-anon/queryOrderNumToId")
    public int queryOrderNumToId(@RequestParam String orderNum) {
        return orderService.queryOrderNumToId(orderNum);
    }

    @PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
            "or (hasAuthority('audit:order:detail:history') and hasPermission(#params[\"orderNo\"], 'r'))")
    @GetMapping("/orders-anon/getSamePhoneCompanyByOrderNo")
    public JsonResult getSamePhoneCompanyByOrderNo(@RequestParam Map<String, Object> params) {
        List<OrderHistoryDto> orderHistoryDtos = orderService.getSamePhoneCompanyByOrderNo(params);
        Integer limit = MapUtils.getInteger(params, "limit");
        Integer page = MapUtils.getInteger(params, "page");
        return JsonResult.ok(DataPageUtil.pageLimit(orderHistoryDtos, page, limit), orderHistoryDtos.size());
    }

    @PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
            "or (hasAuthority('audit:order:detail:history') and hasPermission(#params[\"orderNo\"], 'r'))")
    @GetMapping("/orders-anon/getSameCompanyNameByOrderNo")
    public JsonResult getSameCompanyNameByOrderNo(@RequestParam Map<String, Object> params) {
        List<OrderHistoryDto> orderHistoryDtos = orderService.getSameCompanyNameByOrderNo(params);
        Integer limit = MapUtils.getInteger(params, "limit");
        Integer page = MapUtils.getInteger(params, "page");
        return JsonResult.ok(DataPageUtil.pageLimit(orderHistoryDtos, page, limit), orderHistoryDtos.size());
    }

    @PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
            "or (hasAuthority('audit:order:detail:history') and hasPermission(#params[\"orderNo\"], 'r'))")
    @GetMapping("/orders-anon/getSameDeviceInfoByOrderNo")
    public JsonResult getSameDeviceInfoByOrderNo(@RequestParam Map<String, Object> params) {
        try {
            List<RiskDeviceInfoModel> riskDeviceInfoModels =
                    riskClient.queryImiNoAndOrderNobyOrderNo(MapUtils.getString(params, "orderNo"));
            params.put("RiskDeviceInfoModelList", riskDeviceInfoModels);
            List<OrderHistoryDto> orderHistoryDtos = orderService.getSameDeviceInfoByOrderNo(params);
            Integer limit = MapUtils.getInteger(params, "limit");
            Integer page = MapUtils.getInteger(params, "page");
            return JsonResult.ok(DataPageUtil.pageLimit(orderHistoryDtos, page, limit), orderHistoryDtos.size());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.errorException(e.getLocalizedMessage());
        }

    }

    //    @PreAuthorize("hasAnyAuthority('order:detail', 'permission:all') " +
//            "or (hasAuthority('audit:order:detail:history') and hasPermission(#params[\"orderNo\"], 'r'))")
    @GetMapping("/orders-anon/getSameDeviceInfoByRelationMan")
    public JsonResult getSameDeviceInfoByRelationMan(@RequestParam Map<String, Object> params) {
        try {
            Integer limit = MapUtils.getInteger(params, "limit");
            Integer page = MapUtils.getInteger(params, "page");
            List<OrderHistoryDto> orderHistoryDtos = orderService.getSameDeviceInfoByRelationMan(params);
            return JsonResult.ok(DataPageUtil.pageLimit(orderHistoryDtos, page, limit), orderHistoryDtos.size());
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.errorException(e.getLocalizedMessage());
        }

    }


    //试算最后一步校验用户是否可以借款
    @GetMapping("/orders-anon/internal/finalJudgeForLoanOrder")
    public boolean finalJudgeForLoanOrder(@RequestParam int userId) {

        return orderService.finalJudgeForLoanOrder(userId);
    }


    //试算最后一步校验用户是否可以借款
    @GetMapping("/orders-anon/internal/finalJudgeForLoan")
    public boolean finalJudgeForLoan(@RequestParam int userId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("校验用户【{}】是否可以借款>>>>", userId);
        UserLoan userLoan = basicDao.getLatestLoanByUserId(userId);

        Map<String, Object> userOrder = basicDao.getLatestRejectOrder(userId);
        if (userOrder == null) {
            return true;
        }
        Date finalAuditTime = (Date) userOrder.get("finalAuditTime");
        Integer checkStatus = (Integer) userOrder.get("checkStatus");
        log.info("用户【{}】的最后审核时间：{}，状态：{}", userId, sdf.format(finalAuditTime), checkStatus);
        boolean var1 = false;
        boolean var2 = false;
        if (userLoan == null) {
            log.info("条件1pass 该用户无借款行为 可以借款");
            var1 = true;
        } else if (userLoan.getLoanStatus() == ProductConstants.FINISH) {
            log.info("条件1pass 该用户最近一条借据已完成 可以借款");
            var1 = true;
        } else if (userLoan.getLoanStatus() == ProductConstants.NOT_ACTIVATED) {
            if (DateUtil.getDayCount(userLoan.getCreateTime(), new Date()) >= CommonConfig.REAPPLYPERIOD) {
                log.info("条件1pass 该用户最近一条借据为未激活 但是已经超过七天");
                var1 = true;
            } else {
                log.info("条件1reject 该用户最近一条借据状态为未激活 未超过七天  创建时间{}", sdf.format(userLoan.getCreateTime()));
            }
        } else {
            log.info("条件1reject 借据未完成");
        }

        if (checkStatus == CheckStatus.PASSED.toNum()) {
            var2 = true;
        } else if (checkStatus == CheckStatus.MECHINEREFUSE.toNum() || checkStatus == CheckStatus.FIRSTREFUSE.toNum() || checkStatus == CheckStatus.FINALREFUSE.toNum()) {
            if (finalAuditTime == null) {
                log.info("条件2pass 该用户没有拒绝的订单");
                var2 = true;
            } else if (DateUtil.getDayCount(finalAuditTime, new Date()) >= CommonConfig.REAPPLYPERIOD) {
                log.info("条件2pass 该用户最近一条订单的拒绝时间为{} 已经超过七天  可以重新申请", sdf.format(finalAuditTime));
                var2 = true;
            }
        }

        return var1 && var2;
    }

    /**
     * 获取个人审批统计报表
     *
     * @param params
     * @return
     * @author baijieye
     */
    @GetMapping("/orders-anon/getPersonHistoryReport")
    public JsonResult getPersonHistoryReport(@RequestParam Map<String, Object> params) {
        return orderService.getPersonHistoryReport(params);
    }

    /**
     * 通過UserId 獲取 orderNo
     *
     * @param userId
     * @return
     * @author baijieye
     */
    @GetMapping("/orders-anon/getOrderNoByUserId")
    public List<String> getOrderNoByUserId(@RequestParam Long userId) {
        return orderService.getOrderNoByUserId(userId);
    }


    @GetMapping("/orders-anon/getOrderByOrderNos")
    public List<OrderTableModel> getOrderByOrderNos(@RequestParam String orderNos) {
        return orderService.getOrderByOrderNos(orderNos);
    }


    /**
     * 获取 今日 个人列表
     *
     * @param params
     * @return
     */
    @GetMapping("/orders-anon/getPersonNowReport")
    public JsonResult getPersonNowReport(@RequestParam Map<String, Object> params) {

        return orderService.getPersonNowReport(params);
    }

    /**
     * 获取平台审批统计报表
     *
     * @param params
     * @return
     * @author baijieye
     */
    @GetMapping("/orders-anon/getSystemHistoryReport")
    public JsonResult getSystemHistoryReport(@RequestParam Map<String, Object> params) {
        return orderService.getSystemHistoryReport(params);
    }

    /**
     * 获取 实时  平台 报表
     *
     * @return
     */
    @GetMapping("/orders-anon/getSystemNowReport")
    public JsonResult getSystemNowReport() {
        return orderService.getSystemNowReport();
    }

    @GetMapping("/orders-anon/getSameUserInfoByOrderNo")
    public JsonResult getSameUserInfoByOrderNo(@RequestParam("orderNo") String orderNo) {
        return orderService.getSameUserInfoByOrderNo(orderNo);
    }

    @GetMapping("/orders-anon/getOrderListByUserId")
    public List<OrderTableModel> getOrderListByUserId(@RequestParam("userId") Long userId) {
        return orderService.getOrderListByUserId(userId);
    }

    @GetMapping("/orders-anon/updateMechineCheckToOrderByOrderNum")
    public boolean updateMechineCheckToOrderByOrderNum(@RequestParam String orderNo, @RequestParam Integer checkStatus) {
        return orderService.updateMechineCheckToOrderByOrderNum(orderNo, checkStatus);
    }

    @GetMapping("/orders-anon/queryAllOrderOntherRefuse")
    public List<OrderOntherRefuse> queryAllOrderOntherRefuse(@RequestParam("orderNo") String orderNo) {

        return orderService.queryAllOrderOntherRefuse(orderNo);
    }


    /**
     * 首頁  第一張 頁面 統計
     *
     * @return
     */
    @PreAuthorize("hasAuthority('home:data') or hasAuthority('permission:all')")
    @GetMapping("/orders-anon/queryIndexFirst")
    public JsonResult queryIndexFirst() {
        return JsonResult.ok(orderService.queryIndexFirst());
    }

    /**
     * 首頁  新增 用戶
     *
     * @return
     */
//    @PreAuthorize("hasAuthority('home:data') or hasAuthority('permission:all')")
    @GetMapping("/orders-anon/queryIndexCustomerNewed")
    public JsonResult queryIndexCustomerNewed(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        DateParam dateParam = DateParam.builder().startDate(StringUtils.isBlank(startDate)
                ? null : DateUtil.getDates(startDate, "yyyy-MM-dd"))
                .endDate(StringUtils.isBlank(endDate) ? null : DateUtil.getDates(endDate, "yyyy-MM-dd")).build();
        return JsonResult.ok(orderService.queryIndexCustomerNewed(dateParam));
    }


    /**
     * 首頁  新增 用戶
     *
     * @return
     */
//    @PreAuthorize("hasAuthority('home:data') or hasAuthority('permission:all')")
    @GetMapping("/orders-anon/queryIndexDueIng")
    public JsonResult queryIndexDueIng(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        DateParam dateParam = DateParam.builder().startDate(StringUtils.isBlank(startDate)
                ? null : DateUtil.getDates(startDate, "yyyy-MM-dd"))
                .endDate(StringUtils.isBlank(endDate) ? null : DateUtil.getDates(endDate, "yyyy-MM-dd")).build();
        return JsonResult.ok(orderService.queryIndexDueIng(dateParam));
    }

    /**
     * 首頁  d1  等逾期報表寫入
     *
     * @return
     */
//    @PreAuthorize("hasAuthority('home:data') or hasAuthority('permission:all')")
    @GetMapping("/orders-anon/queryIndexDueType")
    public JsonResult queryIndexDueType(@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        DateParam dateParam = DateParam.builder().startDate(StringUtils.isBlank(startDate)
                ? null : DateUtil.getDates(startDate, "yyyy-MM-dd"))
                .endDate(StringUtils.isBlank(endDate) ? null : DateUtil.getDates(endDate, "yyyy-MM-dd")).build();
        return JsonResult.ok(orderService.queryIndexDueType(dateParam));
    }

    /**
     * 首頁  訂單通過
     *
     * @return
     */
//    @PreAuthorize("hasAuthority('home:data') or hasAuthority('permission:all')")
    @GetMapping("/orders-anon/queryIndexOrderPass")
    public JsonResult queryIndexOrderPass(@RequestParam(required = false) String startDate
            , @RequestParam(required = false) String endDate) {
        DateParam dateParam = DateParam.builder().startDate(StringUtils.isBlank(startDate)
                ? null : DateUtil.getDates(startDate, "yyyy-MM-dd"))
                .endDate(StringUtils.isBlank(endDate) ? null : DateUtil.getDates(endDate, "yyyy-MM-dd")).build();
       return JsonResult.ok(orderService.queryIndexOrderPass(dateParam));
    }

    @GetMapping("/orders-anon/userOrderCount")
    public int countUserOrderByUserId(@RequestParam  Long userId) {
        return orderService.countGetUserOrderByUserId(userId);
    }

    @GetMapping("/orders-anon/noRepayLoanCount")
    int countGetNoRepayLoanByUserId(@RequestParam  Long userId) {
        return orderService.countGetNoRepayLoanByUserId(userId);
    }

    @GetMapping("/orders-anon/sameAadhaarAddress/loanStatus/list/")
    List<Integer> listGetLoanStatusBySameAadhaarAddress(@RequestParam  String address) {
        return orderService.listGetLoanStatusBySameAadhaarAddress(address);
    }

    @GetMapping("/orders-anon/sameAadhaarAddress/dueOrder/list")
    List<String> listGetDueOrderNumBySameAadhaarAddress(@RequestParam  String address) {
        return orderService.listGetDueOrderNumBySameAadhaarAddress(address);
    }

    @GetMapping("/orders-anon/loanStatus/list/")
    List<Integer> listGetLoanStatusByOrderNumList(@RequestParam  List<String> orderNumList) {
        return orderService.listGetLoanStatusByOrderNumList(orderNumList);
    }

    @GetMapping("/orders-anon/dueOrderNum/list/")
    List<String> listGetRepayedDueOrderNumInOrderNumList(@RequestParam  List<String> orderNumList) {
        return orderService.listGetRepayedDueOrderNumInOrderNumList(orderNumList);
    }

    @GetMapping("/orders-anon/currentDueOrderNum/list/")
    List<String> listGetCurrentDueOrderNumByPhoneList(@RequestParam  List<String> phoneList) {
        return orderService.listGetCurrentDueOrderNumByPhoneList(phoneList);
    }

    /**
     * 找出上一次还完款的借据单号
     */
    @GetMapping("/orders-anon/{userId}/lastRePayedLoanOrderNum")
    String getUserLastRePayedLoanOrderNum(@PathVariable Long userId) {
        return orderService.getUserLastRePayedLoanOrderNum(userId);
    }

    @GetMapping("/orders-anon/dueDaysList")
    List<Integer> listGetDueDaysListByOrderNum(@RequestParam String orderNum) {
        return orderService.listGetDueDaysListByOrderNum(orderNum);
    }

    /**
     * 从号码中找出申请过的号码
     */
    @GetMapping("/finance-anon/applyedPhone/list")
    List<String> listGetApplyPhonesInPhones(@RequestParam List<String> phoneList) {
        return orderService.listGetApplyPhonesInPhones(phoneList);
    }
}
