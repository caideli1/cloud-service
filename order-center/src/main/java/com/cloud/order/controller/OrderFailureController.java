package com.cloud.order.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.common.redis.RedisKey;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.order.OrderFailureVo;
import com.cloud.order.service.FinancePayLogService;
import com.cloud.service.feign.pay.PayClient;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/8/26 11:56
 * 描述：
 */
@Slf4j
@RestController
public class OrderFailureController {
    @Autowired
    private FinancePayLogService financePayLogService;
    @Autowired
    private PayClient payClient;
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 还款失败订单列表
     * @param page
     * @param limit
     * @param userPhone 手机号模糊
     * @param finalFailureTimeStart 最后失败时间 起始查询条件
     * @param finalFailureTimeEnd 最后失败时间 结束查询条件
     * @param shouldRepayTimeEnd 应还时间 结束查询条件
     * @param shouldRepayTimeStart 应还时间 起始查询条件
     * @param finalFailureTimeOrder 最后失败时间 1 正序排序 0 倒序排序
     * @param shouldRepayTimeOrder 应还时间 1 正序排序 0 倒序排序
     * @param dueDaysOrder 逾期天数 1 正序排序 0 倒序排序
     * @param countNumOrder 还款失败次数 1 正序排序 0 倒序排序
     * @return
     */
    @GetMapping("/failure/getOrderFailureList")
    public JsonResult getOrderFailureList(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int limit,
                                          String userPhone,String finalFailureTimeStart,String finalFailureTimeEnd,
                                          String shouldRepayTimeEnd,String shouldRepayTimeStart,Integer finalFailureTimeOrder,
                                          Integer shouldRepayTimeOrder,Integer dueDaysOrder,Integer countNumOrder) {
        Map<String, Object> params = new HashMap<String, Object>();
        setParams(params,userPhone,finalFailureTimeStart,finalFailureTimeEnd,
                shouldRepayTimeEnd,shouldRepayTimeStart,finalFailureTimeOrder,
                shouldRepayTimeOrder,dueDaysOrder,countNumOrder);
        List<OrderFailureVo> orderFailureVoList = financePayLogService.getOrderFailureList(params,page,limit);
        PageInfo pageInfo = new PageInfo<>(orderFailureVoList);
        return JsonResult.ok(pageInfo, (int) pageInfo.getTotal());
    }

    /**
     * 我的催收员的还款失败订单列表
     * @param page
     * @param limit
     * @param userPhone 手机号模糊
     * @param finalFailureTimeStart 最后失败时间 起始查询条件
     * @param finalFailureTimeEnd 最后失败时间 结束查询条件
     * @param shouldRepayTimeEnd 应还时间 结束查询条件
     * @param shouldRepayTimeStart 应还时间 起始查询条件
     * @param finalFailureTimeOrder 最后失败时间 1 正序排序 0 倒序排序
     * @param shouldRepayTimeOrder 应还时间 1 正序排序 0 倒序排序
     * @param dueDaysOrder 逾期天数 1 正序排序 0 倒序排序
     * @param countNumOrder 还款失败次数 1 正序排序 0 倒序排序
     * @return
     */
    @GetMapping("/failure/getMyOrderFailureList")
    public JsonResult getMyOrderFailureList(@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "10") int limit,
                                          String userPhone,String finalFailureTimeStart,String finalFailureTimeEnd,
                                          String shouldRepayTimeEnd,String shouldRepayTimeStart,Integer finalFailureTimeOrder,
                                          Integer shouldRepayTimeOrder,Integer dueDaysOrder,Integer countNumOrder) {
        Map<String, Object> params = new HashMap<String, Object>();
        setParams(params,userPhone,finalFailureTimeStart,finalFailureTimeEnd,
                shouldRepayTimeEnd,shouldRepayTimeStart,finalFailureTimeOrder,
                shouldRepayTimeOrder,dueDaysOrder,countNumOrder);
        params.put("userId", AppUserUtil.getLoginSysUser().getId());
        List<OrderFailureVo> orderFailureVoList = financePayLogService.getMyOrderFailureList(params,page,limit);
        PageInfo pageInfo = new PageInfo<>(orderFailureVoList);
        return JsonResult.ok(pageInfo, (int) pageInfo.getTotal());
    }

    public void setParams(Map<String, Object> params,String userPhone,String finalFailureTimeStart,String finalFailureTimeEnd,
                          String shouldRepayTimeEnd,String shouldRepayTimeStart,Integer finalFailureTimeOrder,
                          Integer shouldRepayTimeOrder,Integer dueDaysOrder,Integer countNumOrder){
        params.put("userPhone", userPhone);
        params.put("finalFailureTimeStart", finalFailureTimeStart);
        params.put("finalFailureTimeEnd", finalFailureTimeEnd);
        params.put("shouldRepayTimeEnd", shouldRepayTimeEnd);
        params.put("shouldRepayTimeStart", shouldRepayTimeStart);
        params.put("finalFailureTimeOrder", finalFailureTimeOrder);
        params.put("shouldRepayTimeOrder", shouldRepayTimeOrder);
        params.put("dueDaysOrder", dueDaysOrder);
        params.put("countNumOrder", countNumOrder);
    }

    /**
     * 我的催收员的三个订单统计数量
     * @return
     */
    @GetMapping("/failure/getMyOrderFailureCount")
    public JsonResult getMyOrderFailureCount() {
        return JsonResult.ok(financePayLogService.getMyOrderFailureCount(AppUserUtil.getLoginSysUser().getId()));
    }

    /**
     * 点击还款失败次数，获取还款失败列表
     * @return
     */
    @PostMapping("/failure/getFinancePayLogList")
    public JsonResult getFinancePayLogList(@RequestParam String orderNo) {
        return JsonResult.ok(financePayLogService.getFailureFinancePayLogListByOrderNo(orderNo));
    }

    /**
     * 将借据标记为 已跟进
     * @param loanNumber 借据编号 也是订单编号
     * @return
     */
    @PostMapping("/failure/follow")
    public JsonResult follow(@RequestParam String loanNumber) {
        RLock distributedLock = null;
        boolean isLock = false;
        try {
            distributedLock = redissonClient.getFairLock(RedisKey.LOCK_FOLLOW+loanNumber);
            if (distributedLock.isLocked()){
                return JsonResult.errorMsg("操作频繁，请稍后重试!");
            }
            //distributedLock.lock(120,TimeUnit.SECONDS);
            isLock = distributedLock.tryLock(2, 30, TimeUnit.SECONDS);
            if (isLock) {
                payClient.follow(loanNumber);
            }else {
                return JsonResult.errorMsg("操作频繁，请稍后重试!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (distributedLock != null && isLock) {
                distributedLock.unlock();
            }
        }
        return JsonResult.ok();
    }
}
