package com.cloud.order.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.order.model.*;
import com.cloud.order.service.FinanceRepayService;
import com.cloud.order.service.FinanceReportService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @Author: zhujingtao
 * @CreateDate: 2019/2/26 14:05
 * 对应数据库表 还款控制类
 */
@Slf4j
@RestController
public class FinanceRepayController {

    @Autowired
    private FinanceReportService reportService;

    @Autowired
    private FinanceRepayService repayService;

    /**
     * 获取汇总报表信息
     *
     * @return 汇总报表信息
     * @Author: zhujingtao
     */
    @PreAuthorize("hasAuthority('loan:record') or hasAuthority('permission:all')")
    @GetMapping("/finance-repay/queryTotalRecord")
    public JsonResult querySumReport() {
        FinanceReportSumRecordModel sumRecordModel = reportService.querySumRecord();
        return JsonResult.ok(sumRecordModel);
    }


    /**
     * 获取汇总报表信息
     *
     * @return 汇总报表信息
     * @Author: zhujingtao
     */
    @GetMapping("/finance-repay/querySumReport")
    public JsonResult queryTotalRecord() {
        FinanceReportSumRecordModel sumRecordModel = reportService.queryTotalRecord();
        return JsonResult.ok(sumRecordModel);

    }

    /**
     * 获取汇总报表信息
     *
     * @param params 前端传入信息
     * @return 汇总报表信息
     * @Author: zhujingtao
     */
    @GetMapping("/finance-repay/queryReport")
    public JsonResult queryReportMessage(@RequestParam Map<String, Object> params) {
        return reportService.queryReportMessage(params);
    }

    /**
     * 获取汇总报表信息
     *
     * @param params 前端传入信息
     * @return 汇总报表信息
     * @Author: zhujingtao
     */
    @PreAuthorize("hasAuthority('repay:list') or hasAuthority('permission:all')")
    @GetMapping("/finance-repay/queryReportMessage")
    public JsonResult queryReport(@RequestParam Map<String, Object> params) {
        //初始化 返回Page
        PageInfo<FinanceReportRecordModel> financeReportRecordModelPageInfo = reportService.queryReport(params);
        return JsonResult.ok(financeReportRecordModelPageInfo.getList(), (int) financeReportRecordModelPageInfo.getTotal());
    }

    /**
     * 获取已还记录信息
     *
     * @param params 前端传入信息
     * @return 封装信息给前端
     * @Author: zhujingtao
     */
    @PreAuthorize("hasAuthority('repay:finished') or hasAuthority('permission:all')")
    @GetMapping("/finance-repay/queryRepaymentPaidList")
    public JsonResult queryRepaymentPaidList(@RequestParam Map<String, Object> params) {
        //初始化 返回Page
        PageInfo<FinanceRepayModel> financeRepayModelPageInfo = repayService.queryRepaymentPaidList(params);
        return JsonResult.ok(financeRepayModelPageInfo.getList(), (int) financeRepayModelPageInfo.getTotal());
    }

    /**
     * 获取未还记录信息
     *
     * @param params 前端传入信息
     * @return 封装信息给前端
     * @Author: zhujingtao
     */
    @PreAuthorize("hasAuthority('repay:no') or hasAuthority('permission:all')")
    @GetMapping("/finance-repay/queryRepaymentUnpaidList")
    public JsonResult queryRepaymentUnpaidList(@RequestParam Map<String, Object> params) {
        //初始化 返回Page
        PageInfo<FinanceRepayModel> financeRepayModelPageInfo = repayService.queryRepaymentUnpaidList(params);
        return JsonResult.ok(financeRepayModelPageInfo.getList(), (int) financeRepayModelPageInfo.getTotal());
    }

    /**
     * 获取支付失败记录信息
     *
     * @param params 前端传入信息
     * @return 封装信息给前端
     * @Author: zhujingtao
     */
    @PreAuthorize("hasAuthority('repay:failure') or hasAuthority('permission:all')")
    @GetMapping("/finance-repay/queryRepaymentFailedList")
    public JsonResult queryRepaymentFailedList(@RequestParam Map<String, Object> params) {
        if (params.get("startPayDate") != null) {
            String startRepayDate = (String) params.get("startPayDate");
            params.put("startRepayDate", startRepayDate);
            params.put("startPayDate", "");
        }
        if (params.get("endPayDate") != null) {
            String endRepayDate = (String) params.get("endPayDate");
            params.put("endRepayDate", endRepayDate);
            params.put("endPayDate", "");
        }
        //初始化 返回Page
        PageInfo<FinanceRepayModel> financeRepayModelPageInfo = repayService.queryRepaymentFailedList(params);
        return JsonResult.ok(financeRepayModelPageInfo.getList(), (int) financeRepayModelPageInfo.getTotal());
    }

