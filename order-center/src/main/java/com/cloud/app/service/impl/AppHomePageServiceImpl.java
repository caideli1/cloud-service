package com.cloud.app.service.impl;

import com.cloud.app.service.AppHomePageService;
import com.cloud.model.appProduct.PlatformStartPageModel;
import com.cloud.model.user.UserDataStatisModel;
import com.cloud.order.dao.AppVersionDao;
import com.cloud.order.dao.ProductDao;
import com.cloud.order.dao.UserDataDao;
import com.cloud.order.model.AppVersionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class AppHomePageServiceImpl implements AppHomePageService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDataDao userDataDao;
    @Autowired
    private AppVersionDao appVersionDao;

    /**
     * 启动图展示
     *
     * @return
     * @Author: wza
     */
    @Override
    public PlatformStartPageModel getStartPage() {
        PlatformStartPageModel pageModel = productDao.getStartPage();
        return pageModel;
    }

    /**
     * 保存启动页机器码
     *
     * @param model
     */
    @Override
    public long saveUserData(UserDataStatisModel model) {
        log.info("save userDate start --- {}", model.getStartImi());
        int result = userDataDao.saveUserData(model);
        log.info("save userDate end --- {}", model.getStartImi());
        if (result > 0) {
            log.info("success");
            return model.getId();
        }
        return result;
    }

    @Override
    public int createAppVersion(String appversion) {
        AppVersionModel model = new AppVersionModel();
        model.setAppversion(appversion);
        model.setOnlinestatus("1");
        model.setCreateTime(new Date());
        return appVersionDao.insert(model);
    }

    @Override
    public int updateAppVersion(int id, String appversion) {
        AppVersionModel model = new AppVersionModel();
        model.setId(id);
        model.setAppversion(appversion);
        model.setUpdateTime(new Date());
        return productDao.updateAppVersion(model);
    }

    @Override
    public AppVersionModel queryAppVersion() {
        return productDao.queryAppVersion();
    }
}
