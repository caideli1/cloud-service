package com.cloud.order.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.order.model.OrderTableModel;
import com.cloud.order.service.FinanceLoanService;
import com.cloud.order.service.OrderService;
import com.cloud.redis.autoconfig.utils.RedisUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/1 13:34
 * 描述：放款池操作
 */
@Slf4j
@RestController
public class OrderLendingPoolController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FinanceLoanService loanService;

    @Autowired(required = false)
    private RedisUtil redisUtil;

    /**
     * 放款池列表 created by caideli 2019/08/02
     * @param applyNum 申请编号
     * @param userPhone 手机号
     * @param term 借款期限
     * @param loanAmount 借款金额
     * @param startFinalAuditTime 审核时间开始区间
     * @param endFinalAuditTime 审核时间结束区间
     * @return
     */
    @PreAuthorize("hasAuthority('lendingPool:query') or hasAuthority('permission:all')")
    @GetMapping("/orders-anon/queryLendingPoolOrders")
    @ResponseBody
    public JsonResult queryLendingPoolOrders(String applyNum, String userPhone, Integer term,
                                             BigDecimal loanAmount, String startFinalAuditTime, String endFinalAuditTime,
                                             @RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer limit) {
        PageInfo<OrderTableModel> orderTableModelPage = orderService.queryLendingPoolOrderList(applyNum,userPhone,term,loanAmount,startFinalAuditTime,endFinalAuditTime,page,limit);
        return JsonResult.ok(orderTableModelPage.getList(), (int) orderTableModelPage.getTotal());
    }

    /**
     * 加入放款池
     *  orderId 订单号
     *  reason 填写的审核通过的原因
     *  isWarning 是否预警: 0.否 1.是
     *  tagIds 标签ids以逗号隔开 例如 1，2，3
     * @return
     */
    //@PreAuthorize("hasAuthority('lendingPool:lendingPool') or hasAuthority('permission:all')")
    @PostMapping("orders-anon/lendingPool")
    public JsonResult lendingPool(@RequestBody Map<String, Object> params) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>开始处理订单【id:{}】的加入放款池", params.get("orderId"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        if (params.get("orderId")==null||params.get("isWarning")==null||params.get("tagIds")==null){
            return JsonResult.errorMsg("参数不全！");
        }
        try {
            //重复请求锁定
            if (redisUtil.isLock("lending_pool:" + params.get("orderId"))) {
                return JsonResult.errorMsg("请勿重复提交");
            }
            orderService.lendingPool(params.get("orderId").toString(),
                    params.get("reason")==null?null:params.get("reason").toString(),
                    Integer.parseInt(params.get("isWarning").toString()),
                    params.get("tagIds").toString(),name);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>结束处理订单【id:{}】的加入放款池", params.get("orderId"));
            return JsonResult.ok();
        } catch (Exception e) {
            log.error("加入放款池异常！", e);
            return JsonResult.errorException("failure:系统错误!");
        }finally {
            redisUtil.remove("lending_pool:" + params.get("orderId"));
        }
    }

    /**
     * 放款池中批量放款
     *
     * @param idList
     * @return
     */
    //@PreAuthorize("hasAuthority('lendingPool:batchLendingLoan') or hasAuthority('permission:all')")
    @PostMapping("/orders-anon/lendingPoolLoan")
    public JsonResult lendingPoolLoan(String[] idList) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>开始处理放款池中批量放款【id:{}】", idList);
        if (ArrayUtils.isEmpty(idList)) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>放款池中批量放款的id为空");
            return JsonResult.errorException("放款失败记录id为空");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        //Arrays.asList(idList).stream().forEach(id -> orderService.lendingPoolLoan(id,name));
        for (String id:idList){
            //重复请求锁定
            if (redisUtil.isLock("lending_pool_loan:" + id)) {
                return JsonResult.errorMsg("请勿重复提交");
            }
            try {
                orderService.lendingPoolLoan(id,name);
            } catch (Exception e) {
                log.error("放款池放款失败的id是："+id+"失败信息是："+e.getMessage(),e);
            }finally {
                redisUtil.remove("lending_pool_loan:" + id);
            }
        }
        return JsonResult.ok();
    }

}
