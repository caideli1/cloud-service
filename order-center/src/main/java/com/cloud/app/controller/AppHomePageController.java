package com.cloud.app.controller;


import com.cloud.app.service.AppHomePageService;
import com.cloud.common.dto.JsonResult;
import com.cloud.common.enums.CommonAppTipEnum;
import com.cloud.common.exception.BusinessException;
import com.cloud.model.appProduct.PlatformStartPageModel;
import com.cloud.model.appProduct.ProductInfo;
import com.cloud.model.user.UserDataStatisModel;
import com.cloud.order.model.AppVersionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: wza
 * @CreateDate: 2019/1/25 18:05
 * @Version: 1.0
 */

@Slf4j
@RestController
public class AppHomePageController {
    @Autowired
    private AppHomePageService appHomePageService;

    @GetMapping("/orders-anon/products")
    public JsonResult showProduct(HttpServletRequest request) {
        throw new BusinessException(CommonAppTipEnum.NEED_UPDATE_APP);
    }

    /**
     *
    @GetMapping("/orders-anon/products")
    public JsonResult showProduct(HttpServletRequest request) {
        String startImi = request.getHeader("imi");
        String channel = request.getHeader("channel");
        List<ProductInfo> productInfos = appHomePageService.getProductList();
        UserDataStatisModel model = new UserDataStatisModel();
        model.setStartImi(startImi);
        model.setChannel(channel);

        UserDataStatisModel statisModel = appHomePageService.findUserDataByImi(startImi);
        if (null == statisModel) {
            synchronized (this) {
                if (null == statisModel) {
                    appHomePageService.saveUserData(model);
                }
            }
        }
        return JsonResult.ok(productInfos);
    }
    */

    /**
     * 启动图展示
     *
     * @return
     * @Author: wza
     */
    @GetMapping("/orders-anon/startupic")
    public JsonResult showStartPage(HttpServletRequest servletRequest) {
        PlatformStartPageModel pageModel = appHomePageService.getStartPage();
        UserDataStatisModel userDataStatisModel =UserDataStatisModel.builder()
                .startImi(servletRequest.getHeader("imi"))
                .channel(servletRequest.getHeader("channel"))
        .build();
        appHomePageService.saveUserData(userDataStatisModel);
        log.info("save userDataStatisModel {}", userDataStatisModel);
        return JsonResult.ok(pageModel);
    }

    /**
     * 创建版本号
     *
     * @param appversion
     * @return
     */
//    @PreAuthorize("hasAuthority('operation:startPage') or hasAuthority('permission:all')")
    @PostMapping(value = "/orders-anon/createAppVersion")
    public JsonResult createAppVersion(@RequestParam String appversion) {
        int model = appHomePageService.createAppVersion(appversion);
        if (model > 0) {
            return JsonResult.ok();
        }
        return JsonResult.errorException("failure:新增版本号失败");
    }

    /**
     * 修改版本号
     *
     * @param id
     * @return
     */
//    @PreAuthorize("hasAuthority('operation:startPage') or hasAuthority('permission:all')")
    @PostMapping(value = "/orders-anon/updateAppVersion")
    public JsonResult updateAppVersion(@RequestParam int id, @RequestParam String appversion) {
        int model = appHomePageService.updateAppVersion(id, appversion);
        if (model > 0) {
            return JsonResult.ok();
        }
        return JsonResult.errorException("failure:修改版本号失败");
    }

    /**
     * 获取版本号
     *
     * @return
     */
//    @PreAuthorize("hasAuthority('operation:startPage') or hasAuthority('permission:all')")
    @GetMapping("/orders-anon/queryAppVersion")
    public JsonResult queryAppVersion() {
        AppVersionModel versionModel = new AppVersionModel();
        AppVersionModel model = appHomePageService.queryAppVersion();
        versionModel.setAppversion(model.getAppversion());
        versionModel.setId(model.getId());
        return JsonResult.ok(versionModel);
    }
}