    /**
     * 获取逾期列表信息
     *
     * @param params 前端传入信息
     * @return 封装信息给前端
     * @Author: zhujingtao
     */
    @PreAuthorize("hasAuthority('order:due') or hasAuthority('permission:all')")
    @GetMapping("/finance-repay/queryLateOrderList")
    public JsonResult queryLateOrderList(@RequestParam Map<String, Object> params) {
        //初始化 返回Page
        PageInfo<FinanceDueOrderModel> financeRepayModelPageInfo = repayService.queryLateOrderList(params);
        return JsonResult.ok(financeRepayModelPageInfo.getList(), (int) financeRepayModelPageInfo.getTotal());
    }

    /**
     * 删除制定ID的账户
     *
     * @param params 前端传值
     * @return 返回前端 信息
     * @Author: zhujingtao
     */
    @PreAuthorize("hasAuthority('account:manage') or hasAuthority('permission:all')")
    @PostMapping("/finance-repay/deleteAccountManager")
    @ResponseBody
    public JsonResult deleteAccountManager(@RequestBody Map<String, String> params) {
        return repayService.deleteAccountManager(params.get("id"));
    }

    /**
     * 新增/修改账户
     *
     * @param params 前端传值
     * @return 返回前端 信息
     * @Author: zhujingtao
     */
    @PreAuthorize("hasAuthority('account:manage') or hasAuthority('permission:all')")
    @PostMapping("/finance-repay/saveAccountManager")
    @ResponseBody
    public JsonResult saveAccountManager(@RequestBody Map<String, Object> params) {
        if (params.get("id") == null || params.get("id").equals("")) {
            return repayService.insertAccountManager(params);
        } else {
            return repayService.updateAccountManager(params);
        }
    }


    /**
     * 启用或停用
     *
     * @param params 前端传值
     * @return 返回前端 信息
     * @Author: zhujingtao
     */
    @PreAuthorize("hasAuthority('account:manage') or hasAuthority('permission:all')")
    @PostMapping("/finance-repay/updateAccountStation")
    public JsonResult updateAccountStation(@RequestBody Map<String, Object> params) {
        return repayService.updateAccountStation(params);
    }

    /**
     * 查找账户列表
     *
     * @param params 传入名称
     * @return 账户列表查询
     * @Author: zhujingtao
     */
    @PreAuthorize("hasAuthority('account:list') or hasAuthority('permission:all')")
    @GetMapping("/finance-repay/selectAccountList")
    public JsonResult selectAccountList(@RequestParam Map<String, Object> params) {
        PageInfo<FinanceAccountManagerModel> financeRepayModelPageInfo = repayService.selectAccountList(params);
        return JsonResult.ok(financeRepayModelPageInfo.getList(), (int) financeRepayModelPageInfo.getTotal());
    }

    /**
     * 查询所有的账户名
     *
     * @return 账户列表
     * @Author: zhujingtao
     */
    @PreAuthorize("hasAuthority('account:list') or hasAuthority('permission:all')")
    @GetMapping("/finance-repay/selectAllAccountName")
    public JsonResult selectAllAccountName() {
        return JsonResult.ok(repayService.selectAllAccountName());
    }


    /**
     * 展期列表查询
     * @param params
     * @return
     * @Author zhujingtao
     */
    @GetMapping("/finance-repay/queryFinanceExtensionList")
    public JsonResult queryFinanceExtensionList(@RequestParam Map<String, Object> params){
        //获取前端传入 分页信息
        Integer page = MapUtils.getInteger(params, "page");
        Integer pageSize = MapUtils.getInteger(params, "limit");
        PageHelper.startPage(page, pageSize);
        PageInfo<FinanceRepayModel> financeRepayModelPageInfo = new PageInfo<>(repayService.queryFinanceExtensionList(params));
        return JsonResult.ok(financeRepayModelPageInfo.getList(), (int) financeRepayModelPageInfo.getTotal());
    }
}
