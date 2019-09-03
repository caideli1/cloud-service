package com.cloud.platform.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.platform.model.PlatformChannelModel;
import com.cloud.platform.service.PlatformChannelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 渠道控制器
 *
 * @author bjy
 * @date 2019/3/12 0012 9:40
 */
@Slf4j
@RestController
@RequestMapping(value = "/users-anon/platform")
public class PlatformChannelController {

    @Autowired
    private PlatformChannelService channelService;

    /**
     * 查询推广渠道列表
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:promotion') or hasAuthority('permission:all')")
    @GetMapping(value = "/getPlatformChannel")
    public JsonResult getPlatformChannel(@RequestParam Map<String, Object> params) {
        return channelService.getPlatformChannel(params);
    }

    /**
     * 新建推广渠道
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:promotion') or hasAuthority('permission:all')")
    @PostMapping(value = "/createPlatformChannel")
    public JsonResult createPlatformChannel(@RequestBody PlatformChannelModel model) {
        return channelService.createPlatformChannel(model);
    }

    /**
     * 修改推广渠道
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:promotion') or hasAuthority('permission:all')")
    @PostMapping(value = "/modifyPlatformChannel")
    public JsonResult modifyPlatformChannel(@RequestBody PlatformChannelModel model) {
        return channelService.modifyPlatformChannel(model);
    }

    /**
     * 删除平台渠道
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:promotion') or hasAuthority('permission:all')")
    @PostMapping("/deletePlatformChannel")
    public JsonResult deletePlatformChannel(@RequestBody Map<String, Object> params) {
        String ids = MapUtils.getString(params, "ids");
        return channelService.deletePlatformChannel(ids);
    }

    /**
     * 获取平台渠道统计信息
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:promotion') or hasAuthority('permission:all')")
    @GetMapping("/getPlatformCount")
    public JsonResult getPlatformCount(@RequestParam Map<String, Object> params) {
        return channelService.getPlatformCount(params);
    }

    /**
     * 上线或者下线渠道
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:promotion') or hasAuthority('permission:all')")
    @PostMapping("/upOrDownChannel")
    public JsonResult upOrDownChannel(@RequestBody Map<String, Object> params) {
        return channelService.upOrDownChannel(params);
    }
}
