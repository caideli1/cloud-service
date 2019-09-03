package com.cloud.platform.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.platform.model.PlatformContractModel;
import com.cloud.platform.service.ContractManagerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 合同管理控制器
 *
 * @author bjy
 * @date 2019/3/13 0013 11:41
 */
@Slf4j
@RestController
@RequestMapping("/users-anon/platform")
public class ContractManagerController {

    @Autowired
    private ContractManagerService contractManagerService;

    /**
     * 获取合同模板列表
     *
     * @param params
     * @return
     */
    @GetMapping("/getContract")
    public JsonResult getContract(@RequestParam Map<String, Object> params) {
        return contractManagerService.getContract(params);
    }

    /**
     * 增加合同模板
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:contract') or hasAuthority('permission:all')")
    @PostMapping("/addContract")
    public JsonResult addContract(@RequestBody PlatformContractModel model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        model.setProcessUser(name);

        return contractManagerService.addContract(model);
    }

    /**
     * 修改合同模板
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:contract') or hasAuthority('permission:all')")
    @PostMapping("/modifyContract")
    public JsonResult modifyContract(@RequestBody PlatformContractModel model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        model.setProcessUser(name);

        return contractManagerService.modifyContract(model);
    }

    /**
     * 删除合同模板
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:contract') or hasAuthority('permission:all')")
    @PostMapping("/deleteContract")
    public JsonResult deleteContract(@RequestBody Map<String, Object> params) {
        String ids = MapUtils.getString(params, "ids");
        return contractManagerService.deleteContract(ids);

    }

    /**
     * 获取合同模板标签信息
     *
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:contract') or hasAuthority('permission:all')")
    @GetMapping("/getContractTags")
    public JsonResult getContractTags() {
        return contractManagerService.getContractTags();
    }
}
