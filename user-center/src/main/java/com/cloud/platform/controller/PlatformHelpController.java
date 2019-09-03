package com.cloud.platform.controller;

import com.cloud.common.dto.JsonResult;
import com.cloud.platform.model.PlatformAnswerModelModel;
import com.cloud.platform.model.PlatformProcessedFeedbackModel;
import com.cloud.platform.service.PlatformHelpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 帮助中心
 *
 * @author bjy
 * @date 2019/3/8 0008 17:51
 */
@Slf4j
@RestController
public class PlatformHelpController {

    @Autowired
    private PlatformHelpService helpService;

    /**
     * 查询平台问答信息
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:qa') or hasAuthority('permission:all')")
    @GetMapping(value = "/users-anon/platform/getPlatformAnswer")
    public JsonResult getPlatformAnswer(@RequestParam Map<String, Object> params) {
        return helpService.getPlatformAnswer(params);

    }

    /**
     * 新建平台问答信息
     *
     * @param modelModel
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:qa') or hasAuthority('permission:all')")
    @PostMapping(value = "/users-anon/platform/createPlatformAnswer")
    public JsonResult createPlatformAnswer(@RequestBody PlatformAnswerModelModel modelModel) {
        return helpService.createPlatformAnswer(modelModel);
    }

    /**
     * 删除平台问答信息
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:qa') or hasAuthority('permission:all')")
    @PostMapping(value = "/users-anon/platform/deletePlatformAnswer")
    public JsonResult deletePlatformAnswer(@RequestBody Map<String, Object> params) {
        String id = MapUtils.getString(params, "ids");
        return helpService.deletePlatformAnswer(id);
    }

    /**
     * 修改平台问答信息
     *
     * @param modelModel
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:qa') or hasAuthority('permission:all')")
    @PostMapping(value = "/users-anon/platform/updatePlatformAnswer")
    public JsonResult updatePlatformAnswer(@RequestBody PlatformAnswerModelModel modelModel) {
        return helpService.updatePlatformAnswer(modelModel);
    }

    /**
     * 查询用户反馈
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:feedback') or hasAuthority('permission:all')")
    @GetMapping(value = "/users-anon/platform/getPlatformUserFeedback")
    public JsonResult getPlatformUserFeedback(@RequestParam Map<String, Object> params) {
        return helpService.getPlatformUserFeedback(params);
    }


    /**
     * 创建邮件回复
     *
     * @param params
     * @return
     */
//    @PreAuthorize("hasAuthority('opertion:qa') or hasAuthority('permission:all')")
    @PostMapping(value = "/users-anon/platform/createMail")
    public JsonResult createMail(@RequestBody Map<String, Object> params) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        params.put("processUser", name);

        return helpService.createMail(params);
    }

    /**
     * 创建人工处理结果
     *
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:qa') or hasAuthority('permission:all')")
    @PostMapping(value = "/users-anon/platform/createPlatformProcessedFeedback")
    public JsonResult createPlatformProcessedFeedback(@RequestBody PlatformProcessedFeedbackModel model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        model.setProcessUser(name);
        return helpService.createPlatformProcessedFeedback(model);
    }

    /**
     * 查询处理记录
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:qa') or hasAuthority('permission:all')")
    @GetMapping(value = "/users-anon/platform/getPlatformProcessedFeedback")
    public JsonResult getPlatformProcessedFeedback(@RequestParam Map<String, Object> params) {
        String id = MapUtils.getString(params, "id");
        List<PlatformProcessedFeedbackModel> modelList = helpService.getPlatformProcessedFeedback(id);
        return JsonResult.ok(modelList);
    }

    /**
     * 标记处理操作
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('opertion:qa') or hasAuthority('permission:all')")
    @PostMapping(value = "/users-anon/platform/markPlatformUserFeedback")
    public JsonResult markPlatformUserFeedback(@RequestBody Map<String, Object> params) {
        String id = MapUtils.getString(params, "id");
        return helpService.markPlatformUserFeedback(id);
    }
}
