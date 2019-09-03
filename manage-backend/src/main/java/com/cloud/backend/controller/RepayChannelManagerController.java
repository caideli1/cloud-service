package com.cloud.backend.controller;

import com.cloud.backend.model.RepayChannelManager;
import com.cloud.backend.service.RepayChannelManagerService;
import com.cloud.common.dto.JsonResult;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.manage.vo.RepayChannelManagerVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author : caideli
 * @Email : 1595252552@qq.com
 * @Date : 2019/7/25 14:13
 * 描述：
 */
@Api(description = " 还款通道管理")
@RestController
public class RepayChannelManagerController {

    @Autowired
    private RepayChannelManagerService repayChannelManagerService;

    @LogAnnotation(module = "获取还款通道列表")
//    @PreAuthorize("hasAuthority('repayChannelManager:query') or hasAuthority('permission:all')")
    @GetMapping("/repayChannelManager/getPage")
    public JsonResult getPage(
            @ApiParam(name = "pageNum", value = "第几页") @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @ApiParam(name = "pageSize", value = "分页大小") @RequestParam(value = "limit", required = false, defaultValue = "10") int limit,
            @ApiParam(name = "status", value = "启用状态") @RequestParam(value = "status", required = false) Integer status) {
        Example example = new Example(RepayChannelManager.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status",status);
        PageInfo<RepayChannelManagerVo> pageInfo = repayChannelManagerService.getPage(example, page, limit);
        return JsonResult.ok(pageInfo.getList(), Integer.valueOf(String.valueOf(pageInfo.getTotal())));
    }

    @LogAnnotation(module = "添加还款通道")
//    @PreAuthorize("hasAuthority('repayChannelManager:save') or hasAuthority('permission:all')")
    @PostMapping("/repayChannelManager/save")
    public JsonResult save(@RequestBody RepayChannelManagerVo repayChannelManagerVo) {
        repayChannelManagerVo.setStatus(false);
        return JsonResult.ok(repayChannelManagerService.insert(repayChannelManagerVo));
    }

    @LogAnnotation(module = "修改还款通道")
//    @PreAuthorize("hasAuthority('repayChannelManager:update') or hasAuthority('permission:all')")
    @PostMapping("/repayChannelManager/update")
    public JsonResult update(@RequestBody RepayChannelManagerVo repayChannelManagerVo) {
        if (repayChannelManagerVo.getId() == null || repayChannelManagerVo.getId() <= 0) {
            return JsonResult.errorMsg("参数格式异常，id=" + repayChannelManagerVo.getId());
        }
        repayChannelManagerVo.setUpdateTime(new Date());
        return JsonResult.ok(repayChannelManagerService.update(repayChannelManagerVo));
    }

    @LogAnnotation(module = "禁用或者激活还款通道")
//    @PreAuthorize("hasAuthority('repayChannelManager:disable') or hasAuthority('permission:all')")
    @PostMapping("/repayChannelManager/enableOrDisable")
    public JsonResult enableOrDisable(@RequestBody RepayChannelManagerVo repayChannelManagerVo) {
        if (repayChannelManagerVo.getId() == null || repayChannelManagerVo.getId() <= 0) {
            return JsonResult.errorMsg("参数格式异常，id=" + repayChannelManagerVo.getId());
        }
        repayChannelManagerVo.setUpdateTime(new Date());
        return JsonResult.ok(repayChannelManagerService.update(repayChannelManagerVo));
    }

    @LogAnnotation(module = "根据名称查找还款渠道")
    @GetMapping("/repayChannelManager/getRepayChannelByName")
    public RepayChannelManagerVo getRepayChannelByName( @RequestParam(value = "name") String name) {
        Example example = new Example(RepayChannelManager.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", name);

        return repayChannelManagerService.findOne(example);
    }
}
