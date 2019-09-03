package com.cloud.app.service;

import com.cloud.model.appProduct.PlatformStartPageModel;
import com.cloud.model.user.UserDataStatisModel;
import com.cloud.order.model.AppVersionModel;

public interface AppHomePageService {
    PlatformStartPageModel getStartPage();

    long saveUserData(UserDataStatisModel model);

    /**
     * 创建版本号
     *
     * @param -appversion
     * @return
     */
    int createAppVersion(String appversion);

    /**
     * 修改版本号
     *
     * @param params
     * @return
     */
    int updateAppVersion(int id, String appversion);

    /**
     * 获取版本号
     *
     * @param params
     * @return
     */
    AppVersionModel queryAppVersion();
}
