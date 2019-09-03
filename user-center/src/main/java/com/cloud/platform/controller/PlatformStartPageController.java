package com.cloud.platform.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.platform.model.PlatformStartPageModel;
import com.cloud.platform.service.PlatformStartPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 启动页配置接口/appversion版本号
 *
 * @author bjy
 * @date 2019/3/8 0008 17:20
 */
@Slf4j
@RestController
public class PlatformStartPageController {

    @Autowired
    private PlatformStartPageService startPageService;

    /**
     * 创建启动页
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('operation:startPage') or hasAuthority('permission:all')")
    @PostMapping(value = "/users-anon/platform/createPlatformStartPage")
    public JsonResult createPlatformStartPage(@RequestBody PlatformStartPageModel model) {
        return startPageService.createPlatformStartPage(model);
    }

    /**
     * 修改启动页
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('operation:startPage') or hasAuthority('permission:all')")
    @PostMapping(value = "/users-anon/platform/updatePlatformStartPage")
    public JsonResult updatePlatformStartPage(@RequestBody PlatformStartPageModel model) {
        return startPageService.updatePlatformStartPage(model);
    }

    /**
     * 获取启动页
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('operation:startPage') or hasAuthority('permission:all')")
    @GetMapping("/users-anon/platform/getPlatformStartPage")
    public JsonResult getPlatformStartPage(@RequestParam Map<String, Object> params) {
        return startPageService.getPlatformStartPage(params);
    }

    /**
     * 启动页上线或者下线
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('operation:startPage') or hasAuthority('permission:all')")
    @PostMapping("/users-anon/platform/upOrDownStartPage")
    public JsonResult upOrDownStartPage(@RequestBody Map<String, Object> params) {
        return startPageService.upOrDownStartPage(params);
    }

    /**
     * 删除启动页
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('operation:startPage') or hasAuthority('permission:all')")
    @PostMapping("/users-anon/platform/deleteStartPage")
    public JsonResult deleteStartPage(@RequestBody Map<String, Object> params) {
        return startPageService.deleteStartPage(params);

    }

}
